package za.co.swiftparcel.courier.dispatch;

/**
 * A parcel whose contents carry declared-value insurance. Provided for you - do not modify.
 */
public interface Insurable {

    /**
     * Returns the value the sender declared for the parcel's contents, in Rands.
     *
     * @return declared value
     */
    double getDeclaredValue();

    /**
     * Returns the insurance premium charged to cover this parcel, in Rands.
     *
     * @return insurance premium
     */
    double insurancePremium();
}
