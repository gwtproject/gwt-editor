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

import com.google.auto.common.MoreTypes;
import com.google.common.collect.Sets;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

/** Created by colin on 7/17/16. */
public class ModelUtils {
  public static List<? extends TypeMirror> findParameterizationOf(
      Types types, TypeMirror intfType, TypeMirror subType) {
    for (TypeMirror supertype : getFlattenedSupertypeHierarchy(types, subType)) {
      if (supertype instanceof DeclaredType) {
        DeclaredType parameterized = (DeclaredType) supertype;
        if (MoreTypes.asElement(intfType)
            .equals(
                parameterized
                    .asElement())) { // dodgy bit here, forcing them raw to compare their base
          // types, is this safe?
          // Found the desired supertype
          return new ArrayList<>(
              parameterized.getTypeArguments()); // copy contents, internal impl is nuts
          // seems too easy...
        }
      }
    }
    return null;
  }

  private static Set<String> VALUE_TYPE_NAMES =
      Sets.newHashSet(
          BigDecimal.class.getName(),
          BigInteger.class.getName(),
          Boolean.class.getName(),
          Byte.class.getName(),
          Character.class.getName(),
          //          Date.class.getName(),
          Double.class.getName(),
          Enum.class.getName(),
          Float.class.getName(),
          Integer.class.getName(),
          Long.class.getName(),
          Short.class.getName(),
          String.class.getName(),
          //          Splittable.class.getName(),
          Void.class.getName());

  public static boolean isValueType(TypeMirror type) {
    if (type.getKind().isPrimitive()) {
      return true;
    }
    if (MoreTypes.asElement(type).getKind() == ElementKind.ENUM) {
      return true;
    }

    // At this point, GWT seemingly arbitrarily uses AutoBean/RequestFactory's notion
    // of a value (via ValueCodex.getAllValueTypes()) to see if the given type is a
    // "value type". More likely we'd want to make this configurable, if supported at
    // all.
    // I've restricted this list at least to immutable types that are general to Java.
    return VALUE_TYPE_NAMES.contains(type.toString());
  }

  // TODO doesn't belong in here, would be nice to have in a "here is how you do GWT things" class
  /**
   * Returns all of the superclasses and superinterfaces for a given type including the type itself.
   * The returned set maintains an internal breadth-first ordering of the type, followed by its
   * interfaces (and their super-interfaces), then the supertype and its interfaces, and so on.
   *
   * @param types Utility methods for operating on types
   * @param typeMirror the type mirror
   * @return set of type mirrors
   */
  public static Set<TypeMirror> getFlattenedSupertypeHierarchy(Types types, TypeMirror typeMirror) {
    List<TypeMirror> toAdd = new ArrayList<>();
    LinkedHashSet<TypeMirror> result = new LinkedHashSet<>();

    toAdd.add(typeMirror);

    for (int i = 0; i < toAdd.size(); i++) {
      TypeMirror type = toAdd.get(i);
      if (result.add(type)) {
        toAdd.addAll(types.directSupertypes(type));
      }
    }

    return result;
  }
}
