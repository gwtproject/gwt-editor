# GWT Editor

![GWT3/J2CL compatible](https://img.shields.io/badge/GWT3/J2CL-compatible-brightgreen.svg)  [![License](https://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html) [![Chat on Gitter](https://badges.gitter.im/hal/elemento.svg)](https://gitter.im/gwtproject/gwt-modules) ![CI](https://github.com/gwtproject/gwt-editor/workflows/CI/badge.svg)

A future-proof port of the `com.google.gwt.editor.Editor` GWT module, with no dependency on `gwt-user` (besides the Java Runtime Emulation), to prepare for GWT 3 / J2Cl.

##  Migrating from `com.google.gwt.editor.Editor`

1. Add the dependency to your build.

   For Maven:

   ```xml
   <dependency>
     <groupId>org.gwtproject.editor</groupId>
     <artifactId>gwt-editor</artifactId>
     <version>HEAD-SNAPSHOT</version>
   </dependency>
   ```

    and the processor:

    ```xml
    <dependency>
      <groupId>org.gwtproject.editor</groupId>
      <artifactId>gwt-editor-processor</artifactId>
      <version>HEAD-SNAPSHOT</version>
      <scope>provided</scope>
    </dependency>
    ```

   For Gradle:

   ```gradle
   implementation("org.gwtproject.editor:gwt-editor:HEAD-SNAPSHOT")
   ```

    and the processor:

    ```xml
   ToDo ... 
    <dependency>
      <groupId>org.gwtproject.editor</groupId>
      <artifactId>gwt-editor-processor</artifactId>
      <version>HEAD-SNAPSHOT</version>
      <scope>provided</scope>
    </dependency>
    ```

2. Update your GWT module to use

   ```xml
   <inherits name="org.gwtproject.editor.Editor" />
   ```

3. Change the `import`s in your Java source files:

   ```java
   import org.gwtproject.editor.client.xxx;
   ```

## Instructions

To build gwt-event:

* run `mvn clean verify`

on the parent directory. This will build the artifact and run tests against GWT2. The J2CL test need to be executed with `mvn j2cl:clean` an `mvn j2cl:test` due to a problem with modules that use processors. See: ![https://github.com/Vertispan/j2clmavenplugin/issues/14](https://github.com/Vertispan/j2clmavenplugin/issues/14)

## System Requirements

**GWT Editor requires GWT 2.9.0 or newer!**


## Dependencies

GWT Editor depends on the following module:

* GWT Event.


## Example usage

### the bean
```java
public class Person {

    private int id;
    private String name;
    private boolean active;

    public Person() {
    }

    public Person(int id, String name, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
```

### The component

> this component uses domino-ui

```java
public class PersonComponent implements IsElement<HTMLDivElement>, Editor<Person> {

    @IsDriver
    interface Driver extends SimpleBeanEditorDriver<Person, PersonComponent> {
    }

    private DominoElement<HTMLDivElement> root = DominoElement.of(div());

    IntegerBox id;
    TextBox name;
    CheckBox active;

    private final Driver driver;

    public PersonComponent() {
        driver = new PersonComponent_Driver_Impl();

        id = IntegerBox.create("id");
        name = TextBox.create("name");
        active = CheckBox.create("Is active");

        root.appendChild(Row.create()
                .appendChild(Column.span12()
                        .appendChild(id))
                .appendChild(Column.span12()
                        .appendChild(name))
                .appendChild(Column.span12()
                        .appendChild(active))
        );

        driver.initialize(this);
    }

    public void edit(Person person){
        driver.edit(person);
    }

    public Person save(){
        return driver.flush();
    }

    @Override
    public HTMLDivElement asElement() {
        return root.asElement();
    }
}
```

### And use it like this

```java
PersonComponent personComponent = new PersonComponent();

        Person person = new Person(10, "Ahmad", false);

        DomGlobal.document.body.appendChild(Card.create()
                .appendChild(personComponent)
                .asElement());

        personComponent.edit(person);
        
```
