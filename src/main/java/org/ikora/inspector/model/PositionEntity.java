package org.ikora.inspector.model;

import javax.persistence.*;

@Entity
@Table(name = "position")
public class PositionEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String start;
    private String end;
    private String file;

    @ManyToOne
    private ProjectEntity project;

    protected PositionEntity() {};

    public PositionEntity(String start, String end, String file, ProjectEntity project){
        this.start = start;
        this.end = end;
        this.file = file;
        this.project = project;
    }

    @Override
    public String toString() {
        return String.format(
                "Position[id=%d, start=%s, end=%s, file=%s, project=%s]",
                id, start, end, file, project.getName()
        );
    }

    public Long getId() {
        return id;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getFile() {
        return file;
    }

    public ProjectEntity getProject() {
        return project;
    }
}
