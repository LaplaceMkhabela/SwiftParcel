package za.co.swiftparcel.courier.dispatch;

public class StandardParcel extends Parcel {
    private static final double BASE_RATE = 5.0;
    private static final double WEIGHT_RATE = 2.5;

    public StandardParcel(String trackingNumber, String senderName, String recipientAddress, double weightKg) {
        super(trackingNumber, senderName, recipientAddress, weightKg);
    }

    @Override
    public double deliveryFee() {
        return BASE_RATE + (getWeightKg() * WEIGHT_RATE);
    }

    @Override
    public int deliveryDays() {
        return 5;
    }

    @Override
    public String getType() {
        return "STANDARD";
    }
}
