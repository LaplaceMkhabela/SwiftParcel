package za.co.swiftparcel.courier;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSchema {

    private DatabaseSchema() {
    }

    public static void createSchema(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE customers (
                        id        INTEGER PRIMARY KEY,
                        full_name TEXT NOT NULL,
                        phone     TEXT NOT NULL,
                        email     TEXT
                    )
                    """);
            statement.execute("""
                    CREATE TABLE parcels (
                        id               INTEGER PRIMARY KEY,
                        tracking_no      TEXT NOT NULL UNIQUE,
                        weight_kg        REAL NOT NULL,
                        service_level    TEXT NOT NULL,
                        zone             TEXT NOT NULL,
                        fragile          INTEGER NOT NULL DEFAULT 0,
                        cash_on_delivery INTEGER NOT NULL DEFAULT 0,
                        address_verified INTEGER NOT NULL DEFAULT 1,
                        on_hold          INTEGER NOT NULL DEFAULT 0,
                        customer_id      INTEGER,
                        FOREIGN KEY (customer_id) REFERENCES customers (id)
                    )
                    """);
            statement.execute("""
                    CREATE TABLE delivery_attempts (
                        id           INTEGER PRIMARY KEY,
                        parcel_id    INTEGER NOT NULL,
                        attempted_at TEXT NOT NULL,
                        outcome      TEXT NOT NULL,
                        driver_note  TEXT,
                        FOREIGN KEY (parcel_id) REFERENCES parcels (id)
                    )
                    """);
        }
    }
}
