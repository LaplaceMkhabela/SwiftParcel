package za.co.swiftparcel.courier.model;

import java.time.LocalDateTime;

public class DeliveryAttempt {

    private final LocalDateTime attemptedAt;
    private final AttemptOutcome outcome;
    private final String driverNote;

    public DeliveryAttempt(LocalDateTime attemptedAt, AttemptOutcome outcome, String driverNote) {
        this.attemptedAt = attemptedAt;
        this.outcome = outcome;
        this.driverNote = driverNote;
    }

    public LocalDateTime getAttemptedAt() {
        return attemptedAt;
    }

    public AttemptOutcome getOutcome() {
        return outcome;
    }

    public String getDriverNote() {
        return driverNote;
    }

    @Override
    public String toString() {
        return outcome + " at " + attemptedAt;
    }
}
