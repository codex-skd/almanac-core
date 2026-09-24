package com.skd.almanaccore.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;

public record RelicDefinition(
        String slot,
        List<ResourceLocation> passives,
        Optional<ResourceLocation> active,
        Optional<ResourceLocation> set
) {

    public static final Codec<RelicDefinition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("slot").forGetter(RelicDefinition::slot),
            ResourceLocation.CODEC.listOf().fieldOf("passives").forGetter(RelicDefinition::passives),
            ResourceLocation.CODEC.optionalFieldOf("active").forGetter(RelicDefinition::active),
            ResourceLocation.CODEC.optionalFieldOf("set").forGetter(RelicDefinition::set)
    ).apply(instance, RelicDefinition::new));
}
