package org.gwtproject.editor.processor.test08;

import java.lang.Override;
import org.gwtproject.editor.client.EditorVisitor;
import org.gwtproject.editor.client.impl.AbstractSimpleBeanEditorDriver;
import org.gwtproject.editor.client.impl.RootEditorContext;
import org.gwtproject.editor.client.impl.SimpleBeanEditorDelegate;
import org.gwtproject.editor.processor.common.Model01Dto;

public class TestEditor08_Driver_Impl extends AbstractSimpleBeanEditorDriver<Model01Dto, TestEditor08> implements TestEditor08.Driver {
  @Override
  public void accept(EditorVisitor visitor) {
    RootEditorContext<Model01Dto> ctx = new RootEditorContext<Model01Dto>(getDelegate(), (Class<Model01Dto>)(Class)org.gwtproject.editor.processor.common.Model01Dto.class, getObject());
    ctx.traverse(visitor, getDelegate());
  }
  
  @Override
  protected SimpleBeanEditorDelegate<Model01Dto, TestEditor08> createDelegate() {
    return new TestEditor08_SimpleBeanEditorDelegate();
  }
}