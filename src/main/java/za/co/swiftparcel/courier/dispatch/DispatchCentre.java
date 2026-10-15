package za.co.swiftparcel.courier.dispatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DispatchCentre {
    private final String centreName;
    private final List<Parcel> parcels;

    public DispatchCentre(String centreName) {
        this.centreName = centreName;
        this.parcels = new ArrayList<>();
    }

    public String getCentreName() {
        return centreName;
    }

    public void addParcel(Parcel parcel) {
        boolean duplicate = parcels.stream()
                .anyMatch(existing -> existing.getTrackingNumber().equals(parcel.getTrackingNumber()));
        if (duplicate) {
            throw new IllegalArgumentException("A parcel with tracking number " + parcel.getTrackingNumber() + " is already present");
        }
        parcels.add(parcel);
    }

    public List<Parcel> getParcels() {
        return Collections.unmodifiableList(parcels);
    }

    public Parcel findParcel(String trackingNumber) {
        return parcels.stream()
                .filter(parcel -> parcel.getTrackingNumber().equals(trackingNumber))
                .findFirst()
                .orElse(null);
    }

    public List<Insurable> insurableParcels() {
        List<Insurable> insurable = new ArrayList<>();
        for (Parcel parcel : parcels) {
            if (parcel instanceof Insurable) {
                insurable.add((Insurable) parcel);
            }
        }
        return insurable;
    }

    public double totalInsurancePremiums() {
        return insurableParcels().stream()
                .mapToDouble(Insurable::insurancePremium)
                .sum();
    }

    public double totalDeliveryRevenue() {
        return parcels.stream()
                .mapToDouble(Parcel::deliveryFee)
                .sum();
    }

    public int getParcelCount() {
        return parcels.size();
    }
}
