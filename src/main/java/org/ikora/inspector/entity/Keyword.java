package org.ikora.inspector.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Keyword {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private KeywordType keywordType;

    private String name;

    @ManyToOne
    private Position position;

    @ManyToMany(targetEntity=Keyword.class)
    private Set<Keyword> usage;

    protected Keyword() {}

    public Keyword(KeywordType keywordType, String name, Position position){
        this.keywordType = keywordType;
        this.name = name;
        this.position = position;
    }

    @Override
    public String toString() {
        return String.format(
                "Keyword[id=%d, type=%s, name=%s, file=%s, project)%s]",
                id, keywordType.getName(), name, position.getFile(), position.getProject().getName()
        );
    }

    public Long getId() {
        return id;
    }

    public KeywordType getType() {
        return keywordType;
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public Set<Keyword> getUsage() {
        return usage;
    }

    public void setUsage(Set<Keyword> usage) {
        this.usage = usage;
    }
}
