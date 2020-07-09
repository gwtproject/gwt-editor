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
package org.gwtproject.editor.processor.model;

import static org.junit.Assert.*;

import com.google.auto.common.MoreTypes;
import com.google.testing.compile.CompilationRule;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import java.util.Arrays;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import org.gwtproject.editor.client.*;
import org.gwtproject.editor.client.adapters.SimpleEditor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/** Created by colin on 7/24/16. */
public class EditorModelTest {

  @Rule public CompilationRule compilationRule = new CompilationRule();

  private Elements elements;
  private Types types;

  private TypeMirror sbedType;

  @Before
  public void initTypes() {
    this.elements = compilationRule.getElements();
    types = compilationRule.getTypes();

    sbedType = elements.getTypeElement(SimpleBeanEditorDriver.class.getCanonicalName()).asType();
  }
  /** Test the simple getters on the Model object. */
  @Test
  public void testBasicAttributes() {
    EditorModel m =
        new EditorModel(
            null,
            new EditorTypes(types, elements),
            typeMirrorFor(PersonEditorDriver.class),
            sbedType);

    assertEquals(typeMirrorFor(PersonEditor.class), m.getEditorType());
    assertEquals(typeMirrorFor(Person.class), m.getProxyType());
  }

  /**
   * Verify that we correctly descend into a subeditor of a CompositeEditor that also is a
   * LeafValueEditor (as is the case of OptionalFieldEditor).
   */
  @Test
  public void testCompositeAndLeafValueEditor() {
    EditorModel m =
        new EditorModel(
            null,
            new EditorTypes(types, elements),
            typeMirrorFor(CompositeAndLeafEditorDriver.class),
            sbedType);

    assertEquals(typeMirrorFor(CompositeAndLeafEditorDriver.AProxy.class), m.getProxyType());
    assertEquals(typeMirrorFor(CompositeAndLeafEditorDriver.AEditor.class), m.getEditorType());

    List<EditorProperty> data = m.getEditorData();
    Assert.assertEquals(1, data.size());

    assertTrue(data.get(0).isCompositeEditor());

    EditorProperty composed = data.get(0).getComposedData();
    assertEquals(
        typeMirrorFor(CompositeAndLeafEditorDriver.BProxy.class), composed.getEditedType());
    assertEquals(
        typeMirrorFor(CompositeAndLeafEditorDriver.BEditor.class), composed.getEditorType());

    // Nonsensical for the optional editor to have any data
    List<EditorProperty> optionalEditorData = m.getEditorData(data.get(0).getEditorType());
    Assert.assertEquals(0, optionalEditorData.size());

    // Make sure we have EditorProperty for the sub-editor
    List<EditorProperty> subEditorData = m.getEditorData(composed.getEditorType());
    Assert.assertEquals(1, subEditorData.size());
  }

  @Test
  public void testCompositeDriver() {
    EditorModel m =
        new EditorModel(
            null,
            new EditorTypes(types, elements),
            typeMirrorFor(CompositeEditorDriver.class),
            sbedType);

    List<EditorProperty> data = m.getEditorData();
    Assert.assertEquals(9, data.size());

    String[] paths = new String[data.size()];
    String[] expressions = new String[data.size()];
    for (int i = 0, j = paths.length; i < j; i++) {
      paths[i] = data.get(i).getPath();
      expressions[i] = data.get(i).getExpression();
    }
    Assert.assertEquals(
        Arrays.asList(
            "address",
            "address.city",
            "address.street",
            "person",
            "person.has",
            "person.is",
            "person.lastModified",
            "person.name",
            "person.readonly"),
        Arrays.asList(paths));
    // address is a property, person is a method in CompositeEditor
    Assert.assertEquals(
        Arrays.asList(
            "address",
            "address.city",
            "address.street",
            "person()",
            "person().has",
            "person().is",
            "person().lastModified",
            "person().name",
            "person().readonly"),
        Arrays.asList(expressions));
    assertTrue(data.get(0).isDelegateRequired());
    assertFalse(data.get(0).isLeafValueEditor() || data.get(0).isValueAwareEditor());
    assertTrue(data.get(3).isDelegateRequired());
    assertFalse(data.get(3).isLeafValueEditor() || data.get(3).isValueAwareEditor());
    int fieldNum = 4;
    checkPersonHasHas(data.get(fieldNum++));
    checkPersonIsIs(data.get(fieldNum++));
    checkPersonLastModified(data.get(fieldNum++));
    checkPersonName(data.get(fieldNum++));
    checkPersonReadonly(data.get(fieldNum++));
  }

