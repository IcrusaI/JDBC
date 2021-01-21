package jdbc;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.sql.SQLException;

public class App extends Application {
    @FXML
    private TableView<Worker> workersTable;

    @FXML
    private TextField fieldSearchByTIN;

    private final Database db = new Database();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("Hello JavaFX");
        stage.setWidth(1000);
        stage.setHeight(700);

        stage.show();
    }

    private void initTable() {
        ObservableList<TableColumn<Worker, ?>> columns = workersTable.getColumns();

        for (int i = 0; i < columns.size(); i++) {
            TableColumn<Worker, ?> column = columns.get(i);

            column.setCellValueFactory(new PropertyValueFactory<>(column.getId()));
        }
    }

    public void onClickSearch() throws SQLException, ClassNotFoundException {
        initTable();

        ObservableList<Worker> workers;

        if (fieldSearchByTIN.getText().equals("")) {
            workers = FXCollections.observableArrayList(db.getWorkers());
        } else {
            workers = FXCollections.observableArrayList(db.getWorkers(fieldSearchByTIN.getText()));
        }

        workersTable.setItems(workers);
    }
}
