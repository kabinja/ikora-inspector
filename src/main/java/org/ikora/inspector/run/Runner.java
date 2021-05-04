package org.ikora.inspector.run;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ikora.inspector.IkoraInspectorApplication;
import org.ikora.inspector.configuration.DatabaseConfiguration;
import org.ikora.inspector.configuration.InspectorConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import lu.uni.serval.ikora.core.model.Projects;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@Component
public class Runner implements CommandLineRunner {
    private static final Logger logger = LogManager.getLogger(Runner.class);

    @Override
    public void run(String... args) throws Exception {
        logger.info("Start analysis...");
        Instant start = Instant.now();

        final InspectorConfiguration configuration = readConfiguration(args);
        final Projects projects = Builder.build(configuration);
        final Database database = createDatabase(configuration.getDatabase());

        database.store(projects);

        long end = Duration.between(start, Instant.now()).toMillis();
        logger.info(String.format("Analysis performed in %d ms", end));
    }

    private InspectorConfiguration readConfiguration(String... args) throws ParseException, IOException {
        Options options = new Options();

        options.addOption("config", true, "path to the json configuration file");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if(!cmd.hasOption("config")){
            throw new MissingArgumentException("config");
        }

        return InspectorConfiguration.initialize(cmd.getOptionValue("config"));
    }

    private Database createDatabase(DatabaseConfiguration configuration) throws Exception {
        if(configuration.getDriver().equalsIgnoreCase("sqlite")){
            final File output = new File(configuration.getLocation());
            DatabaseSettings.setUrl(output.getAbsolutePath());
        }
        else{
            throw new Exception(String.format(
                    "Database driver '%s' in configuration is not supported",
                    configuration.getDriver())
            );
        }

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(IkoraInspectorApplication.class);
        return applicationContext.getBean(Database.class);
    }
}
