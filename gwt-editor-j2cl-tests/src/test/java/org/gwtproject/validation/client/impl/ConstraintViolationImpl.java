/*
 * Copyright © 2018 The GWT Project Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gwtproject.validation.client.impl;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.util.Objects;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;

public final class ConstraintViolationImpl<T> implements ConstraintViolation<T>, Serializable {
  private static final long serialVersionUID = 1L;
  private final String message;
  private final String messageTemplate;
  private final T rootBean;
  private final Class<T> rootBeanClass;
  private final Object leafBean;
  private final Path propertyPath;
  private final Object invalidValue;
  private final ElementType elementType;
  private final ConstraintDescriptor<?> constraintDescriptor;

  public static <T> ConstraintViolationImpl.Builder<T> builder() {
    return new ConstraintViolationImpl.Builder();
  }

  private ConstraintViolationImpl(
      String message,
      String messageTemplate,
      T rootBean,
      Class<T> rootBeanClass,
      Object leafBean,
      Path propertyPath,
      Object invalidValue,
      ElementType elementType,
      ConstraintDescriptor<?> constraintDescriptor) {
    this.message = message;
    this.messageTemplate = messageTemplate;
    this.rootBean = rootBean;
    this.rootBeanClass = rootBeanClass;
    this.leafBean = leafBean;
    this.propertyPath = propertyPath;
    this.invalidValue = invalidValue;
    this.elementType = elementType;
    this.constraintDescriptor = constraintDescriptor;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (!(o instanceof ConstraintViolationImpl)) {
      return false;
    } else {
      ConstraintViolationImpl<?> other = (ConstraintViolationImpl) o;
      return Objects.equals(this.message, other.message)
          && Objects.equals(this.propertyPath, other.propertyPath)
          && Objects.equals(this.rootBean, other.rootBean)
          && Objects.equals(this.leafBean, other.leafBean)
          && Objects.equals(this.elementType, other.elementType)
          && Objects.equals(this.invalidValue, other.invalidValue);
    }
  }

  public ConstraintDescriptor<?> getConstraintDescriptor() {
    return this.constraintDescriptor;
  }

  public Object getInvalidValue() {
    return this.invalidValue;
  }

  public Object getLeafBean() {
    return this.leafBean;
  }

  public String getMessage() {
    return this.message;
  }

  public String getMessageTemplate() {
    return this.messageTemplate;
  }

  public Path getPropertyPath() {
    return this.propertyPath;
  }

  public T getRootBean() {
    return this.rootBean;
  }

  public Class<T> getRootBeanClass() {
    return this.rootBeanClass;
  }

  public int hashCode() {
    int result = this.message != null ? this.message.hashCode() : 0;
    result = 31 * result + (this.propertyPath != null ? this.propertyPath.hashCode() : 0);
    result = 31 * result + (this.rootBean != null ? this.rootBean.hashCode() : 0);
    result = 31 * result + (this.leafBean != null ? this.leafBean.hashCode() : 0);
    result = 31 * result + (this.elementType != null ? this.elementType.hashCode() : 0);
    result = 31 * result + (this.invalidValue != null ? this.invalidValue.hashCode() : 0);
    return result;
  }

  public String toString() {
    return "ConstraintViolationImpl(message= "
        + this.message
        + ", path= "
        + this.propertyPath
        + ", invalidValue="
        + this.invalidValue
        + ", desc="
        + this.constraintDescriptor
        + ", elementType="
        + this.elementType
        + ")";
  }

  public static class Builder<T> {
    private String message;
    private String messageTemplate;
    private T rootBean;
    private Class<T> rootBeanClass;
    private Object leafBean;
    private Path propertyPath;
    private Object invalidValue;
    private ElementType elementType;
    private ConstraintDescriptor<?> constraintDescriptor;

    public Builder() {}

    public ConstraintViolationImpl<T> build() {
      return new ConstraintViolationImpl(
          this.message,
          this.messageTemplate,
          this.rootBean,
          this.rootBeanClass,
          this.leafBean,
          this.propertyPath,
          this.invalidValue,
          this.elementType,
          this.constraintDescriptor);
    }

    public ConstraintViolationImpl.Builder<T> setConstraintDescriptor(
        ConstraintDescriptor<?> constraintDescriptor) {
      this.constraintDescriptor = constraintDescriptor;
      return this;
    }

    public ConstraintViolationImpl.Builder<T> setElementType(ElementType elementType) {
      this.elementType = elementType;
      return this;
    }

    public ConstraintViolationImpl.Builder<T> setInvalidValue(Object invalidValue) {
      this.invalidValue = invalidValue;
      return this;
    }

    public ConstraintViolationImpl.Builder<T> setLeafBean(Object leafBean) {
      this.leafBean = leafBean;
      return this;
    }

    public ConstraintViolationImpl.Builder<T> setMessage(String message) {
      this.message = message;
      return this;
    }

    public ConstraintViolationImpl.Builder<T> setMessageTemplate(String messageTemplate) {
      this.messageTemplate = messageTemplate;
      return this;
    }

    public ConstraintViolationImpl.Builder<T> setPropertyPath(Path propertyPath) {
      this.propertyPath = propertyPath;
      return this;
    }

    public ConstraintViolationImpl.Builder<T> setRootBean(T rootBean) {
      this.rootBean = rootBean;
      return this;
    }

    public ConstraintViolationImpl.Builder<T> setRootBeanClass(Class<T> rootBeanClass) {
      this.rootBeanClass = rootBeanClass;
      return this;
    }
  }
}
