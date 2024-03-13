package org.gwtproject.editor.processor.test04;
import java.lang.Class;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import org.gwtproject.editor.client.Editor;
import org.gwtproject.editor.client.impl.AbstractEditorContext;
import org.gwtproject.editor.processor.common.UserDto;
public class TestEditor04_id_Context extends AbstractEditorContext<Long> {
  private final UserDto parent;
  public TestEditor04_id_Context(UserDto parent, Editor<Long> editor, String path) {
    super(editor, path);
    this.parent = parent;
  }
  @Override
  public boolean canSetInModel() {
    return parent != null && true && true;
  }
  @Override
  @SuppressWarnings("unchecked")
  public Long checkAssignment(Object value) {
    return (Long) value;
  }
  @Override
  @SuppressWarnings("unchecked")
  public Class<Long> getEditedType() {
    return java.lang.Long.class;
  }
  @Override
  public Long getFromModel() {
    return (parent != null && true) ? parent.getId() : null;
  }
  @Override
  public void setInModel(Long data) {
    parent.setId(data);
  }
}