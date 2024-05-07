package com.monitoring.blockchain.repository;

import com.monitoring.blockchain.models.documents.BlockDocument;
import com.monitoring.blockchain.models.documents.FirmwareDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirmwareRepository extends MongoRepository<FirmwareDocument, String> {
}
