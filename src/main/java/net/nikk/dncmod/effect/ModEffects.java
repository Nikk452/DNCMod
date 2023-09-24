package net.nikk.dncmod.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;

public class ModEffects {
    public static StatusEffect HEAVY;
    public static StatusEffect registerStatusEffect(String name) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(DNCMod.MOD_ID, name),
                new HeavyEffect(StatusEffectCategory.HARMFUL, 1124687).addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "1107DE5E-7CE8-4030-940E-213C1F160190", -0.15000000596046448, EntityAttributeModifier.Operation.MULTIPLY_TOTAL).addAttributeModifier(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, "2107DE4E-7CE8-4030-940E-514C1F160890", -0.1, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
    }

    public static void registerEffects() {
        HEAVY = registerStatusEffect("heavy");
    }
}
