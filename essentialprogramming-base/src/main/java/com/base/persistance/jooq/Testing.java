package com.base.persistance.jooq;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.base.persistence.entities.generated.tables.Group;
import com.base.persistence.entities.generated.tables.Team;
import com.base.persistence.repository.TeamRepository;
import lombok.extern.log4j.Log4j2;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Result;
import org.jooq.SelectOnConditionStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class Testing {

    @Autowired
    private DSLContext jooqContext;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TeamRepository repository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        repository.findAll().stream().map(com.base.persistence.entities.Team::toString)
            .forEach(log::info);

        Field<Object> rank = DSL.field("ranking");
        var jooqQuery =
            jooqContext.select(
                Team.TEAM.ID,
                Team.TEAM.NAME,
                Team.TEAM.POINTS,
//                Group.GROUP.NAME.as("group_name"),
                DSL.rowNumber()
                    .over()
//                    .partitionBy(Group.GROUP.NAME)
                    .partitionBy(Team.TEAM.NAME)
                    .orderBy(Team.TEAM.POINTS.desc())
                    .as(rank)
            )
            .from(Team.TEAM)
//            .join(Group.GROUP)
//            .on(Team.TEAM.GROUPID.eq(Group.GROUP.ID))
            .where(rank.eq(1));
//            .orderBy(Group.GROUP.NAME.asc());

        String queryString = jooqQuery.getSQL();

        log.info(queryString);
        Query query = entityManager.createNativeQuery(queryString, Tuple.class) ;
        List<Object> values = jooqQuery.getBindValues();

        IntStream.range(0, values.size()).forEach(i -> {
            query.setParameter(i + 1, values.get(i));
        });

//        List<Tuple> result = query.getResultList();
//        List<Tuple> result = new ArrayList<>();
//
//        result.forEach(curr -> {
//             String value = Arrays
//                     .stream(curr.toArray())
//                     .map(String::valueOf)
//                     .collect(Collectors.joining(" "));
//             log.info(value);
//        });
    }
}
