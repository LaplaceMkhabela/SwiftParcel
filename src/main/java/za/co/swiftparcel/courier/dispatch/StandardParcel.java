package za.co.swiftparcel.courier.dispatch;

public class StandardParcel extends Parcel {
    private static final double BASE_RATE = 5.0;
    private static final double WEIGHT_RATE = 2.5;

    public StandardParcel(String trackingNumber, String origin, String destination, double weight) {
        super(trackingNumber, origin, destination, weight);
    }

    @Override
    public double calculateCost() {
        return BASE_RATE + (getWeight() * WEIGHT_RATE);
    }

    @Override
    public String getType() {
        return "STANDARD";
    }
}