  //  public void testCyclicDriver() {
  //    UnitTestTreeLogger.Builder builder = new UnitTestTreeLogger.Builder();
  //    builder.setLowestLogLevel(TreeLogger.ERROR);
  //    builder.expectError(
  //            EditorModel.cycleErrorMessage(
  //                    typeMirrorFor(CyclicEditorDriver.AEditor.class), "<Root Object>",
  //                    "b.a"), null);
  //    builder.expectError(EditorModel.poisonedMessage(), null);
  //    UnitTestTreeLogger testLogger = builder.createLogger();
  //    try {
  //      new EditorModel(testLogger, typeMirrorFor(CyclicEditorDriver.class),
  //              sbedType);
  //      fail("Should have complained about cycle");
  //    } catch (UnableToCompleteException expected) {
  //    }
  //    testLogger.assertCorrectLogEntries();
  //  }

  @Test
  public void testDottedPath() {
    EditorModel m =
        new EditorModel(
            null,
            new EditorTypes(types, elements),
            typeMirrorFor(DottedPathEditorDriver.class),
            sbedType);
    List<EditorProperty> fields = m.getEditorData();
    Assert.assertEquals(2, fields.size());
    Assert.assertEquals("name", fields.get(0).getPath());
    assertFalse(fields.get(0).isDeclaredPathNested());
    Assert.assertEquals("", fields.get(0).getBeanOwnerExpression());
    Assert.assertEquals("true", fields.get(0).getBeanOwnerGuard("object"));
    Assert.assertEquals(".getName()", fields.get(0).getGetterExpression());
    Assert.assertEquals("address.street", fields.get(1).getPath());
    Assert.assertEquals(".getAddress()", fields.get(1).getBeanOwnerExpression());
    Assert.assertEquals("object.getAddress() != null", fields.get(1).getBeanOwnerGuard("object"));
    Assert.assertEquals(".getStreet()", fields.get(1).getGetterExpression());
    Assert.assertEquals("setStreet", fields.get(1).getSetterName());
    Assert.assertEquals("street", fields.get(1).getPropertyName());
    assertTrue(fields.get(1).isDeclaredPathNested());
    assertEquals(typeMirrorFor(Address.class), fields.get(1).getPropertyOwnerType());
  }

  /** Make sure we find all field-based editors. */
  @Test
  public void testFieldEditors() {
    EditorModel m =
        new EditorModel(
            null,
            new EditorTypes(types, elements),
            typeMirrorFor(PersonEditorDriver.class),
            sbedType);
    List<EditorProperty> fields = m.getEditorData();
    Assert.assertEquals(5, fields.size());

    int fieldNum = 0;
    // hasHas
    checkPersonHasHas(fields.get(fieldNum++));
    // isIs
    checkPersonIsIs(fields.get(fieldNum++));
    // lastModified
    checkPersonLastModified(fields.get(fieldNum++));
    // name
    checkPersonName(fields.get(fieldNum++));
    // readonly
    checkPersonReadonly(fields.get(fieldNum++));
  }

