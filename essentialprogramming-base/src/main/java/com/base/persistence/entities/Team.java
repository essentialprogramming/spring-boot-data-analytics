package com.base.persistence.entities;

import com.base.persistence.model.TeamData;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "team")
@Table(name = "team")
@ToString
@SqlResultSetMapping(
        name = "TeamDataMapping",
        classes = @ConstructorResult(
                targetClass = TeamData.class,
                columns = {
                        @ColumnResult(name = "group_name"),
                        @ColumnResult(name = "name"),
                        @ColumnResult(name = "points", type = Integer.class),
                        @ColumnResult(name = "ranking", type = Integer.class)})
)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "points")
    private int points;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    @ToString.Exclude
    private Group group;

}
