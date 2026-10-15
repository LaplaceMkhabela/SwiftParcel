package za.co.swiftparcel.courier.dispatch;

public class ExpressParcel extends Parcel {
    private static final double BASE_FEE = 15.0;
    private static final double FEE_PER_KG = 5.0;
    private static final int DELIVERY_DAYS = 1;

    public ExpressParcel(String trackingNumber, String senderName, String recipientAddress, double weightKg) {
        super(trackingNumber, senderName, recipientAddress, weightKg);
    }

    @Override
    public double deliveryFee() {
        return BASE_FEE + (getWeightKg() * FEE_PER_KG);
    }

    @Override
    public int deliveryDays() {
        return DELIVERY_DAYS;
    }

    @Override
    public String serviceLevel() {
        return "EXPRESS";
    }
}
