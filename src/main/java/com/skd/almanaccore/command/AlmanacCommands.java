package com.skd.almanaccore.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import com.skd.almanaccore.data.AlmanacContentRegistry;

public final class AlmanacCommands {

    private AlmanacCommands() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("almanaccore")
                        .then(Commands.literal("status")
                                .executes(AlmanacCommands::status))
        );
    }

    private static int status(CommandContext<CommandSourceStack> ctx) {
        AlmanacContentRegistry registry = AlmanacContentRegistry.getInstance();

        int total = registry.getSpellCount() + registry.getRitualCount()
                + registry.getResearchNodeCount() + registry.getRelicCount();
        ctx.getSource().sendSuccess(() -> Component.literal("Almanac Core — " + total + " definition(s) loaded"), false);

        ctx.getSource().sendSuccess(() -> Component.literal("  Spells (" + registry.getSpellCount() + "):"), false);
        for (var entry : registry.getSpells().entrySet()) {
            ctx.getSource().sendSuccess(() -> Component.literal("    - " + entry.getKey()), false);
        }

        ctx.getSource().sendSuccess(() -> Component.literal("  Rituals (" + registry.getRitualCount() + "):"), false);
        for (var entry : registry.getRituals().entrySet()) {
            ctx.getSource().sendSuccess(() -> Component.literal("    - " + entry.getKey()), false);
        }

        ctx.getSource().sendSuccess(() -> Component.literal("  Research Nodes (" + registry.getResearchNodeCount() + "):"), false);
        for (var entry : registry.getResearchNodes().entrySet()) {
            ctx.getSource().sendSuccess(() -> Component.literal("    - " + entry.getKey()), false);
        }

        ctx.getSource().sendSuccess(() -> Component.literal("  Relics (" + registry.getRelicCount() + "):"), false);
        for (var entry : registry.getRelics().entrySet()) {
            ctx.getSource().sendSuccess(() -> Component.literal("    - " + entry.getKey()), false);
        }

        return total;
    }
}