  @Test
  public void testFlatData() {
    EditorModel m =
        new EditorModel(
            null,
            new EditorTypes(types, elements),
            typeMirrorFor(CompositeEditorDriver.class),
            sbedType);

    assertNotNull(m.getEditorData(typeMirrorFor(Object.class)));
    Assert.assertEquals(0, m.getEditorData(typeMirrorFor(Object.class)).size());

    List<EditorProperty> composite = m.getEditorData(typeMirrorFor(CompositeProxyEditor.class));
    Assert.assertEquals(2, composite.size());
    Assert.assertEquals("address", composite.get(0).getPropertyName());
    Assert.assertEquals("person", composite.get(1).getPropertyName());

    List<EditorProperty> person = m.getEditorData(typeMirrorFor(PersonEditor.class));
    Assert.assertEquals(5, person.size());
    int fieldNum = 0;
    Assert.assertEquals("has", person.get(fieldNum++).getPropertyName());
    Assert.assertEquals("is", person.get(fieldNum++).getPropertyName());
    Assert.assertEquals("lastModified", person.get(fieldNum++).getPropertyName());
    Assert.assertEquals("name", person.get(fieldNum++).getPropertyName());
    Assert.assertEquals("readonly", person.get(fieldNum++).getPropertyName());

    List<EditorProperty> address = m.getEditorData(typeMirrorFor(AddressEditor.class));
    Assert.assertEquals("city", address.get(0).getPropertyName());
    Assert.assertEquals("street", address.get(1).getPropertyName());
  }

  /** Tests a plain IsEditor that allows the editor instance to be swapped in by a view object. */
  @Test
  public void testIsEditor() {
    EditorModel m =
        new EditorModel(
            null,
            new EditorTypes(types, elements),
            typeMirrorFor(UsesIsEditorDriver.class),
            sbedType);

    List<EditorProperty> data = m.getEditorData();
    assertNotNull(data);
    Assert.assertEquals(2, data.size());
    Assert.assertEquals(
        Arrays.asList("b", "b.string"),
        Arrays.asList(data.get(0).getPath(), data.get(1).getPath()));
    Assert.assertEquals(
        Arrays.asList("bEditor().asEditor()", "stringEditor()"),
        Arrays.asList(data.get(0).getSimpleExpression(), data.get(1).getSimpleExpression()));
  }

  /** Tests the case where an IsEditor also implements the Editor interface. */
  @Test
  public void testIsEditorAndEditor() {
    EditorModel m =
        new EditorModel(
            null,
            new EditorTypes(types, elements),
            typeMirrorFor(UsesIsEditorAndEditorDriver.class),
            sbedType);

    List<EditorProperty> data = m.getEditorData();
    assertNotNull(data);
    Assert.assertEquals(4, data.size());
    Assert.assertEquals(
        Arrays.asList("b", "b.string", "b", "b.string"),
        Arrays.asList(
            data.get(0).getPath(),
            data.get(1).getPath(),
            data.get(2).getPath(),
            data.get(3).getPath()));
    Assert.assertEquals(
        Arrays.asList(
            "bEditor().asEditor()",
            "bEditor().asEditor().coEditor()",
            "bEditor()",
            "bEditor().viewEditor()"),
        Arrays.asList(
            data.get(0).getExpression(),
            data.get(1).getExpression(),
            data.get(2).getExpression(),
            data.get(3).getExpression()));
    Assert.assertEquals(
        Arrays.asList(true, false, true, false),
        Arrays.asList(
            data.get(0).isDelegateRequired(),
            data.get(1).isDelegateRequired(),
            data.get(2).isDelegateRequired(),
            data.get(3).isDelegateRequired()));
  }

  @Test
  public void testListDriver() {
    EditorModel m =
        new EditorModel(
            null,
            new EditorTypes(types, elements),
            typeMirrorFor(ListEditorDriver.class),
            sbedType);
    assertEquals(typeMirrorFor(Person.class), m.getProxyType());
    assertEquals(typeMirrorFor(ListEditor.class), m.getEditorType());

    EditorProperty data = m.getRootData();
    assertTrue(data.isCompositeEditor());

    EditorProperty composed = data.getComposedData();
    assertEquals(typeMirrorFor(Address.class), composed.getEditedType());
    assertEquals(typeMirrorFor(AddressEditor.class), composed.getEditorType());

    // Nonsensical for the list editor to have any data
    List<EditorProperty> listEditorData = m.getEditorData(m.getEditorType());
    Assert.assertEquals(0, listEditorData.size());

    // Make sure we have EditorProperty for the sub-editor
    List<EditorProperty> subEditorData = m.getEditorData(composed.getEditorType());
    Assert.assertEquals(2, subEditorData.size());
  }

