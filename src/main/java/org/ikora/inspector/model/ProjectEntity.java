package org.ikora.inspector.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private int loc;

    @ManyToMany(targetEntity= ProjectEntity.class)
    private Set<ProjectEntity> dependencies;

    protected ProjectEntity() {}

    public ProjectEntity(String name, int loc){
        this.name = name;
        this.loc = loc;
        this.dependencies = dependencies;
    }

    @Override
    public String toString() {
        return String.format("Project[id=%d, name=%s, loc=%d]", id, name, loc);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLoc() {
        return loc;
    }

    public Set<ProjectEntity> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Set<ProjectEntity> dependencies) {
        this.dependencies = dependencies;
    }
}
