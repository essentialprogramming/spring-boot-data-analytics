package com.service;

import com.base.persistence.entities.Team;
import com.base.persistence.repository.TeamRepository;
import com.mapper.TeamMapper;
import com.output.TeamJSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public List<TeamJSON> getTeamsByGroupId(Integer groupId) {

        final List<Team> teams = teamRepository.findAllByGroupId(groupId);

        if (teams.isEmpty()) {
            throw new HttpClientErrorException(NOT_FOUND, "No teams found for the given group Id!");
        }
        return teams.stream()
                .map(TeamMapper::teamToTeamJSON)
                .toList();
    }

}
