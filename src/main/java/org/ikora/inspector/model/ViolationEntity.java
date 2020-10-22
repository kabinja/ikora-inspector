package org.ikora.inspector.model;

import javax.persistence.*;

@Entity
@Table(name = "violation")
public class ViolationEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    private PositionEntity position;

    protected ViolationEntity() {}

    public ViolationEntity(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Type[id=%d, name=%s]", id, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PositionEntity getPosition() {
        return position;
    }
}
