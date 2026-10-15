package za.co.swiftparcel.courier.dispatch;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InsurableTest {
    @Test
    void testFragileParcelDeclaredValue() {
        FragileParcel parcel = new FragileParcel("TRK003", "Cape Town", "Johannesburg", 2.5, 1000.0);
        assertEquals(1000.0, parcel.getDeclaredValue(), 0.001);
    }

    @Test
    void testFragileParcelInsurancePremium() {
        FragileParcel parcel = new FragileParcel("TRK003", "Cape Town", "Johannesburg", 2.5, 1000.0);
        assertEquals(20.0, parcel.insurancePremium(), 0.001);
    }

    @Test
    void testStandardParcelIsNotInsurable() {
        StandardParcel parcel = new StandardParcel("TRK001", "Cape Town", "Johannesburg", 2.5);
        assertFalse(parcel instanceof Insurable);
    }

    @Test
    void testExpressParcelIsNotInsurable() {
        ExpressParcel parcel = new ExpressParcel("TRK002", "Cape Town", "Johannesburg", 2.5);
        assertFalse(parcel instanceof Insurable);
    }
}
