package org.gwtproject.editor.processor.test08;

import org.gwtproject.editor.client.testing.FakeLeafValueEditorWithHasEditorDelegate;
import org.gwtproject.editor.processor.common.Model01Dto;
import org.gwtproject.editor.client.testing.FakeLeafValueEditorWithHasEditorDelegate;
import org.gwtproject.editor.client.annotation.IsDriver;
import org.gwtproject.editor.client.Editor;
import org.gwtproject.editor.client.Editor.Path;
import org.gwtproject.editor.client.SimpleBeanEditorDriver;

public class TestEditor08 implements Editor<Model01Dto> {
  
  private Driver driver;
  
  @IsDriver
  interface Driver
      extends SimpleBeanEditorDriver<Model01Dto, TestEditor08> {
    
  }
  
  FakeLeafValueEditorWithHasEditorDelegate<Long> id;
  FakeLeafValueEditorWithHasEditorDelegate<String> name;
  FakeLeafValueEditorWithHasEditorDelegate<String> phone;
  FakeLeafValueEditorWithHasEditorDelegate<String> email;
  
  public TestEditor08() {
    driver = new TestEditor08_Driver_Impl();
    driver.initialize(this);
  }
  
}