package org.gwtproject.editor.processor.test07;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import org.gwtproject.editor.client.Editor;
import org.gwtproject.editor.client.EditorVisitor;
import org.gwtproject.editor.client.impl.SimpleBeanEditorDelegate;
import org.gwtproject.editor.processor.common.Model01Generic02Dto;

public class TestEditor07_SimpleBeanEditorDelegate extends SimpleBeanEditorDelegate {
  private TestEditor07 editor;
  private Model01Generic02Dto<String> object;
  
  @Override()
  protected TestEditor07 getEditor() {
    return editor;
  }
  
  @Override()
  protected void setEditor(Editor editor) {
    this.editor = (TestEditor07)editor;
  }
  
  @Override()
  public Model01Generic02Dto<String> getObject() {
    return object;
  }
  
  @Override()
  protected void setObject(Object object) {
    this.object = (Model01Generic02Dto<String>)object;
  }
  
  @Override()
  protected void initializeSubDelegates() {
  }
  
  @Override()
  public void accept(EditorVisitor visitor) {
    {
      TestEditor07_id_Context ctx = new TestEditor07_id_Context(getObject(), editor.id, appendPath("id"));
      ctx.traverse(visitor, null);
    }
    {
      TestEditor07_name_Context ctx = new TestEditor07_name_Context(getObject(), editor.name, appendPath("name"));
      ctx.traverse(visitor, null);
    }
    {
      TestEditor07_phone_Context ctx = new TestEditor07_phone_Context(getObject(), editor.phone, appendPath("phone"));
      ctx.traverse(visitor, null);
    }
    {
      TestEditor07_email_Context ctx = new TestEditor07_email_Context(getObject(), editor.email, appendPath("email"));
      ctx.traverse(visitor, null);
    }
  }
}