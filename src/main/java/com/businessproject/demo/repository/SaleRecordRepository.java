package com.businessproject.demo.repository;

import com.businessproject.demo.model.dbmodels.SaleRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;

public interface SaleRecordRepository extends MongoRepository<SaleRecord, String> {
    // Finds all sales records that are from the same sales representative
    List<SaleRecord> findAllBySalesRepId(String salesRepId);

    // Finds all sales records that are made in between a start date and end date
    @Query(value = "{'madeAt': {$gte :?0, $lte: ?1}}")
    List<SaleRecord> findSaleRecords(ZonedDateTime from, ZonedDateTime to);
}
