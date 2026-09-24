package com.skd.almanaccore.data;

import com.skd.almanaccore.codec.RelicDefinition;
import com.skd.almanaccore.codec.ResearchNodeDefinition;
import com.skd.almanaccore.codec.RitualDefinition;
import com.skd.almanaccore.codec.SpellDefinition;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public final class AlmanacContentRegistry {

    private static final AlmanacContentRegistry INSTANCE = new AlmanacContentRegistry();

    private final Map<ResourceLocation, SpellDefinition> spells = new LinkedHashMap<>();
    private final Map<ResourceLocation, RitualDefinition> rituals = new LinkedHashMap<>();
    private final Map<ResourceLocation, ResearchNodeDefinition> researchNodes = new LinkedHashMap<>();
    private final Map<ResourceLocation, RelicDefinition> relics = new LinkedHashMap<>();

    private AlmanacContentRegistry() {
    }

    public static AlmanacContentRegistry getInstance() {
        return INSTANCE;
    }

    public void registerSpell(ResourceLocation id, SpellDefinition spell) {
        spells.put(id, spell);
    }

    public Optional<SpellDefinition> getSpell(ResourceLocation id) {
        return Optional.ofNullable(spells.get(id));
    }

    public Collection<SpellDefinition> getAllSpells() {
        return Collections.unmodifiableCollection(spells.values());
    }

    public Map<ResourceLocation, SpellDefinition> getSpells() {
        return Collections.unmodifiableMap(spells);
    }

    public int getSpellCount() {
        return spells.size();
    }

    public void registerRitual(ResourceLocation id, RitualDefinition ritual) {
        rituals.put(id, ritual);
    }

    public Optional<RitualDefinition> getRitual(ResourceLocation id) {
        return Optional.ofNullable(rituals.get(id));
    }

    public Collection<RitualDefinition> getAllRituals() {
        return Collections.unmodifiableCollection(rituals.values());
    }

    public Map<ResourceLocation, RitualDefinition> getRituals() {
        return Collections.unmodifiableMap(rituals);
    }

    public int getRitualCount() {
        return rituals.size();
    }

    public void registerResearchNode(ResourceLocation id, ResearchNodeDefinition node) {
        researchNodes.put(id, node);
    }

    public Optional<ResearchNodeDefinition> getResearchNode(ResourceLocation id) {
        return Optional.ofNullable(researchNodes.get(id));
    }

    public Collection<ResearchNodeDefinition> getAllResearchNodes() {
        return Collections.unmodifiableCollection(researchNodes.values());
    }

    public Map<ResourceLocation, ResearchNodeDefinition> getResearchNodes() {
        return Collections.unmodifiableMap(researchNodes);
    }

    public int getResearchNodeCount() {
        return researchNodes.size();
    }

    public void registerRelic(ResourceLocation id, RelicDefinition relic) {
        relics.put(id, relic);
    }

    public Optional<RelicDefinition> getRelic(ResourceLocation id) {
        return Optional.ofNullable(relics.get(id));
    }

    public Collection<RelicDefinition> getAllRelics() {
        return Collections.unmodifiableCollection(relics.values());
    }

    public Map<ResourceLocation, RelicDefinition> getRelics() {
        return Collections.unmodifiableMap(relics);
    }

    public int getRelicCount() {
        return relics.size();
    }

    public void clearSpells() {
        spells.clear();
    }

    public void clearRituals() {
        rituals.clear();
    }

    public void clearResearchNodes() {
        researchNodes.clear();
    }

    public void clearRelics() {
        relics.clear();
    }

    public void clear() {
        clearSpells();
        clearRituals();
        clearResearchNodes();
        clearRelics();
    }
}
