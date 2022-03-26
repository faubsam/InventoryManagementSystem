package model;

/**
 * InHouse class provides the constructor for a new in-house part and the methods for the machine ID
 * @author Samuel A. Faubert
 */
public class InHouse extends Part{

    /**
     * the part's machine ID
     */
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     *
     * @param machineId set the machine ID
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     *
     * @return return machine ID
     */
    public int getMachineId() {
        return this.machineId;
    }
}
