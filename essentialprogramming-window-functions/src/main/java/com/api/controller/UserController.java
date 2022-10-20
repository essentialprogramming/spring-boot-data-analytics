package com.api.controller;


import com.model.UserInput;
import com.output.Response;
import com.output.UserJSON;
import com.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.Serializable;
import java.security.GeneralSecurityException;


@Tag(description = "User API", name = "User Services")
@RequestMapping("/v1/")
@RestController
@Validated
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;


    @PostMapping(value = "user", consumes = {"application/json"}, produces = {"application/json"})
    @Operation(summary = "Create user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Return user if successfully added",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserJSON.class)))
            })
    public ResponseEntity<Serializable> createUser(@RequestBody @Valid UserInput userInput, HttpServletRequest request) throws GeneralSecurityException {

        return Response.ok(createUser(userInput));
    }

    private Serializable createUser(UserInput userInput) {
        boolean isValid = userService.checkAvailabilityByEmail(userInput.getEmail());
        if (!isValid) {
            throw new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return userService.createUser(userInput);
    }


    @GetMapping(value = "user", produces = {"application/json"})
    @Operation(summary = "Load user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Return authenticated user if it was successfully found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserJSON.class)))
            })
    public ResponseEntity<Serializable> load(@RequestParam(name = "email", required = true) String email) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.loadUser(email));

    }
}