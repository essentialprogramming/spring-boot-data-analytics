package com.base.persistence.repository;

import com.base.persistence.model.TeamData;
import java.util.List;

public interface TeamRepositoryCustom {

    List<TeamData> findAllTeamsRanked();

    List<TeamData> getAllTeamsFromGroup(final String groupName);

    List<TeamData> findAllTeamsInFirstPlace();

}
