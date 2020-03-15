package org.gwtproject.editor.processor.test03;

import java.lang.Override;
import org.gwtproject.editor.client.EditorVisitor;
import org.gwtproject.editor.client.impl.AbstractSimpleBeanEditorDriver;
import org.gwtproject.editor.client.impl.RootEditorContext;
import org.gwtproject.editor.client.impl.SimpleBeanEditorDelegate;
import org.gwtproject.editor.processor.common.EditorBeanWithInterface;

public class TestEditor03_Driver_Impl extends AbstractSimpleBeanEditorDriver<EditorBeanWithInterface, TestEditor03> implements TestEditor03.Driver {
  @Override
  public void accept(EditorVisitor visitor) {
    RootEditorContext<EditorBeanWithInterface> ctx = new RootEditorContext<EditorBeanWithInterface>(getDelegate(), (Class<EditorBeanWithInterface>)(Class)org.gwtproject.editor.processor.common.EditorBeanWithInterface.class, getObject());
    ctx.traverse(visitor, getDelegate());
  }
  
  @Override
  protected SimpleBeanEditorDelegate<EditorBeanWithInterface, TestEditor03> createDelegate() {
    return new TestEditor03_SimpleBeanEditorDelegate();
  }
}
