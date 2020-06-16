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
package org.gwtproject.editor.client;

/**
 * Used to edit non-object or immutable values. The Editor framework will not descend into a
 * LeafValueEditor.
 *
 * @param <T> The type of primitive value
 * @see org.gwtproject.editor.client.adapters.SimpleEditor
 */
public interface LeafValueEditor<T> extends Editor<T> {
  /**
   * Sets the value.
   *
   * @param value a value object of type V
   * @see #getValue()
   */
  void setValue(T value);

  /**
   * Returns the current value.
   *
   * @return the value as an object of type V
   * @see #setValue
   */
  T getValue();
}
