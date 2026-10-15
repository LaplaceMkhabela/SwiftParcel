package za.co.swiftparcel.courier.dispatch;

public class FragileParcel extends Parcel implements Insurable {
    private static final double BASE_FEE = 10.0;
    private static final double FEE_PER_KG = 4.0;
    private static final int DELIVERY_DAYS = 3;
    private static final double INSURANCE_RATE = 0.02;

    private final double declaredValue;
    private final String handlingNotes;

    public FragileParcel(String trackingNumber, String senderName, String recipientAddress, double weightKg, double declaredValue, String handlingNotes) {
        super(trackingNumber, senderName, recipientAddress, weightKg);
        if (declaredValue < 0) {
            throw new IllegalArgumentException("Declared value must not be negative");
        }
        this.declaredValue = declaredValue;
        this.handlingNotes = handlingNotes;
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
        return "Fragile";
    }

    @Override
    public double getDeclaredValue() {
        return declaredValue;
    }

    public String getHandlingNotes() {
        return handlingNotes;
    }

    @Override
    public double insurancePremium() {
        return declaredValue * INSURANCE_RATE;
    }
}
