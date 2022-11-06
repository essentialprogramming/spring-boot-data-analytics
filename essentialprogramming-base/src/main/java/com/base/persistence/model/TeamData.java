package com.base.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Team info and ranking in it`s group.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor

public class TeamData {

    private String group;
    private String team;
    private Integer points;
    private Integer ranking;
}
