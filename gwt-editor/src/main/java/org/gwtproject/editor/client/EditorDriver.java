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

import java.util.List;
import javax.validation.ConstraintViolation;

/**
 * Defines common capabilities of editor drivers.
 *
 * @param <T> the type of data returned from {@link #flush()}
 * @see org.gwtproject.editor.client.SimpleBeanEditorDriver
 */
public interface EditorDriver<T> {
  /** @param visitor Visit the Editor hierarchy controlled by the EditorDriver */
  void accept(EditorVisitor visitor);

  /**
   * Update the object being edited with the current state of the Editor.
   *
   * @return an implementation-specific value
   */
  T flush();

  /**
   * Returns any unconsumed EditorErrors from the last call to {@link #flush()}.
   *
   * @return a List of {@link EditorError} instances
   */
  List<EditorError> getErrors();

  /**
   * Indicates if the last call to {@link #flush()} resulted in any errors.
   *
   * @return {@code true} if errors are present
   */
  boolean hasErrors();

  /**
   * Returns {@code true} if any of the Editors in the hierarchy have been modified relative to the
   * last value passed into {@link SimpleBeanEditorDriver#edit(Object)}.
   *
   * <p>This method is not affected by {@link #flush()} to support the following workflow:
   *
   * <ol>
   *   <li>{@code EditorDriver.edit()}
   *   <li>The user edits the on-screen values
   *   <li>{@code EditorDriver.flush()}
   *   <li>The data in the edited object is validated:
   *       <ol>
   *         <li>The validation fails, returning to step 2
   *         <li>The validation succeeds and the editing UI is dismissed
   *       </ol>
   * </ol>
   *
   * The simplest implementation of a "navigate away from dirty UI warning" by checking {@code
   * isDirty()} is correct for the above workflow. If the {@link #flush()} method were to clear the
   * dirty state, it would be necessary to implement an alternate flag to distinguish between a
   * newly-initialized editor entering step 2 or re-entering step 2.
   *
   * @see EditorDelegate#setDirty(boolean)
   * @return boolean
   */
  boolean isDirty();

  /**
   * Show {@link ConstraintViolation ConstraintViolations} generated through a {@link
   * javax.validation.Validator Validator}. The violations will be converted into {@link
   * EditorError} objects whose {@link EditorError#getUserData() getUserData()} method can be used
   * to access the original ConstraintViolation object.
   *
   * @param violations an Iterable over {@link ConstraintViolation} instances
   * @return <code>true</code> if there were any unconsumed EditorErrors which can be retrieved from
   *     {@link #getErrors()}
   */
  boolean setConstraintViolations(Iterable<ConstraintViolation<?>> violations);
}
