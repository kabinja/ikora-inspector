package org.ikora.inspector.utils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

public class FileUtils {
    public static String createTmpFolder(String location) throws IOException {
        File folder = location != null ? new File(location) : null;

        if(location == null || location.isEmpty() || !folder.isDirectory()){
            File tmp = org.apache.commons.io.FileUtils.getTempDirectory();
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            String folderName = "project-analytics-" + ts.toInstant().toEpochMilli();
            folder = new File(tmp, folderName);

            location = folder.getAbsolutePath();
        }

        if(!folder.exists()) {
            if(!folder.mkdir()){
                throw new IOException("Failed to create directory " + location);
            }
        }

        folder.deleteOnExit();

        return location;
    }
}
