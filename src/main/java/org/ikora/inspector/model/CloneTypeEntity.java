package org.ikora.inspector.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
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
