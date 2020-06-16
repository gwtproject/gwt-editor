/*
 * Copyright © 2018 GWT Timer J2CL Tests
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

import org.gwtproject.editor.client.adapters.SimpleEditor;

/** Simple editor used by multiple tests. */
public class AddressEditor implements Editor<Address> {
  public SimpleEditor<String> city = SimpleEditor.of(SimpleBeanEditorTest.UNINITIALIZED);
  public SimpleEditor<String> street = SimpleEditor.of(SimpleBeanEditorTest.UNINITIALIZED);
}
