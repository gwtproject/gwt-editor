package org.gwtproject.editor.processor.common;

import org.gwtproject.editor.client.EditorError;
import org.gwtproject.editor.client.HasEditorErrors;
import org.gwtproject.editor.client.LeafValueEditor;
import org.gwtproject.editor.client.TakesValue;

import java.util.List;

public class WidgetImplementsAsEditorInteger
    implements TakesValue<Integer>,
               LeafValueEditor<Integer>,
               HasEditorErrors<Integer> {
  
  private Integer value;
  
  public WidgetImplementsAsEditorInteger() {
  }
  
  @Override
  public void setValue(Integer value) {
    this.value = value;
  }
  
  @Override
  public Integer getValue() {
    return value;
  }
  
  @Override
  public void showErrors(List<EditorError> errors) {
    // does nothing!
  }
  
}
