package org.gwtproject.editor.processor.test05;

import java.lang.Override;
import java.lang.SuppressWarnings;
import org.gwtproject.editor.client.EditorVisitor;
import org.gwtproject.editor.client.impl.AbstractSimpleBeanEditorDriver;
import org.gwtproject.editor.client.impl.RootEditorContext;
import org.gwtproject.editor.client.impl.SimpleBeanEditorDelegate;
import org.gwtproject.editor.processor.common.Model01Dto;

public class TestEditor05_Driver_Impl extends AbstractSimpleBeanEditorDriver<Model01Dto, TestEditor05> implements TestEditor05.Driver {
  @Override
  @SuppressWarnings("unchecked")
  public void accept(EditorVisitor visitor) {
    RootEditorContext<Model01Dto> ctx = new RootEditorContext<Model01Dto>(getDelegate(), (Class<Model01Dto>)(Class)org.gwtproject.editor.processor.common.Model01Dto.class, getObject());
    ctx.traverse(visitor, getDelegate());
  }
  
  @Override
  protected SimpleBeanEditorDelegate<Model01Dto, TestEditor05> createDelegate() {
    return new TestEditor05_SimpleBeanEditorDelegate();
  }
}