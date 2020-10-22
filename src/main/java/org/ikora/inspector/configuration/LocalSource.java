package org.ikora.inspector.configuration;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;

public class LocalSource {
    private Set<String> folders;

    public Set<File> getFolders() {
        return folders.stream()
                .map(File::new)
                .collect(Collectors.toSet());
    }

    public void setFolders(Set<String> folders) {
        this.folders = folders;
    }
}
