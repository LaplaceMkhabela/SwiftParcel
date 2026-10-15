package za.co.swiftparcel.courier.dispatch;

import java.util.ArrayList;
import java.util.List;

public class DispatchCentre {
    private final String centreName;
    private final List<Parcel> parcels;

    public DispatchCentre(String centreName) {
        this.centreName = centreName;
        this.parcels = new ArrayList<>();
    }

    public String getName() {
        return centreName;
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
