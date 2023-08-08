package net.nikk.dncmod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.util.ExperienceData;
import java.util.Collection;
import java.util.Collections;

public class AddXpCommand {
    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher,
                                CommandRegistryAccess commandRegistryAccess,
                                CommandManager.RegistrationEnvironment registrationEnvironment) {
        LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = CommandManager.literal(DNCMod.MOD_ID).requires(source -> source.hasPermissionLevel(2));
        literalArgumentBuilder.then(CommandManager.literal("addXp").then(CommandManager.argument("amount", IntegerArgumentType.integer()))).executes((context) -> run(context, Collections.singleton(((ServerCommandSource)context.getSource()).getPlayerOrThrow()), IntegerArgumentType.getInteger(context, "amount"), EntityArgumentType.getPlayers(context, "Player").toString())).then(CommandManager.argument("Player", EntityArgumentType.players())).executes((context) -> run(context, EntityArgumentType.getPlayers(context, "target"), IntegerArgumentType.getInteger(context, "amount"), EntityArgumentType.getPlayers(context, "Player").toString()));
        commandDispatcher.register(literalArgumentBuilder);
    }

    public static int run(CommandContext<ServerCommandSource> context, Collection<ServerPlayerEntity> targets, int amount, String s) throws CommandSyntaxException {
        if(amount>0) if (s.equals("All")){
            for(ServerPlayerEntity player : context.getSource().getServer().getPlayerManager().getPlayerList()){
                ExperienceData.addExperience(player.getWorld(),player, amount);
            }
        } else {
            ExperienceData.addExperience(context.getSource().getWorld(),context.getSource().getPlayer(), amount);
        }
        context.getSource().sendFeedback(Text.literal(amount+" xp was added to"+(s.equals("")?context.getSource().getName():"All")),true);
        return 1;
    }
}
