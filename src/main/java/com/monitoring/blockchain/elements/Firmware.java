package com.monitoring.blockchain.elements;

public class Firmware {
    private String version;
    private String manufacturer;
    private String hash;

    public Firmware(String version, String manufacturer, String hash) {
        this.version = version;
        this.manufacturer = manufacturer;
        this.hash = hash;
    }

    // Getters
    public String getVersion() {
        return version;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getHash() {
        return hash;
    }
}
