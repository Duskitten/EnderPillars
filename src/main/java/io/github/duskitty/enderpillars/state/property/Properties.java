package io.github.duskitty.enderpillars.state.property;


import net.minecraft.state.property.BooleanProperty;

public class Properties {
    public static final BooleanProperty HASPEARL;

    static {
        HASPEARL = BooleanProperty.of("pearl");
    }
}
