package za.co.swiftparcel.courier.dispatch;

public class FragileParcel extends Parcel implements Insurable {
    private static final double BASE_RATE = 10.0;
    private static final double WEIGHT_RATE = 4.0;
    private static final double PREMIUM_RATE = 0.02;

    private double declaredValue;

    public FragileParcel(String trackingNumber, String origin, String destination, double weight) {
        this(trackingNumber, origin, destination, weight, 0.0);
    }

    public FragileParcel(String trackingNumber, String origin, String destination, double weight, double declaredValue) {
        super(trackingNumber, origin, destination, weight);
        this.declaredValue = declaredValue;
    }

    @Override
    public double calculateCost() {
        return BASE_RATE + (getWeight() * WEIGHT_RATE);
    }

    @Override
    public String getType() {
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
        return declaredValue * PREMIUM_RATE;
    }
}
