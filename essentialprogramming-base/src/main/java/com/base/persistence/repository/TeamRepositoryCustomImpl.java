package com.base.persistence.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.base.persistence.entities.generated.tables.Group;
import com.base.persistence.entities.generated.tables.Team;
import com.base.persistence.repository.dto.TeamStandingDTO;
import lombok.extern.log4j.Log4j2;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record4;
import org.jooq.SelectConditionStep;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
public class TeamRepositoryCustomImpl implements TeamRepositoryCustom {

    @Autowired
    private DSLContext jooqContext;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TeamStandingDTO> findAllTeamsInFirstPlace() {
        Field<Object> ranking = DSL.field("ranking");
        Field<Object> groupName = DSL.field("group_name");
        Table<?> subQueryAlias = DSL.table("nested");

        Table<Record4<String, Integer, String, Integer>> subQuery =
            jooqContext.select(
                    Team.TEAM.NAME,
                    Team.TEAM.POINTS,
                    Group.GROUP.NAME.as(groupName),
                    DSL.rank()
                        .over()
                        .partitionBy(Group.GROUP.NAME)
                        .orderBy(Team.TEAM.POINTS.desc())
                        .as(ranking)
                )
                .from(Team.TEAM)
                .join(Group.GROUP)
                .on(Team.TEAM.GROUP_ID.eq(Group.GROUP.ID))
                .orderBy(Group.GROUP.NAME.asc())
                .asTable(subQueryAlias);

        SelectConditionStep<Record> finalQuery = jooqContext
            .select(subQuery.fields())
            .from(subQuery)
            .where(ranking.equal(1));

        String queryString = finalQuery.getSQL();

        log.info(queryString);
        Query query = entityManager.createNativeQuery(queryString, Tuple.class);
        List<Object> values = finalQuery.getBindValues();

        IntStream.range(0, values.size()).forEach(i -> {
            query.setParameter(i + 1, values.get(i));
        });

        List<Tuple> result = query.getResultList();

        return result
            .stream()
            .map(curr -> new TeamStandingDTO(
                curr.get(0, String.class),
                curr.get(1, Integer.class),
                curr.get(2, String.class),
                curr.get(3, BigInteger.class).intValue()
            ))
            .collect(Collectors.toList());
    }
}
