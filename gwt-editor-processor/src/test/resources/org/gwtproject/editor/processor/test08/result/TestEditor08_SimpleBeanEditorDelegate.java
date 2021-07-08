package org.gwtproject.editor.processor.test08;
import java.lang.Object;
import java.lang.Override;
import org.gwtproject.editor.client.Editor;
import org.gwtproject.editor.client.EditorVisitor;
import org.gwtproject.editor.client.impl.SimpleBeanEditorDelegate;
import org.gwtproject.editor.client.testing.FakeLeafValueEditorWithHasEditorDelegate_Long_SimpleBeanEditorDelegate;
import org.gwtproject.editor.client.testing.FakeLeafValueEditorWithHasEditorDelegate_String_SimpleBeanEditorDelegate;
import org.gwtproject.editor.processor.common.Model01Dto;
public class TestEditor08_SimpleBeanEditorDelegate extends SimpleBeanEditorDelegate {
  private TestEditor08 editor;
  private Model01Dto object;
  private SimpleBeanEditorDelegate idDelegate;
  private SimpleBeanEditorDelegate nameDelegate;
  private SimpleBeanEditorDelegate phoneDelegate;
  private SimpleBeanEditorDelegate emailDelegate;
  @Override
  protected TestEditor08 getEditor() {
    return editor;
  }
  @Override
  protected void setEditor(Editor editor) {
    this.editor = (TestEditor08) editor;
  }
  @Override
  public Model01Dto getObject() {
    return object;
  }
  @Override
  protected void setObject(Object object) {
    this.object = (Model01Dto) object;
  }
  @Override
  protected void initializeSubDelegates() {
    if (editor.id != null) {
      idDelegate = new FakeLeafValueEditorWithHasEditorDelegate_Long_SimpleBeanEditorDelegate();
      addSubDelegate(idDelegate, appendPath("id"), editor.id);
    }
    if (editor.name != null) {
      nameDelegate = new FakeLeafValueEditorWithHasEditorDelegate_String_SimpleBeanEditorDelegate();
      addSubDelegate(nameDelegate, appendPath("name"), editor.name);
    }
    if (editor.phone != null) {
      phoneDelegate = new FakeLeafValueEditorWithHasEditorDelegate_String_SimpleBeanEditorDelegate();
      addSubDelegate(phoneDelegate, appendPath("phone"), editor.phone);
    }
    if (editor.email != null) {
      emailDelegate = new FakeLeafValueEditorWithHasEditorDelegate_String_SimpleBeanEditorDelegate();
      addSubDelegate(emailDelegate, appendPath("email"), editor.email);
    }
  }
  @Override
  public void accept(EditorVisitor visitor) {
    if (idDelegate != null) {
      TestEditor08_id_Context ctx = new TestEditor08_id_Context(getObject(), editor.id, appendPath("id"));
      ctx.setEditorDelegate(idDelegate);
      ctx.traverse(visitor, idDelegate);
    }
    if (nameDelegate != null) {
      TestEditor08_name_Context ctx = new TestEditor08_name_Context(getObject(), editor.name, appendPath("name"));
      ctx.setEditorDelegate(nameDelegate);
      ctx.traverse(visitor, nameDelegate);
    }
    if (phoneDelegate != null) {
      TestEditor08_phone_Context ctx = new TestEditor08_phone_Context(getObject(), editor.phone, appendPath("phone"));
      ctx.setEditorDelegate(phoneDelegate);
      ctx.traverse(visitor, phoneDelegate);
    }
    if (emailDelegate != null) {
      TestEditor08_email_Context ctx = new TestEditor08_email_Context(getObject(), editor.email, appendPath("email"));
      ctx.setEditorDelegate(emailDelegate);
      ctx.traverse(visitor, emailDelegate);
    }
  }
}
