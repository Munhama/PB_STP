package sample;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button deleteButton;

    @FXML
    private Button clearButton;

    @FXML
    private TextField numberField;

    @FXML
    private Button findButton;

    @FXML
    private TableView<Person> phoneBookField;

    @FXML
    private TableColumn<Person, String> tableNumber;

    @FXML
    private TableColumn<Person, String> tableName;

    @FXML
    private Button createButton;

    @FXML
    private TextField nameField;

    @FXML
    private Button addButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button changeButton;


    @FXML
    void initialize() {

        tableName.setCellValueFactory(
                new PropertyValueFactory<Person, String>("name")
        );
        tableNumber.setCellValueFactory(
                new PropertyValueFactory<Person, String>("number")
        );

        ObservableList<Person> data =
            FXCollections.observableArrayList(
                    new Person("Vlasd", "1231231231")
            );

Multimap<String, String> map = ArrayListMultimap.create();
Collection<String> col;
map.put("Vlasd", "1231231231");

        phoneBookField.setItems(data);

        addButton.setOnAction(actionEvent -> {
            String name = nameField.getText();
            String number = numberField.getText();

            if (!name.equals("") && !number.equals("")) {
                data.clear();
                map.put(name, number);
                Set<String> keys = map.keySet();
                for (String key : keys) {
                    Collection<String> values = map.get(key);
                    for (String val : values) {
                        data.add(new Person(key, val));
                    }
                }
                System.out.println(map);
            }
        });
    }

    public static class Person{
        private String name;
        private String number;

        Person(String name, String number){
            this.name = name;
            this.number = number;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

