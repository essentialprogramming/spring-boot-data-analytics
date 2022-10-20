package com.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserJSON implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;
    private String firstName;
    private String lastName;

}
