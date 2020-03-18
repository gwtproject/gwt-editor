package org.gwtproject.editor.processor.test01;

import java.lang.Override;
import org.gwtproject.editor.client.EditorVisitor;
import org.gwtproject.editor.client.impl.AbstractSimpleBeanEditorDriver;
import org.gwtproject.editor.client.impl.RootEditorContext;
import org.gwtproject.editor.client.impl.SimpleBeanEditorDelegate;
import org.gwtproject.editor.processor.common.EditorBean;

public class TestEditor01_Driver_Impl extends AbstractSimpleBeanEditorDriver<EditorBean, TestEditor01> implements TestEditor01.Driver {
  @Override
  public void accept(EditorVisitor visitor) {
    RootEditorContext<EditorBean> ctx = new RootEditorContext<EditorBean>(getDelegate(), (Class<EditorBean>)(Class)org.gwtproject.editor.processor.common.EditorBean.class, getObject());
    ctx.traverse(visitor, getDelegate());
  }
  
  @Override
  protected SimpleBeanEditorDelegate<EditorBean, TestEditor01> createDelegate() {
    return new TestEditor01_SimpleBeanEditorDelegate();
  }
}
