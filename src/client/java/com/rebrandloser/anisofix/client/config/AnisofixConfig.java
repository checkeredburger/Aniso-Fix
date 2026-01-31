package com.rebrandloser.anisofix.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AnisofixConfig {
    private static final File CONFIG_FILE = new File("config/anisofix.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static AnisofixConfig instance;

    public int panicThreshold = 6000;
    public int recoveryThreshold = 4000;
    public boolean showHud = true;
    public boolean showGpuName = true;
    public boolean showGpuVendor = false;
    public boolean showCpuName = true;
    public boolean showVram = true;

    public static AnisofixConfig getInstance() {
        if (instance == null) {
            load();
        }
        return instance;
    }

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                instance = GSON.fromJson(reader, AnisofixConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (instance == null) {
            instance = new AnisofixConfig();
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(instance, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
