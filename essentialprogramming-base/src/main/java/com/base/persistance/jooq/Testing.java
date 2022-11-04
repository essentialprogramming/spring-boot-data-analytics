package com.base.persistance.jooq;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.base.persistence.entities.generated.tables.Group;
import com.base.persistence.entities.generated.tables.Team;
import com.base.persistence.repository.TeamRepository;
import com.base.persistence.repository.dto.TeamStandingDTO;
import lombok.extern.log4j.Log4j2;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record4;
import org.jooq.SelectConditionStep;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class Testing {

    @Autowired
    private TeamRepository repository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        repository.findAllTeamsInFirstPlace()
            .stream()
            .map(TeamStandingDTO::toString)
            .forEach(log::info);
    }
}
