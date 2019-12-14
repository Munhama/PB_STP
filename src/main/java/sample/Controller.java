package sample;

import com.google.common.collect.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;

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
    private TextField nameField;

    @FXML
    private Button addButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button changeButton;

    String oldName = "";
    String oldNumber = "";

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

        SortedSetMultimap<String, String> map = TreeMultimap.create();
        map.put("Vlasd", "1231231231");

        String numberMatcher = "^-?\\d+$";

        phoneBookField.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        phoneBookField.setItems(data);

        addButton.setOnAction(actionEvent -> {
            String name = nameField.getText();
            String number = numberField.getText();

            if (!name.equals("") && number.matches(numberMatcher) && !number.equals("")) {
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

        clearButton.setOnAction(actionEvent -> {
            data.clear();
            map.clear();
        });

        changeButton.setOnAction(actionEvent -> {
            nameField.setDisable(true);
            Person p = phoneBookField.getSelectionModel().getSelectedItem();
            oldName = p.getName();
            oldNumber = p.getNumber();
            nameField.setText(p.getName());
            numberField.setText(p.getNumber());
        });

        saveButton.setOnAction(actionEvent -> {
            Person newPerson = new Person(nameField.getText(), numberField.getText());
            Iterable itr = map.get(oldName);

            List<String> result = Lists.newArrayList(itr);
            result.set(result.indexOf(oldNumber), newPerson.getNumber());
            map.replaceValues(oldName, result);
            if (newPerson.getNumber().matches(numberMatcher) && !newPerson.getNumber().equals("")) {
                data.clear();
                Set<String> keys = map.keySet();
                for (String key : keys) {
                    Collection<String> values = map.get(key);
                    for (String val : values) {
                        data.add(new Person(key, val));
                    }
                }
            }
            nameField.setDisable(false);
            oldName = "";
            oldNumber = "";
        });

        deleteButton.setOnAction(actionEvent -> {
            Person p = phoneBookField.getSelectionModel().getSelectedItem();

            map.remove(p.getName(), p.getNumber());

            data.clear();
            Set<String> keys = map.keySet();
            for (String key : keys) {
                Collection<String> values = map.get(key);
                for (String val : values) {
                    data.add(new Person(key, val));
                }
            }
        });

        findButton.setOnAction(actionEvent -> {
            phoneBookField.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            String name = nameField.getText();

            phoneBookField.getSelectionModel().clearSelection();
            phoneBookField.requestFocus();

            Iterable itr = map.get(name);
            List<String> result = Lists.newArrayList(itr);
            boolean flag = true;
            for (Person p : data) {
                if (p.getName().equals(name)) {
                    phoneBookField.getSelectionModel().select(p);
                    if (flag) {
                        phoneBookField.scrollTo(p);
                        flag = false;
                    }
                }
            }
        });
    }

    public static class Person {
        private String name;
        private String number;

        Person(String name, String number) {
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

