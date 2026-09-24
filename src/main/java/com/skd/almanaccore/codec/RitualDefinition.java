package com.skd.almanaccore.codec;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Optional;

public record RitualDefinition(
        int altarTier,
        List<RitualInput> inputs,
        double essence,
        int duration,
        RitualRiskDef risk,
        List<RitualOutput> outputs
) {

    public static final Codec<RitualDefinition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("altar_tier").forGetter(RitualDefinition::altarTier),
            RitualInput.CODEC.listOf().fieldOf("inputs").forGetter(RitualDefinition::inputs),
            Codec.DOUBLE.fieldOf("essence").forGetter(RitualDefinition::essence),
            Codec.INT.fieldOf("duration").forGetter(RitualDefinition::duration),
            RitualRiskDef.CODEC.fieldOf("risk").forGetter(RitualDefinition::risk),
            RitualOutput.CODEC.listOf().fieldOf("outputs").forGetter(RitualDefinition::outputs)
    ).apply(instance, RitualDefinition::new));

    public record RitualInput(Optional<ResourceLocation> item, Optional<TagKey<Item>> tag) {

        public static final Codec<RitualInput> CODEC = new Codec<>() {
            @Override
            public <T> DataResult<T> encode(RitualInput input, DynamicOps<T> ops, T prefix) {
                // Mirror of decode(): exactly one of item/tag must be present (enforced at
                // construction time by decode(), so this only asserts the invariant). Without a
                // real encode(), ByteBufCodecs.fromCodec(RitualDefinition.CODEC) — used for
                // server->client sync — would silently drop the item/tag entirely and either
                // corrupt the payload or throw on decode when the client parses it back.
                if (input.item().isPresent()) {
                    T map = ops.mergeToMap(prefix, ops.createString("item"), ops.createString(input.item().get().toString()))
                            .getOrThrow();
                    return DataResult.success(map);
                } else if (input.tag().isPresent()) {
                    T map = ops.mergeToMap(prefix, ops.createString("tag"), ops.createString(input.tag().get().location().toString()))
                            .getOrThrow();
                    return DataResult.success(map);
                }
                return DataResult.error(() -> "RitualInput must have either 'item' or 'tag'");
            }

            @Override
            public <T> DataResult<Pair<RitualInput, T>> decode(DynamicOps<T> ops, T input) {
                JsonElement json = ops.convertTo(JsonOps.INSTANCE, input);
                if (!(json instanceof JsonObject obj)) {
                    return DataResult.error(() -> "Expected JsonObject for RitualInput");
                }
                boolean hasItem = obj.has("item") && obj.get("item").getAsJsonPrimitive().isString();
                boolean hasTag = obj.has("tag") && obj.get("tag").getAsJsonPrimitive().isString();
                if (!hasItem && !hasTag) {
                    return DataResult.error(() -> "RitualInput must have either 'item' or 'tag'");
                }
                if (hasItem && hasTag) {
                    return DataResult.error(() -> "RitualInput must have either 'item' or 'tag', not both");
                }
                if (hasItem) {
                    ResourceLocation item = ResourceLocation.parse(obj.get("item").getAsString());
                    return DataResult.success(Pair.of(new RitualInput(Optional.of(item), Optional.empty()), input));
                } else {
                    TagKey<Item> tag = TagKey.create(Registries.ITEM, ResourceLocation.parse(obj.get("tag").getAsString()));
                    return DataResult.success(Pair.of(new RitualInput(Optional.empty(), Optional.of(tag)), input));
                }
            }
        };
    }

    public record RitualRiskDef(double chance, String penalty) {
        public static final Codec<RitualRiskDef> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.DOUBLE.fieldOf("chance").forGetter(RitualRiskDef::chance),
                Codec.STRING.fieldOf("penalty").forGetter(RitualRiskDef::penalty)
        ).apply(instance, RitualRiskDef::new));
    }

    public record RitualOutput(ResourceLocation type, ResourceLocation value) {
        public static final Codec<RitualOutput> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResourceLocation.CODEC.fieldOf("type").forGetter(RitualOutput::type),
                ResourceLocation.CODEC.fieldOf("value").forGetter(RitualOutput::value)
        ).apply(instance, RitualOutput::new));
    }
}
