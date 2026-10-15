package za.co.swiftparcel.courier.dispatch;

import java.util.ArrayList;
import java.util.List;

public class DispatchCentre {
    private final String name;
    private final List<Parcel> parcels;

    public DispatchCentre(String name) {
        this.name = name;
        this.parcels = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void dispatch(Parcel parcel) {
        parcels.add(parcel);
    }

    public List<Parcel> getParcels() {
        return List.copyOf(parcels);
    }

    public double calculateTotalCost() {
        return parcels.stream()
                .mapToDouble(Parcel::deliveryFee)
                .sum();
    }

    public int getParcelCount() {
        return parcels.size();
    }
}
