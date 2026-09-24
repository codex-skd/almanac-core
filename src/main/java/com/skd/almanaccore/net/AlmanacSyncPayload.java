package com.skd.almanaccore.net;

import com.mojang.datafixers.util.Pair;
import com.skd.almanaccore.codec.RelicDefinition;
import com.skd.almanaccore.codec.ResearchNodeDefinition;
import com.skd.almanaccore.codec.RitualDefinition;
import com.skd.almanaccore.codec.SpellDefinition;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record AlmanacSyncPayload(
        List<Pair<ResourceLocation, SpellDefinition>> spells,
        List<Pair<ResourceLocation, RitualDefinition>> rituals,
        List<Pair<ResourceLocation, ResearchNodeDefinition>> researchNodes,
        List<Pair<ResourceLocation, RelicDefinition>> relics
) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<AlmanacSyncPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("almanac_core", "sync"));

    private static <T> StreamCodec<FriendlyByteBuf, Pair<ResourceLocation, T>> entryCodec(com.mojang.serialization.Codec<T> codec) {
        return StreamCodec.composite(
                ResourceLocation.STREAM_CODEC,
                Pair::getFirst,
                ByteBufCodecs.fromCodec(codec),
                Pair::getSecond,
                Pair::of
        );
    }

    public static final StreamCodec<FriendlyByteBuf, AlmanacSyncPayload> STREAM_CODEC = StreamCodec.composite(
            entryCodec(SpellDefinition.CODEC).apply(ByteBufCodecs.list()), AlmanacSyncPayload::spells,
            entryCodec(RitualDefinition.CODEC).apply(ByteBufCodecs.list()), AlmanacSyncPayload::rituals,
            entryCodec(ResearchNodeDefinition.CODEC).apply(ByteBufCodecs.list()), AlmanacSyncPayload::researchNodes,
            entryCodec(RelicDefinition.CODEC).apply(ByteBufCodecs.list()), AlmanacSyncPayload::relics,
            AlmanacSyncPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
