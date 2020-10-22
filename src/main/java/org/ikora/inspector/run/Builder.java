package org.ikora.inspector.run;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ikora.inspector.configuration.Gitlab;
import org.ikora.inspector.configuration.InspectorConfiguration;
import tech.ikora.BuildConfiguration;
import tech.ikora.builder.BuildResult;
import tech.ikora.gitloader.Api;
import tech.ikora.gitloader.GitEngine;
import tech.ikora.gitloader.GitEngineFactory;
import tech.ikora.gitloader.git.LocalRepository;
import tech.ikora.model.Projects;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Builder {
    private static final Logger logger = LogManager.getLogger(Builder.class);

    static Projects build(InspectorConfiguration configuration) throws Exception {
        // read configuration and setup system
        Set<File> location = getLocation(configuration);

        // configuration definition
        BuildConfiguration buildConfiguration = new BuildConfiguration();

        // analyze projects
        logger.info("Start building projects...");
        BuildResult results = tech.ikora.builder.Builder.build(location, buildConfiguration, true);

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

            final GitEngine git = GitEngineFactory.create(Api.Gitlab);
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

    private static String createTmpFolder(String location) {
        File folder = location != null ? new File(location) : null;

        if(location == null || location.isEmpty() || !folder.isDirectory()){
            File tmp = FileUtils.getTempDirectory();
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            String folderName = "project-analytics-" + ts.toInstant().toEpochMilli();
            folder = new File(tmp, folderName);

            location = folder.getAbsolutePath();
        }

        if(!folder.exists()) {
            if(!folder.mkdir()){
                logger.error("Failed to create directory '" + location + "'", new IOException("Failed to create directory " + location));
            }
        }

        folder.deleteOnExit();

        return location;
    }
}
