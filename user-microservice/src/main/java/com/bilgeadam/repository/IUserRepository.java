package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.User;
import com.bilgeadam.repository.enums.ERole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends MongoRepository<User, String> {

    Optional<User> findOptionalByAuthid(Long authid);

    Optional<User> findOptionalByUsername(String username);

    List<User> findByCompanyId(String id);
}
