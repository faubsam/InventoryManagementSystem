package controller;

import javafx.collections.FXCollections;
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

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.getAllParts;

import model.Inventory;
import model.Part;
import model.Product;

/**
 * AddProduct class manages the buttons in the add product screen
 * @author Samuel A. Faubert
 */
public class AddProduct implements Initializable {
    public TextField addProductNameText;
    public Label addProductIDLabel;
    public Label addProductNameLabel;
    public Label addProductInvLabel;
    public Label addProductPriceLabel;
    public Label addProductMaxLabel;
    public TextField addProductIDText;
    public TextField addProductPriceText;
    public TextField addProductInvText;
    public TextField addProductMaxText;
    public Label addProductMinLabel;
    public TextField addProductMinText;
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
    public TextField searchPartsText;

    private static ObservableList<Part> partsList = FXCollections.observableArrayList();

    /**
     * cancel button sends the user back to the main screen without saving any of the changes
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
     * save button applies all the changes to the product and adds it to the products list
     * @param actionEvent
     * @throws Exception validates the data input by the user
     */
    public void onProductSaveButton(ActionEvent actionEvent) throws Exception{
        try {
            int id = Inventory.getIdCounter();
            String name = addProductNameText.getText();
            Double price = Double.parseDouble(addProductPriceText.getText());
            int max = Integer.parseInt(addProductMaxText.getText());
            int min = Integer.parseInt(addProductMinText.getText());
            int inv = Integer.parseInt(addProductInvText.getText());
            if (min > max) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Min cannot be greater than max");
                alert.showAndWait();
                throw new Exception("Min cannot be greater than max");
            }
            if (inv > max || inv < min) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Inv must be between min and max");
                alert.showAndWait();
                throw new Exception("Inv must be between min and max");
            }

            Product newProduct = new Product(id, name, price, inv, min, max);

            try {
                for (int i = 0; i < partsList.size(); i++) {
                    newProduct.addAssociatedPart(partsList.get(i));
                }
            } catch (NullPointerException e) {

            }

            Inventory.addProduct(newProduct);
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
     * removes a part from the associated parts for that product
     * @param actionEvent
     */
    public void onRemovePartFromProductButton(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this associated part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Part partToRemove = (Part) associatedPartsTable.getSelectionModel().getSelectedItem();
            associatedPartsTable.getItems().remove(partToRemove);
            partsList.remove(partToRemove);
        }
    }

    /**
     * adds a part to the product's associated parts list
     * @param actionEvent
     */
    public void onAddPartToProductButton(ActionEvent actionEvent) {
        Part partToAdd = (Part) partsTable.getSelectionModel().getSelectedItem();

        associatedPartsTable.getItems().add(partToAdd);
        partsList.add(partToAdd);
    }

    /**
     * displays the search results from the search box
     * @param actionEvent
     */
    public void getSearchPartText(ActionEvent actionEvent) {
        String query = searchPartsText.getText();
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
        searchPartsText.setText("");
    }

    /**
     * initializes the addProduct screen with the values from the parts list
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTable.setItems(getAllParts());

        associatedPartsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartsInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));




    }
}
