package com.bilgeadam.service;

import com.bilgeadam.mapper.ICommentMapper;
import com.bilgeadam.rabbitmq.model.*;
import com.bilgeadam.rabbitmq.producer.*;
import com.bilgeadam.repository.ICommentRepository;
import com.bilgeadam.repository.entity.Comment;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService extends ServiceManager<Comment, String> {
    private final ICommentRepository commentRepository;
    private final GetCompanyCommentsProducer getCompanyCommentsProducer;
    private final GetPendingCommentsCompanyNameProducer getPendingCommentsCompanyNameProducer;
    private final GetPendingCommentsEmployeeProducer getPendingCommentsEmployeeProducer;
    private final GetTotalCommentsByCompanyProducer getTotalCommentsByCompanyProducer;
    private final GetTotalCommentsByEmployeeProducer getTotalCommentsByEmployeeProducer;
    public CommentService(ICommentRepository commentRepository, GetCompanyCommentsProducer getCompanyCommentsProducer,
                          GetPendingCommentsCompanyNameProducer getPendingCommentsCompanyNameProducer,
                          GetPendingCommentsEmployeeProducer getPendingCommentsEmployeeProducer,
                          GetTotalCommentsByCompanyProducer getTotalCommentsByCompanyProducer,
                          GetTotalCommentsByEmployeeProducer getTotalCommentsByEmployeeProducer){
        super(commentRepository);
        this.commentRepository = commentRepository;
        this.getCompanyCommentsProducer = getCompanyCommentsProducer;
        this.getPendingCommentsCompanyNameProducer = getPendingCommentsCompanyNameProducer;
        this.getPendingCommentsEmployeeProducer = getPendingCommentsEmployeeProducer;
        this.getTotalCommentsByCompanyProducer = getTotalCommentsByCompanyProducer;
        this.getTotalCommentsByEmployeeProducer = getTotalCommentsByEmployeeProducer;
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
            String[] employeeNameSurnameArray = getPendingCommentsEmployeeProducer.returnEmployeeNameSurname(employeeModel).split(" ");
            String employeeNameSurname = employeeNameSurnameArray[0] + " " + employeeNameSurnameArray[1];
            String avatar = employeeNameSurnameArray[2];
            Long authId = Long.valueOf(employeeNameSurnameArray[3]);

            GetPendingCommentsResponseModel model = GetPendingCommentsResponseModel.builder()
                    .id(comment.getId())
                    .userId(comment.getUserId())
                    .authId(authId)
                    .avatar(avatar)
                    .companyName(companyName)
                    .employeeNameSurname(employeeNameSurname)
                    .header(comment.getHeader())
                    .content(comment.getContent())
                    .status(comment.getStatus())
                    .build();
            returningList.add(model);
            System.out.println("model"+model.getAuthId());
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
        return commentList;
    }
    public List<Comment> getCommentsWithCompanyId(String companyId) {
        List<Comment> commentList = commentRepository.findAllByCompanyId(companyId);
        return commentList;
    }

    public Integer getNumberOfComments() {
        return commentRepository.findAll().size();
    }

   // returns total comments of a company for employee dashboard
    public Integer getTotalCommentsByCompany(Long authid) {
        GetTotalCommentsByCompanyModel model = new GetTotalCommentsByCompanyModel();
        model.setAuthid(authid);
        String companyId = getTotalCommentsByCompanyProducer.companyIdForCompanyComments(model);
        List<Comment> commentList = commentRepository.findAllByCompanyId(companyId);
        return commentList.size();
    }

    public Integer getTotalCommentsByEmployee(Long authid) {
        GetTotalCommentsByEmployeeModel model = new GetTotalCommentsByEmployeeModel();
        model.setAuthid(authid);
        GetTotalCommentsByEmployeeResponseModel responseModel = getTotalCommentsByEmployeeProducer.companyUserIdForEmployeeComments(model);
        String userId = responseModel.getUserId();
        List<Comment> commentList = commentRepository.findAllByUserId(userId);
        List<Comment> pendingCommentList = commentList.stream().filter(comment ->  comment.getStatus().equals(EStatus.PENDING)).collect(Collectors.toList());
        return pendingCommentList.size();
    }
}
