package net.nikk.dncmod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.IOManager;

public class SetXpMultiCommand {
    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher,
                                CommandRegistryAccess commandRegistryAccess,
                                CommandManager.RegistrationEnvironment registrationEnvironment) {
        commandDispatcher.register(CommandManager.literal("DNCMod").requires(source -> source.hasPermissionLevel(2))
                        .then(CommandManager.literal("setXpMultiplier")
                                .then(CommandManager.argument("amount", IntegerArgumentType.integer())
                                        .executes(context -> run(context,IntegerArgumentType.getInteger(context, "amount")))
                                )
                        )
        );
    }

    public static int run(CommandContext<ServerCommandSource> context, int amount) throws CommandSyntaxException {
        DNCMod.CONFIG.xp_per_lvl_multi = amount;
        IOManager.updateModConfig(DNCMod.CONFIG);
        context.getSource().sendFeedback(Text.literal("Xp multiplier was changed to: x"+amount),true);
        return 1;
    }
}
