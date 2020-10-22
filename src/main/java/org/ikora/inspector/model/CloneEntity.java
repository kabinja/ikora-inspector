package org.ikora.inspector.model;

import javax.persistence.*;

@Entity
public class CloneEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Long group;

    @ManyToOne
    private CloneTypeEntity type;

    @ManyToOne
    private KeywordEntity keyword;

    public CloneEntity() {}

    public CloneEntity(Long group, CloneTypeEntity type, KeywordEntity keyword){
        this.group = group;
        this.type = type;
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return String.format(
                "Clone[group=%d, type=%s, keyword=%s]",
                group, type.getName(), keyword.getName()
        );
    }

    public Long getGroup() {
        return group;
    }

    public CloneTypeEntity getType() {
        return type;
    }

    public KeywordEntity getKeyword() {
        return keyword;
    }
}
