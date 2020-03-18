package org.gwtproject.editor.processor.test04;

import org.gwtproject.editor.processor.common.UserDto;
import org.gwtproject.editor.processor.common.WidgetImplementsAsEditor;
import org.gwtproject.editor.processor.common.WidgetImplementsAsEditorBoolean;
import org.gwtproject.editor.processor.common.WidgetImplementsAsEditorInteger;
import org.gwtproject.editor.processor.common.WidgetImplementsAsEditorLong;
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
  WidgetImplementsAsEditorLong id;
  @Path("email")
  WidgetImplementsAsEditor email;
  @Path("firstName")
  WidgetImplementsAsEditor firstName;
  @Path("lastName")
  WidgetImplementsAsEditor lastName;
  @Path("age")
  WidgetImplementsAsEditorInteger age;
  @Path("phone")
  WidgetImplementsAsEditor phone;
  @Path("active")
  WidgetImplementsAsEditorBoolean active;
  
  public TestEditor04() {
    driver = new TestEditor04_Driver_Impl();
    driver.initialize(this);
  }
  
}