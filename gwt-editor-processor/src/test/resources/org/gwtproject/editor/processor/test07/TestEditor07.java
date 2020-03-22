package org.gwtproject.editor.processor.test07;

import org.gwtproject.editor.processor.common.Model01Generic02Dto;
import org.gwtproject.editor.client.testing.FakeLeafValueEditor;
import org.gwtproject.editor.client.annotation.IsDriver;
import org.gwtproject.editor.client.Editor;
import org.gwtproject.editor.client.Editor.Path;
import org.gwtproject.editor.client.SimpleBeanEditorDriver;

public class TestEditor07 implements Editor<Model01Generic02Dto<String>> {
  
  private Driver driver;
  
  @IsDriver
  interface Driver
      extends SimpleBeanEditorDriver<Model01Generic02Dto<String>, TestEditor07> {
    
  }
  
  FakeLeafValueEditor<Long> id;
  FakeLeafValueEditor<String> name;
  FakeLeafValueEditor<String> phone;
  FakeLeafValueEditor<String> email;
  
  public TestEditor07() {
    driver = new TestEditor07_Driver_Impl();
    driver.initialize(this);
  }
  
}