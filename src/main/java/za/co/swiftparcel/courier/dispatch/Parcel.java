package za.co.swiftparcel.courier.dispatch;

public abstract class Parcel {
    private final String trackingNumber;
    private final String senderName;
    private String recipientAddress;
    private double weightKg;
    private boolean delivered;

    public Parcel(String trackingNumber, String senderName, String recipientAddress, double weightKg) {
        if (weightKg <= 0) {
            throw new IllegalArgumentException("Weight must be greater than 0");
        }
        this.trackingNumber = trackingNumber;
        this.senderName = senderName;
        this.recipientAddress = recipientAddress;
        this.weightKg = weightKg;
        this.delivered = false;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void updateRecipientAddress(String recipientAddress) {
        if (delivered) {
            throw new IllegalStateException("Cannot update the recipient address of a delivered parcel");
        }
        this.recipientAddress = recipientAddress;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public void updateWeightKg(double weightKg) {
        if (weightKg <= 0) {
            throw new IllegalArgumentException("Weight must be greater than 0");
        }
        this.weightKg = weightKg;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void markDelivered() {
        this.delivered = true;
    }

    @Override
    public String toString() {
        return "Parcel %s from %s".formatted(trackingNumber, senderName);
    }

    public abstract double calculateCost();

    public abstract String getType();
}
