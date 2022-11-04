package com.base.persistence.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Describes the placing of a team in its group
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class TeamStandingDTO {

    private String name;

    private Integer points;

    private String groupName;

    private Integer ranking;
}
