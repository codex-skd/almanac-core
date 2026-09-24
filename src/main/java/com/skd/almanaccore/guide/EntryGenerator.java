package com.skd.almanaccore.guide;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.skd.almanaccore.codec.RelicDefinition;
import com.skd.almanaccore.codec.ResearchNodeDefinition;
import com.skd.almanaccore.codec.RitualDefinition;
import com.skd.almanaccore.codec.SpellDefinition;
import net.minecraft.resources.ResourceLocation;

public final class EntryGenerator {

    private EntryGenerator() {
    }

    public static JsonObject generateSpellEntry(ResourceLocation id, SpellDefinition def, ResourceLocation category) {
        JsonObject entry = new JsonObject();
        entry.addProperty("name", id.toString());
        entry.addProperty("icon", id.toString());
        entry.addProperty("category", category.toString());

        JsonObject page = new JsonObject();
        page.addProperty("type", "patchouli:text");
        page.addProperty("text", "Spell " + id + " — School: " + def.school()
                + ", Tier: " + def.tier() + ", Cost: " + def.cost()
                + ", Cooldown: " + def.cooldown() + " ticks, Cast type: " + def.castType());

        JsonArray pages = new JsonArray();
        pages.add(page);
        entry.add("pages", pages);

        return entry;
    }

    public static JsonObject generateRitualEntry(ResourceLocation id, RitualDefinition def, ResourceLocation category) {
        JsonObject entry = new JsonObject();
        entry.addProperty("name", id.toString());
        entry.addProperty("icon", id.toString());
        entry.addProperty("category", category.toString());

        JsonObject page = new JsonObject();
        page.addProperty("type", "patchouli:text");
        page.addProperty("text", "Ritual " + id + " — Altar tier: " + def.altarTier()
                + ", Essence: " + def.essence() + ", Duration: " + def.duration()
                + " ticks, Inputs: " + def.inputs().size()
                + ", Outputs: " + def.outputs().size());

        JsonArray pages = new JsonArray();
        pages.add(page);
        entry.add("pages", pages);

        return entry;
    }

    public static JsonObject generateResearchNodeEntry(ResourceLocation id, ResearchNodeDefinition def, ResourceLocation category) {
        JsonObject entry = new JsonObject();
        entry.addProperty("name", id.toString());
        entry.addProperty("icon", id.toString());
        entry.addProperty("category", category.toString());

        JsonObject page = new JsonObject();
        page.addProperty("type", "patchouli:text");
        String constellation = def.constellation().orElse("none");
        page.addProperty("text", "Research node " + id + " — Constellation: " + constellation
                + ", Requirements: " + def.requirements().size()
                + ", Unlocks: " + def.unlocks().size()
                + ", Position: (" + def.position().x() + ", " + def.position().y() + ")");

        JsonArray pages = new JsonArray();
        pages.add(page);
        entry.add("pages", pages);

        return entry;
    }

    public static JsonObject generateRelicEntry(ResourceLocation id, RelicDefinition def, ResourceLocation category) {
        JsonObject entry = new JsonObject();
        entry.addProperty("name", id.toString());
        entry.addProperty("icon", id.toString());
        entry.addProperty("category", category.toString());

        StringBuilder text = new StringBuilder("Relic " + id + " — Slot: " + def.slot());
        if (def.passives().size() > 0) {
            text.append(", Passives: ").append(def.passives().size());
        }
        def.active().ifPresent(a -> text.append(", Active: ").append(a));
        def.set().ifPresent(s -> text.append(", Set: ").append(s));

        JsonObject page = new JsonObject();
        page.addProperty("type", "patchouli:text");
        page.addProperty("text", text.toString());

        JsonArray pages = new JsonArray();
        pages.add(page);
        entry.add("pages", pages);

        return entry;
    }
}
