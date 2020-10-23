package org.ikora.inspector;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ikora.inspector.configuration.DatabaseConfiguration;
import org.ikora.inspector.configuration.InspectorConfiguration;
import org.ikora.inspector.configuration.LocalSource;
import org.ikora.inspector.utils.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextLoader;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.ikora.inspector.Helpers.getResourcesFile;

@ContextConfiguration(classes = IkoraInspectorApplication.class)
@ExtendWith(SpringExtension.class)
@BootstrapWith(IkoraInspectorApplicationTests.Bootstrapper.class)
class IkoraInspectorApplicationTests {
	static class Bootstrapper extends SpringBootTestContextBootstrapper {
		static class ArgumentSupplyingContextLoader extends SpringBootContextLoader {
			@Override
			protected SpringApplication getSpringApplication() {
				final String configuration = initializeConfigFile();

				return new SpringApplication() {
					@Override
					public ConfigurableApplicationContext run(String... args) {
						return super.run("-config", configuration);
					}
				};
			}

			private String initializeConfigFile(){
				String configuration = "";

				try{
					final Set<String> projectLocations = new HashSet<>();
					projectLocations.add(getResourcesFile("robot/project-suite/project-A").getAbsolutePath());
					projectLocations.add(getResourcesFile("robot/project-suite/project-B").getAbsolutePath());
					projectLocations.add(getResourcesFile("robot/project-suite/project-C").getAbsolutePath());

					final File workspace = new File(FileUtils.createTmpFolder("ikora-inspector"));
					workspace.mkdirs();
					workspace.deleteOnExit();

					final File databaseFile = new File(workspace, "test.sqlite");
					final File configurationFile = new File(workspace, "configuration.json");

					createConfigFile(configurationFile, databaseFile, projectLocations);

					return configurationFile.getAbsolutePath();
				}
				catch (Exception e){
					System.out.println("Failed to initialize configuration file");
				}

				return configuration;
			}

			private void createConfigFile(File configurationFile, File databaseFile, Set<String> projectFolders) throws IOException {
				final LocalSource localSource = new LocalSource();
				localSource.setFolders(projectFolders);

				DatabaseConfiguration database = new DatabaseConfiguration();
				database.setLocation(databaseFile.getAbsolutePath());

				InspectorConfiguration configuration = new InspectorConfiguration();
				configuration.setDatabase(database);
				configuration.setLocalSource(localSource);

				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(configurationFile, configuration);
			}
		}

		@Override
		protected Class<? extends ContextLoader> getDefaultContextLoaderClass(Class<?> testClass) {
			return ArgumentSupplyingContextLoader.class;
		}
	}

	@Test
	void contextLoads() {
	}
}
