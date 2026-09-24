package com.skd.almanaccore.guide;

import net.minecraft.resources.ResourceLocation;

/**
 * Naming-convention helper for entry gating via advancements.
 * <p>
 * The consuming mod (e.g. majestic) is responsible for
 * (a) putting this id in the entry JSON's {@code advancement} field so vellumli natively gates visibility on it, and
 * (b) granting that same advancement to a player when the corresponding research node unlocks (e.g. from astral_core's research-unlock event).
 * <p>
 * This class only computes the id — it does not touch advancements or vellumli itself.
 */
public final class EntryGate {

    private EntryGate() {
    }

    public static ResourceLocation advancementIdFor(ResourceLocation entryId) {
        return ResourceLocation.fromNamespaceAndPath(entryId.getNamespace(), "guide/" + entryId.getPath());
    }
}
