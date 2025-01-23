package com.epam.springsecurityrevise.controller;

import com.epam.springsecurityrevise.util.HttpUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.epam.springsecurityrevise.constants.Uri.Admin.*;

@RestController
@RequestMapping(BASE_URI)
public class AdminController {

    @GetMapping
    public ResponseEntity<String> get() {
        return HttpUtil.buildResponse("GET:: admin controller", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> post() {
        return HttpUtil.buildResponse("POST:: admin controller", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> put() {
        return HttpUtil.buildResponse("PUT:: admin controller", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> delete() {
        return HttpUtil.buildResponse("DELETE:: admin controller", HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<String> patch() {
        return HttpUtil.buildResponse("PATCH:: admin controller", HttpStatus.OK);
    }
}