  /** Make sure we can find all method-based editors. */
  @Test
  public void testMethodEditors() {
    EditorModel m =
        new EditorModel(
            null,
            new EditorTypes(types, elements),
            typeMirrorFor(PersonEditorDriverUsingMethods.class),
            sbedType);
    List<EditorProperty> fields = m.getEditorData();
    Assert.assertEquals(2, fields.size());

    // nameEditor()
    checkPersonName(fields.get(0));
    checkPersonReadonly(fields.get(1));
  }

  /**
   * Tests the case where an Editor wants to editor a property that is not provided by its
   * associated Proxy type.
   */
  //  public void testMissingGetter() {
  //    UnitTestTreeLogger.Builder builder = new UnitTestTreeLogger.Builder();
  //    builder.setLowestLogLevel(TreeLogger.ERROR);
  //    builder.expectError(
  //            EditorModel.noGetterMessage("missing",
  //                    typeMirrorFor(MissingGetterEditorDriver.AProxy.class)), null);
  //    builder.expectError(
  //            EditorModel.noGetterMessage("yetAgain",
  //                    typeMirrorFor(MissingGetterEditorDriver.AProxy.class)), null);
  //    builder.expectError(EditorModel.poisonedMessage(), null);
  //    UnitTestTreeLogger testLogger = builder.createLogger();
  //    try {
  //      new EditorModel(testLogger,
  //              typeMirrorFor(MissingGetterEditorDriver.class), sbedType);
  //      fail("Should have thrown exception");
  //    } catch (UnableToCompleteException expecetd) {
  //    }
  //    testLogger.assertCorrectLogEntries();
  //  }

  /** Tests the sanity-check error messages emitted by the constructor. */
  //  public void testSanityErrorMessages() {
  //    UnitTestTreeLogger.Builder builder = new UnitTestTreeLogger.Builder();
  //    builder.setLowestLogLevel(TreeLogger.ERROR);
  //    builder.expectError(
  //            EditorModel.unexpectedInputTypeMessage(sbedType,
  //                    types.getJavaLangObject()), null);
  //    builder.expectError(EditorModel.mustExtendMessage(sbedType), null);
  //    builder.expectError(
  //
  // EditorModel.tooManyInterfacesMessage(typeMirrorFor(TooManyInterfacesEditorDriver.class)),
  //            null);
  //    builder.expectError(EditorModel.foundPrimitiveMessage(JPrimitiveType.LONG,
  //            "", "lastModified.foo"), null);
  //    builder.expectError(EditorModel.poisonedMessage(), null);
  //    UnitTestTreeLogger testLogger = builder.createLogger();
  //
  //    try {
  //      new EditorModel(testLogger, types.getJavaLangObject(), sbedType);
  //      fail("Should have thrown exception");
  //    } catch (UnableToCompleteException expected) {
  //    }
  //    try {
  //      new EditorModel(testLogger, sbedType, sbedType);
  //      fail("Should have thrown exception");
  //    } catch (UnableToCompleteException expected) {
  //    }
  //    try {
  //      new EditorModel(testLogger,
  //              typeMirrorFor(TooManyInterfacesEditorDriver.class), sbedType);
  //      fail("Should have thrown exception");
  //    } catch (UnableToCompleteException expected) {
  //    }
  //    try {
  //      new EditorModel(testLogger,
  //              typeMirrorFor(PersonEditorWithBadPrimitiveAccessDriver.class),
  //              sbedType);
  //      fail("Should have thrown exception");
  //    } catch (UnableToCompleteException expected) {
  //    }
  //    testLogger.assertCorrectLogEntries();
  //  }

