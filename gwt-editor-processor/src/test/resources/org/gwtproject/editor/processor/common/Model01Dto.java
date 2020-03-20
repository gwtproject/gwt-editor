package org.gwtproject.editor.processor.common;

public class Model01Dto
    implements Model01 {
  
  private Long    id;
  private String  name;
  private String  phone;
  private String  email;
  
  public Model01Dto() {
    // jackson serialization
  }
  
  public Model01Dto(Long id,
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

//  @Override
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
  
  public Model01Dto setEmail(String email) {
    this.email = email;
    return this;
  }
  
}