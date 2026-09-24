package com.skd.almanaccore.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;

public record ResearchNodeDefinition(
        Optional<String> constellation,
        List<ResourceLocation> requirements,
        List<ResourceLocation> unlocks,
        TreePosition position
) {

    public static final Codec<ResearchNodeDefinition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.optionalFieldOf("constellation").forGetter(ResearchNodeDefinition::constellation),
            ResourceLocation.CODEC.listOf().fieldOf("requirements").forGetter(ResearchNodeDefinition::requirements),
            ResourceLocation.CODEC.listOf().fieldOf("unlocks").forGetter(ResearchNodeDefinition::unlocks),
            TreePosition.CODEC.fieldOf("position").forGetter(ResearchNodeDefinition::position)
    ).apply(instance, ResearchNodeDefinition::new));

    public record TreePosition(int x, int y) {
        public static final Codec<TreePosition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("x").forGetter(TreePosition::x),
                Codec.INT.fieldOf("y").forGetter(TreePosition::y)
        ).apply(instance, TreePosition::new));
    }
}
