package org.gwtproject.editor.processor.common;

public class Model01Generic02Dto<T>
    implements Model01 {
  
  private Long    id;
  private String  name;
  private String  phone;
  private String  email;
  
  public Model01Generic02Dto() {
    // jackson serialization
  }
  
  public Model01Generic02Dto(Long id,
                    String name,
                    String phone,
                    String email) {
    this.id    = id;
    this.name  = name;
    this.phone = phone;
    this.email = email;
  }
  
  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Model01 setName(String name) {
    this.name = name;
    return this;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }
  
  public Model01Generic02Dto<T> setEmail(String email) {
    this.email = email;
    return this;
  }
  
}