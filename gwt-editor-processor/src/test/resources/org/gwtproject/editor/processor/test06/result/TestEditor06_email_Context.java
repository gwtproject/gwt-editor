package org.gwtproject.editor.processor.test06;
import java.lang.Class;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import org.gwtproject.editor.client.Editor;
import org.gwtproject.editor.client.impl.AbstractEditorContext;
import org.gwtproject.editor.processor.common.BadModel01Dto;
public class TestEditor06_email_Context extends AbstractEditorContext<String> {
  private final BadModel01Dto parent;
  public TestEditor06_email_Context(BadModel01Dto parent, Editor<String> editor, String path) {
    super(editor, path);
    this.parent = parent;
  }
  @Override
  public boolean canSetInModel() {
    return parent != null && false && true;
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
    return (parent != null && true) ? parent.getEmail() : null;
  }
  @Override
  public void setInModel(String data) {
    throw new UnsupportedOperationException();
  }
}