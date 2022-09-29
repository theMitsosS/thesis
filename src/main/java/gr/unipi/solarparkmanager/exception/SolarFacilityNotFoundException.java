package gr.unipi.solarparkmanager.exception;

public class SolarFacilityNotFoundException extends Exception {

    public SolarFacilityNotFoundException(Integer facilityId) {
        super("Facility with ID:" + facilityId + " does not exist");
    }
}
