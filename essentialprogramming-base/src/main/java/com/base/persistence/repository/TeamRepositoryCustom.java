package com.base.persistence.repository;

import com.base.persistence.entities.Team;
import com.base.persistence.model.TeamData;
import java.util.List;

public interface TeamRepositoryCustom {

    List<Team> getAllTeamsFromGroup(final String groupName);

    List<TeamData> findAllTeamsInFirstPlace();

}
