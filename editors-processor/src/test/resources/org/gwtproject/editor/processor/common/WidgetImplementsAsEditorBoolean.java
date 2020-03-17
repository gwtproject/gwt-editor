package org.gwtproject.editor.processor.common;

import org.gwtproject.editor.client.EditorError;
import org.gwtproject.editor.client.HasEditorErrors;
import org.gwtproject.editor.client.LeafValueEditor;
import org.gwtproject.editor.client.TakesValue;

import java.util.List;

public class WidgetImplementsAsEditorBoolean
    implements TakesValue<Boolean>,
               LeafValueEditor<Boolean>,
               HasEditorErrors<Boolean> {
  
  private Boolean value;
  
  public WidgetImplementsAsEditorBoolean() {
  }
  
  @Override
  public void setValue(Boolean value) {
    this.value = value;
  }
  
  @Override
  public Boolean getValue() {
    return value;
  }
  
  @Override
  public void showErrors(List<EditorError> errors) {
    // does nothing!
  }
  
}
