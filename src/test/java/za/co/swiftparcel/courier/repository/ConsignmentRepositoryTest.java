package za.co.swiftparcel.courier.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.swiftparcel.courier.Database;
import za.co.swiftparcel.courier.DatabaseSchema;
import za.co.swiftparcel.courier.model.AttemptOutcome;
import za.co.swiftparcel.courier.model.Consignment;
import za.co.swiftparcel.courier.model.DeliveryAttempt;
import za.co.swiftparcel.courier.model.ServiceLevel;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ConsignmentRepositoryTest {

    private Connection connection;
    private ConsignmentRepository repository;

    @BeforeEach
    void setUp() throws SQLException {
        connection = Database.connect(":memory:");
        DatabaseSchema.createSchema(connection);
        repository = new ConsignmentRepository(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void saveAndFindRoundTripsConsignment() throws SQLException {
        Consignment consignment = new Consignment("SP-1", 12.5, ServiceLevel.EXPRESS, "JHB-NORTH");
        consignment.setFragile(true);
        consignment.setCashOnDelivery(true);
        consignment.setAddressVerified(false);
        repository.save(consignment);

        Consignment loaded = repository.findByTrackingNo("SP-1").orElseThrow();
        assertEquals("SP-1", loaded.getTrackingNo());
        assertEquals(12.5, loaded.getWeightKg(), 0.001);
        assertEquals(ServiceLevel.EXPRESS, loaded.getServiceLevel());
        assertEquals("JHB-NORTH", loaded.getZone());
        assertTrue(loaded.isFragile());
        assertTrue(loaded.isCashOnDelivery());
        assertFalse(loaded.isAddressVerified());
        assertFalse(loaded.isOnHold());
    }

    @Test
    void findMissingReturnsEmpty() throws SQLException {
        assertTrue(repository.findByTrackingNo("NOPE").isEmpty());
    }

    @Test
    void recordAttemptPersistsAndLoads() throws SQLException {
        Consignment consignment = new Consignment("SP-2", 3.0, ServiceLevel.STANDARD, "CPT");
        repository.save(consignment);
        LocalDateTime when = LocalDateTime.of(2026, 1, 2, 10, 30);
        repository.recordAttempt("SP-2", new DeliveryAttempt(when, AttemptOutcome.DELIVERED, "Handed to recipient"));

        Consignment loaded = repository.findByTrackingNo("SP-2").orElseThrow();
        assertEquals(1, loaded.getAttempts().size());
        assertEquals(AttemptOutcome.DELIVERED, loaded.getAttempts().get(0).getOutcome());
        assertTrue(loaded.isDelivered());
    }

    @Test
    void saveUpdatesExistingParcel() throws SQLException {
        Consignment consignment = new Consignment("SP-3", 3.0, ServiceLevel.STANDARD, "CPT");
        repository.save(consignment);
        consignment.setFragile(true);
        consignment.placeOnHold();
        repository.save(consignment);

        Consignment loaded = repository.findByTrackingNo("SP-3").orElseThrow();
        assertTrue(loaded.isFragile());
        assertTrue(loaded.isOnHold());
    }
}
