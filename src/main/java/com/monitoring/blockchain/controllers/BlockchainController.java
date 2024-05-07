package com.monitoring.blockchain.controllers;

import com.monitoring.blockchain.elements.Block;
import com.monitoring.blockchain.services.BlockchainService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blockchain")
public class BlockchainController {
    private final BlockchainService blockchainService;

    public BlockchainController(BlockchainService blockchainService) {
        this.blockchainService = blockchainService;
    }

    @GetMapping("/checkIntegrity")
    public boolean checkFirmwareIntegrity(@RequestParam("version") String version,
                                          @RequestParam("hash") String hash) {
        return blockchainService.checkFirmwareIntegrity(version, hash);
    }

    @GetMapping("/checkIntegrityByBlockHash")
    public boolean checkFirmwareIntegrityByBlockHash(@RequestParam("blockHash") String blockHash,
                                                     @RequestParam("version") String version,
                                                     @RequestParam("hash") String hash) {
        return blockchainService.checkFirmwareIntegrityByBlockHash(blockHash, version, hash);
    }

    @GetMapping("/latestBlock")
    public Block getLatestBlock() {
        return blockchainService.getLatestBlock();
    }

    @GetMapping("/blockchainSize")
    public int getBlockchainSize() {
        return blockchainService.getBlockchainSize();
    }

    @GetMapping("/difficulty")
    public int getDifficulty() {
        return blockchainService.getDifficulty();
    }

    @GetMapping("/blockchain")
    public List<Block> getBlockchain() {
        return blockchainService.getBlockchain();
    }

    @GetMapping("/block/{index}")
    public Block getBlockByIndex(@PathVariable int index) {
        return blockchainService.getBlockByIndex(index);
    }

    @GetMapping("/block/{hash}")
    public Block getBlockByHash(@PathVariable String hash) {
        return blockchainService.getBlockByHash(hash);
    }
}