  //  public void testUnparameterizedEditor() {
  //    UnitTestTreeLogger.Builder builder = new UnitTestTreeLogger.Builder();
  //    builder.setLowestLogLevel(TreeLogger.ERROR);
  //    builder.expectError(
  //            EditorModel.noEditorParameterizationMessage(
  //                    types.findType(Editor.class.getName()),
  //                    types.findType(SimpleEditor.class.getName()).isGenericType().getRawType()),
  //            null);
  //    UnitTestTreeLogger testLogger = builder.createLogger();
  //    try {
  //      new EditorModel(testLogger,
  //              typeMirrorFor(UnparameterizedEditorEditorDriver.class), sbedType);
  //      fail("Should have thrown exception");
  //    } catch (UnableToCompleteException expecetd) {
  //    }
  //    testLogger.assertCorrectLogEntries();
  //  }

  /** Verify that {@code @Path("")} is valid. */
  @Test
  public void testZeroLengthPath() {
    EditorModel m =
        new EditorModel(
            null,
            new EditorTypes(types, elements),
            typeMirrorFor(PersonEditorWithAliasedSubEditorsDriver.class),
            sbedType);
    List<EditorProperty> fields = m.getEditorData();
    Assert.assertEquals(12, fields.size());
  }

  private void checkPersonHasHas(EditorProperty editorField) {
    assertNotNull(editorField);
    assertEquals(typeMirrorFor(SimpleEditor.class), raw(editorField.getEditorType()));
    assertTrue(editorField.isLeafValueEditor());
    assertFalse(editorField.isDelegateRequired());
    assertFalse(editorField.isValueAwareEditor());
    Assert.assertEquals(".hasHas()", editorField.getGetterExpression());
    Assert.assertEquals("setHas", editorField.getSetterName());
  }

  private void checkPersonIsIs(EditorProperty editorField) {
    assertNotNull(editorField);
    assertEquals(typeMirrorFor(SimpleEditor.class), raw(editorField.getEditorType()));
    assertTrue(editorField.isLeafValueEditor());
    assertFalse(editorField.isDelegateRequired());
    assertFalse(editorField.isValueAwareEditor());
    Assert.assertEquals(".isIs()", editorField.getGetterExpression());
    Assert.assertEquals("setIs", editorField.getSetterName());
  }

  private void checkPersonLastModified(EditorProperty editorField) {
    assertNotNull(editorField);
    assertEquals(typeMirrorFor(SimpleEditor.class), raw(editorField.getEditorType()));
    assertTrue(editorField.isLeafValueEditor());
    assertFalse(editorField.isDelegateRequired());
    assertFalse(editorField.isValueAwareEditor());
    Assert.assertEquals(".getLastModified()", editorField.getGetterExpression());
    Assert.assertEquals("setLastModified", editorField.getSetterName());
  }

  private void checkPersonName(EditorProperty editorField) {
    assertNotNull(editorField);
    assertEquals(typeMirrorFor(SimpleEditor.class), raw(editorField.getEditorType()));
    assertTrue(editorField.isLeafValueEditor());
    assertFalse(editorField.isDelegateRequired());
    assertFalse(editorField.isValueAwareEditor());
    Assert.assertEquals(".getName()", editorField.getGetterExpression());
    Assert.assertEquals("setName", editorField.getSetterName());
  }

  /** @param editorField */
  private void checkPersonReadonly(EditorProperty editorField) {
    assertNotNull(editorField);
    assertEquals(typeMirrorFor(SimpleEditor.class), raw(editorField.getEditorType()));
    assertTrue(editorField.isLeafValueEditor());
    assertFalse(editorField.isDelegateRequired());
    assertFalse(editorField.isValueAwareEditor());
    Assert.assertEquals(".getReadonly()", editorField.getGetterExpression());
    assertNull(editorField.getSetterName());
  }

  private static void assertEquals(TypeMirror expected, TypeMirror actual) {
    assertTrue(
        "Expected " + expected + ", actual " + actual,
        MoreTypes.equivalence().equivalent(expected, actual));
  }

