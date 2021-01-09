package com.businessproject.demo.repository;

import com.businessproject.demo.model.dbmodels.SaleRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;

public interface SaleRecordRepository extends MongoRepository<SaleRecord, String> {
    List<SaleRecord> findAllBySalesRepId(String requesterId);

    @Query(value = "{'madeAt': {$gte :?0, $lte: ?1}}")
    List<SaleRecord> findSaleRecords(ZonedDateTime from, ZonedDateTime to);
}
