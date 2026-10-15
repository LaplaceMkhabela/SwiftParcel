package za.co.swiftparcel.courier.dispatch;

public class FragileParcel extends Parcel implements Insurable {
    private static final double BASE_RATE = 10.0;
    private static final double WEIGHT_RATE = 4.0;
    private static final double INSURANCE_RATE = 0.02;

    public FragileParcel(String trackingNumber, String origin, String destination, double weight) {
        super(trackingNumber, origin, destination, weight);
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
    public double getInsurancePremium() {
        return calculateCost() * INSURANCE_RATE;
    }

    @Override
    public boolean isInsurable() {
        return true;
    }
}
