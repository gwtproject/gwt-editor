package org.gwtproject.editor.processor.test04;

import java.lang.Class;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import org.gwtproject.editor.client.Editor;
import org.gwtproject.editor.client.impl.AbstractEditorContext;
import org.gwtproject.editor.processor.common.UserDto;

public class TestEditor04_age_Context extends AbstractEditorContext<Integer> {
  private final UserDto parent;
  
  public TestEditor04_age_Context(UserDto parent, Editor<Integer> editor, String path) {
    super(editor, path);
    this.parent = parent;
  }
  
  @Override
  public boolean canSetInModel() {
    return parent != null && true && true;
  }
  
  @Override
  public Integer checkAssignment(Object value) {
    return (Integer) value;
  }
  
  @Override
  public Class getEditedType() {
    return java.lang.Integer.class;
  }
  
  @Override
  public Integer getFromModel() {
    return (parent != null && true) ? parent.getAge() : null;
  }
  
  @Override
  public void setInModel(Integer data) {
    parent.setAge(data);
  }
}
