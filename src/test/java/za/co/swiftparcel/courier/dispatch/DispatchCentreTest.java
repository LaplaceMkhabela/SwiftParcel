package za.co.swiftparcel.courier.dispatch;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DispatchCentreTest {
    @Test
    void testGetName() {
        DispatchCentre centre = new DispatchCentre("Cape Town Hub");
        assertEquals("Cape Town Hub", centre.getName());
    }

    @Test
    void testDispatchAddsParcel() {
        DispatchCentre centre = new DispatchCentre("Cape Town Hub");
        Parcel parcel = new StandardParcel("TRK001", "Cape Town", "Johannesburg", 2.5);
        centre.dispatch(parcel);
        assertEquals(1, centre.getParcelCount());
    }

    @Test
    void testGetParcelsReturnsImmutableList() {
        DispatchCentre centre = new DispatchCentre("Cape Town Hub");
        Parcel parcel = new StandardParcel("TRK001", "Cape Town", "Johannesburg", 2.5);
        centre.dispatch(parcel);
        assertThrows(UnsupportedOperationException.class, () -> centre.getParcels().add(parcel));
    }

    @Test
    void testCalculateTotalCost() {
        DispatchCentre centre = new DispatchCentre("Cape Town Hub");
        centre.dispatch(new StandardParcel("TRK001", "Cape Town", "Johannesburg", 2.5));
        centre.dispatch(new ExpressParcel("TRK002", "Cape Town", "Johannesburg", 2.5, 0.0));
        assertEquals(38.75, centre.calculateTotalCost(), 0.001);
    }

    @Test
    void testEmptyCentreCost() {
        DispatchCentre centre = new DispatchCentre("Cape Town Hub");
        assertEquals(0.0, centre.calculateTotalCost(), 0.001);
    }
}
