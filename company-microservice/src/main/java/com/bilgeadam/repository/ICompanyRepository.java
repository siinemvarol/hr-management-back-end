package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Company;
import com.bilgeadam.repository.enums.EStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICompanyRepository extends MongoRepository<Company,String> {

    List<Company> findByCreateDateAfter(Long date);

    List<Company> findByStatus(EStatus s);

    List<Company> findCompaniesByStatus(EStatus s);
  
    Optional<Company> findOptionalById(String id);

}
