package org.ikora.inspector.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private int loc;

    @ManyToMany(targetEntity=Project.class)
    private Set<Project> dependencies;

    protected Project() {}

    public Project(String name, int loc){
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

    public Set<Project> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Set<Project> dependencies) {
        this.dependencies = dependencies;
    }
}
