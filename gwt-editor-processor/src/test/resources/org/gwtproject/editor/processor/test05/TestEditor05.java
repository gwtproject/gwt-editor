package org.gwtproject.editor.processor.test05;

import org.gwtproject.editor.processor.common.Model01Dto;
import org.gwtproject.editor.client.testing.FakeLeafValueEditor;
import org.gwtproject.editor.client.annotation.IsDriver;
import org.gwtproject.editor.client.Editor;
import org.gwtproject.editor.client.Editor.Path;
import org.gwtproject.editor.client.SimpleBeanEditorDriver;

public class TestEditor05 implements Editor<Model01Dto> {
  
  private Driver driver;
  
  @IsDriver
  interface Driver
      extends SimpleBeanEditorDriver<Model01Dto, TestEditor05> {
    
  }
  
  FakeLeafValueEditor<Long> id;
  FakeLeafValueEditor<String> name;
  FakeLeafValueEditor<String> phone;
  FakeLeafValueEditor<String> email;
  
  public TestEditor05() {
    driver = new TestEditor05_Driver_Impl();
    driver.initialize(this);
  }
  
}