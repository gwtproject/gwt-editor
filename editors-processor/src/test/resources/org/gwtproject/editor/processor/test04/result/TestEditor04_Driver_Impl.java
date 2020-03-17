package org.gwtproject.editor.processor.test04;

import java.lang.Override;
import org.gwtproject.editor.client.EditorVisitor;
import org.gwtproject.editor.client.impl.AbstractSimpleBeanEditorDriver;
import org.gwtproject.editor.client.impl.RootEditorContext;
import org.gwtproject.editor.client.impl.SimpleBeanEditorDelegate;
import org.gwtproject.editor.processor.common.UserDto;

public class TestEditor04_Driver_Impl extends AbstractSimpleBeanEditorDriver<UserDto, TestEditor04> implements TestEditor04.Driver {
  @Override
  public void accept(EditorVisitor visitor) {
    RootEditorContext<UserDto> ctx = new RootEditorContext<UserDto>(getDelegate(), (Class<UserDto>)(Class)org.gwtproject.editor.processor.common.UserDto.class, getObject());
    ctx.traverse(visitor, getDelegate());
  }
  
  @Override
  protected SimpleBeanEditorDelegate<UserDto, TestEditor04> createDelegate() {
    return new TestEditor04_SimpleBeanEditorDelegate();
  }
}