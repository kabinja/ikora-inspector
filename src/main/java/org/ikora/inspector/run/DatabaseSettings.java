package org.ikora.inspector.run;

public class DatabaseSettings {
    static String url;

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        DatabaseSettings.url = url;
    }
}
