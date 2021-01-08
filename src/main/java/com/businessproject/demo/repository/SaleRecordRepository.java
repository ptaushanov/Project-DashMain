package com.businessproject.demo.repository;

import com.businessproject.demo.model.SaleRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SaleRecordRepository extends MongoRepository<SaleRecord, String> {
    
}
