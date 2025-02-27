package com.ufma.PortalEgresso.model.entity.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ufma.PortalEgresso.model.entity.ENUMs.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class DepoimentoDTO {
    private String texto;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;
    private Status status;
}
