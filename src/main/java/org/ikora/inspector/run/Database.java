package org.ikora.inspector.run;

import org.ikora.inspector.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import tech.ikora.model.Projects;

@Lazy
@Component
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

    Database(){
    }

    void store(Projects projects){
    }
}
