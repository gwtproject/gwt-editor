package org.gwtproject.editor.processor.test04;

import java.lang.Class;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import org.gwtproject.editor.client.Editor;
import org.gwtproject.editor.client.impl.AbstractEditorContext;
import org.gwtproject.editor.processor.common.UserDto;

public class TestEditor04_lastName_Context extends AbstractEditorContext<String> {
  private final UserDto parent;
  
  public TestEditor04_lastName_Context(UserDto parent, Editor<String> editor, String path) {
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
    return (parent != null && true) ? parent.getLastName() : null;
  }
  
  @Override
  public void setInModel(String data) {
    parent.setLastName(data);
  }
}
