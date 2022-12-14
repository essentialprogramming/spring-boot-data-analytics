package com.base.persistence.repository;

import java.util.List;

import com.base.persistence.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer>, TeamRepositoryCustom {

}
