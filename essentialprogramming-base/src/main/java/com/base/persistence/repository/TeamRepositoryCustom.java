package com.base.persistence.repository;

import com.base.persistence.entities.Team;
import com.base.persistence.repository.dto.TeamStandingDTO;
import java.util.List;

public interface TeamRepositoryCustom {

    List<Team> getAllTeamsFromGroup(String groupName);

    List<TeamStandingDTO> findAllTeamsInFirstPlace();

}
