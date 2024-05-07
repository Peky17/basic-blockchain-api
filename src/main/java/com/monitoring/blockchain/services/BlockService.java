package com.monitoring.blockchain.services;

import com.monitoring.blockchain.elements.Block;
import com.monitoring.blockchain.elements.Firmware;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class BlockService {
    private final BlockchainService blockchainService;

    public BlockService(BlockchainService blockchainService) {
        this.blockchainService = blockchainService;
    }

    public void addFirmwareToBlock(String version, String manufacturer, String hash) {
        Block block = new Block(blockchainService.getLatestBlock().calculateHash());
        Firmware firmware = new Firmware(version, manufacturer, hash);
        block.addFirmware(firmware);
        blockchainService.addBlock(block);
    }

    public String calculateHash(String previousHash, String version, String manufacturer, String hash) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(version);
        stringBuilder.append(manufacturer);
        stringBuilder.append(hash);
        String data = previousHash + stringBuilder.toString();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(data.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
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

    public String getPreviousHash() {
        return blockchainService.getLatestBlock().getPreviousHash();
    }

    public List<Firmware> getFirmwareList() {
        return blockchainService.getLatestBlock().getFirmwareList();
    }
}