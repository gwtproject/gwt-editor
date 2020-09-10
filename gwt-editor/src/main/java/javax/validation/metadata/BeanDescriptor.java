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
package javax.validation.metadata;

import java.util.Set;

/**
 * Describes a constrained Java Bean and the constraints associated to it.
 *
 * @author Emmanuel Bernard
 */
public interface BeanDescriptor extends ElementDescriptor {
  /**
   * Returns <code>true</code> if the bean involves validation:
   *
   * <ul>
   *   <li>a constraint is hosted on the bean itself
   *   <li>a constraint is hosted on one of the bean properties
   *   <li>or a bean property is marked for cascade (<code>@Valid</code>)
   * </ul>
   *
   * @return <code>true</code> if the bean involves validation, <code>false</code> otherwise.
   */
  boolean isBeanConstrained();

  /**
   * Return the property descriptor for a given property. Return <code>null</code> if the property
   * does not exist or has no constraint nor is marked as cascaded (see {@link
   * #getConstrainedProperties()} )
   *
   * <p>The returned object (and associated objects including <code>ConstraintDescriptor</code>s)
   * are immutable.
   *
   * @param propertyName property evaluated
   * @return the property descriptor for a given property.
   * @throws IllegalArgumentException if propertyName is null
   */
  PropertyDescriptor getConstraintsForProperty(String propertyName);

  /**
   * Returns a set of property descriptors having at least one constraint defined or marked as
   * cascaded (<code>@Valid</code>). If not property matches, an empty set is returned.
   */
  Set<PropertyDescriptor> getConstrainedProperties();
}
