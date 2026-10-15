package za.co.swiftparcel.courier.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Consignment {

    private final String trackingNo;
    private final double weightKg;
    private final ServiceLevel serviceLevel;
    private final String zone;
    private boolean fragile;
    private boolean cashOnDelivery;
    private boolean addressVerified;
    private boolean onHold;
    private final List<DeliveryAttempt> attempts = new ArrayList<>();

    public Consignment(String trackingNo, double weightKg, ServiceLevel serviceLevel, String zone) {
        this.trackingNo = trackingNo;
        this.weightKg = weightKg;
        this.serviceLevel = serviceLevel;
        this.zone = zone;
        this.addressVerified = true;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public ServiceLevel getServiceLevel() {
        return serviceLevel;
    }

    public String getZone() {
        return zone;
    }

    public boolean isFragile() {
        return fragile;
    }

    public void setFragile(boolean fragile) {
        this.fragile = fragile;
    }

    public boolean isCashOnDelivery() {
        return cashOnDelivery;
    }

    public void setCashOnDelivery(boolean cashOnDelivery) {
        this.cashOnDelivery = cashOnDelivery;
    }

    public boolean isAddressVerified() {
        return addressVerified;
    }

    public void setAddressVerified(boolean addressVerified) {
        this.addressVerified = addressVerified;
    }

    public boolean isOnHold() {
        return onHold;
    }

    public void placeOnHold() {
        this.onHold = true;
    }

    public void releaseHold() {
        this.onHold = false;
    }

    public void recordAttempt(DeliveryAttempt attempt) {
        attempts.add(attempt);
    }

    public List<DeliveryAttempt> getAttempts() {
        return Collections.unmodifiableList(attempts);
    }

    public boolean isDelivered() {
        return !attempts.isEmpty() && attempts.get(attempts.size() - 1).getOutcome() == AttemptOutcome.DELIVERED;
    }

    @Override
    public String toString() {
        return trackingNo + " (" + serviceLevel + ", " + weightKg + "kg, zone " + zone + ")";
    }
}
