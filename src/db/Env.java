package db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class Env {
    private File envFile;
    private Properties values;

    public Env(String fileName) {
        this.envFile = new File(fileName);

        if (!this.envFile.exists()) {
            this.envFile.mkdirs();
        }

        this.values = new Properties();
        try (FileInputStream in = new FileInputStream(this.envFile)) {
            this.values.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String get(String key) {
        return this.values.getProperty(key);
    }
}
