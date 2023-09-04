package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICompanyRepository extends MongoRepository<Company,String> {
}
