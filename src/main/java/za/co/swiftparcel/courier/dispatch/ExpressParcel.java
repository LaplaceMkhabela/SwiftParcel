package za.co.swiftparcel.courier.dispatch;

public class ExpressParcel extends Parcel implements Insurable {
    private static final double BASE_FEE = 15.0;
    private static final double FEE_PER_KG = 5.0;
    private static final int DELIVERY_DAYS = 1;
    private static final double INSURANCE_RATE = 0.02;

    private final double declaredValue;

    public ExpressParcel(String trackingNumber, String senderName, String recipientAddress, double weightKg, double declaredValue) {
        super(trackingNumber, senderName, recipientAddress, weightKg);
        if (declaredValue < 0) {
            throw new IllegalArgumentException("Declared value must not be negative");
        }
        this.declaredValue = declaredValue;
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
        return "Express";
    }

    @Override
    public double getDeclaredValue() {
        return declaredValue;
    }

    @Override
    public double insurancePremium() {
        return declaredValue * INSURANCE_RATE;
    }
}
