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

import org.gwtproject.editor.client.CompositeEditor;
import org.gwtproject.editor.client.CompositeEditor.EditorChain;
import org.gwtproject.editor.client.EditorContext;
import org.gwtproject.editor.client.HasEditorDelegate;

/**
 * Extends the logic in Refresher to provide the editor instance with references to framework
 * plumbing fixes.
 */
@SuppressWarnings("rawtypes")
public class Initializer extends Refresher {

  @Override
  public <Q> boolean visit(EditorContext<Q> ctx) {
    @SuppressWarnings("unchecked")
    AbstractEditorDelegate<Q, ?> delegate = (AbstractEditorDelegate<Q, ?>) ctx.getEditorDelegate();

    // Pass in the EditorDelegate
    HasEditorDelegate<Q> asHasDelegate = ctx.asHasEditorDelegate();
    if (asHasDelegate != null) {
      asHasDelegate.setDelegate(delegate);
    }

    // Set the EditorChain
    CompositeEditor<Q, ?, ?> asComposite = ctx.asCompositeEditor();
    if (asComposite != null) {
      // Various javac generics compilation problems here
      @SuppressWarnings("rawtypes")
      EditorChain chain = delegate.getEditorChain();
      asComposite.setEditorChain(chain);
    }

    return super.visit(ctx);
  }
}
