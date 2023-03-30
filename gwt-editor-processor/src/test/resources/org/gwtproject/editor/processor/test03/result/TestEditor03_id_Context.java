package org.gwtproject.editor.processor.test03;
import java.lang.Class;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import org.gwtproject.editor.client.Editor;
import org.gwtproject.editor.client.impl.AbstractEditorContext;
import org.gwtproject.editor.processor.common.EditorBeanWithInterface;
public class TestEditor03_id_Context extends AbstractEditorContext<String> {
  private final EditorBeanWithInterface parent;
  public TestEditor03_id_Context(EditorBeanWithInterface parent, Editor<String> editor,
                                 String path) {
    super(editor, path);
    this.parent = parent;
  }
  @Override
  public boolean canSetInModel() {
    return parent != null && true && true;
  }
  @Override
  @SuppressWarnings("unchecked")
  public String checkAssignment(Object value) {
    return (String) value;
  }
  @Override
  @SuppressWarnings("unchecked")
  public Class<String> getEditedType() {
    return java.lang.String.class;
  }
  @Override
  public String getFromModel() {
    return (parent != null && true) ? parent.getId() : null;
  }
  @Override
  public void setInModel(String data) {
    parent.setId(data);
  }
}