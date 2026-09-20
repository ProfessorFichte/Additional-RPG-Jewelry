# 2.3.1+1.20.1

> ### ⚠️ Read this before updating
>
> This release is a **major technical overhaul and is not backwards compatible.**
>
> - **Requires the matching Spell Engine and More RPG Library releases.** This version will not run
>   on Spell Engine **0.9.x**, and mods built against 0.9.x will not work alongside it.
> - **Update the whole set together.** Spell Engine, More RPG Library and every RPG Series mod must
>   be on matching versions. Mixing in an older add-on will break at startup or misbehave in play.
>
> **Back up your world before updating.**

- Thanks to Daedelus for the PR!
- Ported to Minecraft 1.20.1 (Fabric + Forge 47). NeoForge is replaced by Forge on this line; the same
  Forge jar also loads on NeoForge 1.20.1.
- Requires the matching 1.20.1 releases of Jewelry (2.4.0), Spell Engine (1.10.5) and More RPG Library
  (2.7.2). Accessory slots come from Trinkets on Fabric and Curios on Forge.
- Every registry write goes through Forge's `RegisterEvent` window, so the mod also boots on Forge 47.0-47.3
  and on NeoForge 1.20.1, which never unlock the vanilla registries.

### Accepted 1.20.1 limitations

- Attribute bonuses ride Jewelry's own 1.20.1 attribute-modifier stand-in instead of the 1.21 item
  component. Bonuses work as before in a Trinkets or Curios accessory slot; without either mod installed
  the vanilla fallback only applies them while the piece is held in the main hand. That is Jewelry's own
  1.20.1 limitation, inherited here.
- On Forge the accessory integration targets Curios 5.14.1 (the 1.20.1 line) rather than the 9.x builds
  used on 1.21.
- The built-in `jewelry_changes` data pack - which adds aquamarine and malachite to Jewelry's gem-vein
  drops - is assembled through Forge's own pack-finder API; it stays always-enabled on both loaders.
