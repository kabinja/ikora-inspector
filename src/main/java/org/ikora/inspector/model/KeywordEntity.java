package org.ikora.inspector.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class KeywordEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private KeywordTypeEntity keywordType;

    private String name;

    @ManyToOne
    private PositionEntity position;

    @ManyToMany(targetEntity= KeywordEntity.class)
    private Set<KeywordEntity> usage;

    protected KeywordEntity() {}

    public KeywordEntity(KeywordTypeEntity keywordType, String name, PositionEntity position){
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

    public KeywordTypeEntity getType() {
        return keywordType;
    }

    public String getName() {
        return name;
    }

    public PositionEntity getPosition() {
        return position;
    }

    public Set<KeywordEntity> getUsage() {
        return usage;
    }

    public void setUsage(Set<KeywordEntity> usage) {
        this.usage = usage;
    }
}
