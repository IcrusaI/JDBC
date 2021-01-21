package jdbc;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.sql.SQLException;
import java.util.Random;

public class App extends Application {
    @FXML
    private TableView<Book> tableView;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField fieldSearchById;

    private final Database db = new Database();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("Books");
        stage.setWidth(1000);
        stage.setHeight(700);

        stage.show();
    }

    private void initTable() {
        ObservableList<TableColumn<Book, ?>> columns = tableView.getColumns();

        for (int i = 0; i < columns.size(); i++) {
            TableColumn<Book, ?> column = columns.get(i);

            column.setCellValueFactory(new PropertyValueFactory<>(column.getId()));
        }
    }

    public void onClickSearch() throws Exception {
        initTable();
        getData();
    }

    private void getData() {
        ObservableList<Book> books = null;

        try {
            if (fieldSearchById.getText().equals("")) {
                books = FXCollections.observableArrayList(db.getBooks());
            } else {
                books = FXCollections.observableArrayList(db.getBook(fieldSearchById.getText()));
            }
        } catch (Exception e) {
            showAlertError(e.getMessage());
        }


        tableView.setItems(books);
    }

    public void onClickDelete() throws Exception {
        try {
            String id = fieldSearchById.getText();

            Book book = db.getBook(id);
            fieldSearchById.setText("");

            db.deleteBook(id);

            getData();

            showAlertInfo("Успешно");
        } catch (Exception e) {
            showAlertError(e.getMessage());
        }
    }

    public void onClickCreate() throws SQLException, ClassNotFoundException {
        Book book = new Book();

        book.setTitle(randomString(16));
        book.setPages(new Random().nextInt());

        db.createBook(book);
        getData();
        showAlertInfo("Успешно");
    }

    public void onClickModify() throws Exception {
        String id = fieldSearchById.getText();

        Book book = db.getBook(id);
        fieldSearchById.setText("");

        book.setPages(new Random().nextInt());

        db.modifyBook(id, book);
        getData();
        showAlertInfo("Успешно");
    }

    private String randomString(int length) {
        Random r = new Random();

        return r.ints(48, 122)
                .filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
                .mapToObj(i -> (char) i)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public void onInput(ObservableValue observable, String oldValue, String newValue) {
        fieldSearchById.setText(newValue.replaceAll("[^0-9]", ""));

        if (fieldSearchById.getText().equals("")) {
            changeDisableButtons(true);
        } else {
            changeDisableButtons(false);
        }
    }

    private void changeDisableButtons(Boolean value) {
        editButton.setDisable(value);
        deleteButton.setDisable(value);
    }

    private void showAlertError(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(text);

        alert.showAndWait();
    }

    private void showAlertInfo(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(text);

        alert.showAndWait();
    }
}
