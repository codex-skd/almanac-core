package com.skd.almanaccore.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import com.skd.almanaccore.AlmanacCore;
import com.skd.almanaccore.codec.RitualDefinition;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;

import java.util.Map;

public final class RitualLoader extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new Gson();
    private static final String DIRECTORY = "almanac/ritual";

    public RitualLoader() {
        super(GSON, DIRECTORY);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> splatMap, ResourceManager resourceManager, ProfilerFiller profiler) {
        AlmanacContentRegistry registry = AlmanacContentRegistry.getInstance();
        registry.clearRituals();

        for (Map.Entry<ResourceLocation, JsonElement> entry : splatMap.entrySet()) {
            ResourceLocation id = entry.getKey();
            JsonElement json = entry.getValue();

            if (!(json instanceof JsonObject obj)) {
                AlmanacCore.LOGGER.warn("Skipping ritual {} — root is not a JSON object", id);
                continue;
            }

            DataResult<RitualDefinition> result = RitualDefinition.CODEC.parse(JsonOps.INSTANCE, obj);
            result.resultOrPartial(error -> AlmanacCore.LOGGER.error("Failed to parse ritual {}: {}", id, error))
                    .ifPresent(ritual -> registry.registerRitual(id, ritual));
        }

        AlmanacCore.LOGGER.info("Loaded {} ritual definitions", registry.getRitualCount());
    }
}
