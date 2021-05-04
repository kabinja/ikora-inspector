package org.ikora.inspector.run;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ikora.inspector.configuration.Gitlab;
import org.ikora.inspector.configuration.InspectorConfiguration;
import lu.uni.serval.ikora.core.BuildConfiguration;
import lu.uni.serval.ikora.core.builder.BuildResult;
import lu.uni.serval.ikora.core.model.Projects;

import lu.uni.serval.commons.git.api.Api;
import lu.uni.serval.commons.git.api.GitEngine;
import lu.uni.serval.commons.git.api.GitEngineFactory;
import lu.uni.serval.commons.git.utils.LocalRepository;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.ikora.inspector.utils.FileUtils.createTmpFolder;

public class Builder {
    private static final Logger logger = LogManager.getLogger(Builder.class);

    static Projects build(InspectorConfiguration configuration) throws Exception {
        // read configuration and setup system
        Set<File> location = getLocation(configuration);

        // configuration definition
        BuildConfiguration buildConfiguration = new BuildConfiguration();

        // analyze projects
        logger.info("Start building projects...");
        BuildResult results = lu.uni.serval.ikora.core.builder.Builder.build(location, buildConfiguration, true);

        logger.info(String.format("Projects parsed in %d ms", results.getParsingTime()));
        logger.info(String.format("Projects linked in %d ms", results.getResolveTime()));
        logger.info(String.format("Projects built in %d ms", results.getBuildTime()));

        if(!results.getErrors().isEmpty()){
            logger.warn("Build finish with errors");
        }

        return results.getProjects();
    }

    private static Set<File> getLocation(InspectorConfiguration configuration) throws Exception {
        Set<File> location;

        if(configuration.isGitLab()){
            Gitlab gitlabConfig = configuration.getGitlab();

            String tmpFolder = createTmpFolder(gitlabConfig.getLocalFolder());

            final GitEngine git = GitEngineFactory.create(Api.GITLAB);
            git.setToken(gitlabConfig.getToken());
            git.setUrl(gitlabConfig.getUrl());
            git.setCloneFolder(tmpFolder);

            if(gitlabConfig.getDefaultBranch() != null){
                git.setDefaultBranch(gitlabConfig.getDefaultBranch());
            }

            if(gitlabConfig.getBranchExceptions() != null){
                for (Map.Entry<String, String> entry: gitlabConfig.getBranchExceptions().entrySet()){
                    git.setBranchForProject(entry.getKey(), entry.getValue());
                }
            }

            final Set<LocalRepository> localRepos = git.cloneProjectsFromGroup(gitlabConfig.getGroup());
            location = localRepos.stream().map(LocalRepository::getLocation).collect(Collectors.toSet());
        }
        else if(configuration.isLocalSource()){
            location = configuration.getLocalSource().getFolders();

            if(location.stream().anyMatch(f -> !f.exists())){
                throw new IOException("Some folders in the configuration do not exist");
            }
        }
        else{
            throw new Exception("Invalid configuration, missing source for text to analyze");
        }

        return location;
    }
}
