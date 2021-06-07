package com.desafio.encurtadorurl.model.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.time.*;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({ "url", "newUrl", "expirationdt"})
public class UrlDTO {
    @JsonProperty("url")
    private String url;
    @JsonProperty("expiration-date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime expirationdt;
    @JsonProperty("short-url")
    private String shortUrl;

    public static boolean verifyExpirationDate(UrlDTO urlDTO) {
        return urlDTO.getExpirationdt().compareTo(LocalDateTime.now()) > 0;
    }
}
