package za.co.swiftparcel.courier.dispatch;

public class ExpressParcel extends Parcel {
    private static final double BASE_RATE = 15.0;
    private static final double WEIGHT_RATE = 5.0;

    public ExpressParcel(String trackingNumber, String senderName, String recipientAddress, double weightKg) {
        super(trackingNumber, senderName, recipientAddress, weightKg);
    }

    @Override
    public double deliveryFee() {
        return BASE_RATE + (getWeightKg() * WEIGHT_RATE);
    }

    @Override
    public String getType() {
        return "EXPRESS";
    }
}
