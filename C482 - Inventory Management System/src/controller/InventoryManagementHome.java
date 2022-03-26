package controller;

import javafx.application.Platform;
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
import model.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.*;

/**
 * The home page of the application displays tables with all parts and all products
 * this class controls the buttons on the home page, the table views, and the search function
 * @author Samuel A. Faubert
 */
public class InventoryManagementHome implements Initializable {
    public Button addPart;
    public Button modifyPart;
    public Button deletePart;
    public TableView partsTable;
    public Button addProduct;
    public Button ModifyProduct;
    public Button deleteProduct;
    public TableView productsTable;
    public Button exit;
    public TableColumn partIDCol;
    public TableColumn partNameCol;
    public TableColumn partInventoryLevelCol;
    public TableColumn partCostCol;
    public TableColumn productIDCol;
    public TableColumn productNameCol;
    public TableColumn productInventoryLevelCol;
    public TableColumn productCostCol;
    public TextField searchPartsText;
    public TextField searchProductsText;

    private static boolean testData = true;
    private static Part partToModify = null;
    private static Product productToModify = null;

    /**
     * for the modify part screen
     * @return the part to modify
     */
    public static Part getPartToModify() {
        return partToModify;
    }

    /**
     * for the modify product screen
     * @return the product to modify
     */
    public static Product getProductToModify() {
        return productToModify;
    }

    /**
     * initializes the application's home page by filling the tables with all parts and all products
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTable.setItems(Inventory.getAllParts());

        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productsTable.setItems(getAllProducts());
    }

    /**
     * deletes a product from the allProducts list and removes it from the table
     * @param actionEvent
     * @throws Exception Exception is thrown if the user attempts to delete a product that still has associated parts
     */
    public void onDeleteProductButton(ActionEvent actionEvent) throws Exception{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            productToModify = (Product) productsTable.getSelectionModel().getSelectedItem();
            if (productToModify.getAllAssociatedParts().isEmpty()) {
                Inventory.deleteProduct(productToModify);
            } else {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setContentText("Cannot delete product that has associated parts. Please remove the associated parts before deleting the product.");
                alert2.showAndWait();
                throw new Exception("Cannot delete product that has associated parts. Please remove the associated parts before deleting the product.");
            }
        }
    }

    /**
     * redirects to the modify product screen with the data from the selected product in the table
     * @param actionEvent
     * @throws IOException
     * @throws NullPointerException
     */
    public void onModifyProductButton(ActionEvent actionEvent) throws IOException, NullPointerException {

            productToModify = (Product) productsTable.getSelectionModel().getSelectedItem();
            if(productToModify != null) {
                Parent root = FXMLLoader.load(getClass().getResource("/views/ModifyProduct.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1074, 795);
                stage.setTitle("Modify Product");
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please select a product to modify");
                alert.showAndWait();
            }

    }

    /**
     * redirects to the add product screen
     * @param actionEvent
     * @throws IOException
     */
    public void onAddProductButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/AddProduct.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1074,795);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * removes a part from the parts list and from the table view
     * @param actionEvent
     */
    public void onDeletePartButton(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            partToModify = (Part) partsTable.getSelectionModel().getSelectedItem();
            Inventory.deletePart(partToModify);
        }
    }

    /**
     * redirects the user to the modify part screen on the part that was selected
     * @param actionEvent
     * @throws IOException
     * @throws NullPointerException if the user clicks on the button without selecting a part
     *
     * RUNTIME ERROR An error was discovered when clicking on the modify button without selecting a part - this caused the program to crash
     * The error corrected by adding a check to see if the selected part is null - if it is, an alert is displayed with a warning and the user is taken back to the home screen
     */
    public void onModifyPartButton(ActionEvent actionEvent) throws IOException, NullPointerException {

        partToModify = (Part) partsTable.getSelectionModel().getSelectedItem();
        if (partToModify != null) {

            Parent root = FXMLLoader.load(getClass().getResource("/views/ModifyPart.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 650);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select a part to modify");
            alert.showAndWait();
        }
    }

    /**
     * redirects the user to the add part screen
     * @param actionEvent
     * @throws IOException
     */
    public void onAddPartButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/AddPart.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600,650);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * closes the application
     * @param actionEvent
     */
    public void onExitAppButton(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     * shows the search results in the table view
     * @param actionEvent
     */
    public void getSearchProductsText(ActionEvent actionEvent) {
        String query = searchProductsText.getText();
        ObservableList<Product> matchedProducts = Inventory.lookupProduct(query);

        if (matchedProducts.isEmpty()) {
            try {
                int productID = Integer.parseInt(query);
                Product matchedProduct = Inventory.lookupProduct(productID);
                if(matchedProduct != null) {
                    matchedProducts.add(matchedProduct);
                }
            } catch (NumberFormatException e) {

            }
        }

        if (!matchedProducts.isEmpty()) {
            productsTable.setItems(matchedProducts);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No search results were found");
            alert.showAndWait();
            productsTable.setItems(getAllProducts());
        }
        searchProductsText.setText("");
    }

    /**
     * shows the search results in the table view
     * @param actionEvent
     */
    public void getSearchPartsText(ActionEvent actionEvent) {
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
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No search results were found");
            alert.showAndWait();
            partsTable.setItems(getAllParts());
        }
        searchPartsText.setText("");
    }
}
