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
package org.gwtproject.editor.processor.test;

import static com.google.testing.compile.Compiler.javac;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
import java.util.Arrays;
import org.gwtproject.editor.processor.DriverProcessor;
import org.junit.Test;

public class DriverProcessorTest {

  @Test
  public void testEditor01() {
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
  public void testEditor02() {
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
  public void testEditor03() {
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

  @Test
  public void testEditor04() {
    Compilation compilation =
        javac()
            .withProcessors(new DriverProcessor())
            .compile(
                Arrays.asList(
                    JavaFileObjects.forResource(
                        "org/gwtproject/editor/processor/test04/TestEditor04.java")));
    CompilationSubject.assertThat(compilation).succeeded();
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile("org/gwtproject/editor/processor/test04/TestEditor04_Driver_Impl")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test04/result/TestEditor04_Driver_Impl.java"));
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile("org/gwtproject/editor/processor/test04/TestEditor04_active_Context")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test04/result/TestEditor04_active_Context.java"));
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile("org/gwtproject/editor/processor/test04/TestEditor04_age_Context")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test04/result/TestEditor04_age_Context.java"));
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile("org/gwtproject/editor/processor/test04/TestEditor04_email_Context")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test04/result/TestEditor04_email_Context.java"));
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile(
            "org/gwtproject/editor/processor/test04/TestEditor04_firstName_Context")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test04/result/TestEditor04_firstName_Context.java"));
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile("org/gwtproject/editor/processor/test04/TestEditor04_id_Context")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test04/result/TestEditor04_id_Context.java"));
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile("org/gwtproject/editor/processor/test04/TestEditor04_lastName_Context")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test04/result/TestEditor04_lastName_Context.java"));
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile("org/gwtproject/editor/processor/test04/TestEditor04_phone_Context")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test04/result/TestEditor04_phone_Context.java"));
  }

  @Test
  public void testEditor05() {
    Compilation compilation =
        javac()
            .withProcessors(new DriverProcessor())
            .compile(
                Arrays.asList(
                    JavaFileObjects.forResource(
                        "org/gwtproject/editor/processor/test05/TestEditor05.java")));
    CompilationSubject.assertThat(compilation).succeeded();
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile("org/gwtproject/editor/processor/test05/TestEditor05_Driver_Impl")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test05/result/TestEditor05_Driver_Impl.java"));
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile("org/gwtproject/editor/processor/test05/TestEditor05_email_Context")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test05/result/TestEditor05_email_Context.java"));
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile("org/gwtproject/editor/processor/test05/TestEditor05_name_Context")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test05/result/TestEditor05_name_Context.java"));
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile("org/gwtproject/editor/processor/test05/TestEditor05_id_Context")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test05/result/TestEditor05_id_Context.java"));
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile("org/gwtproject/editor/processor/test05/TestEditor05_phone_Context")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test05/result/TestEditor05_phone_Context.java"));
  }

  @Test
  public void testEditor06() {
    Compilation compilation =
        javac()
            .withProcessors(new DriverProcessor())
            .compile(
                Arrays.asList(
                    JavaFileObjects.forResource(
                        "org/gwtproject/editor/processor/test06/TestEditor06.java")));
    CompilationSubject.assertThat(compilation).succeeded();
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile("org/gwtproject/editor/processor/test06/TestEditor06_email_Context")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test06/result/TestEditor06_email_Context.java"));
  }

  @Test
  public void testEditor07() {
    Compilation compilation =
        javac()
            .withProcessors(new DriverProcessor())
            .compile(
                Arrays.asList(
                    JavaFileObjects.forResource(
                        "org/gwtproject/editor/processor/test07/TestEditor07.java")));
    CompilationSubject.assertThat(compilation).succeeded();
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile("org/gwtproject/editor/processor/test07/TestEditor07_Driver_Impl")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test07/result/TestEditor07_Driver_Impl.java"));
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile("org/gwtproject/editor/processor/test07/TestEditor07_email_Context")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test07/result/TestEditor07_email_Context.java"));
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile("org/gwtproject/editor/processor/test07/TestEditor07_name_Context")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test07/result/TestEditor07_name_Context.java"));
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile("org/gwtproject/editor/processor/test07/TestEditor07_id_Context")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test07/result/TestEditor07_id_Context.java"));
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile("org/gwtproject/editor/processor/test07/TestEditor07_phone_Context")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test07/result/TestEditor07_phone_Context.java"));
    CompilationSubject.assertThat(compilation)
        .generatedSourceFile(
            "org/gwtproject/editor/processor/test07/TestEditor07_SimpleBeanEditorDelegate")
        .hasSourceEquivalentTo(
            JavaFileObjects.forResource(
                "org/gwtproject/editor/processor/test07/result/TestEditor07_SimpleBeanEditorDelegate.java"));
  }
}
