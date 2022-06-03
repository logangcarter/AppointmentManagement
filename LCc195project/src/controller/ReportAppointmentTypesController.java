package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Type;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Controller for the report appoint type form
 */
public class ReportAppointmentTypesController {
    @FXML
    private TableView<Type> typeTableView;
    @FXML
    private TableColumn<Type, String> typeCol;
    @FXML
    private TableColumn<Type, Integer> januaryCol;
    @FXML
    private TableColumn<Type, Integer> februaryCol;
    @FXML
    private TableColumn<Type, Integer> marchCol;
    @FXML
    private TableColumn<Type, Integer> aprilCol;
    @FXML
    private TableColumn<Type, Integer> mayCol;
    @FXML
    private TableColumn<Type, Integer> juneCol;
    @FXML
    private TableColumn<Type, Integer> julyCol;
    @FXML
    private TableColumn<Type, Integer> augustCol;
    @FXML
    private TableColumn<Type, Integer> septemberCol;
    @FXML
    private TableColumn<Type, Integer> octoberCol;
    @FXML
    private TableColumn<Type, Integer> novemberCol;
    @FXML
    private TableColumn<Type, Integer> decemberCol;
    Stage stage;
    Parent scene;

    /**
     * Switches to the appointment calendar form
     * @param event on click of cancel button
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentCalendar.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Sets up the tableview
     * @throws SQLException
     */
    public void initialize() throws SQLException {
        typeTableView.setItems(Type.getAllTypesAndMonths());

        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        januaryCol.setCellValueFactory(new PropertyValueFactory<>("januaryApps"));
        februaryCol.setCellValueFactory(new PropertyValueFactory<>("februaryApps"));
        marchCol.setCellValueFactory(new PropertyValueFactory<>("marchApps"));
        aprilCol.setCellValueFactory(new PropertyValueFactory<>("aprilApps"));
        mayCol.setCellValueFactory(new PropertyValueFactory<>("mayApps"));
        juneCol.setCellValueFactory(new PropertyValueFactory<>("juneApps"));
        julyCol.setCellValueFactory(new PropertyValueFactory<>("julyApps"));
        augustCol.setCellValueFactory(new PropertyValueFactory<>("augustApps"));
        septemberCol.setCellValueFactory(new PropertyValueFactory<>("septemberApps"));
        octoberCol.setCellValueFactory(new PropertyValueFactory<>("octoberApps"));
        novemberCol.setCellValueFactory(new PropertyValueFactory<>("novemberApps"));
        decemberCol.setCellValueFactory(new PropertyValueFactory<>("decemberApps"));
    }
}
