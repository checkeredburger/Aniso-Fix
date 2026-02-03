# Changelog

## Aniso Fix 0.0.5-BETA

### Changes

#### Dynamic Mipmap Scaling (VRAM Saver)
- Introduced a new feature to dynamically adjust mipmap levels to save VRAM when usage exceeds a defined panic threshold.
- New configuration options: `dynamicMipmapScaling` and `mipmapPanicLevel` have been added.
- ModMenu integration for the new mipmap scaling options is now available.
- When entering panic mode with dynamic mipmap scaling enabled, the game's mipmap level is automatically set to the configured `mipmapPanicLevel`, and resources are reloaded.
- Upon VRAM recovery, the original mipmap level is restored, and resources are reloaded.
- Panic notification messages have been updated to include information about mipmap reduction.

#### HUD Improvements
- **AF Level Display**: A HUD element has been added to indicate the Anisotropic Filtering status. Due to technical limitations in accessing the exact in-game AF level for Minecraft 1.21.11, it will show "1x (PANIC)" when VRAM panic mode is active and "Not Available" otherwise.
- **Mipmap Level Display**: A new HUD element has been added to show the current Mipmap level.
- **Configurable Visibility**: New options `showAFLevel` and `showMipmapLevel` have been added to the config and ModMenu to toggle the visibility of these HUD elements.