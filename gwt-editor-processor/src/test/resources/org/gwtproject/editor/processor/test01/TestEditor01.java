package org.gwtproject.editor.processor.test01;

import org.gwtproject.editor.processor.common.EditorBean;
import org.gwtproject.editor.client.testing.FakeLeafValueEditor;
import org.gwtproject.editor.client.annotation.IsDriver;
import org.gwtproject.editor.client.Editor;
import org.gwtproject.editor.client.SimpleBeanEditorDriver;

public class TestEditor01 implements Editor<EditorBean> {
  
  private Driver driver;
  
  @IsDriver
  interface Driver
      extends SimpleBeanEditorDriver<EditorBean, TestEditor01> {
    
  }
  
  FakeLeafValueEditor<String> id;
  FakeLeafValueEditor<String> label;
  
  public TestEditor01() {
    driver = new TestEditor01_Driver_Impl();
    driver.initialize(this);
  }
  
}