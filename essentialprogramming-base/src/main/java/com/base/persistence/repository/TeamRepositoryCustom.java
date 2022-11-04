package com.base.persistence.repository;

import java.util.List;

import com.base.persistence.repository.dto.TeamStandingDTO;

public interface TeamRepositoryCustom {

    List<TeamStandingDTO> findAllTeamsInFirstPlace();

}
