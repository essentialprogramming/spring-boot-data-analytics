package com.model;


import com.base.model.Patterns;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInput {

    @NotNull(message = "FirstName field can not be null!")
    @Pattern(regexp = Patterns.NAME_REGEXP, message = "Invalid firstname! Only uppercase and lowercase letters are allowed.")
    @JsonProperty("firstName")
    private String firstName;

    @NotNull(message = "LastName field can not be null!")
    @Pattern(regexp = Patterns.NAME_REGEXP, message = "Invalid lastname! Only uppercase and lowercase letters are allowed.")
    @JsonProperty("lastName")
    private String lastName;

    @NotNull(message = "Email field can not be null!")
    @Email(message = "Email is invalid! Must be of format: example@domain.com",
            regexp = Patterns.EMAIL_REGEXP)
    @JsonProperty("email")
    private String email;


    
}
