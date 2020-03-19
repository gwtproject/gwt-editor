package org.gwtproject.editor.processor.test03;

import org.gwtproject.editor.processor.common.EditorBeanWithInterface;
import org.gwtproject.editor.client.testing.FakeLeafValueEditor;
import org.gwtproject.editor.client.annotation.IsDriver;
import org.gwtproject.editor.client.Editor;
import org.gwtproject.editor.client.Editor.Path;
import org.gwtproject.editor.client.SimpleBeanEditorDriver;

public class TestEditor03 implements Editor<EditorBeanWithInterface> {
  
  private Driver driver;
  
  @IsDriver
  interface Driver
      extends SimpleBeanEditorDriver<EditorBeanWithInterface, TestEditor03> {
    
  }
  
  @Path("id")
  FakeLeafValueEditor<String> id;
  @Path("label")
  FakeLeafValueEditor<String> label;
  
  public TestEditor03() {
    driver = new TestEditor03_Driver_Impl();
    driver.initialize(this);
  }
  
}