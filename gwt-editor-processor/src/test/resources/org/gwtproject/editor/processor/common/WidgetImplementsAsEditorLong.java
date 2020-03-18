package org.gwtproject.editor.processor.common;

import org.gwtproject.editor.client.EditorError;
import org.gwtproject.editor.client.HasEditorErrors;
import org.gwtproject.editor.client.LeafValueEditor;
import org.gwtproject.editor.client.TakesValue;

import java.util.List;

public class WidgetImplementsAsEditorLong
    implements TakesValue<Long>,
               LeafValueEditor<Long>,
               HasEditorErrors<Long> {
  
  private Long value;
  
  public WidgetImplementsAsEditorLong() {
  }
  
  @Override
  public void setValue(Long value) {
    this.value = value;
  }
  
  @Override
  public Long getValue() {
    return value;
  }
  
  @Override
  public void showErrors(List<EditorError> errors) {
    // does nothing!
  }
  
}
