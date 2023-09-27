package com.bilgeadam.controller;

import com.bilgeadam.rabbitmq.model.GetPendingCommentsCompanyNameModel;
import com.bilgeadam.rabbitmq.model.GetPendingCommentsResponseModel;
import com.bilgeadam.repository.entity.Comment;
import com.bilgeadam.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constant.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(COMMENT)
@CrossOrigin(origins = "*" ,allowedHeaders = "*")
public class CommentController {

    private final CommentService commentService;


    @GetMapping(GET_COMMENTS)
    public ResponseEntity<List<Comment>> getComments(){
        return ResponseEntity.ok(commentService.getComments());
    }


    @GetMapping(GET_PENDING_COMMENTS)
    public ResponseEntity<List<GetPendingCommentsResponseModel>> getPendingComments(){
        return ResponseEntity.ok(commentService.getPendingComments2());
    }

    @PutMapping(ACTIVATE_COMMENT)
    public ResponseEntity<Boolean> activeComment(String id){
        return ResponseEntity.ok(commentService.activeComment(id));
    }

    @PutMapping(DENIED_COMMENT)
    public ResponseEntity<Boolean> deniedComment(String id){
        return ResponseEntity.ok(commentService.deniedComment(id));
    }
    @GetMapping(GET_COMMENTS_BY_COMPANY+"/{authid}")
    public ResponseEntity<List<Comment>> getCommentsByCompany(@PathVariable Long authid){
        return ResponseEntity.ok(commentService.getCommentsByCompanyId(authid));
    }

    @GetMapping(GET_COMMENTS_BY_COMPANY_FOR_GUEST+"/{companyid}")
    public ResponseEntity<List<Comment>> getCommentsByCompanyForGuest(@PathVariable String companyId){
        return ResponseEntity.ok(commentService.getCommentsWithCompanyId(companyId));
    }

    @GetMapping(GET_NUMBER_OF_COMMENTS)
    public ResponseEntity<Integer> getNumberOfComments(){
        return ResponseEntity.ok(commentService.getNumberOfComments());
    }

}
