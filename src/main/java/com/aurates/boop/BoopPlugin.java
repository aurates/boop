package com.aurates.boop;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class BoopPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        registerCommand("boop", "Boop!");
        registerCommand("bonk", "Bonk!");
    }

    private void registerCommand(String commandName, String text) {
        Command command = getCommand(commandName);
        if (command != null) {
            command.setExecutor(new MessageCommand(text));
        }
    }

    private static final class MessageCommand implements CommandExecutor {
        private final String messageText;

        private MessageCommand(String messageText) {
            this.messageText = messageText;
        }

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            Player target;
            if (args.length > 0) {
                target = sender.getServer().getPlayerExact(args[0]);
                if (target == null) {
                    sender.sendMessage(Component.text("Player not found.", NamedTextColor.RED));
                    return true;
                }
            } else if (sender instanceof Player player) {
                target = player;
            } else {
                sender.sendMessage(Component.text("Usage: /" + label + " <player>", NamedTextColor.RED));
                return true;
            }

            target.sendMessage(Component.text(target.getName() + ": " + messageText, NamedTextColor.LIGHT_PURPLE));
            if (!sender.equals(target)) {
                sender.sendMessage(Component.text("Sent to " + target.getName() + ".", NamedTextColor.LIGHT_PURPLE));
            }
            return true;
        }
    }
}
