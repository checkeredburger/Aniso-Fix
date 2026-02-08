package com.rebrandloser.anisofix.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL;

public class RealTimeTextureManager {
    private static boolean anisotropicFilteringSupported = false;
    private static float maxAnisotropy = 1.0f;
    private static float currentAnisotropy = 1.0f;
    private static int currentMipmapLevel = 4;
    private static boolean inPanicMode = false;
    
    static {
        initialize();
    }
    
    private static void initialize() {
        anisotropicFilteringSupported = GL.getCapabilities().GL_EXT_texture_filter_anisotropic;
        if (anisotropicFilteringSupported) {
            maxAnisotropy = GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT);
            currentAnisotropy = maxAnisotropy;
        }
    }
    
    public static void setAnisotropicFilteringLevel(float level) {
        if (!anisotropicFilteringSupported) {
            return;
        }

        currentAnisotropy = Math.max(1.0f, Math.min(level, maxAnisotropy));

        int currentBinding = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
        if (currentBinding != 0) {
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D,
                               EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT,
                               currentAnisotropy);
        }
    }
    
    public static void applyAnisotropicFilteringToTexture(int textureId) {
        if (!anisotropicFilteringSupported) {
            return;
        }
        
        int currentBinding = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, 
                           EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, 
                           currentAnisotropy);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, currentBinding);
    }
    
    public static float getMaxAnisotropy() {
        return maxAnisotropy;
    }
    
    public static boolean isAnisotropicFilteringSupported() {
        return anisotropicFilteringSupported;
    }
    
    public static float getCurrentAnisotropy() {
        return currentAnisotropy;
    }
    
    public static void setCurrentMipmapLevel(int level) {
        currentMipmapLevel = level;
    }
    
    public static int getCurrentMipmapLevel() {
        return currentMipmapLevel;
    }
    
    public static void setInPanicMode(boolean panicMode) {
        inPanicMode = panicMode;
    }
    
    public static boolean isInPanicMode() {
        return inPanicMode;
    }
    
    public static void applyCurrentSettingsToTexture(int textureId) {
        if (isAnisotropicFilteringSupported()) {
            applyAnisotropicFilteringToTexture(textureId);
        }
    }

    public static void updateCurrentTextureBinding() {
        if (!anisotropicFilteringSupported) {
            return;
        }

        int currentBinding = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
        if (currentBinding != 0) {
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D,
                               EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT,
                               currentAnisotropy);
        }
    }
}