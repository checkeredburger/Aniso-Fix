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

            general.addEntry(entryBuilder.startBooleanToggle(Text.of("Dynamic Mipmap Scaling"), config.dynamicMipmapScaling)
                    .setDefaultValue(false)
                    .setTooltip(Text.of("When in Panic Mode, also reduce mipmap levels to save VRAM. (EXPERIMENTAL: May cause a reload)"))
                    .setSaveConsumer(newValue -> config.dynamicMipmapScaling = newValue)
                    .build());

            general.addEntry(entryBuilder.startIntSlider(Text.of("Mipmap Panic Level"), config.mipmapPanicLevel, 0, 4)
                    .setDefaultValue(0)
                    .setTooltip(Text.of("The mipmap level to use during Panic Mode (0 = disabled)."))
                    .setSaveConsumer(newValue -> config.mipmapPanicLevel = newValue)
                    .build());

            ConfigCategory hud = builder.getOrCreateCategory(Text.of("HUD"));
            
            hud.addEntry(entryBuilder.startBooleanToggle(Text.of("Enable HUD"), config.showHud)
                    .setDefaultValue(true)
                    .setTooltip(Text.of("Toggle the visibility of the Hardware HUD."))
                    .setSaveConsumer(newValue -> config.showHud = newValue)
                    .build());

            hud.addEntry(entryBuilder.startBooleanToggle(Text.of("Show GPU Name"), config.showGpuName)
                    .setDefaultValue(true)
                    .setTooltip(Text.of("Display the name of your GPU in the HUD."))
                    .setSaveConsumer(newValue -> config.showGpuName = newValue)
                    .build());

            hud.addEntry(entryBuilder.startBooleanToggle(Text.of("Show GPU Vendor"), config.showGpuVendor)
                    .setDefaultValue(false)
                    .setTooltip(Text.of("Display the vendor of your GPU in the HUD (e.g., NVIDIA, AMD)."))
                    .setSaveConsumer(newValue -> config.showGpuVendor = newValue)
                    .build());

            hud.addEntry(entryBuilder.startBooleanToggle(Text.of("Show CPU Name"), config.showCpuName)
                    .setDefaultValue(true)
                    .setTooltip(Text.of("Display the name of your CPU in the HUD."))
                    .setSaveConsumer(newValue -> config.showCpuName = newValue)
                    .build());

            hud.addEntry(entryBuilder.startBooleanToggle(Text.of("Show VRAM Usage"), config.showVram)
                    .setDefaultValue(true)
                    .setTooltip(Text.of("Display current VRAM usage and total VRAM in the HUD."))
                    .setSaveConsumer(newValue -> config.showVram = newValue)
                    .build());
            
            hud.addEntry(entryBuilder.startBooleanToggle(Text.of("Show AF Level"), config.showAFLevel)
                    .setDefaultValue(true)
                    .setTooltip(Text.of("Displays the Anisotropic Filtering status in the HUD. Note: May show 'Not Available' due to game limitations."))
                    .setSaveConsumer(newValue -> config.showAFLevel = newValue)
                    .build());

            hud.addEntry(entryBuilder.startBooleanToggle(Text.of("Show Mipmap Level"), config.showMipmapLevel)
                    .setDefaultValue(true)
                    .setTooltip(Text.of("Displays the current Mipmap level in the HUD."))
                    .setSaveConsumer(newValue -> config.showMipmapLevel = newValue)
                    .build());

            hud.addEntry(entryBuilder.startEnumSelector(Text.of("Anchor Point"), AnisofixConfig.HudAnchor.class, config.hudAnchor)
                    .setDefaultValue(AnisofixConfig.HudAnchor.TOP_RIGHT)
                    .setTooltip(Text.of("Sets the corner of the screen where the HUD will be displayed."))
                    .setSaveConsumer(newValue -> config.hudAnchor = newValue)
                    .build());

            hud.addEntry(entryBuilder.startIntField(Text.of("X Offset"), config.hudX)
                    .setDefaultValue(2)
                    .setTooltip(Text.of("Adjusts the horizontal position of the HUD from the anchor point."))
                    .setSaveConsumer(newValue -> config.hudX = newValue)
                    .build());

            hud.addEntry(entryBuilder.startIntField(Text.of("Y Offset"), config.hudY)
                    .setDefaultValue(2)
                    .setTooltip(Text.of("Adjusts the vertical position of the HUD from the anchor point."))
                    .setSaveConsumer(newValue -> config.hudY = newValue)
                    .build());

            builder.setSavingRunnable(AnisofixConfig::save);
            return builder.build();
        };
    }
}
