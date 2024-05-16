package com.monitoring.blockchain.controllers;
import com.monitoring.blockchain.models.Block;
import com.monitoring.blockchain.services.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/blockchain")
public class BlockchainController {

    @Autowired
    private BlockchainService blockchainService;

    @GetMapping("/chain")
    public List<Block> getChain() {
        return blockchainService.getChain();
    }

    @PostMapping("/transactions/new")
    public String newTransaction(@RequestParam String sender, @RequestParam String recipient, @RequestParam int amount) {
        blockchainService.addTransaction(sender, recipient, amount);
        return "Transaction added";
    }

    @PostMapping("/mine")
    public Block mine() {
        return blockchainService.mineBlock();
    }

    @PostMapping("/nodes/register")
    public Map<String, Object> registerNodes(@RequestBody Map<String, List<String>> nodes) {
        List<String> nodeAddresses = nodes.get("nodes");
        if (nodeAddresses == null) {
            throw new IllegalArgumentException("Please supply a valid list of nodes");
        }

        for (String node : nodeAddresses) {
            blockchainService.registerNode(node);
        }

        return Map.of(
                "message", "New nodes have been added",
                "total_nodes", blockchainService.getNodes()
        );
    }

    @GetMapping("/nodes/resolve")
    public Map<String, Object> consensus() {
        boolean replaced = blockchainService.resolveConflicts();

        if (replaced) {
            return Map.of(
                    "message", "Our chain was replaced",
                    "new_chain", blockchainService.getChain()
            );
        } else {
            return Map.of(
                    "message", "Our chain is authoritative",
                    "chain", blockchainService.getChain()
            );
        }
    }
}
