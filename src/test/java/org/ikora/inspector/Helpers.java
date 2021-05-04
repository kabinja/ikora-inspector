package org.ikora.inspector;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class Helpers {
    public static File getResourcesFile(String name) throws IOException, URISyntaxException {
        URL resource = lu.uni.serval.ikora.core.utils.FileUtils.class.getClassLoader().getResource(name);
        if (resource == null) {
            throw new IOException("Failed to locate resource template for project analytics");
        }

        return Paths.get(resource.toURI()).toFile();
    }
}
