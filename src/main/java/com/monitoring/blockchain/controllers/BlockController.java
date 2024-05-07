package com.monitoring.blockchain.controllers;

import com.monitoring.blockchain.elements.Firmware;
import com.monitoring.blockchain.services.BlockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/block")
public class BlockController {
    private final BlockService blockService;

    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }

    @PostMapping("/addFirmware")
    public void addFirmwareToBlock(@RequestParam("version") String version,
                                   @RequestParam("manufacturer") String manufacturer,
                                   @RequestParam("hash") String hash) {
        blockService.addFirmwareToBlock(version, manufacturer, hash);
    }

    @GetMapping("/calculateHash")
    public String calculateHash(@RequestParam("previousHash") String previousHash,
                                @RequestParam("version") String version,
                                @RequestParam("manufacturer") String manufacturer,
                                @RequestParam("hash") String hash) {
        return blockService.calculateHash(previousHash, version, manufacturer, hash);
    }

    @GetMapping("/previousHash")
    public String getPreviousHash() {
        return blockService.getPreviousHash();
    }

    @GetMapping("/firmwareList")
    public List<Firmware> getFirmwareList() {
        return blockService.getFirmwareList();
    }
}

