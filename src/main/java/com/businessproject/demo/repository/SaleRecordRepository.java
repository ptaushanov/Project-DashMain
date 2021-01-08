package com.businessproject.demo.repository;

import com.businessproject.demo.model.dbmodels.SaleRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SaleRecordRepository extends MongoRepository<SaleRecord, String> {
    List<SaleRecord> findAllBySalesRepId(String requesterId);
}
