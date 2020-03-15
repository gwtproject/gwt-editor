package org.gwtproject.editor.processor.test02;

import java.lang.Override;
import org.gwtproject.editor.client.EditorVisitor;
import org.gwtproject.editor.client.impl.AbstractSimpleBeanEditorDriver;
import org.gwtproject.editor.client.impl.RootEditorContext;
import org.gwtproject.editor.client.impl.SimpleBeanEditorDelegate;
import org.gwtproject.editor.processor.common.EditorBeanWithInterface;

public class TestEditor02_Driver_Impl extends AbstractSimpleBeanEditorDriver<EditorBeanWithInterface, TestEditor02> implements TestEditor02.Driver {
  @Override
  public void accept(EditorVisitor visitor) {
    RootEditorContext<EditorBeanWithInterface> ctx = new RootEditorContext<EditorBeanWithInterface>(getDelegate(), (Class<EditorBeanWithInterface>)(Class)org.gwtproject.editor.processor.common.EditorBeanWithInterface.class, getObject());
    ctx.traverse(visitor, getDelegate());
  }
  
  @Override
  protected SimpleBeanEditorDelegate<EditorBeanWithInterface, TestEditor02> createDelegate() {
    return new TestEditor02_SimpleBeanEditorDelegate();
  }
}
