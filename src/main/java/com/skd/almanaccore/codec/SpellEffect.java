package com.skd.almanaccore.codec;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;

public record SpellEffect(ResourceLocation type, JsonObject rawParams) {

    public static final Codec<SpellEffect> CODEC = new Codec<>() {
        @Override
        public <T> DataResult<T> encode(SpellEffect input, DynamicOps<T> ops, T prefix) {
            return DataResult.success(prefix);
        }

        @Override
        public <T> DataResult<Pair<SpellEffect, T>> decode(DynamicOps<T> ops, T input) {
            JsonElement json = ops.convertTo(JsonOps.INSTANCE, input);
            if (!(json instanceof JsonObject obj)) {
                return DataResult.error(() -> "Expected JsonObject for SpellEffect, got " + json);
            }
            JsonElement typeElement = obj.get("type");
            if (typeElement == null || !typeElement.getAsJsonPrimitive().isString()) {
                return DataResult.error(() -> "SpellEffect missing string 'type' field");
            }
            DataResult<ResourceLocation> rlResult = ResourceLocation.CODEC.parse(JsonOps.INSTANCE, typeElement);
            return rlResult.map(rl -> Pair.of(new SpellEffect(rl, obj), input));
        }
    };
}
