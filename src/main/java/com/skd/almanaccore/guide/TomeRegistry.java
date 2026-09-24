package com.skd.almanaccore.guide;

import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class TomeRegistry {

    private static final TomeRegistry INSTANCE = new TomeRegistry();

    private final Map<ResourceLocation, TomeDefinition> tomes = new LinkedHashMap<>();

    private TomeRegistry() {
    }

    public static TomeRegistry getInstance() {
        return INSTANCE;
    }

    public void register(TomeDefinition tome) {
        tomes.put(tome.id(), tome);
    }

    public Optional<TomeDefinition> get(ResourceLocation id) {
        return Optional.ofNullable(tomes.get(id));
    }

    public Collection<TomeDefinition> getAll() {
        return Collections.unmodifiableCollection(tomes.values());
    }

    public Map<ResourceLocation, TomeDefinition> getMap() {
        return Collections.unmodifiableMap(tomes);
    }

    public void clear() {
        tomes.clear();
    }

    public record TomeDefinition(ResourceLocation id, List<ResourceLocation> unlockedEntries) {
    }
}
