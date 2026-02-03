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

            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            ConfigCategory general = builder.getOrCreateCategory(Text.of("General"));
            
            general.addEntry(entryBuilder.startIntSlider(Text.of("Panic Threshold (MB)"), config.panicThreshold, 1000, 24000)
                    .setDefaultValue(6000)
                    .setTooltip(Text.of("Free VRAM amount (in MB) at which Panic Mode activates."))
                    .setSaveConsumer(newValue -> config.panicThreshold = newValue)
                    .build());

            general.addEntry(entryBuilder.startIntSlider(Text.of("Recovery Threshold (MB)"), config.recoveryThreshold, 1000, 24000)
                    .setDefaultValue(4000)
                    .setTooltip(Text.of("Free VRAM amount (in MB) at which Panic Mode deactivates."))
                    .setSaveConsumer(newValue -> config.recoveryThreshold = newValue)
                    .build());

            general.addEntry(entryBuilder.startBooleanToggle(Text.of("Enable Notifications"), config.enableNotifications)
                    .setDefaultValue(true)
                    .setTooltip(Text.of("Show a chat message when entering/exiting Panic Mode."))
                    .setSaveConsumer(newValue -> config.enableNotifications = newValue)
                    .build());

            ConfigCategory hud = builder.getOrCreateCategory(Text.of("HUD"));
            
            hud.addEntry(entryBuilder.startBooleanToggle(Text.of("Enable HUD"), config.showHud)
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

            hud.addEntry(entryBuilder.startEnumSelector(Text.of("Anchor Point"), AnisofixConfig.HudAnchor.class, config.hudAnchor)
                    .setDefaultValue(AnisofixConfig.HudAnchor.TOP_RIGHT)
                    .setSaveConsumer(newValue -> config.hudAnchor = newValue)
                    .build());

            hud.addEntry(entryBuilder.startIntField(Text.of("X Offset"), config.hudX)
                    .setDefaultValue(2)
                    .setSaveConsumer(newValue -> config.hudX = newValue)
                    .build());

            hud.addEntry(entryBuilder.startIntField(Text.of("Y Offset"), config.hudY)
                    .setDefaultValue(2)
                    .setSaveConsumer(newValue -> config.hudY = newValue)
                    .build());

            builder.setSavingRunnable(AnisofixConfig::save);
            return builder.build();
        };
    }
}
