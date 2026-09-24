package com.skd.almanaccore.guide;

import com.skd.vellumli.api.VellumliAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

/**
 * Thin wrapper around vellumli's public, stub-backed API ({@code com.skd.vellumli.api.VellumliAPI}).
 * Safe to call even when vellumli is not installed, since {@code VellumliAPI.get()} returns a no-op stub in that case.
 * <p>
 * No vellumli code is copied or derived; this class only calls its published API surface
 * (CC BY-NC-SA 3.0 — ShareAlike only applies to derivative works of vellumli itself,
 * not to code that merely calls its API).
 */
public final class VellumliBridge {

    private VellumliBridge() {
    }

    public static ItemStack giveBookStack(ResourceLocation bookId) {
        return VellumliAPI.get().getBookStack(bookId);
    }

    public static void openBook(ServerPlayer player, ResourceLocation bookId) {
        VellumliAPI.get().openBookGUI(player, bookId);
    }

    public static void openEntry(ServerPlayer player, ResourceLocation bookId, ResourceLocation entryId, int page) {
        VellumliAPI.get().openBookEntry(player, bookId, entryId, page);
    }
}
