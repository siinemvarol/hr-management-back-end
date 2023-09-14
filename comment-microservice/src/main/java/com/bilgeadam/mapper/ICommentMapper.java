package com.bilgeadam.mapper;

import com.bilgeadam.rabbitmq.model.AddCommentSaveCommentModel;
import com.bilgeadam.repository.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICommentMapper {
    ICommentMapper INSTANCE = Mappers.getMapper(ICommentMapper.class);

    Comment fromAddCommentSaveCommentModelToComment(final AddCommentSaveCommentModel addCommentSaveCommentModel);
}
