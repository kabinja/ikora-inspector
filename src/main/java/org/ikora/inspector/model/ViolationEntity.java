package org.ikora.inspector.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "violation_type")
public class ViolationEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "violation",
            joinColumns = @JoinColumn(name = "type"),
            inverseJoinColumns = @JoinColumn(name = "position")
    )
    private Set<PositionEntity> positions;

    protected ViolationEntity() {}

    public ViolationEntity(String name, String description, Set<PositionEntity> positions){
        this.name = name;
        this.description = description;
        this.positions = positions;
    }

    @Override
    public String toString() {
        return String.format("Violation[id=%d, name=%s, description=%s, number=%d]", id, name, description, positions.size());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<PositionEntity> getPositions() {
        return positions;
    }
}
