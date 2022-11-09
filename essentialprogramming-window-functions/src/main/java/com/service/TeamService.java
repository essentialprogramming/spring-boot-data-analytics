package com.service;

import com.base.persistence.entities.Team;
import com.base.persistence.repository.TeamRepositoryCustom;
import com.base.persistence.model.TeamData;
import com.mapper.TeamMapper;
import com.output.TeamJSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {

    private final TeamRepositoryCustom teamRepository;

    @Transactional
    public List<TeamJSON> getTeamsByGroupName(final String groupName) {

        final List<TeamData> teams = teamRepository.getAllTeamsFromGroup(groupName);

        if (teams.isEmpty()) {
            throw new HttpClientErrorException(NOT_FOUND, "No teams found for the given group name!");
        }
        return teams.stream()
                .map(TeamMapper::teamToTeamJSON)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<TeamData> getGroupWinners() {
        final List<TeamData> teams = teamRepository.findAllTeamsInFirstPlace();

        if (teams.isEmpty()) {
            throw new HttpClientErrorException(NOT_FOUND, "No teams found");
        }

        return teams;
    }

    @Transactional
    public List<TeamData> getAllTeamsRanked() {
        final List<TeamData> teams = teamRepository.findAllTeamsRanked();

        if (teams.isEmpty()) {
            throw new HttpClientErrorException(NOT_FOUND, "No teams found");
        }

        return teams;
    }
}
