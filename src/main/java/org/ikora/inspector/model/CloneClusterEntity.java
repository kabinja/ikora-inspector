package org.ikora.inspector.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "clone_cluster")
public class CloneClusterEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private CloneTypeEntity type;

    @ManyToOne
    private CloneDispersionEntity dispersion;

    @ManyToMany
    @JoinTable(
            name = "clone",
            joinColumns = @JoinColumn(name = "cluster"),
            inverseJoinColumns = @JoinColumn(name = "keyword")
    )
    private Set<KeywordEntity> clones;

    public CloneClusterEntity() {}

    public CloneClusterEntity(CloneTypeEntity type, CloneDispersionEntity dispersion, Set<KeywordEntity> clones){
        this.type = type;
        this.dispersion = dispersion;
        this.clones = clones;
    }

    @Override
    public String toString() {
        return String.format(
                "Clone[id=%d, type=%s, dispersion=%s, size=%d]",
                id, type.getName(), dispersion.getName(), clones.size()
        );
    }

    public CloneTypeEntity getType() {
        return type;
    }

    public Set<KeywordEntity> getKeywords() {
        return clones;
    }
}
