# Almanac Core

The **content-as-data library** of the [Majestic](https://gitlab.com/stalking-dragons/minecraft/majestic)
mod ecosystem for Minecraft NeoForge. Almanac Core lets magic content be defined as **datapack
data** instead of code. It ships **no concrete content** of its own.

## What it provides

- **Codecs** — `SpellDefinition`, `RitualDefinition`, `ResearchNodeDefinition`, `RelicDefinition`:
  full Mojang `Codec`s for datapack-driven content.
- **Loaders & registry** — one reload listener per kind parses JSON from
  `data/<namespace>/almanac/{spell,ritual,research_node,relic}` into a shared in-memory registry
  that survives partial reloads.
- **Client sync** — the loaded content set is pushed to clients on login and on every datapack
  reload.
- **Debug command** — `/almanaccore status` (counts and ids per kind).
- **Vellumli guide bridge** (`guide/`) — a **data-only** utility set for other mods (e.g. Majestic):
  `EntryGenerator` turns loaded definitions into Patchouli/Vellumli-format entry JSON,
  `GuideStructure` maps content kinds to guide categories, `EntryGate` derives advancement ids for
  entry gating, `TomeRegistry` tracks advancement-tome unlocks, and `VellumliBridge` wraps
  Vellumli's public `VellumliAPI` to give/open books. No Vellumli code is copied or derived — only
  its published API is called, and it stays a soft (optional) dependency.

It adds **no content, blocks or items** of its own. Astral Core is declared as a required
dependency; wiring the loaded definitions against Astral Core's `*Type`s is still pending.

## Planned (not implemented yet)

- **Ritual recipe type** — a `RecipeType` wrapper for rituals (JEI / querying).
- **JEI plugin** — ritual and research categories (soft; only with JEI present).
- **Effect package / config** — hybrid declarative effects and strict-validation config.

See `docs/DESIGN_ALMANAC_CORE_1-21-1.md` for the design.

## Requirements

- Minecraft 1.21.1
- NeoForge 21.1.249
- Java 21

## Dependencies

- [Astral Core](https://gitlab.com/stalking-dragons/minecraft/astral-core) (MIT) — **required**.
- JEI (`mezz.jei:jei-1.21.1-neoforge`) — optional.
- [Vellumli](https://gitlab.com/stalking-dragons/minecraft/vellumli) (CC BY-NC-SA 3.0) — optional,
  consumed as a data-only integration; never bundled, never derived.

Dependencies are **external**: installed as separate jars, never bundled.

## Building from Source

```bash
./gradlew build
```

The built JAR will be in `build/libs/`.

## License

MIT — see [LICENSE](LICENSE).
