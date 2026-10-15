package za.co.swiftparcel.courier.dispatch;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InsurableTest {
    @Test
    void testFragileParcelIsInsurable() {
        FragileParcel parcel = new FragileParcel("TRK003", "Cape Town", "Johannesburg", 2.5);
        assertTrue(parcel.isInsurable());
    }

    @Test
    void testFragileParcelInsurancePremium() {
        FragileParcel parcel = new FragileParcel("TRK003", "Cape Town", "Johannesburg", 2.5);
        assertEquals(0.40, parcel.getInsurancePremium(), 0.001);
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
