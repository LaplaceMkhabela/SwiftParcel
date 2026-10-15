package za.co.swiftparcel.courier.service;

import za.co.swiftparcel.courier.model.AttemptOutcome;
import za.co.swiftparcel.courier.model.Consignment;
import za.co.swiftparcel.courier.model.DeliveryAttempt;
import za.co.swiftparcel.courier.model.Driver;
import za.co.swiftparcel.courier.model.ServiceLevel;

public class DeliveryRulesService {

    public static final int MAX_ATTEMPTS = 3;

    public boolean canLoadOntoVehicle(Consignment consignment, Driver driver) {
        if (driver.isOnShift() && !consignment.isOnHold() && consignment.isAddressVerified() && driver.getCurrentLoadKg() + consignment.getWeightKg() <= driver.getVehicleCapacityKg() && (!consignment.isFragile() || driver.isFragileCertified()) && (!consignment.isCashOnDelivery() || driver.isCashCollectionApproved()) && (driver.getZone().equals(consignment.getZone()) || consignment.getServiceLevel() == ServiceLevel.SAME_DAY)) {
            return true;
        }
        return false;
    }

    public void load(Consignment consignment, Driver driver) {
        if (!canLoadOntoVehicle(consignment, driver)) {
            throw new IllegalStateException(driver.getName() + " cannot take consignment " + consignment.getTrackingNo());
        }
        driver.setCurrentLoadKg(driver.getCurrentLoadKg() + consignment.getWeightKg());
    }

    public String decideNextStep(Consignment consignment) {
        String step;
        if (!consignment.isDelivered()) {
            if (!consignment.getAttempts().isEmpty()) {
                DeliveryAttempt lastAttempt = consignment.getAttempts().get(consignment.getAttempts().size() - 1);
                if (lastAttempt.getOutcome() == AttemptOutcome.REFUSED) {
                    step = "RETURN_TO_SENDER";
                } else {
                    if (lastAttempt.getOutcome() == AttemptOutcome.WRONG_ADDRESS) {
                        if (consignment.isAddressVerified()) {
                            step = "CONTACT_RECIPIENT";
                        } else {
                            step = "HOLD_AT_DEPOT";
                        }
                    } else {
                        if (consignment.getAttempts().size() >= MAX_ATTEMPTS) {
                            step = "RETURN_TO_SENDER";
                        } else {
                            if (consignment.getServiceLevel() == ServiceLevel.STANDARD) {
                                step = "SCHEDULE_REDELIVERY";
                            } else {
                                step = "REDELIVER_TODAY";
                            }
                        }
                    }
                }
            } else {
                step = "AWAITING_FIRST_ATTEMPT";
            }
        } else {
            step = "NONE";
        }
        return step;
    }
}
