package za.co.swiftparcel.courier.dispatch;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DispatchCentreTest {
    @Test
    void testGetName() {
        DispatchCentre centre = new DispatchCentre("Cape Town Hub");
        assertEquals("Cape Town Hub", centre.getCentreName());
    }

    @Test
    void testDispatchAddsParcel() {
        DispatchCentre centre = new DispatchCentre("Cape Town Hub");
        Parcel parcel = new StandardParcel("TRK001", "Cape Town", "Johannesburg", 2.5);
        centre.addParcel(parcel);
        assertEquals(1, centre.getParcelCount());
    }

    @Test
    void testAddParcelRejectsDuplicateTrackingNumber() {
        DispatchCentre centre = new DispatchCentre("Cape Town Hub");
        centre.addParcel(new StandardParcel("TRK001", "Cape Town", "Johannesburg", 2.5));
        assertThrows(IllegalArgumentException.class,
                () -> centre.addParcel(new StandardParcel("TRK001", "Durban", "Pretoria", 1.0)));
    }

    @Test
    void testGetParcelsReturnsImmutableList() {
        DispatchCentre centre = new DispatchCentre("Cape Town Hub");
        Parcel parcel = new StandardParcel("TRK001", "Cape Town", "Johannesburg", 2.5);
        centre.addParcel(parcel);
        assertThrows(UnsupportedOperationException.class, () -> centre.getParcels().add(parcel));
    }

    @Test
    void testCalculateTotalCost() {
        DispatchCentre centre = new DispatchCentre("Cape Town Hub");
        centre.addParcel(new StandardParcel("TRK001", "Cape Town", "Johannesburg", 2.5));
        centre.addParcel(new ExpressParcel("TRK002", "Cape Town", "Johannesburg", 2.5, 0.0));
        assertEquals(38.75, centre.calculateTotalCost(), 0.001);
    }

    @Test
    void testEmptyCentreCost() {
        DispatchCentre centre = new DispatchCentre("Cape Town Hub");
        assertEquals(0.0, centre.calculateTotalCost(), 0.001);
    }
}
