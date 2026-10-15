package za.co.swiftparcel.courier;

import za.co.swiftparcel.courier.model.AttemptOutcome;
import za.co.swiftparcel.courier.model.Consignment;
import za.co.swiftparcel.courier.model.DeliveryAttempt;
import za.co.swiftparcel.courier.model.Driver;
import za.co.swiftparcel.courier.model.ServiceLevel;
import za.co.swiftparcel.courier.service.DeliveryRulesService;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Main {

    private static final String DATABASE_FILE = "courier.db";

    public static void main(String[] args) throws SQLException {
        DeliveryRulesService rules = new DeliveryRulesService();

        Driver driver = new Driver("DRV-07", "Thabo Mokoena", "JHB-NORTH", 800);
        Consignment consignment = new Consignment("SP-100245", 12.5, ServiceLevel.STANDARD, "JHB-NORTH");

        System.out.println("Can load " + consignment + " onto " + driver + "? "
                + rules.canLoadOntoVehicle(consignment, driver));

        consignment.recordAttempt(new DeliveryAttempt(LocalDateTime.now(), AttemptOutcome.NO_ONE_HOME, "Gate locked"));
        System.out.println("Next step for " + consignment.getTrackingNo() + ": " + rules.decideNextStep(consignment));

        new File(DATABASE_FILE).delete();
        try (Connection connection = Database.connect(DATABASE_FILE)) {
            DatabaseSchema.createSchema(connection);
            System.out.println("Database ready at " + DATABASE_FILE);
        }
    }
}
