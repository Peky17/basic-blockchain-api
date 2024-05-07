package com.monitoring.blockchain.elements;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Block {
    private String previousHash;
    public List<Firmware> firmwareList;

    public Block(String previousHash) {
        this.previousHash = previousHash;
        this.firmwareList = new ArrayList<>();
    }

    // Add firmware to block
    public void addFirmware(Firmware firmware) {
        firmwareList.add(firmware);
    }

    // Calculate block hash
    public String calculateHash() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Firmware firmware : firmwareList) {
            stringBuilder.append(firmware.getVersion());
            stringBuilder.append(firmware.getManufacturer());
            stringBuilder.append(firmware.getHash());
        }
        String data = previousHash + stringBuilder.toString();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get previous hash
    public String getPreviousHash() {
        return previousHash;
    }

    // Get firmware list
    public List<Firmware> getFirmwareList() {
        return firmwareList;
    }
}