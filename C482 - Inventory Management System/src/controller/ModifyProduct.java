package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.getAllParts;

/**
 * modify product class provides the control for the buttons in the modifyProduct screen
 * @author Samuel A. Faubert
 */
public class ModifyProduct implements Initializable {
    public TextField modifyProductNameText;
    public Label modifyProductIDLabel;
    public Label modifyProductNameLabel;
    public Label modifyProductInvLabel;
    public Label modifyProductPriceLabel;
    public Label modifyProductMaxLabel;
    public TextField modifyProductIDText;
    public TextField modifyProductPriceText;
    public TextField modifyProductInvText;
    public TextField modifyProductMaxText;
    public Label modifyProductMinLabel;
    public TextField modifyProductMinText;
    public TableView partsTable;
    public TableColumn partIDCol;
    public TableColumn partNameCol;
    public TableColumn partInventoryLevelCol;
    public TableColumn partCostCol;
    public TableView associatedPartsTable;
    public TableColumn associatedPartsIDCol;
    public TableColumn associatedPartsNameCol;
    public TableColumn associatedPartsInventoryLevelCol;
    public TableColumn associatedPartsPriceCol;
    public Button addPartToProductButton;
    public Button removePartFromProductButton;
    public Button productSaveButton;
    public Button productCancelButton;
    public TextField searchPartText;

    private Product productToModify = null;
    private Part partToAdd = null;
    private Part partToRemove = null;

    /**
     * adds a part to the associated parts lists
     * @param actionEvent
     */
    public void onAddPartToProductButton(ActionEvent actionEvent) {
        partToAdd = (Part) partsTable.getSelectionModel().getSelectedItem();
        associatedPartsTable.getItems().add(partToAdd);
    }

    /**
     * removes a part from the associated parts list
     * @param actionEvent
     */
    public void onRemovePartFromProductButton(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this associated part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            partToRemove = (Part) associatedPartsTable.getSelectionModel().getSelectedItem();
            associatedPartsTable.getItems().remove(partToRemove);
        }
    }

    /**
     * save all changes to the product and saves it to the allProducts list in inventory
     * @param actionEvent
     * @throws Exception
     */
    public void onProductSaveButton(ActionEvent actionEvent) throws Exception {

        ObservableList<Part> partsList = productToModify.getAllAssociatedParts();
        try {
            int id = productToModify.getId();
            String name = modifyProductNameText.getText();
            Double price = Double.parseDouble(modifyProductPriceText.getText());
            int max = Integer.parseInt(modifyProductMaxText.getText());
            int min = Integer.parseInt(modifyProductMinText.getText());

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Min cannot be greater than max");
                alert.showAndWait();
                throw new Exception("Min cannot be greater than max");
            }
            int stock = Integer.parseInt(modifyProductInvText.getText());
            if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Inv must be between min and max");
                alert.showAndWait();
                throw new Exception("Inv must be between min and max");
            }

            productToModify = new Product(id, name, price, stock, min, max);

            for (int i = 0; i < partsList.size(); i++) {
                productToModify.addAssociatedPart(partsList.get(i));
            }

            int index = Inventory.getProductIndex(productToModify.getId());
            Inventory.updateProduct(index, productToModify);

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
     * cancels all changes made to the product without saving them and returns to the main screen
     * @param actionEvent
     * @throws IOException
     */
    public void onProductCancelButton(ActionEvent actionEvent) throws IOException {
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
     * populates the parts table based on the search results
     * @param actionEvent
     */
    public void getSearchPartText(ActionEvent actionEvent) {
        String query = searchPartText.getText();
        ObservableList<Part> matchedParts = Inventory.lookupPart(query);

        if (matchedParts.isEmpty()) {
            try {
                int partID = Integer.parseInt(query);
                Part matchedPart = Inventory.lookupPart(partID);
                if (matchedPart != null) {
                    matchedParts.add(matchedPart);
                }
            } catch (NumberFormatException e) {

            }
        }

        if (!matchedParts.isEmpty()) {
            partsTable.setItems(matchedParts);
        } else {
            partsTable.setItems(getAllParts());
        }
        searchPartText.setText("");
    }

    /**
     * initializes the modifyproduct screen with the data from the product object to be modified
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            productToModify = InventoryManagementHome.getProductToModify();
        } catch (NullPointerException e) {

        }

        modifyProductIDText.setPromptText(String.valueOf(productToModify.getId()));
        modifyProductIDText.setEditable(false);
        modifyProductNameText.setText(productToModify.getName());
        modifyProductInvText.setText(String.valueOf(productToModify.getStock()));
        modifyProductMaxText.setText(String.valueOf(productToModify.getMax()));
        modifyProductMinText.setText(String.valueOf(productToModify.getMin()));
        modifyProductPriceText.setText(String.valueOf(productToModify.getPrice()));

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTable.setItems(getAllParts());

        associatedPartsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartsInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartsTable.setItems(productToModify.getAllAssociatedParts());


    }
}
