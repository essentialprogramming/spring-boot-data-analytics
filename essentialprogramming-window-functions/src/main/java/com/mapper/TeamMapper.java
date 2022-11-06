package com.mapper;

import com.base.persistence.model.TeamData;
import com.output.TeamJSON;

public class TeamMapper {

    private TeamMapper() {

    }

    public static TeamJSON teamToTeamJSON(final TeamData team) {
        return TeamJSON.builder()
                .name(team.getTeam())
                .points(team.getPoints())
                .build();
    }
}
