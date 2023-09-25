package com.bilgeadam.service;

import com.bilgeadam.mapper.ICommentMapper;
import com.bilgeadam.rabbitmq.model.*;
import com.bilgeadam.rabbitmq.producer.GetCompanyCommentsProducer;
import com.bilgeadam.rabbitmq.producer.GetPendingCommentsCompanyNameProducer;
import com.bilgeadam.rabbitmq.producer.GetPendingCommentsEmployeeProducer;
import com.bilgeadam.repository.ICommentRepository;
import com.bilgeadam.repository.entity.Comment;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService extends ServiceManager<Comment, String> {
    private final ICommentRepository commentRepository;
    private final GetCompanyCommentsProducer getCompanyCommentsProducer;
    private final GetPendingCommentsCompanyNameProducer getPendingCommentsCompanyNameProducer;
    private final GetPendingCommentsEmployeeProducer getPendingCommentsEmployeeProducer;
    public CommentService(ICommentRepository commentRepository, GetCompanyCommentsProducer getCompanyCommentsProducer,
                          GetPendingCommentsCompanyNameProducer getPendingCommentsCompanyNameProducer,
                          GetPendingCommentsEmployeeProducer getPendingCommentsEmployeeProducer){
        super(commentRepository);
        this.commentRepository = commentRepository;
        this.getCompanyCommentsProducer = getCompanyCommentsProducer;
        this.getPendingCommentsCompanyNameProducer = getPendingCommentsCompanyNameProducer;
        this.getPendingCommentsEmployeeProducer = getPendingCommentsEmployeeProducer;
    }

    // adds comment to comment db
    public Boolean addCommentSaveComment(AddCommentSaveCommentModel addCommentSaveCommentModel) {
        commentRepository.save(ICommentMapper.INSTANCE.fromAddCommentSaveCommentModelToComment(addCommentSaveCommentModel));
        return true;
    }

    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getPendingComments() {
        return commentRepository.findByStatus(EStatus.PENDING);
    }

    public List<GetPendingCommentsResponseModel> getPendingComments2() {
        List<Comment> commentList = commentRepository.findByStatus(EStatus.PENDING);
        List<GetPendingCommentsResponseModel> returningList = new ArrayList<>();
        commentList.forEach(comment -> {
            GetPendingCommentsCompanyNameModel companyNameModel = new GetPendingCommentsCompanyNameModel();
            companyNameModel.setId(comment.getCompanyId());
            String companyName = getPendingCommentsCompanyNameProducer.returnCompanyName(companyNameModel);

            GetPendingCommentsEmployeeModel employeeModel = new GetPendingCommentsEmployeeModel();
            employeeModel.setId(comment.getUserId());
            String employeeNameSurname = getPendingCommentsEmployeeProducer.returnEmployeeNameSurname(employeeModel);

            GetPendingCommentsResponseModel model = GetPendingCommentsResponseModel.builder()
                    .id(comment.getId())
                    .userId(comment.getUserId())
                    .companyName(companyName)
                    .employeeNameSurname(employeeNameSurname)
                    .header(comment.getHeader())
                    .content(comment.getContent())
                    .status(comment.getStatus())
                    .build();
            returningList.add(model);
        });
        return returningList;
    }

    public Boolean activeComment(String id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            optionalComment.get().setStatus(EStatus.APPROVED);
            update(optionalComment.get());
            return true;
        }
        throw new RuntimeException("hata");
    }

    public Boolean deniedComment(String id) {
        Optional<Comment> optionalCompany = commentRepository.findById(id);
        if (optionalCompany.isPresent()) {
            optionalCompany.get().setStatus(EStatus.CANCELLED);
            update(optionalCompany.get());
            return true;
        }
        throw new RuntimeException("hata");
    }

    public List<Comment> getCommentsByCompanyId(Long authid) {
        GetCompanyCommentsModel getCompanyCommentsModel = new GetCompanyCommentsModel();
        getCompanyCommentsModel.setAuthid(authid);
        String companyId = getCompanyCommentsProducer.sendAuthIdToUser(getCompanyCommentsModel);
        List<Comment> commentList = commentRepository.findAllByCompanyId(companyId);
        System.out.println("comment service'teki comment list...: " + commentList);
        return commentList;
    }
}
