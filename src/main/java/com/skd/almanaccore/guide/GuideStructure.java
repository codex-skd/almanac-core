package com.skd.almanaccore.guide;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public final class GuideStructure {

    private static final Map<String, ResourceLocation> CATEGORY_MAP = new HashMap<>();

    private GuideStructure() {
    }

    public static void register(String kind, ResourceLocation category) {
        CATEGORY_MAP.put(kind, category);
    }

    public static ResourceLocation getCategory(String kind) {
        ResourceLocation explicit = CATEGORY_MAP.get(kind);
        if (explicit != null) {
            return explicit;
        }
        return switch (kind) {
            case "spell" -> ResourceLocation.fromNamespaceAndPath("almanac_core", "spells");
            case "ritual" -> ResourceLocation.fromNamespaceAndPath("almanac_core", "rituals");
            case "research_node" -> ResourceLocation.fromNamespaceAndPath("almanac_core", "research");
            case "relic" -> ResourceLocation.fromNamespaceAndPath("almanac_core", "relics");
            default -> ResourceLocation.fromNamespaceAndPath("almanac_core", "misc");
        };
    }
}
