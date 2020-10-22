package org.ikora.inspector.entity;

import javax.persistence.*;

@Entity
public class Violation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    private Position position;

    protected Violation() {}

    public Violation(String name){
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

    public Position getPosition() {
        return position;
    }
}
