package org.ikora.inspector.run;

import org.ikora.inspector.model.*;
import org.ikora.inspector.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import lu.uni.serval.ikora.core.analytics.KeywordStatistics;
import lu.uni.serval.ikora.core.analytics.clones.CloneCluster;
import lu.uni.serval.ikora.core.analytics.clones.Clones;
import lu.uni.serval.ikora.core.analytics.clones.KeywordCloneDetection;
import lu.uni.serval.ikora.core.analytics.visitor.SizeVisitor;
import lu.uni.serval.ikora.core.model.*;

import java.util.*;

@Lazy
@Service
public class Database {
    @Autowired
    private KeywordTypeRepository keywordTypeRepository;
    @Autowired
    private CloneTypeRepository cloneTypeRepository;
    @Autowired
    private CloneDispersionRepository cloneDispersionRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private KeywordRepository keywordRepository;
    @Autowired
    private CloneRepository cloneRepository;
    @Autowired
    private ViolationRepository violationRepository;
    @Autowired
    private PositionRepository positionRepository;

    private Projects projects;

    private final Map<Class<? extends KeywordDefinition>, KeywordTypeEntity> keywordTypeToEntity = new HashMap<>();
    private final Map<Clones.Type, CloneTypeEntity> cloneTypeToEntity = new HashMap<>();
    private final Map<Boolean, CloneDispersionEntity> cloneDispersionToEntity = new HashMap<>();

    private final Map<Project, ProjectEntity> projectToEntity = new HashMap<>();
    private final Map<KeywordDefinition, KeywordEntity> keywordToEntity = new HashMap<>();

    void store(Projects projects){
        this.projects = projects;

        storeTypes();
        storeProjects();
        storeViolations();
    }

    private void storeTypes(){
        //keyword types
        keywordTypeToEntity.put(UserKeyword.class, keywordTypeRepository.save(new KeywordTypeEntity(
                "User Keyword",
                ""
        )));

        keywordTypeToEntity.put(TestCase.class, keywordTypeRepository.save(new KeywordTypeEntity(
                "Test Case",
                ""
        )));

        //clone types
        cloneTypeToEntity.put(Clones.Type.TYPE_1, cloneTypeRepository.save(new CloneTypeEntity(
                "Type 1",
                ""
        )));

        cloneTypeToEntity.put(Clones.Type.TYPE_2, cloneTypeRepository.save(new CloneTypeEntity(
                "Type 2",
                ""
        )));

        cloneTypeToEntity.put(Clones.Type.TYPE_3, cloneTypeRepository.save(new CloneTypeEntity(
                "Type 3",
                ""
        )));

        cloneTypeToEntity.put(Clones.Type.TYPE_4, cloneTypeRepository.save(new CloneTypeEntity(
                "Type 4",
                ""
        )));

        //clone dispersion
        cloneDispersionToEntity.put(true, cloneDispersionRepository.save(new CloneDispersionEntity(
            "Cross Project",
            ""
        )));

        cloneDispersionToEntity.put(false, cloneDispersionRepository.save(new CloneDispersionEntity(
                "Within Project",
                ""
        )));
    }

    private void storeProjects(){
        for(Project project: projects){
            ProjectEntity projectEntity = new ProjectEntity(project.getName(), project.getLoc());
            projectToEntity.put(project, projectRepository.save(projectEntity));
        }

        storeProjectDependencies();
        storeKeywords();
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

    private void storeKeywords(){
        for(Map.Entry<Project, ProjectEntity> entry: projectToEntity.entrySet()){
            for(TestCase testCase: entry.getKey().getTestCases()){
                storeKeyword(testCase);
            }

            for(UserKeyword userKeyword: entry.getKey().getUserKeywords()){
                storeKeyword(userKeyword);
            }
        }

        storeClones();
        storeUsages();
    }

    private void storeKeyword(KeywordDefinition keyword){
        final SizeVisitor.Result size = KeywordStatistics.getSize(keyword);

        keywordToEntity.put(keyword, keywordRepository.save(new KeywordEntity(
                keywordTypeToEntity.get(keyword.getClass()),
                keyword.getName(),
                keyword.getLoc(),
                size.getTotalSize(),
                KeywordStatistics.getLevel(keyword),
                size.getLibraryKeywordSize(),
                getPosition(keyword.getRange(), keyword.getSourceFile())
        )));
    }

    private void storeClones(){
        final Clones<KeywordDefinition> clones = KeywordCloneDetection.findClones(projects);

        for (CloneCluster<KeywordDefinition> cluster: clones.getClusters()){
            cloneRepository.save(new CloneClusterEntity(
                    cloneTypeToEntity.get(cluster.getType()),
                    cloneDispersionToEntity.get(cluster.isCrossProject()),
                    getKeywords(cluster)
            ));
        }
    }

    private void storeUsages(){
        for(Map.Entry<KeywordDefinition, KeywordEntity> entry: keywordToEntity.entrySet()){
            final Set<KeywordDefinition> usages = entry.getKey().getUsages(KeywordDefinition.class);

            if(!usages.isEmpty()){
                entry.getValue().setUsage(getKeywords(usages));
                keywordToEntity.put(entry.getKey(), keywordRepository.save(entry.getValue()));
            }
        }
    }

    private void storeViolations(){

    }

    private Set<KeywordEntity> getKeywords(Collection<KeywordDefinition> collection){
        final Set<KeywordEntity> keywords = new HashSet<>(collection.size());

        for(KeywordDefinition keyword: collection){
            keywords.add(keywordToEntity.get(keyword));
        }

        return keywords;
    }

    private PositionEntity getPosition(Range range, SourceFile sourceFile){
        final String start = range.getStart().toString();
        final String end = range.getEnd().toString();
        final String file = sourceFile.getName();
        final ProjectEntity project = projectToEntity.get(sourceFile.getProject());

        final Optional<PositionEntity> result = positionRepository.findByStartAndEndAndFileAndProject(
                start,
                end,
                file,
                project
        );

        return result.orElseGet(() -> positionRepository.save(new PositionEntity(start, end, file, project)));
    }
}
