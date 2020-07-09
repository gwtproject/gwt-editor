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

import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import org.gwtproject.editor.client.*;

/** Created by colin on 7/17/16. */
public class EditorTypes {
  private final Types types;
  private final Elements elements;

  public EditorTypes(Types types, Elements elements) {
    this.types = types;
    this.elements = elements;
  }

  public Types getTypes() {
    return types;
  }

  public Elements getElements() {
    return elements;
  }

  public TypeMirror getCompositeEditorInterface() {
    return types.erasure(elements.getTypeElement(CompositeEditor.class.getName()).asType());
  }

  public TypeMirror getIsEditorInterface() {
    return types.erasure(elements.getTypeElement(IsEditor.class.getName()).asType());
  }

  public TypeMirror getEditorInterface() {
    return types.erasure(elements.getTypeElement(Editor.class.getName()).asType());
  }

  public TypeMirror getLeafValueEditorInterface() {
    return types.erasure(elements.getTypeElement(LeafValueEditor.class.getName()).asType());
  }

  public TypeMirror getHasEditorErrorsInterface() {
    return types.erasure(elements.getTypeElement(HasEditorErrors.class.getName()).asType());
  }

  public TypeMirror getHasEditorDelegateInterface() {
    return types.erasure(elements.getTypeElement(HasEditorDelegate.class.getName()).asType());
  }

  public TypeMirror getValueAwareEditorInterface() {
    return types.erasure(elements.getTypeElement(ValueAwareEditor.class.getName()).asType());
  }
}
