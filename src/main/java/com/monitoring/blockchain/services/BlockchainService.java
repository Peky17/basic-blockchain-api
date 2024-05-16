package com.monitoring.blockchain.services;

import com.monitoring.blockchain.models.Block;
import com.monitoring.blockchain.models.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BlockchainService {
    private List<Block> chain;
    private List<Transaction> currentTransactions;
    private Set<String> nodes;
    private static final String MINER_ADDRESS = "our-miner-address";

    public BlockchainService() {
        chain = new ArrayList<>();
        currentTransactions = new ArrayList<>();
        nodes = new HashSet<>();
        // Crear el bloque génesis
        createBlock(100, "1");
    }

    public Block createBlock(long proof, String previousHash) {
        Block block = new Block(chain.size() + 1,
                System.currentTimeMillis(),
                new ArrayList<>(currentTransactions),
                proof, previousHash);
        currentTransactions.clear();
        chain.add(block);
        return block;
    }

    public void addTransaction(String sender, String recipient, int amount) {
        currentTransactions.add(new Transaction(sender, recipient, amount));
    }

    public Block getLastBlock() {
        return chain.get(chain.size() - 1);
    }

    public List<Block> getChain() {
        return chain;
    }

    public void registerNode(String address) {
        nodes.add(address);
    }

    public Set<String> getNodes() {
        return nodes;
    }

    public long proofOfWork(long lastProof) {
        long proof = 0;
        while (!validProof(lastProof, proof)) {
            proof++;
        }
        return proof;
    }

    private boolean validProof(long lastProof, long proof) {
        String guess = lastProof + "" + proof;
        String guessHash = org.apache.commons.codec.digest.DigestUtils.sha256Hex(guess);
        return guessHash.substring(0, 4).equals("0000");
    }

    public String hash(Block block) {
        String blockString = block.toString(); // Asegúrate de que el método toString() de Block devuelva una representación JSON del bloque
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex(blockString);
    }

    public Block mineBlock() {
        Block lastBlock = getLastBlock();
        long lastProof = lastBlock.getProof();
        long proof = proofOfWork(lastProof);
        // Recompensa al minero con 1 moneda
        addTransaction("0", MINER_ADDRESS, 1);
        // Crear el nuevo bloque
        String previousHash = hash(lastBlock);
        return createBlock(proof, previousHash);
    }

    public boolean validChain(List<Block> chain) {
        Block lastBlock = chain.get(0);
        int currentIndex = 1;

        while (currentIndex < chain.size()) {
            Block block = chain.get(currentIndex);

            if (!block.getPreviousHash().equals(hash(lastBlock))) {
                return false;
            }

            if (!validProof(lastBlock.getProof(), block.getProof())) {
                return false;
            }

            lastBlock = block;
            currentIndex++;
        }

        return true;
    }

    public boolean resolveConflicts() {
        List<Block> newChain = null;
        int maxLength = chain.size();

        RestTemplate restTemplate = new RestTemplate();

        for (String node : nodes) {
            String url = "http://" + node + "/api/blockchain/chain";
            BlockChainResponse response = restTemplate.getForObject(url, BlockChainResponse.class);

            if (response != null && response.getLength() > maxLength && validChain(response.getChain())) {
                maxLength = response.getLength();
                newChain = response.getChain();
            }
        }

        if (newChain != null) {
            chain = newChain;
            return true;
        }

        return false;
    }

    public static class BlockChainResponse {
        private int length;
        private List<Block> chain;
        public int getLength() {
            return length;
        }
        public void setLength(int length) {
            this.length = length;
        }
        public List<Block> getChain() {
            return chain;
        }
        public void setChain(List<Block> chain) {
            this.chain = chain;
        }
    }
}
