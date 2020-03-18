package org.gwtproject.editor.processor.common;

import org.gwtproject.editor.client.EditorError;
import org.gwtproject.editor.client.HasEditorErrors;
import org.gwtproject.editor.client.LeafValueEditor;
import org.gwtproject.editor.client.TakesValue;

import java.util.List;

public class WidgetImplementsAsEditor
    implements TakesValue<String>,
               LeafValueEditor<String>,
               HasEditorErrors<String> {
  
  private String value;
  
  public WidgetImplementsAsEditor() {
  }
  
  @Override
  public void setValue(String value) {
    this.value = value;
  }
  
  @Override
  public String getValue() {
    return value;
  }
  
  @Override
  public void showErrors(List<EditorError> errors) {
    // does nothing!
  }
  
}
