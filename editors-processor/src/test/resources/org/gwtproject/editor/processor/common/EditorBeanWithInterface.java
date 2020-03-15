package org.gwtproject.editor.processor.common;

public class EditorBeanWithInterface implements IsEditorBean {

  private String id;
  private String label;
  private String icon;
  private String color;
  
  public EditorBeanWithInterface() {
  }
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getLabel() {
    return label;
  }
  
  public void setLabel(String label) {
    this.label = label;
  }
  
  public String getIcon() {
    return icon;
  }
  
  public void setIcon(String icon) {
    this.icon = icon;
  }
  
  public String getColor() {
    return color;
  }
  
  public void setColor(String color) {
    this.color = color;
  }
  
}
