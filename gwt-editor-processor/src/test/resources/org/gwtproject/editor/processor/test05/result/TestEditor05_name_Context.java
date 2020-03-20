package org.gwtproject.editor.processor.test05;

import java.lang.Class;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import org.gwtproject.editor.client.Editor;
import org.gwtproject.editor.client.impl.AbstractEditorContext;
import org.gwtproject.editor.processor.common.Model01Dto;

public class TestEditor05_name_Context extends AbstractEditorContext<String> {
  private final Model01Dto parent;
  
  public TestEditor05_name_Context(Model01Dto parent, Editor<String> editor, String path) {
    super(editor, path);
    this.parent = parent;
  }
  
  @Override
  public boolean canSetInModel() {
    return parent != null && true && true;
  }
  
  @Override
  public String checkAssignment(Object value) {
    return (String) value;
  }
  
  @Override
  public Class getEditedType() {
    return java.lang.String.class;
  }
  
  @Override
  public String getFromModel() {
    return (parent != null && true) ? parent.getName() : null;
  }
  
  @Override
  public void setInModel(String data) {
    parent.setName(data);
  }
}
