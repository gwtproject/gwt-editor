package org.gwtproject.editor.processor.test04;

import java.lang.Boolean;
import java.lang.Class;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import org.gwtproject.editor.client.Editor;
import org.gwtproject.editor.client.impl.AbstractEditorContext;
import org.gwtproject.editor.processor.common.UserDto;

public class TestEditor04_active_Context extends AbstractEditorContext<Boolean> {
  private final UserDto parent;
  
  public TestEditor04_active_Context(UserDto parent, Editor<Boolean> editor, String path) {
    super(editor, path);
    this.parent = parent;
  }
  
  @Override
  public boolean canSetInModel() {
    return parent != null && true && true;
  }
  
  @Override
  public Boolean checkAssignment(Object value) {
    return (Boolean) value;
  }
  
  @Override
  public Class getEditedType() {
    return java.lang.Boolean.class;
  }
  
  @Override
  public Boolean getFromModel() {
    return (parent != null && true) ? parent.isActive() : null;
  }
  
  @Override
  public void setInModel(Boolean data) {
    parent.setActive(data);
  }
}
