package net.nikk.dncmod.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum GoblinVariant {
    DEFAULT(0),
    BELLY_1(1),
    BELLY_2(2),
    BELLY_3(3),
    BELLY_4(4),
    BELLY_5(5),
    BELLY_6(6),
    DEFAULT_1(7),
    DEFAULT_2(8),
    DEFAULT_3(9),
    DEFAULT_4(10),
    DEFAULT_5(11),
    DEFAULT_6(12)
    ;

    private static final GoblinVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(GoblinVariant::getId)).toArray(GoblinVariant[]::new);
    private final int id;

    GoblinVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static GoblinVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
