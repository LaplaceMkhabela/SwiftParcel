package za.co.swiftparcel.courier.dispatch;

public class FragileParcel extends Parcel implements Insurable {
    private static final double BASE_FEE = 10.0;
    private static final double FEE_PER_KG = 4.0;
    private static final double INSURANCE_RATE = 0.02;

    private double declaredValue;

    public FragileParcel(String trackingNumber, String senderName, String recipientAddress, double weightKg) {
        this(trackingNumber, senderName, recipientAddress, weightKg, 0.0);
    }

    public FragileParcel(String trackingNumber, String senderName, String recipientAddress, double weightKg, double declaredValue) {
        super(trackingNumber, senderName, recipientAddress, weightKg);
        this.declaredValue = declaredValue;
    }

    @Override
    public double deliveryFee() {
        return BASE_FEE + (getWeightKg() * FEE_PER_KG);
    }

    @Override
    public int deliveryDays() {
        return 3;
    }

    @Override
    public String serviceLevel() {
        return "FRAGILE";
    }

    @Override
    public double getDeclaredValue() {
        return declaredValue;
    }

    public void setDeclaredValue(double declaredValue) {
        this.declaredValue = declaredValue;
    }

    @Override
    public double insurancePremium() {
        return declaredValue * INSURANCE_RATE;
    }
}
