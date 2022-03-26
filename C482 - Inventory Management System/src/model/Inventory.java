package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class holds the list of parts and products in the program's memory
 * Methods in this class serve to modify the parts or products and the arrayLists that hold the objects
 * @author Samuel A. Faubert
 */
public class Inventory {


    /**
     * list of all parts in the inventory
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * list of all products in the inventory
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /**
     * id counter keeps track of the new parts and product IDs to ensure that no ID is repeated
     */
    private static int idCounter = 1;

    /**
     *
     * @param newPart new part to create
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     *
     * @param newProduct new product to create
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     *
     * @param partId part to search for in the list
     * @return returns the part's ID
     */
    public static Part lookupPart(int partId) {
       for (int i = 0; i < allParts.size(); i++) {
           if (allParts.get(i).getId() == partId) {
               return allParts.get(i);
           }
       }

        return null;
    }

    /**
     *
     * @param productId product to search for
     * @return return the product's ID
     */
    public static Product lookupProduct(int productId) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == productId) {
                return allProducts.get(i);
            }
        }

        return null;
    }

    /**
     *
     * @param partName uses the provided string to search through the list for part names that contain the string
     * @return returns a list of parts that match the query
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsByName = FXCollections.observableArrayList();

        for(Part part: allParts) {
            if (part.getName().contains(partName)) {
                partsByName.add(part);
            }
        }

        return partsByName;

    }

    /**
     *
     * @param productName searches through the product list object's names to find matches
     * @return returns a list of products that contain the query
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productsByName = FXCollections.observableArrayList();

        for (Product product: allProducts) {
            if (product.getName().contains(productName)){
                productsByName.add(product);
            }
        }

        return productsByName;
    }

    /**
     *
     * @param index the index of the part to update in the arrayList
     * @param selectedPart the Part object to update
     */
    public static void updatePart(int index, Part selectedPart) {

        allParts.set(index, selectedPart);
    }

    /**
     *
     * @param index the index of the product to update in the arrayList
     * @param selectedProduct the product objec to update
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     *
     * @param selectedPart part to remove from the list
     * @return returns true
     */
    public static boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        return true;
    }

    /**
     *
     * @param selectedProduct product to remove from the list
     * @return returns true
     */
    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return true;
    }

    /**
     *
     * @return returns the list of all parts in the inventory
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     *
     * @return returns the list of all products in the inventory
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     *
     * @return returns the next ID to used for a new part or product
     */
    public static int getIdCounter() {
        return idCounter;
    }

    /**
     * increments the ID for next use
     */
    public static void setIdCounter() {
        idCounter++;
    }

    /**
     *
     * @param partId the part that gets retrieved from the list
     * @return returns the index of that part in the list
     */
    public static int getPartIndex(int partId) {
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == partId) {
                return i;
            }
        }

        return 0;
    }

    /**
     *
     * @param productId the product that gets retrieved from the list
     * @return returns the index of that product from the list
     */
    public static int getProductIndex(int productId) {
        for (int i = 0; i< allProducts.size(); i++) {
            if (allProducts.get(i).getId() == productId) {
                return i;
            }
        }
        return 0;
    }
}
