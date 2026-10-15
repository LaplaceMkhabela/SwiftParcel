package za.co.swiftparcel.courier.dispatch;

public class ExpressParcel extends Parcel {
    private static final double BASE_RATE = 15.0;
    private static final double WEIGHT_RATE = 5.0;

    public ExpressParcel(String trackingNumber, String origin, String destination, double weight) {
        super(trackingNumber, origin, destination, weight);
    }

    @Override
    public double calculateCost() {
        return BASE_RATE + (getWeight() * WEIGHT_RATE);
    }

    @Override
    public String getType() {
        return "EXPRESS";
    }
}
