package com.monitoring.blockchain.models.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document(collection = "block")
public class BlockDocument {
    @Id
    private String id;
    private String previousHash;
    private List<FirmwareDocument> firmwareList;

    public BlockDocument() {
    }
    public BlockDocument(String previousHash, List<FirmwareDocument> firmwareList) {
        this.previousHash = previousHash;
        this.firmwareList = firmwareList;
    }

    public BlockDocument(String id, String previousHash, List<FirmwareDocument> firmwareList) {
        this.id = id;
        this.previousHash = previousHash;
        this.firmwareList = firmwareList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public List<FirmwareDocument> getFirmwareList() {
        return firmwareList;
    }

    public void setFirmwareList(List<FirmwareDocument> firmwareList) {
        this.firmwareList = firmwareList;
    }
}
