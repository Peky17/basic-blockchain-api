package com.monitoring.blockchain.models.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "firmware")
public class FirmwareDocument {
    @Id
    private String id;
    private String version;
    private String manufacturer;
    private String hash;

    public FirmwareDocument() {
    }

    public FirmwareDocument(String version, String manufacturer, String hash) {
        this.version = version;
        this.manufacturer = manufacturer;
        this.hash = hash;
    }

    public FirmwareDocument(String id, String version, String manufacturer, String hash) {
        this.id = id;
        this.version = version;
        this.manufacturer = manufacturer;
        this.hash = hash;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
