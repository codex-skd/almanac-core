package com.skd.almanaccore;

import com.skd.almanaccore.command.AlmanacCommands;
import com.skd.almanaccore.data.AlmanacContentRegistry;
import com.skd.almanaccore.data.RitualLoader;
import com.skd.almanaccore.data.ResearchNodeLoader;
import com.skd.almanaccore.data.RelicLoader;
import com.skd.almanaccore.data.SpellLoader;
import com.skd.almanaccore.net.AlmanacNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(AlmanacCore.MOD_ID)
public final class AlmanacCore {

    public static final String MOD_ID = "almanac_core";

    public static final Logger LOGGER = LoggerFactory.getLogger("Almanac Core");

    public AlmanacCore(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("Almanac Core v{} loading", modContainer.getModInfo().getVersion());

        // Register network payloads on the mod event bus
        modEventBus.addListener(RegisterPayloadHandlersEvent.class, AlmanacNetworking::registerPayloads);

        // Register reload listener on the NeoForge game event bus
        NeoForge.EVENT_BUS.addListener(AddReloadListenerEvent.class, event -> {
            event.addListener(new SpellLoader());
            event.addListener(new RitualLoader());
            event.addListener(new ResearchNodeLoader());
            event.addListener(new RelicLoader());
        });

        // Sync content to clients after datapack reload / on player login
        NeoForge.EVENT_BUS.addListener(OnDatapackSyncEvent.class, event -> {
            ServerPlayer player = event.getPlayer();
            if (player != null) {
                // Single-player login sync
                AlmanacNetworking.syncToPlayer(player);
            } else {
                // Server-wide reload sync (all players)
                AlmanacNetworking.syncToAllPlayers();
            }
        });

        // Register commands
        NeoForge.EVENT_BUS.addListener(RegisterCommandsEvent.class, event -> {
            AlmanacCommands.register(event.getDispatcher());
        });
    }
}
