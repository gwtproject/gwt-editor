package org.gwtproject.editor.processor.test07;

import java.lang.Override;
import java.lang.String;
import org.gwtproject.editor.client.EditorVisitor;
import org.gwtproject.editor.client.impl.AbstractSimpleBeanEditorDriver;
import org.gwtproject.editor.client.impl.RootEditorContext;
import org.gwtproject.editor.client.impl.SimpleBeanEditorDelegate;
import org.gwtproject.editor.processor.common.Model01Generic02Dto;

public class TestEditor07_Driver_Impl extends AbstractSimpleBeanEditorDriver<Model01Generic02Dto<String>, TestEditor07> implements TestEditor07.Driver {
  @Override
  public void accept(EditorVisitor visitor) {
    RootEditorContext<Model01Generic02Dto<String>> ctx = new RootEditorContext<Model01Generic02Dto<String>>(getDelegate(), (Class<Model01Generic02Dto<String>>)(Class)org.gwtproject.editor.processor.common.Model01Generic02Dto.class, getObject());
    ctx.traverse(visitor, getDelegate());
  }
  
  @Override
  protected SimpleBeanEditorDelegate<Model01Generic02Dto<String>, TestEditor07> createDelegate() {
    return new TestEditor07_SimpleBeanEditorDelegate();
  }
}
