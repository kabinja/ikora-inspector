package org.ikora.inspector;

import org.ikora.inspector.run.Runner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IkoraInspectorApplication {
	public static void main(String[] args) {
		SpringApplication.run(Runner.class, args);
	}
}
