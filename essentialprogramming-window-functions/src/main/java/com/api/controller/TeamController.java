package com.api.controller;

import java.util.List;

import com.base.persistence.model.TeamData;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(description = "Team API", name = "Team Services")
@RequestMapping("/v1/")
@RestController
@Validated
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeamController {

    private final TeamService teamService;

    @GetMapping(value = "group/{name}/teams", produces = {"application/json"})
    @Operation(summary = "Get teams by group name",
        responses = {
            @ApiResponse(responseCode = "200", description = "Return all teams from a group",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TeamJSON.class)))
        })
    public ResponseEntity<List<TeamJSON>> getTeamsByGroup(
            final @PathVariable(name = "name") String groupName
    ) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(teamService.getTeamsByGroupName(groupName));

    }

    @GetMapping(value = "group-winners", produces = {"application/json"})
    @Operation(summary = "Get team with most points from each group",
        responses = {
            @ApiResponse(responseCode = "200", description = "Return all teams which won their group",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TeamData.class)))
        })
    public ResponseEntity<List<TeamData>> getGroupWinners() {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(teamService.getGroupWinners());
    }

}
