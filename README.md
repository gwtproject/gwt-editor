# GWT-EDITORS

### Example usage

#### the bean
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

#### The component

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

#### And use it like this

```java
PersonComponent personComponent = new PersonComponent();

        Person person = new Person(10, "Ahmad", false);

        DomGlobal.document.body.appendChild(Card.create()
                .appendChild(personComponent)
                .asElement());

        personComponent.edit(person);
        
```