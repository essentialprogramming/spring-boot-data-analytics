package com.base.persistence.repository;

import com.base.persistence.entities.generated.tables.Group;
import com.base.persistence.entities.generated.tables.Team;
import com.base.persistence.repository.dto.TeamStandingDTO;
import lombok.extern.log4j.Log4j2;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.base.persistence.entities.generated.Tables.GROUP;
import static com.base.persistence.entities.generated.tables.Team.TEAM;

@Log4j2
public class TeamRepositoryCustomImpl implements TeamRepositoryCustom {

    @Autowired
    private DSLContext jooqContext;

    @PersistenceContext
    private EntityManager entityManager;

    private Team teamTable = TEAM;
    private Group groupTable = GROUP;

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
                        .on(Team.TEAM.GROUPID.eq(Group.GROUP.ID))
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
                .toList();
    }

    public List<com.base.persistence.entities.Team> getAllTeamsFromGroup(String groupId) {

        jooqContext.configuration().settings().setRenderNameCase(RenderNameCase.AS_IS);

        jooqContext.configuration().settings().setRenderQuotedNames(RenderQuotedNames.ALWAYS);

        SelectOnConditionStep<Record> jooqQuery = jooqContext.select(getFieldList())
                .from(teamTable)
                .innerJoin(groupTable)
                .on(teamTable.GROUPID.eq(groupTable.ID));

        Query q = entityManager.createNativeQuery(jooqQuery.getSQL(), Tuple.class);
        setBindParameterValues(q, jooqQuery);

        List<Tuple> result = q.getResultList();

        return result.stream().map(field -> new com.base.persistence.entities.Team(
                field.get(0, BigInteger.class).intValue(),
                field.get(1, String.class),
                field.get(2, BigInteger.class).intValue(),
                null
        )).toList();

    }

    private List<Field> getFieldList() {

        List<Field> fieldList = new ArrayList<>();

        fieldList.add(TEAM.ID);
        fieldList.add(TEAM.NAME);
        fieldList.add(TEAM.GROUPID);

        return fieldList;
    }

    private void setBindParameterValues(Query hibernateQuery, org.jooq.Query jooqQuery) {
        List<Object> values = jooqQuery.getBindValues();
        for (int i = 0; i < values.size(); i++) {
            hibernateQuery.setParameter(i + 1, values.get(i));
        }
    }
}
