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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class NameFactory {

  private final Set<String> usedNames = new HashSet<String>();

  /**
   * Creates a new <code>NameFactory</code> that knows about <code>existingNames</code>.
   *
   * @param existingNames a list of names that may be <code>null</code>.
   */
  public NameFactory(Collection<String> existingNames) {
    if (existingNames == null) {
      return;
    }
    usedNames.addAll(existingNames);
  }

  /** Creates a new <code>NameFactory</code> that doesn't know about any existing names. */
  public NameFactory() {
    this(null);
  }

  /**
   * Reserves a known name. Asserts that the name is not already in this set.
   *
   * @param name a not <code>null</code> name
   */
  public void addName(String name) {
    assert !usedNames.contains(name);
    usedNames.add(name);
  }

  /**
   * Creates a new unique name based off of <code>name</code> and adds it to the list of known
   * names.
   *
   * @param name a not <code>null</code> name to base the new unique name from
   * @return a new unique, not <code>null</code> name. This name may be possibly identical to <code>
   *     name</code>.
   */
  public String createName(String name) {
    String newName = name;

    for (int count = 0; true; ++count) {
      if (usedNames.contains(newName)) {
        newName = name + count;
      } else {
        usedNames.add(newName);
        return newName;
      }
    }
  }
}
