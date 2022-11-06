package com.output;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamJSON implements Serializable {

    private String name;
    private Integer points;

}
