package com.skd.almanaccore.codec;

import net.minecraft.util.StringRepresentable;

public enum CastType implements StringRepresentable {
    PROJECTILE("projectile"),
    TOUCH("touch"),
    AREA("area"),
    SELF("self"),
    CHANNEL("channel");

    private final String serializedName;

    CastType(String serializedName) {
        this.serializedName = serializedName;
    }

    @Override
    public String getSerializedName() {
        return serializedName;
    }
}
