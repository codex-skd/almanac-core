package com.skd.almanaccore.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;

import java.util.List;
import java.util.Optional;

public record SpellDefinition(
        ResourceLocation school,
        int tier,
        double cost,
        CastType castType,
        int cooldown,
        Unlock unlock,
        List<SpellEffect> effects,
        Optional<ClientHints> client
) {

    public static final Codec<SpellDefinition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("school").forGetter(SpellDefinition::school),
            Codec.INT.fieldOf("tier").forGetter(SpellDefinition::tier),
            Codec.DOUBLE.fieldOf("cost").forGetter(SpellDefinition::cost),
            StringRepresentable.fromEnum(CastType::values).fieldOf("cast_type").forGetter(SpellDefinition::castType),
            Codec.INT.fieldOf("cooldown").forGetter(SpellDefinition::cooldown),
            Unlock.CODEC.fieldOf("unlock").forGetter(SpellDefinition::unlock),
            SpellEffect.CODEC.listOf().fieldOf("effects").forGetter(SpellDefinition::effects),
            ClientHints.CODEC.optionalFieldOf("client").forGetter(SpellDefinition::client)
    ).apply(instance, SpellDefinition::new));

    public record Unlock(String type, ResourceLocation value) {
        public static final Codec<Unlock> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("type").forGetter(Unlock::type),
                ResourceLocation.CODEC.fieldOf("value").forGetter(Unlock::value)
        ).apply(instance, Unlock::new));
    }

    public record ClientHints(ResourceLocation particle, ResourceLocation sound) {
        public static final Codec<ClientHints> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResourceLocation.CODEC.fieldOf("particle").forGetter(ClientHints::particle),
                ResourceLocation.CODEC.fieldOf("sound").forGetter(ClientHints::sound)
        ).apply(instance, ClientHints::new));
    }
}
