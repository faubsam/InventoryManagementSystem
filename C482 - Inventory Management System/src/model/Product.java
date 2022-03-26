package model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * Product class provides constructor for new product along with getters and setters for product attributes
 * @author Samuel A. Faubert
 */
public class Product {
    /**
     * list of all associated parts for the product
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * the product's ID
     */
    int id;
    /**
     * the product's name
     */
    String name;
    /**
     * the product's price
     */
    double price;
    /**
     * the product's inventory level
     */
    int stock;
    /**
     * the product's minimal allowed inventory level
     */
    int min;
    /**
     * the product's maximum allowed inventory level
     */
    int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     *
     * @param part part to add to the list
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     *
     * @param part part to delete
     * @return return true if delete successful
     */
    public boolean deleteAssociatedPart(Part part) {
        associatedParts.remove(part);
        return true;
    }

    /**
     *
     * @return return list of all associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {

        return associatedParts;
    }
}
