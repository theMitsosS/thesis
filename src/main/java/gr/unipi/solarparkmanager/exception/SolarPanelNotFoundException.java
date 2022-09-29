package gr.unipi.solarparkmanager.exception;

public class SolarPanelNotFoundException extends Exception {

    public SolarPanelNotFoundException(Integer solarPanelId) {
        super("Solar panel with ID:" + solarPanelId + " does not exist");
    }
}
