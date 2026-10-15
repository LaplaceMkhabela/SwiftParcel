package za.co.swiftparcel.courier.dispatch;

public abstract class Parcel {
    private final String trackingNumber;
    private final String origin;
    private final String destination;
    private final double weight;

    public Parcel(String trackingNumber, String origin, String destination, double weight) {
        this.trackingNumber = trackingNumber;
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public double getWeight() {
        return weight;
    }

    public abstract double calculateCost();

    public abstract String getType();
}
