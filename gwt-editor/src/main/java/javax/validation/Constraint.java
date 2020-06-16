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
package javax.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Link between a constraint annotation and its constraint validation implementations.
 *
 * <p>A given constraint annotation should be annotated by a <code>@Constraint</code> annotation
 * which refers to its list of constraint validation implementations.
 *
 * @author Emmanuel Bernard
 * @author Gavin King
 * @author Hardy Ferentschik
 */
@Documented
@Target({ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface Constraint {
  /**
   * <code>ConstraintValidator</code> classes must reference distinct target types. If two <code>
   * ConstraintValidator</code> refer to the same type, an exception will occur.
   *
   * @return array of ConstraintValidator classes implementing the constraint
   */
  public Class<? extends ConstraintValidator<?, ?>>[] validatedBy();
}
