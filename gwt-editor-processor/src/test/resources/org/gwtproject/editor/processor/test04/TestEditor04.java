package org.gwtproject.editor.processor.test04;

import org.gwtproject.editor.processor.common.UserDto;
import org.gwtproject.editor.client.testing.FakeLeafValueEditor;
import org.gwtproject.editor.client.annotation.IsDriver;
import org.gwtproject.editor.client.Editor;
import org.gwtproject.editor.client.Editor.Path;
import org.gwtproject.editor.client.SimpleBeanEditorDriver;

public class TestEditor04 implements Editor<UserDto> {
  
  private Driver driver;
  
  @IsDriver
  interface Driver
      extends SimpleBeanEditorDriver<UserDto, TestEditor04> {
    
  }
  
  @Path("id")
  FakeLeafValueEditor<Long> id;
  @Path("email")
  FakeLeafValueEditor<String> email;
  @Path("firstName")
  FakeLeafValueEditor<String> firstName;
  @Path("lastName")
  FakeLeafValueEditor<String> lastName;
  @Path("age")
  FakeLeafValueEditor<Integer> age;
  @Path("phone")
  FakeLeafValueEditor<String> phone;
  @Path("active")
  FakeLeafValueEditor<Boolean> active;
  
  public TestEditor04() {
    driver = new TestEditor04_Driver_Impl();
    driver.initialize(this);
  }
  
}