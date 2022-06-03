import dao.*;
import model.Appointment;
import utilities.DBQuery;
import utilities.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.Time;
import utilities.UserSettings;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Entry point into application
 */
public class Main extends Application {

    /**
     * Starts the initial scene as login form
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 298, 196));
        primaryStage.show();
    }

    /**
     * Gets the application ready to run, connects to database, get SQL statements ready and populates lists
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {
        Connection connection = DBConnection.startConnection();

        DBQuery.setStatement(connection); //Create statement object

        //Populate objects from database
        UserSettings.setUserLocation();
        CountryDao countryDao = new CountryDao();
        countryDao.populateCountries();
        RegionDao regionDao = new RegionDao();
        regionDao.populateRegions();
        CustomerDao customerDao = new CustomerDao();
        customerDao.populateCustomers();
        ContactDao contactDao = new ContactDao();
        contactDao.populateContacts();
        Time.setAllHours();
        Time.setAllMinutes();
        Appointment.setAllLocations();
        AppointmentDao appointmentDao = new AppointmentDao();
        appointmentDao.populateAppointments();


        launch(args);
        DBConnection.closeConnection();
    }
}
