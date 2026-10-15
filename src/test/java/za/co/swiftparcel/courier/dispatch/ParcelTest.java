package za.co.swiftparcel.courier.dispatch;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParcelTest {
    @Test
    void testGetTrackingNumber() {
        Parcel parcel = new StandardParcel("TRK001", "Cape Town", "Johannesburg", 2.5);
        assertEquals("TRK001", parcel.getTrackingNumber());
    }

    @Test
    void testGetOrigin() {
        Parcel parcel = new StandardParcel("TRK001", "Cape Town", "Johannesburg", 2.5);
        assertEquals("Cape Town", parcel.getOrigin());
    }

    @Test
    void testGetDestination() {
        Parcel parcel = new StandardParcel("TRK001", "Cape Town", "Johannesburg", 2.5);
        assertEquals("Johannesburg", parcel.getDestination());
    }

    @Test
    void testGetWeight() {
        Parcel parcel = new StandardParcel("TRK001", "Cape Town", "Johannesburg", 2.5);
        assertEquals(2.5, parcel.getWeight(), 0.001);
    }
}