  private Element typeElementFor(Class<?> clazz) {
    return MoreTypes.asElement(raw(elements.getTypeElement(clazz.getCanonicalName()).asType()));
  }

  private TypeMirror typeMirrorFor(Class<?> clazz) {
    return raw(elements.getTypeElement(clazz.getCanonicalName()).asType());
  }

  private TypeMirror raw(TypeMirror editorType) {
    return types.erasure(editorType);
  }

  static class Address {
    String city;
    String street;

    public String getCity() {
      return city;
    }

    public void setCity(String city) {
      this.city = city;
    }

    public String getStreet() {
      return street;
    }

    public void setStreet(String street) {
      this.street = street;
    }
  }

  static class AddressEditor implements Editor<Address> {
    public SimpleEditor<String> city;
    public SimpleEditor<String> street;
  }

  static class CompositeObject {
    Address address;
    Person person;

    public Address getAddress() {
      return address;
    }

    public Person getPerson() {
      return person;
    }
  }

  static class CompositeProxyEditor implements Editor<CompositeObject> {
    AddressEditor address;

    PersonEditor person() {
      return null;
    }
  }

  interface CompositeEditorDriver
      extends SimpleBeanEditorDriver<CompositeObject, CompositeProxyEditor> {}

  interface CyclicEditorDriver
      extends SimpleBeanEditorDriver<CyclicEditorDriver.AProxy, CyclicEditorDriver.AEditor> {
    interface AProxy extends EntityProxy {
      BProxy getB();
    }

    interface BProxy extends EntityProxy {
      AProxy getA();
    }

    interface AEditor extends Editor<AProxy> {
      BEditor bEditor();
    }

    interface BEditor extends Editor<BProxy> {
      AEditor aEditor();
    }
  }

  interface DottedPathEditorDriver
      extends SimpleBeanEditorDriver<Person, DottedPathEditorDriver.PersonEditor> {
    interface PersonEditor extends Editor<Person> {
      SimpleEditor<String> nameEditor();

      @Editor.Path("address.street")
      SimpleEditor<String> streetEditor();
    }
  }

  interface ListEditor extends CompositeEditor<Person, Address, AddressEditor>, Editor<Person> {}

  interface ListEditorDriver extends SimpleBeanEditorDriver<Person, ListEditor> {}

  interface MissingGetterEditorDriver
      extends SimpleBeanEditorDriver<
          MissingGetterEditorDriver.AProxy, MissingGetterEditorDriver.AEditor> {
    interface AProxy extends EntityProxy {}

    interface AEditor extends Editor<AProxy> {
      SimpleEditor<String> missingEditor();

      SimpleEditor<String> yetAgain();
    }
  }

  static class Person {
    Address address;

    String name;

    long lastModified;
    String readonly;
    boolean has;
    boolean is;

    public Address getAddress() {
      return address;
    }

    public void setAddress(Address address) {
      this.address = address;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public long getLastModified() {
      return lastModified;
    }

    public void setLastModified(long lastModified) {
      this.lastModified = lastModified;
    }

    public String getReadonly() {
      return readonly;
    }

    public boolean hasHas() {
      return has;
    }

    public void setHas(boolean has) {
      this.has = has;
    }

    public boolean isIs() {
      return is;
    }

    public void setIs(boolean is) {
      this.is = is;
    }
  }

  static class PersonEditor implements Editor<Person> {
    SimpleEditor<Boolean> has;
    SimpleEditor<Boolean> is;
    SimpleEditor<Long> lastModified;
    public SimpleEditor<String> name;
    SimpleEditor<String> readonly;
    public static SimpleEditor ignoredStatic;
    private SimpleEditor<String> ignoredPrivate;
    @Editor.Ignore public SimpleEditor<String> ignoredPublic;
  }

  static class PersonEditorWithAliasedSubEditors implements Editor<Person> {
    @Path("")
    PersonEditor e1;

