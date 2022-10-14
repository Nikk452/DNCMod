package net.nikk.dncmod.mixin;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.TimeCommand;
import net.minecraft.text.Text;
import net.nikk.dncmod.DNCMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TimeCommand.class)
public class TimeCommandMixin {
    @Inject(method = "executeSet", at =@At("HEAD"), cancellable = true)
    private static void disableTimeSetTAW(ServerCommandSource source, int time, CallbackInfoReturnable<Integer> cir){
        if(DNCMod.CONFIG.syncWithSystemTime){
            source.sendFeedback(Text.literal("Time set command is disabled while synchronization with system time is enabled"), false);
            cir.setReturnValue(0);
        }
    }

    @Inject(method = "executeAdd", at =@At("HEAD"), cancellable = true)
    private static void disableTimeAddTAW(ServerCommandSource source, int time, CallbackInfoReturnable<Integer> cir){
        if(DNCMod.CONFIG.syncWithSystemTime){
            source.sendFeedback(Text.literal("Time add command is disabled while synchronization with system time is enabled"), false);
            cir.setReturnValue(0);
        }
    }
}
