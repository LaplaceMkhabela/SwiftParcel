package za.co.swiftparcel.courier.dispatch;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParcelTypesTest {
    @Test
    void testStandardParcelType() {
        Parcel parcel = new StandardParcel("TRK001", "Cape Town", "Johannesburg", 2.5);
        assertEquals("STANDARD", parcel.getType());
    }

    @Test
    void testExpressParcelType() {
        Parcel parcel = new ExpressParcel("TRK002", "Cape Town", "Johannesburg", 2.5);
        assertEquals("EXPRESS", parcel.getType());
    }

    @Test
    void testFragileParcelType() {
        Parcel parcel = new FragileParcel("TRK003", "Cape Town", "Johannesburg", 2.5);
        assertEquals("FRAGILE", parcel.getType());
    }

    @Test
    void testStandardParcelCost() {
        Parcel parcel = new StandardParcel("TRK001", "Cape Town", "Johannesburg", 2.5);
        assertEquals(11.25, parcel.deliveryFee(), 0.001);
    }

    @Test
    void testExpressParcelCost() {
        Parcel parcel = new ExpressParcel("TRK002", "Cape Town", "Johannesburg", 2.5);
        assertEquals(27.50, parcel.deliveryFee(), 0.001);
    }

    @Test
    void testFragileParcelCost() {
        Parcel parcel = new FragileParcel("TRK003", "Cape Town", "Johannesburg", 2.5);
        assertEquals(20.00, parcel.deliveryFee(), 0.001);
    }
}
