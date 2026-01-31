package com.rebrandloser.anisofix.client.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            AnisofixConfig config = AnisofixConfig.getInstance();
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.of("AnisoFix Config"));

            ConfigCategory general = builder.getOrCreateCategory(Text.of("General"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            general.addEntry(entryBuilder.startIntSlider(Text.of("Panic Threshold (MB)"), config.panicThreshold, 1000, 24000)
                    .setDefaultValue(6000)
                    .setSaveConsumer(newValue -> config.panicThreshold = newValue)
                    .build());

            general.addEntry(entryBuilder.startIntSlider(Text.of("Recovery Threshold (MB)"), config.recoveryThreshold, 1000, 24000)
                    .setDefaultValue(4000)
                    .setSaveConsumer(newValue -> config.recoveryThreshold = newValue)
                    .build());
            
            ConfigCategory hud = builder.getOrCreateCategory(Text.of("Debug HUD"));
            
            hud.addEntry(entryBuilder.startBooleanToggle(Text.of("Show HUD"), config.showHud)
                    .setDefaultValue(true)
                    .setSaveConsumer(newValue -> config.showHud = newValue)
                    .build());

            hud.addEntry(entryBuilder.startBooleanToggle(Text.of("Show GPU Name"), config.showGpuName)
                    .setDefaultValue(true)
                    .setSaveConsumer(newValue -> config.showGpuName = newValue)
                    .build());

            hud.addEntry(entryBuilder.startBooleanToggle(Text.of("Show GPU Vendor"), config.showGpuVendor)
                    .setDefaultValue(false)
                    .setSaveConsumer(newValue -> config.showGpuVendor = newValue)
                    .build());

            hud.addEntry(entryBuilder.startBooleanToggle(Text.of("Show CPU Name"), config.showCpuName)
                    .setDefaultValue(true)
                    .setSaveConsumer(newValue -> config.showCpuName = newValue)
                    .build());

            hud.addEntry(entryBuilder.startBooleanToggle(Text.of("Show VRAM Usage"), config.showVram)
                    .setDefaultValue(true)
                    .setSaveConsumer(newValue -> config.showVram = newValue)
                    .build());

            builder.setSavingRunnable(AnisofixConfig::save);
            return builder.build();
        };
    }
}
