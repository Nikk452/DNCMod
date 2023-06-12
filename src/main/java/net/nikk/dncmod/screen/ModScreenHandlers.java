package net.nikk.dncmod.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.nikk.dncmod.DNCMod;

public class ModScreenHandlers {
    public static ScreenHandlerType<SpellBookScreenHandler> SPELL_BOOK_SCREEN_HANDLER =
            new ScreenHandlerType<SpellBookScreenHandler>(SpellBookScreenHandler::new);

    public static void registerAllScreenHandlers() {
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(DNCMod.MOD_ID, "spell_book_screen"),
                SPELL_BOOK_SCREEN_HANDLER);
    }
}
