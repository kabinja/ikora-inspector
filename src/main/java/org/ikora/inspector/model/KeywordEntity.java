package org.ikora.inspector.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "keyword")
public class KeywordEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private KeywordTypeEntity keywordType;

    private String name;
    private int loc;
    private int size;
    private int level;
    private int library_calls;

    @ManyToOne
    private PositionEntity position;

    @ManyToMany(targetEntity= KeywordEntity.class)
    @JoinTable(
            name = "usage",
            joinColumns = @JoinColumn(name = "source"),
            inverseJoinColumns = @JoinColumn(name = "target")
    )
    private Set<KeywordEntity> usage;

    protected KeywordEntity() {}

    public KeywordEntity(KeywordTypeEntity keywordType, String name, int loc, int size, int level, int libraryCalls, PositionEntity position){
        this.keywordType = keywordType;
        this.name = name;
        this.loc = loc;
        this.size = size;
        this.level = level;
        this.library_calls = libraryCalls;
        this.position = position;
    }

    @Override
    public String toString() {
        return String.format(
                "Keyword[id=%d, type=%s, name=%s, loc=%d, size=%d, level=%d file=%s, project)%s]",
                id, keywordType.getName(), name, loc, size, level, position.getFile(), position.getProject().getName()
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

    public int getLoc() {
        return loc;
    }

    public int getSize() {
        return size;
    }

    public int getLevel() {
        return level;
    }

    public int getLibraryCalls() {
        return library_calls;
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
