package org.ikora.inspector.model;

import javax.persistence.*;

@Entity
@Table(name = "clone_type")
public class CloneTypeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;

    protected CloneTypeEntity() {}

    public CloneTypeEntity(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("CloneType[id=%d, name=%s]", id, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
