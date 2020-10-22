package org.ikora.inspector.run;

import org.ikora.inspector.model.ProjectEntity;
import org.ikora.inspector.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import tech.ikora.model.Project;
import tech.ikora.model.Projects;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Lazy
@Service
public class Database {
    @Autowired
    private KeywordTypeRepository keywordTypeRepository;
    @Autowired
    private CloneTypeRepository cloneTypeRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private KeywordRepository keywordRepository;
    @Autowired
    private CloneClusterRepository cloneClusterRepository;
    @Autowired
    private ViolationRepository violationRepository;
    @Autowired
    private PositionRepository positionRepository;

    private Map<Project, ProjectEntity> projectToEntity = new HashMap<>();

    void store(Projects projects){
        storeProjects(projects);
        storeProjectDependencies();
    }

    private void storeProjects(Projects projects){
        for(Project project: projects){
            ProjectEntity projectEntity = new ProjectEntity(project.getName(), project.getLoc());
            projectToEntity.put(project, projectRepository.save(projectEntity));
        }
    }

    private void storeProjectDependencies(){
        for(Map.Entry<Project, ProjectEntity> entry: projectToEntity.entrySet()){
            Set<ProjectEntity> dependencies = new HashSet<>();
            ProjectEntity current = entry.getValue();

            for(Project dependency: entry.getKey().getDependencies()){
                dependencies.add(projectToEntity.get(dependency));
            }

            current.setDependencies(dependencies);
            projectToEntity.put(entry.getKey(), projectRepository.save(current));
        }
    }
}
