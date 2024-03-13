package org.gwtproject.editor.processor.test07;
import java.lang.Class;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import org.gwtproject.editor.client.Editor;
import org.gwtproject.editor.client.impl.AbstractEditorContext;
import org.gwtproject.editor.processor.common.Model01Generic02Dto;
public class TestEditor07_name_Context extends AbstractEditorContext<String> {
  private final Model01Generic02Dto<String> parent;
  public TestEditor07_name_Context(Model01Generic02Dto<String> parent, Editor<String> editor,
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
    return (parent != null && true) ? parent.getName() : null;
  }
  @Override
  public void setInModel(String data) {
    parent.setName(data);
  }
}