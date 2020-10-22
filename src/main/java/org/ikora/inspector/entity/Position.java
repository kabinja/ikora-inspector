package org.ikora.inspector.entity;

import javax.persistence.*;

@Entity
public class Position {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String start;
    private String end;
    private String file;

    @ManyToOne
    private Project project;

    protected Position() {};

    public Position(String start, String end, String file, Project project){
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

    public Project getProject() {
        return project;
    }
}
