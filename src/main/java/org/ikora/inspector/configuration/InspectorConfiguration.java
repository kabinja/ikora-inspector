package org.ikora.inspector.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class InspectorConfiguration {
    @JsonProperty("output")
    private String output;
    @JsonProperty("gitlab")
    private Gitlab gitlab;
    @JsonProperty("local source")
    private LocalSource localSource;

    @JsonIgnore
    private static final Logger logger = LogManager.getLogger(InspectorConfiguration.class);

    private InspectorConfiguration(){}

    public static InspectorConfiguration initialize(String config) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(config);

        InspectorConfiguration instance = mapper.readValue(file, InspectorConfiguration.class);
        logger.info("Configuration loaded from " + config);

        return instance;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output){
        this.output = output;
    }

    public Gitlab getGitlab(){
        return gitlab;
    }

    public void setGitlab(Gitlab gitlab){
        this.gitlab = gitlab;
    }

    public LocalSource getLocalSource() {
        return localSource;
    }

    public void setLocalSource(LocalSource localSource) {
        this.localSource = localSource;
    }

    public static Logger getLogger() {
        return logger;
    }

    public boolean isGitLab() {
        return gitlab != null;
    }

    public boolean isLocalSource() {
        return localSource != null;
    }
}
