package com.desafio.encurtadorurl.controller;

import com.desafio.encurtadorurl.model.dto.*;
import com.desafio.encurtadorurl.service.*;
import com.desafio.encurtadorurl.util.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import javax.validation.*;
import javax.validation.constraints.NotEmpty;
import java.net.*;
import java.util.*;

@RestController
@RequestMapping("urls")
@RequiredArgsConstructor
public class UrlController {
    private final UrlService urlService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UrlDTO>> findAll() {
        return ResponseEntity.ok(urlService.findAll());
    }

    @GetMapping(value = "/{shortUrl}")
    public ResponseEntity<Void> findByNewUrl(@Valid @NotEmpty @PathVariable String shortUrl,  HttpServletResponse httpServletResponse) {
        UrlDTO urlDTO = urlService.findByNewUrl(shortUrl);

        String redirectTo = urlDTO.getUrl();
        if (!redirectTo.startsWith(UrlUtils.HTTP_PREFIX) && !redirectTo.startsWith(UrlUtils.HTTPS_PREFIX)) {
            redirectTo = UrlUtils.HTTP_PREFIX.concat(redirectTo);
        }
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, redirectTo).build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> save(@Valid @RequestBody UrlFormDTO urlFrom) {
        UrlDTO urlDTO = urlService.save(urlFrom.getUrl());
        return ResponseEntity.created(URI.create(urlDTO.getShortUrl())).build();
    }
}
