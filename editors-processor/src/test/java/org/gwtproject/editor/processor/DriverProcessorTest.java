/*
 * Copyright © 2018 The GWT Authors
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
package org.gwtproject.editor.processor;

import static com.google.testing.compile.Compiler.javac;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class DriverProcessorTest {

  @Test
  void testEditor01() {
    Compilation compilation =
        javac()
            .withProcessors(new DriverProcessor())
            .compile(
                Arrays.asList(
                    JavaFileObjects.forResource(
                        "org/gwtproject/editor/processor/test01/TestEditor01.java")));
    CompilationSubject.assertThat(compilation).succeeded();
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile("org/gwtproject/editor/processor/test01/TestEditor01_Driver_Impl")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test01/result/TestEditor01_Driver_Impl.java"));
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile("org/gwtproject/editor/processor/test01/TestEditor01_id_Context")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test01/result/TestEditor01_id_Context.java"));
  }

  @Test
  void testEditor02() {
    Compilation compilation =
        javac()
            .withProcessors(new DriverProcessor())
            .compile(
                Arrays.asList(
                    JavaFileObjects.forResource(
                        "org/gwtproject/editor/processor/test02/TestEditor02.java")));
    CompilationSubject.assertThat(compilation).succeeded();
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile("org/gwtproject/editor/processor/test02/TestEditor02_Driver_Impl")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test02/result/TestEditor02_Driver_Impl.java"));
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile("org/gwtproject/editor/processor/test02/TestEditor02_id_Context")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test02/result/TestEditor02_id_Context.java"));
  }

  @Test
  void testEditor03() {
    Compilation compilation =
        javac()
            .withProcessors(new DriverProcessor())
            .compile(
                Arrays.asList(
                    JavaFileObjects.forResource(
                        "org/gwtproject/editor/processor/test03/TestEditor03.java")));
    CompilationSubject.assertThat(compilation).succeeded();
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile("org/gwtproject/editor/processor/test03/TestEditor03_Driver_Impl")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test03/result/TestEditor03_Driver_Impl.java"));
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile("org/gwtproject/editor/processor/test03/TestEditor03_id_Context")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test03/result/TestEditor03_id_Context.java"));
  }
}
