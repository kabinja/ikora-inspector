package org.ikora.inspector.model;

import javax.persistence.*;

@Entity
@Table(name = "keyword_type")
public class KeywordTypeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;

    protected KeywordTypeEntity() {}

    public KeywordTypeEntity(String name, String description){
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("KeywordType[id=%d, name=%s, description=%s]", id, name, description);
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
