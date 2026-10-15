package za.co.swiftparcel.courier.repository;

import za.co.swiftparcel.courier.model.Consignment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class ConsignmentRepository {

    private final Connection connection;

    public ConsignmentRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Consignment consignment) throws SQLException {
        String sql = """
                INSERT INTO parcels (tracking_no, weight_kg, service_level, zone, fragile, cash_on_delivery, address_verified, on_hold, customer_id)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT (tracking_no) DO UPDATE SET
                    weight_kg        = excluded.weight_kg,
                    service_level    = excluded.service_level,
                    zone             = excluded.zone,
                    fragile          = excluded.fragile,
                    cash_on_delivery = excluded.cash_on_delivery,
                    address_verified = excluded.address_verified,
                    on_hold          = excluded.on_hold
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, consignment.getTrackingNo());
            statement.setDouble(2, consignment.getWeightKg());
            statement.setString(3, consignment.getServiceLevel().name());
            statement.setString(4, consignment.getZone());
            statement.setInt(5, consignment.isFragile() ? 1 : 0);
            statement.setInt(6, consignment.isCashOnDelivery() ? 1 : 0);
            statement.setInt(7, consignment.isAddressVerified() ? 1 : 0);
            statement.setInt(8, consignment.isOnHold() ? 1 : 0);
            statement.setNull(9, Types.INTEGER);
            statement.executeUpdate();
        }
    }
}
