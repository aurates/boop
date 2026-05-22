package com.aurates.boop;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class BoopPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        registerCommand("meow", "§e§omeow! ᓚᘏ𑄝");
    }

    private void registerCommand(String commandName, String text) {
        PluginCommand command = getCommand(commandName);
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
            if (args.length == 0) {
                sender.sendMessage(Component.text("Usage: /" + label + " <player>", NamedTextColor.RED));
                return true;
            }

            Player target = sender.getServer().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(Component.text("Player not found.", NamedTextColor.RED));
                return true;
            }

            if (sender instanceof Player player && target.equals(player)) {
                sender.sendMessage(Component.text("You cannot " + label + " yourself.", NamedTextColor.RED));
                return true;
            }

            Component message = LegacyComponentSerializer.legacySection().deserialize(messageText);
            Component toTarget = Component.text("From " + sender.getName() + ": ", NamedTextColor.GRAY).append(message);
            Component toSender = Component.text("To " + target.getName() + ": ", NamedTextColor.GRAY).append(message);
            target.sendMessage(toTarget);
            sender.sendMessage(toSender);
            return true;
        }
    }
}
