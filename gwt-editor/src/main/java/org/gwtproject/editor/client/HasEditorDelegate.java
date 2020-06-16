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
 * Indicates that an Editor requires an EditorDelegate.
 *
 * @param <T> the type of object the EditorDelegate operates on
 */
public interface HasEditorDelegate<T> extends Editor<T> {

  /**
   * Called by the EditorDriver to provide access to the EditorDelegate the Editor is peered with.
   *
   * @param delegate an {@link EditorDelegate} of type T
   */
  void setDelegate(EditorDelegate<T> delegate);
}
