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
                    .setTitle(Text.translatable("anisofix.config.title"));

            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            ConfigCategory performance = builder.getOrCreateCategory(Text.translatable("anisofix.category.performance"));
            ConfigCategory hud = builder.getOrCreateCategory(Text.translatable("anisofix.category.hud"));

            performance.addEntry(entryBuilder.startIntSlider(Text.translatable("anisofix.config.panic_threshold"), config.panicThreshold, 1000, 24000)
                    .setDefaultValue(6000)
                    .setTooltip(Text.translatable("anisofix.config.panic_threshold.tooltip"))
                    .setSaveConsumer(newValue -> config.panicThreshold = newValue)
                    .build());

            performance.addEntry(entryBuilder.startIntSlider(Text.translatable("anisofix.config.recovery_threshold"), config.recoveryThreshold, 1000, 24000)
                    .setDefaultValue(4000)
                    .setTooltip(Text.translatable("anisofix.config.recovery_threshold.tooltip"))
                    .setSaveConsumer(newValue -> config.recoveryThreshold = newValue)
                    .build());

            performance.addEntry(entryBuilder.startBooleanToggle(Text.translatable("anisofix.config.enable_notifications"), config.enableNotifications)
                    .setDefaultValue(true)
                    .setTooltip(Text.translatable("anisofix.config.enable_notifications.tooltip"))
                    .setSaveConsumer(newValue -> config.enableNotifications = newValue)
                    .build());

            performance.addEntry(entryBuilder.startBooleanToggle(Text.translatable("anisofix.config.dynamic_mipmap_scaling"), config.dynamicMipmapScaling)
                    .setDefaultValue(false)
                    .setTooltip(Text.translatable("anisofix.config.dynamic_mipmap_scaling.tooltip"))
                    .setSaveConsumer(newValue -> config.dynamicMipmapScaling = newValue)
                    .build());

            performance.addEntry(entryBuilder.startIntSlider(Text.translatable("anisofix.config.mipmap_panic_level"), config.mipmapPanicLevel, 0, 4)
                    .setDefaultValue(0)
                    .setTooltip(Text.translatable("anisofix.config.mipmap_panic_level.tooltip"))
                    .setSaveConsumer(newValue -> config.mipmapPanicLevel = newValue)
                    .build());

            hud.addEntry(entryBuilder.startBooleanToggle(Text.translatable("anisofix.config.show_hud"), config.showHud)
                    .setDefaultValue(true)
                    .setTooltip(Text.translatable("anisofix.config.show_hud.tooltip"))
                    .setSaveConsumer(newValue -> config.showHud = newValue)
                    .build());

            hud.addEntry(entryBuilder.startBooleanToggle(Text.translatable("anisofix.config.show_gpu_name"), config.showGpuName)
                    .setDefaultValue(true)
                    .setTooltip(Text.translatable("anisofix.config.show_gpu_name.tooltip"))
                    .setSaveConsumer(newValue -> config.showGpuName = newValue)
                    .build());

            hud.addEntry(entryBuilder.startBooleanToggle(Text.translatable("anisofix.config.show_gpu_vendor"), config.showGpuVendor)
                    .setDefaultValue(false)
                    .setTooltip(Text.translatable("anisofix.config.show_gpu_vendor.tooltip"))
                    .setSaveConsumer(newValue -> config.showGpuVendor = newValue)
                    .build());

            hud.addEntry(entryBuilder.startBooleanToggle(Text.translatable("anisofix.config.show_cpu_name"), config.showCpuName)
                    .setDefaultValue(true)
                    .setTooltip(Text.translatable("anisofix.config.show_cpu_name.tooltip"))
                    .setSaveConsumer(newValue -> config.showCpuName = newValue)
                    .build());

            hud.addEntry(entryBuilder.startBooleanToggle(Text.translatable("anisofix.config.show_vram"), config.showVram)
                    .setDefaultValue(true)
                    .setTooltip(Text.translatable("anisofix.config.show_vram.tooltip"))
                    .setSaveConsumer(newValue -> config.showVram = newValue)
                    .build());

            hud.addEntry(entryBuilder.startBooleanToggle(Text.translatable("anisofix.config.show_af_level"), config.showAFLevel)
                    .setDefaultValue(true)
                    .setTooltip(Text.translatable("anisofix.config.show_af_level.tooltip"))
                    .setSaveConsumer(newValue -> config.showAFLevel = newValue)
                    .build());

            hud.addEntry(entryBuilder.startBooleanToggle(Text.translatable("anisofix.config.show_mipmap_level"), config.showMipmapLevel)
                    .setDefaultValue(true)
                    .setTooltip(Text.translatable("anisofix.config.show_mipmap_level.tooltip"))
                    .setSaveConsumer(newValue -> config.showMipmapLevel = newValue)
                    .build());

            hud.addEntry(entryBuilder.startEnumSelector(Text.translatable("anisofix.config.anchor_point"), AnisofixConfig.HudAnchor.class, config.hudAnchor)
                    .setDefaultValue(AnisofixConfig.HudAnchor.TOP_RIGHT)
                    .setTooltip(Text.translatable("anisofix.config.anchor_point.tooltip"))
                    .setSaveConsumer(newValue -> config.hudAnchor = newValue)
                    .build());

            hud.addEntry(entryBuilder.startIntField(Text.translatable("anisofix.config.x_offset"), config.hudX)
                    .setDefaultValue(2)
                    .setTooltip(Text.translatable("anisofix.config.x_offset.tooltip"))
                    .setSaveConsumer(newValue -> config.hudX = newValue)
                    .build());

            hud.addEntry(entryBuilder.startIntField(Text.translatable("anisofix.config.y_offset"), config.hudY)
                    .setDefaultValue(2)
                    .setTooltip(Text.translatable("anisofix.config.y_offset.tooltip"))
                    .setSaveConsumer(newValue -> config.hudY = newValue)
                    .build());

            builder.setSavingRunnable(AnisofixConfig::save);
            return builder.build();
        };
    }
}
