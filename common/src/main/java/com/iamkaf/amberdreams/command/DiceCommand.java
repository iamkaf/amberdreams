package com.iamkaf.amberdreams.command;

import com.iamkaf.amber.api.command.SubCommand;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.util.Random;

public class DiceCommand implements SubCommand {
    private static int roll(CommandContext<CommandSourceStack> context, Integer value) {
        int result = new Random().nextInt(value) + 1;
        context.getSource()
                .sendSuccess(() -> Component.literal(String.format("You rolled a %s!", result)), false);
        return 1;
    }

    @Override
    public String getName() {
        return "dice";
    }

    @Override
    public String getDescription() {
        return "Rolls a dice.";
    }

    @Override
    public LiteralArgumentBuilder<CommandSourceStack> register(LiteralArgumentBuilder<CommandSourceStack> parent) {
        return parent.then(Commands.literal(getName())
                .executes(context -> DiceCommand.roll(context, 6))
                .then(Commands.argument("value", IntegerArgumentType.integer(1, 32767))
                        .executes(context -> DiceCommand.roll(context,
                                IntegerArgumentType.getInteger(context, "value")
                        ))));
    }
}
