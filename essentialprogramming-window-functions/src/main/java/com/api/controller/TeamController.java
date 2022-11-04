package com.api.controller;

import java.util.List;

import com.base.persistence.repository.dto.TeamStandingDTO;
import com.output.TeamJSON;
import com.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(description = "Team API", name = "Team Services")
@RequestMapping("/v1/")
@RestController
@Validated
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeamController {

    private final TeamService teamService;

    @GetMapping(value = "team-list", produces = {"application/json"})
    @Operation(summary = "Get teams by groupId",
        responses = {
            @ApiResponse(responseCode = "200", description = "Return all teams from a group",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TeamJSON.class)))
        })
    public ResponseEntity<List<TeamJSON>> getTeamsByGroupId(@RequestParam(name = "groupId") Integer groupId) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(teamService.getTeamsByGroupId(groupId));

    }

    @GetMapping(value = "first-places", produces = {"application/json"})
    @Operation(summary = "Get first place teams by points",
        responses = {
            @ApiResponse(responseCode = "200", description = "Return all first place teams",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TeamStandingDTO.class)))
        })
    public ResponseEntity<List<TeamStandingDTO>> getFirstPlaceTeams() {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(teamService.getFirstPlaceTeams());
    }

}
