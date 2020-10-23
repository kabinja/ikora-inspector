package org.ikora.inspector.model;

import javax.persistence.*;

@Entity
@Table(name = "clone_dispersion")
public class CloneDispersionEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;

    protected CloneDispersionEntity() {}

    public CloneDispersionEntity(String name, String description){
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("CloneDispersion[id=%d, name=%s, description=%s]", id, name, description);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
