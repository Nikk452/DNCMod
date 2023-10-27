package net.nikk.dncmod.screen;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureManager;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;

public class ModScreenHandlers {
    public static ScreenHandlerType<SpellBookScreenHandler> SPELL_BOOK_SCREEN_HANDLER =
            new ScreenHandlerType<SpellBookScreenHandler>(SpellBookScreenHandler::new, FeatureFlags.VANILLA_FEATURES);

    public static void registerAllScreenHandlers() {
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(DNCMod.MOD_ID, "spell_book_screen"),
                SPELL_BOOK_SCREEN_HANDLER);
    }
}
