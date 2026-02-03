<p align="center">
  <img src="https://cdn.modrinth.com/data/cached_images/429cca803342db7999a453685266dabfb43ef99e.png" alt="Aniso Fix Logo" width="600">
</p>

---

**Automated VRAM management for modern Minecraft.**

Minecraft's updated texture engine can be a significant VRAM hog. Aniso Fix addresses this by monitoring your virtual memory in real-time. If your VRAM usage exceeds a user-defined limit, the mod instantly drops your Anisotropic Filtering (AF) to 1x to maintain stability.

---

### Features

* **Smart Scaling:** Automatically clamps AF when VRAM is critically full and restores it once memory levels return to a safe threshold.
* **Zero Interruptions:** Settings are applied by updating samplers directly. This ensures no resource reloads, stuttering, or loading screens during the transition.
* **VRAM HUD:** Includes a customizable in-game overlay to monitor your GPU usage and memory pressure in real-time.
* **Fully Configurable:** Fine-tune your "Panic" and "Recovery" percentages via Mod Menu to suit your hardware.

---

### Availability

[![Available for Fabric](https://raw.githubusercontent.com/intergrav/devins-badges/v3/assets/cozy/supported/fabric_64h.png)](https://modrinth.com/mod/aniso-fix/version/0.0.4)
[![View on GitHub](https://raw.githubusercontent.com/intergrav/devins-badges/v3/assets/cozy/available/github_64h.png)](https://github.com/checkeredburger/Aniso-Fix)
[![Available on Modrinth](https://raw.githubusercontent.com/intergrav/devins-badges/v3/assets/cozy/available/modrinth_64h.png)](https://modrinth.com/mod/aniso-fix)
---

### Dependencies

Aniso Fix requires the following mods to be installed:

[![Cloth Config](https://raw.githubusercontent.com/intergrav/devins-badges/v3/assets/cozy/requires/cloth-config-api_64h.png)](https://modrinth.com/mod/cloth-config) [![Fabric API](https://raw.githubusercontent.com/intergrav/devins-badges/v3/assets/cozy/requires/fabric-api_64h.png)](https://modrinth.com/mod/fabric-api) [![Mod Menu](https://raw.githubusercontent.com/intergrav/devins-badges/v3/assets/cozy/requires/mod-menu_64h.png)](https://modrinth.com/mod/modmenu)

---

<p align="center">
  <img src="https://img.shields.io/github/v/release/checkeredburger/Aniso-Fix?color=211f1f&label=Release&style=for-the-badge&logo=github" />
  <img src="https://img.shields.io/github/actions/workflow/status/checkeredburger/Aniso-Fix/build.yml?color=211f1f&label=Build&style=for-the-badge&logo=githubactions" />
  <img src="https://img.shields.io/modrinth/dt/aniso-fix?color=00af5c&label=Downloads&style=for-the-badge&logo=modrinth" />
  <img src="https://img.shields.io/modrinth/followers/aniso-fix?color=00af5c&label=Followers&style=for-the-badge&logo=modrinth" />
  <img src="https://img.shields.io/github/stars/checkeredburger/Aniso-Fix?color=fab005&label=Stars&style=for-the-badge&logo=github" />
</p>
