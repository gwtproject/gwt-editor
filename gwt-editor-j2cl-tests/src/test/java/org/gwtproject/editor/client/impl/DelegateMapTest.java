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

import static org.junit.Assert.*;

import com.google.j2cl.junit.apt.J2clTestInput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.gwtproject.editor.client.*;
import org.gwtproject.editor.client.adapters.SimpleEditor;
import org.gwtproject.editor.client.annotation.IsDriver;
import org.junit.Test;

@J2clTestInput(DelegateMapTest.class)
public class DelegateMapTest {

  class AddressCoEditorView extends AddressEditor implements IsEditor<AddressEditor> {
    private AddressEditor addressEditor = new AddressEditor();

    @Override
    public AddressEditor asEditor() {
      return addressEditor;
    }
  }

  class PersonEditorWithCoAddressEditorView implements Editor<Person> {
    AddressCoEditorView addressEditor = new AddressCoEditorView();
    SimpleEditor<String> name = SimpleEditor.of("uninitialized");

    @Path("manager.name")
    SimpleEditorWithDelegate<String> managerName =
        new SimpleEditorWithDelegate<String>("uninitialized");
  }

  @IsDriver
  interface PersonEditorWithCoAddressEditorViewDriver
      extends SimpleBeanEditorDriver<Person, PersonEditorWithCoAddressEditorView> {}

  class SimpleEditorWithDelegate<T> extends SimpleEditor<T> implements HasEditorDelegate<T> {
    EditorDelegate<T> delegate;

    public SimpleEditorWithDelegate(T value) {
      super(value);
    }

    @Override
    public void setDelegate(EditorDelegate<T> delegate) {
      this.delegate = delegate;
    }
  }

  private AbstractSimpleBeanEditorDriver<Person, PersonEditorWithCoAddressEditorView> driver;
  private PersonEditorWithCoAddressEditorView editor;
  private DelegateMap map;
  private Person person;

  @Test
  public void test() {
    // Test by-object
    assertEquals(Arrays.asList(editor), editors(map, person));
    assertEquals(
        Arrays.asList(editor.addressEditor.addressEditor, editor.addressEditor),
        editors(map, person.getAddress()));

    // Test by-path
    assertEquals(Arrays.asList(editor), editors(map, ""));
    assertEquals(
        Arrays.asList(editor.addressEditor.addressEditor, editor.addressEditor),
        editors(map, "address"));
    assertEquals(Arrays.<Editor<?>>asList(editor.managerName), editors(map, "manager.name"));
  }

  @Test
  public void testSimplePath() {
    assertSame(editor.name, map.getEditorByPath("name").get(0));
    assertSame(editor.managerName, map.getEditorByPath("manager.name").get(0));
  }

  {
    Address a = new Address();
    a.setCity("city");
    a.setStreet("street");

    Person m = new Person();
    m.setName("manager");

    person = new Person();
    person.setName("name");
    person.setAddress(a);
    person.setManager(m);

    editor = new PersonEditorWithCoAddressEditorView();
    driver = new DelegateMapTest_PersonEditorWithCoAddressEditorViewDriver_Impl();
    driver.initialize(editor);
    driver.edit(person);

    map = DelegateMap.of(driver, DelegateMap.IDENTITY);
  }

  private List<Editor<?>> editors(DelegateMap map, Object o) {
    List<Editor<?>> toReturn = new ArrayList<Editor<?>>();
    for (AbstractEditorDelegate<?, ?> delegate : map.get(o)) {
      toReturn.add(delegate.getEditor());
    }
    return toReturn;
  }

  private List<Editor<?>> editors(DelegateMap map, String path) {
    List<Editor<?>> toReturn = new ArrayList<Editor<?>>();
    for (AbstractEditorDelegate<?, ?> delegate : map.getDelegatesByPath(path)) {
      toReturn.add(delegate.getEditor());
    }
    return toReturn;
  }
}
