package com.skd.almanaccore.net;

import com.skd.almanaccore.AlmanacCore;
import com.skd.almanaccore.data.AlmanacContentRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.ArrayList;

public final class AlmanacNetworking {

    private AlmanacNetworking() {
    }

    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(AlmanacCore.MOD_ID);
        registrar.playToClient(
                AlmanacSyncPayload.TYPE,
                AlmanacSyncPayload.STREAM_CODEC,
                (payload, context) -> {
                    context.enqueueWork(() -> {
                        AlmanacContentRegistry registry = AlmanacContentRegistry.getInstance();
                        registry.clear();
                        for (var entry : payload.spells()) {
                            registry.registerSpell(entry.getFirst(), entry.getSecond());
                        }
                        for (var entry : payload.rituals()) {
                            registry.registerRitual(entry.getFirst(), entry.getSecond());
                        }
                        for (var entry : payload.researchNodes()) {
                            registry.registerResearchNode(entry.getFirst(), entry.getSecond());
                        }
                        for (var entry : payload.relics()) {
                            registry.registerRelic(entry.getFirst(), entry.getSecond());
                        }
                        AlmanacCore.LOGGER.info("Client received sync: {} spells, {} rituals, {} research_nodes, {} relics",
                                payload.spells().size(), payload.rituals().size(),
                                payload.researchNodes().size(), payload.relics().size());
                    });
                }
        );
    }

    public static void syncToPlayer(ServerPlayer player) {
        AlmanacContentRegistry registry = AlmanacContentRegistry.getInstance();
        var spells = new ArrayList<>(registry.getSpells().entrySet().stream()
                .map(e -> new com.mojang.datafixers.util.Pair<>(e.getKey(), e.getValue()))
                .toList());
        var rituals = new ArrayList<>(registry.getRituals().entrySet().stream()
                .map(e -> new com.mojang.datafixers.util.Pair<>(e.getKey(), e.getValue()))
                .toList());
        var researchNodes = new ArrayList<>(registry.getResearchNodes().entrySet().stream()
                .map(e -> new com.mojang.datafixers.util.Pair<>(e.getKey(), e.getValue()))
                .toList());
        var relics = new ArrayList<>(registry.getRelics().entrySet().stream()
                .map(e -> new com.mojang.datafixers.util.Pair<>(e.getKey(), e.getValue()))
                .toList());
        PacketDistributor.sendToPlayer(player, new AlmanacSyncPayload(spells, rituals, researchNodes, relics));
    }

    public static void syncToAllPlayers() {
        AlmanacContentRegistry registry = AlmanacContentRegistry.getInstance();
        var spells = new ArrayList<>(registry.getSpells().entrySet().stream()
                .map(e -> new com.mojang.datafixers.util.Pair<>(e.getKey(), e.getValue()))
                .toList());
        var rituals = new ArrayList<>(registry.getRituals().entrySet().stream()
                .map(e -> new com.mojang.datafixers.util.Pair<>(e.getKey(), e.getValue()))
                .toList());
        var researchNodes = new ArrayList<>(registry.getResearchNodes().entrySet().stream()
                .map(e -> new com.mojang.datafixers.util.Pair<>(e.getKey(), e.getValue()))
                .toList());
        var relics = new ArrayList<>(registry.getRelics().entrySet().stream()
                .map(e -> new com.mojang.datafixers.util.Pair<>(e.getKey(), e.getValue()))
                .toList());
        PacketDistributor.sendToAllPlayers(new AlmanacSyncPayload(spells, rituals, researchNodes, relics));
    }
}
