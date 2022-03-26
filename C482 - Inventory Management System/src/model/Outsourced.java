package model;

/**
 * Outsourced class provides constructor for outsourced part and the methods for the part's company name
 * @author Samuel A. Faubert
 */
public class Outsourced extends Part {

    /**
     * the part's company name
     */
    private  String companyName;


    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     *
     * @param companyName set company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     *
     * @return returns company name
     */
    public String getCompanyName() {
        return this.companyName;
    }
}
