package com.base.persistence.repository.impl;

import static com.base.persistence.entities.generated.Tables.GROUP;
import static com.base.persistence.entities.generated.Tables.TEAM;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.base.persistence.entities.generated.tables.Group;
import com.base.persistence.repository.TeamRepositoryCustom;
import com.base.persistence.model.TeamData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.impl.DSL;

@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class TeamRepositoryCustomImpl implements TeamRepositoryCustom {

    @PersistenceContext
    private final EntityManager entityManager;
    private final DSLContext dslContext;

    @Override
    public List<TeamData> findAllTeamsInFirstPlace() {
        Field<Object> ranking = DSL.field("ranking");
        Field<Object> groupName = DSL.field("group_name");
        Table<?> subQueryAlias = DSL.table("nested");

        var subQuery =
                dslContext.select(
                                TEAM.NAME,
                                TEAM.POINTS,
                                GROUP.NAME.as(groupName),
                                DSL.rank()
                                        .over()
                                        .partitionBy(GROUP.NAME)
                                        .orderBy(TEAM.POINTS.desc())
                                        .as(ranking)
                        )
                        .from(TEAM)
                        .join(GROUP)
                        .on(TEAM.GROUP_ID.eq(Group.GROUP.ID))
                        .orderBy(Group.GROUP.NAME.asc())
                        .asTable(subQueryAlias);

        var jooqQuery = dslContext
                .select(subQuery.fields())
                .from(subQuery)
                .where(ranking.equal(1));

        final String queryString = jooqQuery.getSQL();
        log.info(queryString);

        final Query query = entityManager.createNativeQuery(queryString, "TeamDataMapping");
        setBindParameterValues(query, jooqQuery);

        return (List<TeamData>) query.getResultList();
    }

    @Override
    public List<TeamData> findAllTeamsRanked() {
        Field<Object> ranking = DSL.field("ranking");
        Field<Object> groupName = DSL.field("group_name");

        var jooqQuery =
                dslContext.select(
                                TEAM.NAME,
                                TEAM.POINTS,
                                GROUP.NAME.as(groupName),
                                DSL.rank()
                                        .over()
                                        .orderBy(TEAM.POINTS.desc())
                                        .as(ranking)
                        )
                        .from(TEAM)
                        .join(GROUP)
                        .on(TEAM.GROUP_ID.eq(Group.GROUP.ID))
                        .orderBy(ranking.asc());

        final String queryString = jooqQuery.getSQL();
        log.info(queryString);

        final Query query = entityManager.createNativeQuery(queryString, "TeamDataMapping");
        setBindParameterValues(query, jooqQuery);

        return (List<TeamData>) query.getResultList();
    }

    public List<TeamData> getAllTeamsFromGroup(
            final String groupName
    ) {

        val jooqQuery = dslContext
                .select(getFieldList())
                .from(TEAM)
                .innerJoin(GROUP)
                .on(TEAM.GROUP_ID.eq(GROUP.ID))
                .where(GROUP.NAME.eq(groupName));

        final Query query = entityManager.createNativeQuery(
                jooqQuery.getSQL(),
                Tuple.class
        );
        setBindParameterValues(query, jooqQuery);

        final List<Tuple> result = query.getResultList();
        return result.stream().map(field -> new TeamData(
                field.get(0, String.class),
                field.get(1, String.class),
                field.get(2, Integer.class),
                null
        )).collect(Collectors.toList());
    }

    private Set<Field<?>> getFieldList() {

        final Set<Field<?>> fieldList = new LinkedHashSet<>();
        final Field<Object> groupName = DSL.field("group_name");

        fieldList.add(GROUP.NAME.as(groupName));
        fieldList.add(TEAM.NAME);
        fieldList.add(TEAM.POINTS);


        return fieldList;
    }

    private void setBindParameterValues(
            final Query hibernateQuery,
            final org.jooq.Query jooqQuery
    ) {
        final List<Object> values = jooqQuery.getBindValues();
        IntStream.range(0, values.size()).forEach(i ->
                hibernateQuery.setParameter(i + 1, values.get(i))
        );
    }
}
