package za.co.swiftparcel.courier.repository;

import za.co.swiftparcel.courier.model.AttemptOutcome;
import za.co.swiftparcel.courier.model.Consignment;
import za.co.swiftparcel.courier.model.DeliveryAttempt;
import za.co.swiftparcel.courier.model.ServiceLevel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public void recordAttempt(String trackingNo, DeliveryAttempt attempt) throws SQLException {
        String sql = """
                INSERT INTO delivery_attempts (parcel_id, attempted_at, outcome, driver_note)
                VALUES (?, ?, ?, ?)
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, findParcelId(trackingNo));
            statement.setString(2, attempt.getAttemptedAt().toString());
            statement.setString(3, attempt.getOutcome().name());
            statement.setString(4, attempt.getDriverNote());
            statement.executeUpdate();
        }
    }

    public Optional<Consignment> findByTrackingNo(String trackingNo) throws SQLException {
        String sql = """
                SELECT weight_kg, service_level, zone, fragile, cash_on_delivery, address_verified, on_hold
                FROM parcels
                WHERE tracking_no = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, trackingNo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }
                Consignment consignment = new Consignment(
                        trackingNo,
                        resultSet.getDouble("weight_kg"),
                        ServiceLevel.valueOf(resultSet.getString("service_level")),
                        resultSet.getString("zone"));
                consignment.setFragile(resultSet.getInt("fragile") == 1);
                consignment.setCashOnDelivery(resultSet.getInt("cash_on_delivery") == 1);
                consignment.setAddressVerified(resultSet.getInt("address_verified") == 1);
                if (resultSet.getInt("on_hold") == 1) {
                    consignment.placeOnHold();
                }
                for (DeliveryAttempt attempt : loadAttempts(trackingNo)) {
                    consignment.recordAttempt(attempt);
                }
                return Optional.of(consignment);
            }
        }
    }

    public List<DeliveryAttempt> loadAttempts(String trackingNo) throws SQLException {
        String sql = """
                SELECT attempted_at, outcome, driver_note
                FROM delivery_attempts
                WHERE parcel_id = (SELECT id FROM parcels WHERE tracking_no = ?)
                ORDER BY attempted_at, id
                """;
        List<DeliveryAttempt> attempts = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, trackingNo);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    attempts.add(new DeliveryAttempt(
                            LocalDateTime.parse(resultSet.getString("attempted_at")),
                            AttemptOutcome.valueOf(resultSet.getString("outcome")),
                            resultSet.getString("driver_note")));
                }
            }
        }
        return attempts;
    }

    private long findParcelId(String trackingNo) throws SQLException {
        String sql = "SELECT id FROM parcels WHERE tracking_no = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, trackingNo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new IllegalArgumentException("No parcel with tracking number " + trackingNo);
                }
                return resultSet.getLong("id");
            }
        }
    }
}
