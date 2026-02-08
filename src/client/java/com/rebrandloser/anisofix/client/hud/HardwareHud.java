package com.rebrandloser.anisofix.client.hud;

import com.rebrandloser.anisofix.client.AnisofixClient;
import com.rebrandloser.anisofix.client.config.AnisofixConfig;
import com.rebrandloser.anisofix.client.monitor.HardwareMonitor;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import com.rebrandloser.anisofix.client.GameOptionsAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

import java.util.ArrayList;
import java.util.List;

public class HardwareHud implements HudRenderCallback {
    @Override
    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        AnisofixConfig config = AnisofixConfig.getInstance();
        if (!config.showHud) return;

        MinecraftClient client = MinecraftClient.getInstance();
        List<String> lines = new ArrayList<>();

        if (config.showGpuName) {
            lines.add("GPU: " + HardwareMonitor.getGpuName());
        }
        if (config.showGpuVendor) {
            lines.add("Vendor: " + HardwareMonitor.getGpuVendor());
        }
        if (config.showCpuName) {
            lines.add("CPU: " + HardwareMonitor.getCpuName());
        }
        if (config.showVram) {
            long total = HardwareMonitor.getTotalVideoMemory();
            long free = HardwareMonitor.getFreeVideoMemory();
            if (total > 0) {
                long used = total - free;
                lines.add(String.format("VRAM: %d / %d MB %s", used / 1024 / 1024, total / 1024 / 1024, AnisofixClient.panicMode ? "(PANIC)" : ""));
            }
        }
        
        if (config.showAFLevel) {
            float currentAF = com.rebrandloser.anisofix.client.RealTimeTextureManager.getCurrentAnisotropy();
            String afStatus = AnisofixClient.panicMode ? "1x (PANIC)" : String.format("%.0fx", currentAF);
            lines.add(String.format("AF: %s", afStatus));
        }

        if (config.showMipmapLevel) {
            int mipmapLevel = client.options.getMipmapLevels().getValue();
            lines.add(String.format("Mipmap: %d", mipmapLevel));
        }

        if (lines.isEmpty()) return;

        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();
        int color = AnisofixClient.panicMode ? 0xFFFF5555 : 0xFFFFFFFF;
        
        int startY;
        int lineHeight = 10;
        int totalHeight = lines.size() * lineHeight;

        if (config.hudAnchor == AnisofixConfig.HudAnchor.TOP_LEFT || config.hudAnchor == AnisofixConfig.HudAnchor.TOP_RIGHT) {
            startY = config.hudY;
        } else {
            startY = height - totalHeight - config.hudY;
        }

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int textWidth = client.textRenderer.getWidth(line);
            int x;

            if (config.hudAnchor == AnisofixConfig.HudAnchor.TOP_LEFT || config.hudAnchor == AnisofixConfig.HudAnchor.BOTTOM_LEFT) {
                x = config.hudX;
            } else {
                x = width - textWidth - config.hudX;
            }

            context.drawTextWithShadow(client.textRenderer, line, x, startY + (i * lineHeight), color);
        }
    }
}