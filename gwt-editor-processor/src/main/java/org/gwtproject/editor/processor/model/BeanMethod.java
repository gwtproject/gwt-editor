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

import java.beans.Introspector;
import java.util.List;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

public enum BeanMethod {
  GET {
    @Override
    public String inferName(ExecutableElement method) {
      if (isBooleanProperty(method) && method.getSimpleName().toString().startsWith(IS_PREFIX)) {
        return Introspector.decapitalize(method.getSimpleName().toString().substring(2));
      }
      return super.inferName(method);
    }

    @Override
    public boolean matches(EditorTypes types, ExecutableElement method) {
      if (method.getParameters().size() > 0) {
        return false;
      }

      if (isBooleanProperty(method)) {
        return true;
      }

      String name = method.getSimpleName().toString();
      return name.startsWith(GET_PREFIX) && name.length() > 3;
    }

    /**
     * Returns {@code true} if the method matches {@code boolean isFoo()} or {@code boolean
     * hasFoo()} property accessors.
     */
    private boolean isBooleanProperty(ExecutableElement method) {
      TypeMirror returnType = method.getReturnType();
      if (returnType.getKind() == TypeKind.BOOLEAN
          || returnType.toString().equals(Boolean.class.getName())) {
        String name = method.getSimpleName().toString();
        if (name.startsWith(IS_PREFIX) && name.length() > 2) {
          return true;
        }
        return name.startsWith(HAS_PREFIX) && name.length() > 3;
      }
      return false;
    }
  },
  SET {
    @Override
    public boolean matches(EditorTypes types, ExecutableElement method) {
      if (method.getReturnType().getKind() != TypeKind.VOID) {
        return false;
      }
      if (method.getParameters().size() != 1) {
        return false;
      }
      String name = method.getSimpleName().toString();
      return name.startsWith(SET_PREFIX) && name.length() > 3;
    }
  },
  SET_BUILDER {
    @Override
    public boolean matches(EditorTypes types, ExecutableElement method) {
      if (!isReturnTypeEnclosingElement(types, method)) {
        return false;
      }
      if (method.getParameters().size() != 1) {
        return false;
      }
      String name = method.getSimpleName().toString();
      return name.startsWith(SET_PREFIX) && name.length() > 3;
    }
  },
  CALL {
    /** Matches all leftover methods. */
    @Override
    public boolean matches(EditorTypes types, ExecutableElement method) {
      return true;
    }
  };

  private static final String GET_PREFIX = "get";
  private static final String HAS_PREFIX = "has";
  private static final String IS_PREFIX = "is";
  private static final String SET_PREFIX = "set";

  /**
   * Determine which Action a method maps to.
   *
   * @param types editor types
   * @param method method to execute
   * @return matching method
   */
  public static BeanMethod which(EditorTypes types, ExecutableElement method) {
    for (BeanMethod action : BeanMethod.values()) {
      if (action.matches(types, method)) {
        return action;
      }
    }
    throw new RuntimeException("CALL should have matched");
  }

  /**
   * Infer the name of a property from the method.
   *
   * @param method executable method
   * @return name of executable method
   */
  public String inferName(ExecutableElement method) {
    if (this == CALL) {
      throw new UnsupportedOperationException(
          "Cannot infer a property name for a CALL-type method");
    }
    return Introspector.decapitalize(method.getSimpleName().toString().substring(3));
  }

  private static boolean isReturnTypeEnclosingElement(EditorTypes types, ExecutableElement method) {
    TypeMirror returnTypeMirror = method.getReturnType();
    if (returnTypeMirror == null || returnTypeMirror.toString().equals("void")) {
      return false;
    }
    TypeMirror matchingReturnTM = null;
    if (types
        .getTypes()
        .erasure(returnTypeMirror)
        .equals(types.getTypes().erasure(method.getEnclosingElement().asType()))) {
      matchingReturnTM = method.getEnclosingElement().asType();
    } else {
      List<? extends TypeMirror> directSupertypes =
          types.getTypes().directSupertypes(method.getEnclosingElement().asType());
      for (TypeMirror superType : directSupertypes) {
        if (types
            .getTypes()
            .erasure(returnTypeMirror)
            .equals(types.getTypes().erasure(superType))) {
          matchingReturnTM = superType;
          break;
        }
      }
    }
    if (matchingReturnTM == null) {
      return false;
    }
    return deepCompare(types, returnTypeMirror, matchingReturnTM);
  }

  private static boolean deepCompare(EditorTypes types, TypeMirror source, TypeMirror target) {
    if (!types.getTypes().erasure(source).equals(types.getTypes().erasure(target))) {
      return false;
    }
    List<? extends TypeMirror> sourceTypeArguments = ((DeclaredType) source).getTypeArguments();
    List<? extends TypeMirror> targetTypeArguments = ((DeclaredType) target).getTypeArguments();
    if (sourceTypeArguments.size() != targetTypeArguments.size()) {
      return false;
    }
    for (int i = 0; i < sourceTypeArguments.size(); i++) {
      if (TypeKind.DECLARED == sourceTypeArguments.get(i).getKind()
          && TypeKind.DECLARED == targetTypeArguments.get(i).getKind()) {
        if (!deepCompare(types, sourceTypeArguments.get(i), targetTypeArguments.get(i))) {
          return false;
        }
      } else {
        if (!types
            .getTypes()
            .erasure(sourceTypeArguments.get(i))
            .equals(types.getTypes().erasure(targetTypeArguments.get(i)))) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Returns {@code true} if the BeanLikeMethod matches the method.
   *
   * @param types editor type
   * @param method executable element
   * @return true, if the BeanLikeMethod matches the method
   */
  public abstract boolean matches(EditorTypes types, ExecutableElement method);
}
