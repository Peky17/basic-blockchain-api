package com.monitoring.blockchain.services;

import com.monitoring.blockchain.elements.Block;
import com.monitoring.blockchain.elements.Firmware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlockchainService {
    private List<Block> chain;
    private int difficulty;

    public BlockchainService() {
        this.difficulty = 1;
        this.chain = new ArrayList<>();
        // Create genesis block
        Block genesisBlock = new Block("0");
        chain.add(genesisBlock);
    }

    public void addBlock(Block block) {
        chain.add(block);
    }

    public boolean checkFirmwareIntegrity(String version, String hash) {
        for (Block block : chain) {
            for (Firmware firmware : block.getFirmwareList()) {
                if (firmware.getVersion().equals(version) && firmware.getHash().equals(hash)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkFirmwareIntegrityByBlockHash(String blockHash, String version, String hash) {
        for (Block block : chain) {
            if (block.calculateHash().equals(blockHash)) {
                for (Firmware firmware : block.getFirmwareList()) {
                    if (firmware.getVersion().equals(version) && firmware.getHash().equals(hash)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    public int getBlockchainSize() {
        return chain.size();
    }

    public int getDifficulty() {
        return difficulty;
    }

    public List<Block> getBlockchain() {
        return chain;
    }

    public Block getBlockByIndex(int index) {
        return chain.get(index);
    }

    public Block getBlockByHash(String hash) {
        for (Block block : chain) {
            if (block.calculateHash().equals(hash)) {
                return block;
            }
        }
        return null;
    }
}
