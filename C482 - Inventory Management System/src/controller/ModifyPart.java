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
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.lang.String;

/**
 * Modify parts class controls the buttons and actions in the ModifyParts screen
 * @author Samuel A. Faubert
 */
public class ModifyPart implements Initializable {
    public Button modifyPartSaveButton;
    public Button modifyPartCancelButton;
    public RadioButton modifyPartInHouseButton;
    public ToggleGroup modifyPartRadioButtons;
    public RadioButton modifyPartOutsourcedButton;
    public TextField modifyPartNameText;
    public Label modifyPartIDLabel;
    public Label modifyPartNameLabel;
    public Label modifyPartInvLabel;
    public Label modifyPartPriceLabel;
    public Label modifyPartMaxLabel;
    public Label modifyPartConditionalLabel;
    public Label modifyPartMinLabel;
    public TextField modifyPartIDText;
    public TextField modifyPartPriceText;
    public TextField modifyPartInvText;
    public TextField modifyPartMaxText;
    public TextField modifyPartConditionalText;
    public TextField modifyPartMinText;

    private static Part partToModify = null;

    /**
     * saves all modifications to the parts list
     * @param actionEvent
     * @throws Exception
     */
    public void onModifyPartSaveButton(ActionEvent actionEvent) throws Exception {
        try {
            int id = partToModify.getId();
            String name = modifyPartNameText.getText();
            double price = Double.parseDouble(modifyPartPriceText.getText());
            int stock = Integer.parseInt(modifyPartInvText.getText());
            int min = Integer.parseInt(modifyPartMinText.getText());
            int max = Integer.parseInt(modifyPartMaxText.getText());

            if (min > max) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Min cannot be greater than max");
                alert.showAndWait();
                throw new Exception("Min cannot be greater than max");
            }
            if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Inv must be between min and max");
                alert.showAndWait();
                throw new Exception("Inv must be between min and max");
            }

            if (modifyPartOutsourcedButton.isSelected()) {
                String companyName = modifyPartConditionalText.getText();
                partToModify = new Outsourced(id, name, price, stock, min, max, companyName);

            } else if (modifyPartInHouseButton.isSelected()) {
                int machineId = Integer.parseInt(modifyPartConditionalText.getText());
                partToModify = new InHouse(id, name, price, stock, min, max, machineId);
            }

            int index = Inventory.getPartIndex(partToModify.getId());
            Inventory.updatePart(index, partToModify);


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
     * cancels all changes and does not save them to the parts list
     * @param actionEvent
     * @throws IOException
     */
    public void onModifyPartCancelButton(ActionEvent actionEvent) throws IOException {
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
     * sets the machine ID field when the inhouse buttono is selected
     * @param actionEvent
     */
    public void onModifyPartInHouseButton(ActionEvent actionEvent) {
        modifyPartConditionalLabel.setText("Machine ID");
    }

    /**
     * sets the company name field when the outsourced button is selected
     * @param actionEvent
     */
    public void onModifyPartOutsourcedButton(ActionEvent actionEvent) {
        modifyPartConditionalLabel.setText("Company Name");
    }

    /**
     * initializes the modify parts screen with the data from the part that will be modified
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            partToModify = InventoryManagementHome.getPartToModify();
        } catch (NullPointerException e) {

        }

        modifyPartIDText.setPromptText(String.valueOf(partToModify.getId()));
        modifyPartIDText.setEditable(false);
        modifyPartNameText.setText(partToModify.getName());
        modifyPartInvText.setText(String.valueOf(partToModify.getStock()));
        modifyPartMaxText.setText(String.valueOf(partToModify.getMax()));
        modifyPartMinText.setText(String.valueOf(partToModify.getMin()));
        modifyPartPriceText.setText(String.valueOf(partToModify.getPrice()));

        if (partToModify instanceof Outsourced) {
            modifyPartOutsourcedButton.setSelected(true);
            modifyPartInHouseButton.setSelected(false);
            modifyPartConditionalLabel.setText("Company Name");
            modifyPartConditionalText.setText(((Outsourced) partToModify).getCompanyName());
        } else if (partToModify instanceof InHouse) {
            modifyPartConditionalText.setText(String.valueOf(((InHouse) partToModify).getMachineId()));
        }


    }
}
