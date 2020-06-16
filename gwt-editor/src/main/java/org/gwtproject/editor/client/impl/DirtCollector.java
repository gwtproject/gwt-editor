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
package org.gwtproject.editor.client.impl;

import java.util.HashMap;
import java.util.Map;
import org.gwtproject.editor.client.EditorContext;
import org.gwtproject.editor.client.EditorVisitor;
import org.gwtproject.editor.client.LeafValueEditor;

class DirtCollector extends EditorVisitor {
  public boolean dirty;
  private final Map<LeafValueEditor<?>, Object> leafValues =
      new HashMap<LeafValueEditor<?>, Object>();

  @Override
  public <T> void endVisit(EditorContext<T> ctx) {
    LeafValueEditor<T> editor = ctx.asLeafValueEditor();
    if (editor != null) {
      leafValues.put(editor, editor.getValue());
    }
    @SuppressWarnings("unchecked")
    AbstractEditorDelegate<T, ?> delegate = (AbstractEditorDelegate<T, ?>) ctx.getEditorDelegate();
    if (delegate != null) {
      dirty |= delegate.isDirty();
    }
  }

  public Map<LeafValueEditor<?>, Object> getLeafValues() {
    return leafValues;
  }

  /**
   * Returns {@code true} if {@link org.gwtproject.editor.client.EditorDelegate#setDirty(boolean)}
   * was used.
   */
  public boolean isDirty() {
    return dirty;
  }
}