    @Path("")
    PersonEditor e2;
  }

  interface PersonEditorWithAliasedSubEditorsDriver
      extends SimpleBeanEditorDriver<Person, PersonEditorWithAliasedSubEditors> {}

  static class PersonEditorWithBadPrimitiveAccess implements Editor<Person> {
    @Path("lastModified.foo")
    SimpleEditor<String> bad;
  }

  interface PersonEditorWithBadPrimitiveAccessDriver
      extends SimpleBeanEditorDriver<Person, PersonEditorWithBadPrimitiveAccess> {}

  abstract static class PersonEditorUsingMethods implements Editor<Person> {
    public abstract SimpleEditor<String> nameEditor();

    protected abstract SimpleEditor<String> readonlyEditor();

    public static SimpleEditor<String> ignoredStatic() {
      return null;
    }

    private SimpleEditor<String> ignoredPrivate() {
      return null;
    }

    @Editor.Ignore
    public abstract SimpleEditor<String> ignoredPublic();
  }

  interface PersonEditorDriver extends SimpleBeanEditorDriver<Person, PersonEditor> {}

  interface PersonEditorDriverUsingMethods
      extends SimpleBeanEditorDriver<Person, PersonEditorUsingMethods> {}

  interface PersonRequestFactory extends RequestFactory {}

  interface TooManyInterfacesEditorDriver
      extends SimpleBeanEditorDriver<CompositeObject, CompositeProxyEditor>, java.io.Serializable {}

  interface UnparameterizedEditorEditorDriver
      extends SimpleBeanEditorDriver<
          UnparameterizedEditorEditorDriver.AProxy, UnparameterizedEditorEditorDriver.AEditor> {
    interface AProxy extends EntityProxy {}

    interface AEditor extends Editor<AProxy> {
      SimpleEditor needsParameterization();
    }
  }

  interface UsesIsEditorDriver
      extends SimpleBeanEditorDriver<UsesIsEditorDriver.AProxy, UsesIsEditorDriver.AEditor> {
    interface AProxy extends EntityProxy {
      BProxy getB();
    }

    interface BProxy extends EntityProxy {
      String getString();
    }

    interface AEditor extends Editor<AProxy> {
      BView bEditor();
    }

    interface BView extends IsEditor<BEditor> {
      @Editor.Path("string")
      BEditor unseen();
    }

    interface BEditor extends Editor<BProxy> {
      SimpleEditor<String> stringEditor();
    }
  }

  interface UsesIsEditorAndEditorDriver
      extends SimpleBeanEditorDriver<
          UsesIsEditorAndEditorDriver.AProxy, UsesIsEditorAndEditorDriver.AEditor> {
    interface AProxy extends EntityProxy {
      BProxy getB();
    }

    interface BProxy extends EntityProxy {
      String getString();
    }

    interface AEditor extends Editor<AProxy> {
      BView bEditor();
    }

    interface BView extends IsEditor<BEditor>, Editor<BProxy>, HasEditorErrors<BProxy> {
      @Editor.Path("string")
      SimpleEditor<String> viewEditor();
    }

    interface BEditor extends Editor<BProxy> {
      @Editor.Path("string")
      SimpleEditor<String> coEditor();
    }
  }

  interface CompositeAndLeafEditorDriver
      extends SimpleBeanEditorDriver<
          CompositeAndLeafEditorDriver.AProxy, CompositeAndLeafEditorDriver.AEditor> {
    interface AProxy extends EntityProxy {
      BProxy getB();
    }

    interface BProxy extends EntityProxy {
      String getString();
    }

    interface AEditor extends Editor<AProxy> {
      OptionalBEditor bEditor();
    }

    interface OptionalBEditor
        extends CompositeEditor<BProxy, BProxy, BEditor>, LeafValueEditor<BProxy> {
      LeafValueEditor<String> ignored();
    }

    interface BEditor extends Editor<BProxy> {
      @Editor.Path("string")
      SimpleEditor<String> coEditor();
    }
  }
}
