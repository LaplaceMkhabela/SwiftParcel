package za.co.swiftparcel.courier.model;

public class Driver {

    private final String driverId;
    private final String name;
    private final String zone;
    private final double vehicleCapacityKg;
    private double currentLoadKg;
    private boolean onShift;
    private boolean fragileCertified;
    private boolean cashCollectionApproved;

    public Driver(String driverId, String name, String zone, double vehicleCapacityKg) {
        this.driverId = driverId;
        this.name = name;
        this.zone = zone;
        this.vehicleCapacityKg = vehicleCapacityKg;
        this.onShift = true;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getName() {
        return name;
    }

    public String getZone() {
        return zone;
    }

    public double getVehicleCapacityKg() {
        return vehicleCapacityKg;
    }

    public double getCurrentLoadKg() {
        return currentLoadKg;
    }

    public void setCurrentLoadKg(double currentLoadKg) {
        this.currentLoadKg = currentLoadKg;
    }

    public boolean isOnShift() {
        return onShift;
    }

    public void startShift() {
        this.onShift = true;
    }

    public void endShift() {
        this.onShift = false;
    }

    public boolean isFragileCertified() {
        return fragileCertified;
    }

    public void setFragileCertified(boolean fragileCertified) {
        this.fragileCertified = fragileCertified;
    }

    public boolean isCashCollectionApproved() {
        return cashCollectionApproved;
    }

    public void setCashCollectionApproved(boolean cashCollectionApproved) {
        this.cashCollectionApproved = cashCollectionApproved;
    }

    @Override
    public String toString() {
        return name + " (" + driverId + ", zone " + zone + ")";
    }
}
