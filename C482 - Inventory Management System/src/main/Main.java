package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class loads the home page for the application and launches the application
 *
 * FUTURE ENHANCEMENT - it would be possible to limit the total amount of inventory, either related to the size of a warehouse or to the amount of cash that the business has on hand to purchase new parts or products
 * this could work by adding a check to the AddNewPart or AddNewProduct buttons, and an error would be displayed if there is no more room or not enough budget for new items
 *
 * The Javadocs are located in the src/Javadocs directory
 * @author Samuel A. Faubert
 */
public class Main extends Application {

    /**
     *
     * @param stage first stage for home page of application
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/InventoryManagementHome.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root, 1130, 618));
        stage.show();
    }

    /**
     *
     * @param args Java main method
     */
    public static void main(String[] args) {

        launch(args);
    }
}
