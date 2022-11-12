package net.nikk.dncmod.mixin;

import com.mojang.datafixers.DataFixer;
import net.minecraft.world.WorldSaveHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.io.File;

@Mixin(WorldSaveHandler.class)
public interface SaveHandlerAccess {
    @Accessor(value = "dataFixer")
    DataFixer getDataFixer();
    @Accessor(value = "playerDataDir")
    File getPlayerDataDir();
}
