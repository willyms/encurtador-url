package com.desafio.encurtadorurl.controller;

import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;

@Controller
@RequestMapping(value = {"/", "", " "})
public class RootController {
    private static final String URI_REDIRECT_SWAGGER = "/swagger-ui/index.html";
    private static final String URI_SWAGGER_CONFIG = URI_REDIRECT_SWAGGER + "?configUrl=/v3/api-docs/swagger-config";

    @GetMapping
    public ResponseEntity<Void> swaggerUi(HttpServletResponse httpResponse) {
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, URI_SWAGGER_CONFIG).build();
    }
}
