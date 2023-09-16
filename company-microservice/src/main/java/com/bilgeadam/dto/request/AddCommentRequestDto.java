package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddCommentRequestDto {

    @NotEmpty(message = "Company id field cannot be empty")
    private String companyId;

    @NotEmpty(message = "User id field cannot be empty")
    private String userId;

    @NotEmpty(message = "Header field cannot be empty")
    private String header;

    @NotEmpty(message = "Content field cannot be empty")
    private String content;
}
