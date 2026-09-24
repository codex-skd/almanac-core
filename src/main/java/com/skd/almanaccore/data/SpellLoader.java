package com.skd.almanaccore.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.skd.almanaccore.AlmanacCore;
import com.skd.almanaccore.codec.SpellDefinition;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;

import java.util.Map;

public final class SpellLoader extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new Gson();
    private static final String DIRECTORY = "almanac/spell";

    public SpellLoader() {
        super(GSON, DIRECTORY);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> splatMap, ResourceManager resourceManager, ProfilerFiller profiler) {
        AlmanacContentRegistry registry = AlmanacContentRegistry.getInstance();
        registry.clearSpells();

        for (Map.Entry<ResourceLocation, JsonElement> entry : splatMap.entrySet()) {
            ResourceLocation id = entry.getKey();
            JsonElement json = entry.getValue();

            if (!(json instanceof JsonObject obj)) {
                AlmanacCore.LOGGER.warn("Skipping spell {} — root is not a JSON object", id);
                continue;
            }

            DataResult<SpellDefinition> result = SpellDefinition.CODEC.parse(JsonOps.INSTANCE, obj);
            result.resultOrPartial(error -> AlmanacCore.LOGGER.error("Failed to parse spell {}: {}", id, error))
                    .ifPresent(spell -> registry.registerSpell(id, spell));
        }

        AlmanacCore.LOGGER.info("Loaded {} spell definitions", registry.getSpellCount());
    }
}
