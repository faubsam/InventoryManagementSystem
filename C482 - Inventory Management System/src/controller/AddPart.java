package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Outsourced;
import model.Part;
import model.Inventory;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * AddPart class provides controls for all buttons on the add part screen
 * @author Samuel A. Faubert
 */
public class AddPart implements Initializable {
    public Button addPartSaveButton;
    public Button addPartCancelButton;
    public RadioButton addPartInHouseButton;
    public RadioButton addPartOutsourcedButton;
    public TextField addPartNameText;
    public Label addPartIDLabel;
    public Label addPartNameLabel;
    public Label addPartInvLabel;
    public Label addPartPriceLabel;
    public Label addPartMaxLabel;
    public Label addPartConditionalLabel;
    public Label addPartMinLabel;
    public TextField addPartIDText;
    public TextField addPartPriceText;
    public TextField addPartInvText;
    public TextField addPartMaxText;
    public TextField addPartConditionalText;
    public TextField addPartMinText;
    public ToggleGroup addPartRadioButtons;

    /**
     * Saves the new part object to the list of parts in inventory
     * @param actionEvent event click on the save button
     * @throws IOException
     * @throws Exception catches exception related to the user's input to ensure validity
     */
    public void onAddPartSaveButton(ActionEvent actionEvent) throws Exception {
        try {
            int id = Inventory.getIdCounter();
            String name = addPartNameText.getText();
            Double price = Double.parseDouble(addPartPriceText.getText());
            int max = Integer.parseInt(addPartMaxText.getText());
            int min = Integer.parseInt(addPartMinText.getText());
            if (min > max) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Min cannot be greater than max");
                alert.showAndWait();
                throw new Exception("Min cannot be greater than max");
            }
            int inv = Integer.parseInt(addPartInvText.getText());
            if (inv > max || inv < min) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Inv must be between min and max");
                alert.showAndWait();
                throw new Exception("Inv must be between min and max");
            }


            if (addPartConditionalLabel.getText().contains("Machine ID")) {
                int machineId = Integer.parseInt(addPartConditionalText.getText());
                Part newPart = new InHouse(id, name, price, inv, min, max, machineId);
                Inventory.addPart(newPart);
            } else if (addPartConditionalLabel.getText().contains("Company Name")) {
                String companyName = addPartConditionalText.getText();
                Part newPart = new Outsourced(id, name, price, inv, min, max, companyName);
                Inventory.addPart(newPart);

            }

            Inventory.setIdCounter();

            Parent root = FXMLLoader.load(getClass().getResource("/views/InventoryManagementHome.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Inventory Management System");
            Scene scene = new Scene(root, 1130, 618);
            stage.setScene(scene);
            stage.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid input detected");
            alert.setContentText("You did not enter valid values for one or more fields\n" + e);
            alert.showAndWait();
        }


    }

    /**
     * Cancels all inputs and does not save the changes to the inventory
     * @param actionEvent click on cancel button
     * @throws IOException
     */
    public void onAddPartCancelButton(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            Parent root = FXMLLoader.load(getClass().getResource("/views/InventoryManagementHome.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Inventory Management System");
            Scene scene = new Scene(root, 1130, 618);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * changes the text field to Machine ID if the inhouse part button is selected
     * @param actionEvent select radio button
     */
    public void onAddPartInHouseButton(ActionEvent actionEvent) {
        addPartConditionalLabel.setText("Machine ID");
    }

    /**
     * changes the text field to company name if the outsourced part button is selected
     * @param actionEvent select the outsourced radio button
     */
    public void onAddPartOutsourcedButton(ActionEvent actionEvent) {
        addPartConditionalLabel.setText("Company Name");
    }

    /**
     * initialize the add part screen
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
