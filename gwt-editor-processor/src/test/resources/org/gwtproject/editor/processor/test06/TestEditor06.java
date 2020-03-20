package org.gwtproject.editor.processor.test06;

import org.gwtproject.editor.processor.common.BadModel01Dto;
import org.gwtproject.editor.client.testing.FakeLeafValueEditor;
import org.gwtproject.editor.client.annotation.IsDriver;
import org.gwtproject.editor.client.Editor;
import org.gwtproject.editor.client.Editor.Path;
import org.gwtproject.editor.client.SimpleBeanEditorDriver;

public class TestEditor06 implements Editor<BadModel01Dto> {
  
  private Driver driver;
  
  @IsDriver
  interface Driver
      extends SimpleBeanEditorDriver<BadModel01Dto, TestEditor06> {
    
  }
  
  FakeLeafValueEditor<Long> id;
  FakeLeafValueEditor<String> name;
  FakeLeafValueEditor<String> phone;
  FakeLeafValueEditor<String> email;
  
  public TestEditor06() {
    driver = new TestEditor06_Driver_Impl();
    driver.initialize(this);
  }
  
}