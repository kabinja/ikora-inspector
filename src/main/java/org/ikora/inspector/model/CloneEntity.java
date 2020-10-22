package org.ikora.inspector.model;

import javax.persistence.*;

@Entity
@Table(name = "clone")
public class CloneEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Long group_id;

    @ManyToOne
    private CloneTypeEntity type;

    @ManyToOne
    private KeywordEntity keyword;

    public CloneEntity() {}

    public CloneEntity(Long group_id, CloneTypeEntity type, KeywordEntity keyword){
        this.group_id = group_id;
        this.type = type;
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return String.format(
                "Clone[group=%d, type=%s, keyword=%s]",
                group_id, type.getName(), keyword.getName()
        );
    }

    public Long getGroupId() {
        return group_id;
    }

    public CloneTypeEntity getType() {
        return type;
    }

    public KeywordEntity getKeyword() {
        return keyword;
    }
}
