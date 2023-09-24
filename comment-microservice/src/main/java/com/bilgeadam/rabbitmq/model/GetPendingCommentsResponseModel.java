package com.bilgeadam.rabbitmq.model;

import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPendingCommentsResponseModel implements Serializable {
    private String companyName;
    private String employeeNameSurname;
    private String header;
    private String content;
    private EStatus status;
}
