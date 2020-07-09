/*
 * Copyright © 2018 The GWT Project Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gwtproject.editor.processor;

import static java.util.stream.Collectors.toSet;

import com.google.auto.common.MoreTypes;
import com.google.auto.service.AutoService;
import com.google.common.base.Stopwatch;
import com.squareup.javapoet.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Stream;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import org.gwtproject.editor.client.Editor;
import org.gwtproject.editor.client.EditorVisitor;
import org.gwtproject.editor.client.SimpleBeanEditorDriver;
import org.gwtproject.editor.client.annotation.IsDriver;
import org.gwtproject.editor.client.impl.AbstractEditorContext;
import org.gwtproject.editor.client.impl.AbstractSimpleBeanEditorDriver;
import org.gwtproject.editor.client.impl.RootEditorContext;
import org.gwtproject.editor.client.impl.SimpleBeanEditorDelegate;
import org.gwtproject.editor.processor.model.EditorModel;
import org.gwtproject.editor.processor.model.EditorProperty;
import org.gwtproject.editor.processor.model.EditorTypes;

@AutoService(Processor.class)
public class DriverProcessor extends AbstractProcessor {

  private Messager messager;
  private Filer filer;
  private Types types;
  private Elements elements;
  private Stopwatch stopwatch;
  private List<String> generatedDelegates;

  public DriverProcessor() {
    super();
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return Stream.of(IsDriver.class.getCanonicalName()).collect(toSet());
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);

    this.messager = processingEnv.getMessager();
    this.filer = processingEnv.getFiler();
    this.types = processingEnv.getTypeUtils();
    this.elements = processingEnv.getElementUtils();

    this.createMessage(
        Diagnostic.Kind.NOTE, "GWT-Editor-Processor (version: HEAD-SNAPSHOT) started ...");

    this.stopwatch = Stopwatch.createStarted();

    this.generatedDelegates = new ArrayList<>();

    setUp();
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if (roundEnv.processingOver()) {
      this.createMessage(
          Diagnostic.Kind.NOTE,
          "GWT-editor-Processor finished ... processing takes: "
              + this.stopwatch.stop().toString());
    } else {
      if (annotations.size() > 0) {
        for (TypeElement annotation : annotations) {
          if (IsDriver.class.getCanonicalName().equals(annotation.toString())) {
            for (Element element : roundEnv.getElementsAnnotatedWith(IsDriver.class)) {
              EditorModel rootEditorModel =
                  new EditorModel(
                      this.messager,
                      new EditorTypes(this.types, this.elements),
                      element.asType(),
                      this.types.erasure(
                          this.elements
                              .getTypeElement(getDriverInterfaceType().toString())
                              .asType()));
              generate((TypeElement) element, rootEditorModel);
            }
          }
        }
      }
    }
    return true;
  }

  private void createMessage(Diagnostic.Kind kind, String message) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    pw.println(message);
    pw.close();
    this.messager.printMessage(kind, sw.toString());
  }

  private void setUp() {}

  protected ClassName getDriverInterfaceType() {
    return ClassName.get(SimpleBeanEditorDriver.class);
  }

  protected ClassName getDriverSuperclassType() {
    return ClassName.get(AbstractSimpleBeanEditorDriver.class);
  }

  protected ClassName getEditorDelegateType() {
    return ClassName.get(SimpleBeanEditorDelegate.class);
  }

  private void generate(TypeElement interfaceToImplement, EditorModel rootEditorModel) {
    // start driver
    String pkgName = this.elements.getPackageOf(interfaceToImplement).getQualifiedName().toString();
    String typeName = createNameFromEnclosedTypes(interfaceToImplement, "_Impl");

    // impl accept(visitor) method
    ParameterizedTypeName rootEdContextType =
        ParameterizedTypeName.get(
            ClassName.get(RootEditorContext.class), TypeName.get(rootEditorModel.getProxyType()));
    MethodSpec accept =
        MethodSpec.methodBuilder("accept")
            .addModifiers(Modifier.PUBLIC)
            .returns(void.class)
            .addAnnotation(Override.class)
            .addParameter(EditorVisitor.class, "visitor")
            // ugly cast to shut up java warnings at compile time - however, this might be overkill,
            // could just use raw types
            .addStatement(
                "$T ctx = new $T(getDelegate(), (Class<$T>)(Class)$L.class, getObject())",
                rootEdContextType,
                rootEdContextType,
                TypeName.get(rootEditorModel.getProxyType()),
                MoreTypes.asElement(rootEditorModel.getProxyType()))
            .addStatement("ctx.traverse(visitor, getDelegate())")
            .build();

    // impl createDelegate() method
    // - lazily building the delegate type if require (this is recursive), see
    // com.google.gwt.editor.rebind.AbstractEditorDriverGenerator.getEditorDelegate()
    // - build context
    // - break out various impl methods to allow custom EditorDriver subtypes like RFED
    ParameterizedTypeName delegateType =
        ParameterizedTypeName.get(
            getEditorDelegateType(),
            TypeName.get(rootEditorModel.getProxyType()),
            TypeName.get(rootEditorModel.getEditorType()));
    MethodSpec createDelegate =
        MethodSpec.methodBuilder("createDelegate")
            .addModifiers(Modifier.PROTECTED)
            .returns(delegateType)
            .addAnnotation(Override.class)
            .addStatement(
                "return new $T()",
                getEditorDelegate(rootEditorModel, rootEditorModel.getRootData()))
            .build();

    // implement interface, extend BaseEditorDriver or whatnot
    TypeSpec driverType =
        TypeSpec.classBuilder(typeName)
            .addOriginatingElement(interfaceToImplement)
            .addModifiers(Modifier.PUBLIC)
            // Once GWT supports the new package of the Generated class
            // we can uncomment this code
            //            .addAnnotation(
            //                AnnotationSpec.builder(getGeneratedClassName())
            //                    .addMember("value", "\"$L\"",
            // DriverProcessor.class.getCanonicalName())
            //                    .build())
            .addSuperinterface(TypeName.get(interfaceToImplement.asType()))
            .superclass(
                ParameterizedTypeName.get(
                    getDriverSuperclassType(),
                    TypeName.get(rootEditorModel.getProxyType()),
                    TypeName.get(rootEditorModel.getEditorType())))
            .addMethod(accept)
            .addMethod(createDelegate)
            .build();

    JavaFile driverFile = JavaFile.builder(pkgName, driverType).build();

    try {
      driverFile.writeTo(this.filer);
    } catch (IOException e) {
      this.createMessage(
          Diagnostic.Kind.WARNING,
          "Exception: type >>"
              + rootEditorModel.getEditorType().toString()
              + " << - trying to write: >>"
              + driverFile.packageName
              + "."
              + driverType
              + "<< -> message >>"
              + e.getMessage()
              + "<< multiple times");
    }
  }

  /**
   * Joins the name of the type with any enclosing types, with "_" as the delimeter, and appends an
   * optional suffix.
   */
  private String createNameFromEnclosedTypes(TypeElement interfaceToImplement, String suffix) {
    StringJoiner joiner = new StringJoiner("_", "", suffix == null ? "" : suffix);
    ClassName.get(interfaceToImplement).simpleNames().forEach(joiner::add);
    return joiner.toString();
  }

  private ClassName getEditorDelegate(EditorModel editorModel, EditorProperty data) {
    String delegateSimpleName =
        escapedMaybeParameterizedBinaryName(data.getEditorType())
            + "_"
            + getEditorDelegateType().simpleName();
    String packageName =
        this.elements
            .getPackageOf(types.asElement(data.getEditorType()))
            .getQualifiedName()
            .toString();

    String delegateClassName = packageName + "." + delegateSimpleName;
    // check, if delegate is already generatd ...
    if (this.generatedDelegates.contains(delegateClassName)) {
      // alreday generated ... nothing to do
      return ClassName.get(packageName, delegateSimpleName);
    }
    this.generatedDelegates.add(delegateClassName);

    TypeName rawEditorType = ClassName.get(types.erasure(data.getEditorType()));

    TypeSpec.Builder delegateTypeBuilder =
        TypeSpec.classBuilder(delegateSimpleName)
            .addOriginatingElement(types.asElement(data.getEditedType()))
            .addOriginatingElement(types.asElement(data.getEditorType()))
            .addModifiers(Modifier.PUBLIC)
            // Once GWT supports the new package of the Generated class
            // we can uncomment this code
            //              .addAnnotation(
            //                  AnnotationSpec.builder(getGeneratedClassName())
            //                      .addMember("value", "\"$L\"",
            // DriverProcessor.class.getCanonicalName())
            //                      .build())
            .superclass(getEditorDelegateType()); // raw type here, for the same reason as above

    NameFactory names = new NameFactory();
    Map<EditorProperty, String> delegateFields = new IdentityHashMap<>();

    delegateTypeBuilder.addField(
        FieldSpec.builder(rawEditorType, "editor", Modifier.PRIVATE).build());
    names.addName("editor");
    delegateTypeBuilder.addField(
        FieldSpec.builder(ClassName.get(data.getEditedType()), "object", Modifier.PRIVATE).build());
    names.addName("object");

    // Fields for the sub-delegates that must be managed
    for (EditorProperty d : editorModel.getEditorData(data.getEditorType())) {
      if (d.isDelegateRequired()) {
        String fieldName = names.createName(d.getPropertyName() + "Delegate");
        delegateFields.put(d, fieldName);
        delegateTypeBuilder.addField(
            getEditorDelegateType(), fieldName, Modifier.PRIVATE); // TODO parameterize
      }
    }

    delegateTypeBuilder.addMethod(
        MethodSpec.methodBuilder("getEditor")
            .addModifiers(Modifier.PROTECTED)
            .returns(rawEditorType)
            .addAnnotation(Override.class)
            .addStatement("return editor")
            .build());

    delegateTypeBuilder.addMethod(
        MethodSpec.methodBuilder("setEditor")
            .addModifiers(Modifier.PROTECTED)
            .returns(void.class)
            .addAnnotation(Override.class)
            .addParameter(Editor.class, "editor")
            .addStatement("this.editor = ($T) editor", rawEditorType)
            .build());

    delegateTypeBuilder.addMethod(
        MethodSpec.methodBuilder("getObject")
            .addModifiers(Modifier.PUBLIC)
            .returns(ClassName.get(data.getEditedType()))
            .addAnnotation(Override.class)
            .addStatement("return object")
            .build());

    delegateTypeBuilder.addMethod(
        MethodSpec.methodBuilder("setObject")
            .addModifiers(Modifier.PROTECTED)
            .returns(void.class)
            .addAnnotation(Override.class)
            .addParameter(ClassName.get(Object.class), "object")
            .addStatement("this.object = ($T) object", ClassName.get(data.getEditedType()))
            .build());

    MethodSpec.Builder initializeSubDelegatesBuilder =
        MethodSpec.methodBuilder("initializeSubDelegates")
            .addModifiers(Modifier.PROTECTED)
            .returns(void.class)
            .addAnnotation(Override.class);
    if (data.isCompositeEditor()) {
      initializeSubDelegatesBuilder.addStatement(
          "createChain($L.class)", MoreTypes.asElement(data.getComposedData().getEditedType()));
    }
    for (EditorProperty d : editorModel.getEditorData(data.getEditorType())) {
      ClassName subDelegateType = getEditorDelegate(editorModel, d);
      if (d.isDelegateRequired()) {
        initializeSubDelegatesBuilder
            .beginControlFlow("if (editor.$L != null)", d.getSimpleExpression())
            .addStatement("$L = new $T()", delegateFields.get(d), subDelegateType)
            .addStatement(
                "addSubDelegate($L, appendPath(\"$L\"), editor.$L)",
                delegateFields.get(d),
                d.getDeclaredPath(),
                d.getSimpleExpression())
            .endControlFlow();
      }
    }
    delegateTypeBuilder.addMethod(initializeSubDelegatesBuilder.build());

    MethodSpec.Builder acceptBuilder =
        MethodSpec.methodBuilder("accept")
            .addModifiers(Modifier.PUBLIC)
            .returns(void.class)
            .addAnnotation(Override.class)
            .addParameter(EditorVisitor.class, "visitor");
    if (data.isCompositeEditor()) {
      acceptBuilder.addStatement("getEditorChain().accept(visitor)");
    }
    for (EditorProperty d : editorModel.getEditorData(data.getEditorType())) {
      if (d.isDelegateRequired()) {
        acceptBuilder.beginControlFlow("if ($L != null)", delegateFields.get(d));
      } else {
        acceptBuilder.beginControlFlow("");
      }
      ClassName editorContextName = getEditorContext(data, d);
      acceptBuilder.addStatement(
          "$T ctx = new $T(getObject(), editor.$L, appendPath(\"$L\"))",
          editorContextName,
          editorContextName,
          d.getSimpleExpression(),
          d.getDeclaredPath());
      if (d.isDelegateRequired()) {
        acceptBuilder.addStatement("ctx.setEditorDelegate($L)", delegateFields.get(d));
        acceptBuilder.addStatement("ctx.traverse(visitor, $L)", delegateFields.get(d));
      } else {
        acceptBuilder.addStatement("ctx.traverse(visitor, null)");
      }
      acceptBuilder.endControlFlow();
    }

    delegateTypeBuilder.addMethod(acceptBuilder.build());

    if (data.isCompositeEditor()) {
      ClassName compositeEditorDelegateType =
          getEditorDelegate(editorModel, data.getComposedData());
      delegateTypeBuilder.addMethod(
          MethodSpec.methodBuilder("createComposedDelegate")
              .addModifiers(Modifier.PROTECTED)
              .returns(compositeEditorDelegateType)
              .addAnnotation(Override.class)
              .addStatement("return new $T()", compositeEditorDelegateType)
              .build());
    }

    JavaFile delegateFile = JavaFile.builder(packageName, delegateTypeBuilder.build()).build();
    try {
      delegateFile.writeTo(filer);
    } catch (IOException e) {
      this.createMessage(
          Diagnostic.Kind.WARNING,
          "Exception: type >>"
              + editorModel.getEditorType().toString()
              + " << - trying to write: >>"
              + delegateFile.packageName
              + "."
              + delegateSimpleName
              + "<< -> message >>"
              + e.getMessage()
              + "<< multiple times");
    }

    return ClassName.get(packageName, delegateSimpleName);
  }

  private String escapedMaybeParameterizedBinaryName(TypeMirror editor) {
    /*
     * The parameterization of the editor type is included to ensure that a
     * correct specialization of a CompositeEditor will be generated. For
     * example, a ListEditor<Person, APersonEditor> would need a different
     * delegate from a ListEditor<Person, AnotherPersonEditor>.
     */
    StringBuilder maybeParameterizedName =
        new StringBuilder(createNameFromEnclosedTypes(MoreTypes.asTypeElement(editor), null));

    // recursive departure from gwt, in case we have ListEditor<Generic<Foo>, GenericEditor<Foo>>,
    // etc
    for (TypeMirror typeParameterElement : MoreTypes.asDeclared(editor).getTypeArguments()) {
      maybeParameterizedName
          .append("$")
          .append(escapedMaybeParameterizedBinaryName(typeParameterElement));
    }
    return escapedBinaryName(maybeParameterizedName.toString());
  }

  private String escapedBinaryName(String binaryName) {
    return binaryName.replace("_", "_1").replace('$', '_').replace('.', '_');
  }

  private ClassName getEditorContext(EditorProperty parent, EditorProperty data) {
    String contextSimpleName =
        escapedMaybeParameterizedBinaryName(parent.getEditorType())
            + "_"
            + data.getDeclaredPath().replace("_", "_1").replace(".", "_")
            + "_Context";
    String packageName =
        elements
            .getPackageOf(types.asElement(parent.getEditorType()))
            .getQualifiedName()
            .toString();

    //    try {
    TypeSpec.Builder contextTypeBuilder =
        TypeSpec.classBuilder(contextSimpleName)
            .addOriginatingElement(types.asElement(parent.getEditorType())) // editor type
            .addOriginatingElement(types.asElement(parent.getEditedType())) // bean
            .addOriginatingElement(types.asElement(data.getEditedType())) // child
            .addModifiers(Modifier.PUBLIC)
            // Once GWT supports the new package of the Generated class
            // we can uncomment this code
            //                  .addAnnotation(
            //                  AnnotationSpec.builder(getGeneratedClassName())
            //                      .addMember("value", "\"$L\"",
            // DriverProcessor.class.getCanonicalName())
            //                      .build())
            .superclass(
                ParameterizedTypeName.get(
                    ClassName.get(AbstractEditorContext.class),
                    ClassName.get(data.getEditedType())));

    contextTypeBuilder.addField(
        ClassName.get(parent.getEditedType()), "parent", Modifier.PRIVATE, Modifier.FINAL);

    contextTypeBuilder.addMethod(
        MethodSpec.constructorBuilder()
            .addModifiers(Modifier.PUBLIC)
            .addParameter(ClassName.get(parent.getEditedType()), "parent")
            .addParameter(
                ParameterizedTypeName.get(
                    ClassName.get(Editor.class), ClassName.get(data.getEditedType())),
                "editor")
            .addParameter(String.class, "path")
            .addStatement("super(editor, path)")
            .addStatement("this.parent = parent")
            .build());

    contextTypeBuilder.addMethod(
        MethodSpec.methodBuilder("canSetInModel")
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(Override.class)
            .returns(boolean.class)
            .addStatement(
                "return parent != null && $L && $L",
                data.getSetterName() == null ? "false" : "true",
                data.getBeanOwnerGuard("parent"))
            .build());

    contextTypeBuilder.addMethod(
        MethodSpec.methodBuilder("checkAssignment")
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(Override.class)
            .returns(ClassName.get(data.getEditedType()))
            .addParameter(Object.class, "value")
            .addStatement("return ($T) value", ClassName.get(data.getEditedType()))
            .build());

    contextTypeBuilder.addMethod(
        MethodSpec.methodBuilder("getEditedType")
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(Override.class)
            .returns(Class.class)
            .addStatement("return $L.class", MoreTypes.asElement(data.getEditedType()))
            .build());

    contextTypeBuilder.addMethod(
        MethodSpec.methodBuilder("getFromModel")
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(Override.class)
            .returns(ClassName.get(data.getEditedType()))
            .addStatement(
                "return (parent != null && $L) ? parent$L$L : null",
                data.getBeanOwnerGuard("parent"),
                data.getBeanOwnerExpression(),
                data.getGetterExpression())
            .build());

    MethodSpec.Builder setInModelMethodBuilder =
        MethodSpec.methodBuilder("setInModel")
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(Override.class)
            .returns(void.class)
            .addParameter(ClassName.get(data.getEditedType()), "data");
    if (data.getSetterName() == null) {
      setInModelMethodBuilder.addStatement("throw new UnsupportedOperationException()");
    } else {
      setInModelMethodBuilder.addStatement(
          "parent$L.$L(data)", data.getBeanOwnerExpression(), data.getSetterName());
    }
    contextTypeBuilder.addMethod(setInModelMethodBuilder.build());

    JavaFile contextFile = JavaFile.builder(packageName, contextTypeBuilder.build()).build();
    try {
      contextFile.writeTo(filer);
    } catch (IOException e) {
      this.createMessage(
          Diagnostic.Kind.WARNING,
          "Exception: type >>"
              + parent.getEditorType().toString()
              + " << - trying to write: >>"
              + contextFile.packageName
              + "."
              + contextSimpleName
              + "<< -> message >>"
              + e.getMessage()
              + "<< multiple times");
    }
    //    } catch (Exception ignored) {
    //      System.out.println("Exception");
    //       already exists, ignore
    //    }

    return ClassName.get(packageName, contextSimpleName);
  }
}
