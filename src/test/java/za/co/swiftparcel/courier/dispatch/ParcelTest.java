package za.co.swiftparcel.courier.dispatch;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParcelTest {
    @Test
    void testGetTrackingNumber() {
        Parcel parcel = new StandardParcel("TRK001", "Alice", "1 Main Road, Cape Town", 2.5);
        assertEquals("TRK001", parcel.getTrackingNumber());
    }

    @Test
    void testGetSenderName() {
        Parcel parcel = new StandardParcel("TRK001", "Alice", "1 Main Road, Cape Town", 2.5);
        assertEquals("Alice", parcel.getSenderName());
    }

    @Test
    void testGetRecipientAddress() {
        Parcel parcel = new StandardParcel("TRK001", "Alice", "1 Main Road, Cape Town", 2.5);
        assertEquals("1 Main Road, Cape Town", parcel.getRecipientAddress());
    }

    @Test
    void testGetWeightKg() {
        Parcel parcel = new StandardParcel("TRK001", "Alice", "1 Main Road, Cape Town", 2.5);
        assertEquals(2.5, parcel.getWeightKg(), 0.001);
    }

    @Test
    void testNotDeliveredByDefault() {
        Parcel parcel = new StandardParcel("TRK001", "Alice", "1 Main Road, Cape Town", 2.5);
        assertFalse(parcel.isDelivered());
    }

    @Test
    void testZeroWeightThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new StandardParcel("TRK001", "Alice", "1 Main Road, Cape Town", 0));
    }

    @Test
    void testNegativeWeightThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new StandardParcel("TRK001", "Alice", "1 Main Road, Cape Town", -1));
    }
}
