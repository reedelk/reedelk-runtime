package com.reedelk.runtime.properties;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.reedelk.runtime.commons.RuntimeMessage.message;
import static org.osgi.framework.Constants.*;

public class RuntimeConfiguration extends Properties {

    public static final String RUNTIME_CONFIG_PROPERTIES_FILE = "com.reedelk.runtime.properties";

    private RuntimeConfiguration() {
        load();
    }

    public static Map<String, String> get() {
        RuntimeConfiguration runtimeConfiguration = new RuntimeConfiguration();

        Map<String, String> configuration = new HashMap<>();
        configuration.put("org.osgi.framework.storage", RuntimeConfiguration.cacheDir());
        configuration.put("org.ops4j.pax.logging.DefaultServiceLog.level", runtimeConfiguration.getPaxDefaultLogLevel());
        configuration.put(FRAMEWORK_SYSTEMPACKAGES_EXTRA, runtimeConfiguration.getSharedSystemPackages());
        configuration.put(FRAMEWORK_STORAGE_CLEAN, FRAMEWORK_STORAGE_CLEAN_ONFIRSTINIT);

        // NAME_CONVENTION
        configuration.put("com.reedelk.system.api.configuration.config.directory", SystemConfiguration.configDirectory());
        // NAME_CONVENTION
        configuration.put("com.reedelk.system.api.configuration.home.directory", SystemConfiguration.homeDirectory());
        // NAME_CONVENTION
        configuration.put("com.reedelk.system.api.configuration.modules.directory", SystemConfiguration.modulesDirectory());
        // NAME_CONVENTION
        configuration.put("com.reedelk.system.api.configuration.runtime.version", Version.getVersion());

        return configuration;
    }

    private String getSharedSystemPackages() {
        return getProperty("shared.system.packages", "");
    }

    private String getPaxDefaultLogLevel() {
        return getProperty("pax.default.log.level", "WARN");
    }

    public static String cacheDir() {
        return Paths.get(SystemConfiguration.homeDirectory(), "cache").toString();
    }

    private void load() {
        String configDirectory = SystemConfiguration.configDirectory();
        Path path = Paths.get(configDirectory, RUNTIME_CONFIG_PROPERTIES_FILE);
        File runtimeConfig = path.toFile();
        try {
            super.load(new FileInputStream(runtimeConfig));
        } catch (IOException exception) {
            String errorMessage = message("runtime.start.error.config.properties.not.found", path.toString());
            throw new IllegalStateException(errorMessage, exception);
        }
    }
}
