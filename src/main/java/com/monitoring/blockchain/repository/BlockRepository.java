package com.monitoring.blockchain.repository;

import com.monitoring.blockchain.models.documents.BlockDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends MongoRepository<BlockDocument, String> {
}
