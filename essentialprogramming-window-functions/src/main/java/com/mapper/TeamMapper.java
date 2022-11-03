package com.mapper;

import com.base.persistence.entities.Team;
import com.output.TeamJSON;

public class TeamMapper {

    private TeamMapper() {

    }

    public static TeamJSON teamToTeamJSON(Team team) {
        return TeamJSON.builder().name(team.getName()).build();
    }
}
