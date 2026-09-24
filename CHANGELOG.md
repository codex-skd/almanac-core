# Changelog

All notable changes to this project will be documented in this file.

## [0.0.0-beta.3]

### Fixed
- CurseForge: `astral-core` is now declared as a required dependency and `vellumli` as an optional
  one, so the CurseForge app/launcher installs Astral Core (and Regalia Slots API through it)
  automatically. beta.1-beta.2 were uploaded without any declared dependency. No code changes.

## [0.0.0-beta.2]

### Added
- **Vellumli guide bridge** (`guide/`): `EntryGenerator` (turns loaded `*Definition`s into
  Patchouli/Vellumli-format entry JSON), `GuideStructure` (content kind → guide category),
  `EntryGate` (deterministic advancement ids for progressive entry visibility), `TomeRegistry`
  (advancement-tome → unlocked-entries bookkeeping), `VellumliBridge` (the only class calling
  [Vellumli](https://gitlab.com/stalking-dragons/minecraft/vellumli)'s public `VellumliAPI` to
  give/open books).
- Wired `vellumli` as a soft, `compileOnly`-only dependency (never `localRuntime`): default dev
  runs boot with vellumli absent from the runtime classpath on purpose, matching how `majestic`
  will ship it as an optional dependency.

### Notes
- Data-only integration: no Vellumli code is copied or derived (CC BY-NC-SA 3.0 ShareAlike) — only
  its published API is called. Without Vellumli installed, `guide/` simply isn't exercised; the
  rest of Almanac Core is unaffected.
- Nothing is wired into `AlmanacCore`'s bootstrap — these are utilities meant to be invoked by a
  consumer mod (starting with `majestic`).
- Delegated to OpenCode and verified correct on the first attempt (no runtime bugs found). Verified
  with a clean `./gradlew build` and a `runGameTestServer` boot with vellumli absent from the
  runtime classpath, reaching the same benign failure point as every prior milestone.

## [0.0.0-beta.1]

First versioned build. **Minecraft 1.21.1 / NeoForge 21.1.249** (Java 21).

### Added
- Initial project setup: build (`net.neoforged.moddev` 2.0.142), Parchment `2024.11.17`, GitLab CI
  mirror pipeline, `maven-publish` for downstream consumption.
- **Codecs** (`codec/`): `SpellDefinition` (school, tier, cost, cast type, cooldown, unlock,
  effects, client hints), `RitualDefinition` (altar tier, item-or-tag inputs, essence cost,
  duration, risk, outputs), `ResearchNodeDefinition` (constellation, requirements, unlocks, tree
  position), `RelicDefinition` (slot, passives, optional active/set) — all full Mojang `Codec`s.
- **Loaders** (`data/`): `SpellLoader`/`RitualLoader`/`ResearchNodeLoader`/`RelicLoader`
  (`SimpleJsonResourceReloadListener` over `data/<ns>/almanac/{spell,ritual,research_node,relic}`),
  feeding a shared in-memory `AlmanacContentRegistry` (per-kind register/get/getAll/clear).
- **Networking**: `AlmanacSyncPayload` carries all four kinds server→client on datapack
  reload/login (`OnDatapackSyncEvent`), via `AlmanacNetworking`.
- Debug command `/almanaccore status` (counts + ids per kind).
- **Astral Core** wired as a real dependency (external jar, never bundled).

### Notes
- Almanac Core is a library: content-as-data only, no concrete entries ship here — that's
  `majestic`'s job (or third-party datapacks). See `docs/DESIGN_ALMANAC_CORE_1-21-1.md`.
- JEI stays a stub (`jei/` package, no real dependency yet); the Vellumli guide bridge (`guide/`)
  doesn't exist yet — both are future milestones.
- Implemented across two milestones (M1 spell codec/loader, M2 ritual/research_node/relic
  codec/loader), delegated to OpenCode and independently verified by Claude. Two real bugs were
  found and fixed: a shared `clear()` that wiped all four registries on any single reload (now
  per-kind), and an empty `encode()` on the ritual input's union `Codec` that would have corrupted
  server→client sync as soon as real ritual data existed — see
  `docs/DESIGN_ALMANAC_CORE_1-21-1.md §7` (Historial).
- Verified with a real `runServer` boot (not just `runGameTestServer`, which never reaches
  datapack reload) using a temporary test ritual JSON — confirmed loading and a clean "Done".
