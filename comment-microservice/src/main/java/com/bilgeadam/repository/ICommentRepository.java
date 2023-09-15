package com.bilgeadam.repository;

import com.bilgeadam.repository.entity.Comment;
import com.bilgeadam.repository.enums.EStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByStatus(EStatus eStatus);
}
