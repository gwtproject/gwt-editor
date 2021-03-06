package org.gwtproject.editor.processor.test08;

import java.lang.Class;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import org.gwtproject.editor.client.Editor;
import org.gwtproject.editor.client.impl.AbstractEditorContext;
import org.gwtproject.editor.processor.common.Model01Dto;

public class TestEditor08_id_Context extends AbstractEditorContext<Long> {
  private final Model01Dto parent;
  
  public TestEditor08_id_Context(Model01Dto parent, Editor<Long> editor, String path) {
    super(editor, path);
    this.parent = parent;
  }
  
  @Override
  public boolean canSetInModel() {
    return parent != null && true && true;
  }
  
  @Override
  public Long checkAssignment(Object value) {
    return (Long) value;
  }
  
  @Override
  public Class getEditedType() {
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
