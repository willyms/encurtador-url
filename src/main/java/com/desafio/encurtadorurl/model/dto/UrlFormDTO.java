package com.desafio.encurtadorurl.model.dto;

import lombok.*;
import org.hibernate.validator.constraints.*;

import javax.validation.constraints.*;

@Getter
@Setter
public class UrlFormDTO {
    @URL
    @NotNull
    private String url;
}
