package me.pvpclub.koth.commands;

import me.pvpclub.koth.Koth;
import me.pvpclub.koth.commands.subcommands.*;
import me.pvpclub.koth.utils.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Main command handler for KOTH plugin
 */
public class KothCommand implements CommandExecutor, TabCompleter {

    private final Koth plugin;
    private final Map<String, SubCommand> subCommands;

    public KothCommand(Koth plugin) {
        this.plugin = plugin;
        this.subCommands = new HashMap<>();
        registerSubCommands();
    }

    /**
     * Register all subcommands
     */
    private void registerSubCommands() {
        registerSubCommand(new WandSubCommand(plugin));
        registerSubCommand(new CreateSubCommand(plugin));
        registerSubCommand(new DeleteSubCommand(plugin));
        registerSubCommand(new EditSubCommand(plugin));
        registerSubCommand(new RewardSubCommand(plugin));
        registerSubCommand(new StartSubCommand(plugin));
        registerSubCommand(new StopSubCommand(plugin));
        registerSubCommand(new ListSubCommand(plugin));
        registerSubCommand(new ActiveSubCommand(plugin));
        registerSubCommand(new ReloadSubCommand(plugin));
    }

    /**
     * Register a subcommand
     */
    private void registerSubCommand(SubCommand subCommand) {
        subCommands.put(subCommand.getName().toLowerCase(), subCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            sendHelpMessage(player);
            return true;
        }

        String subCommandName = args[0].toLowerCase();
        SubCommand subCommand = subCommands.get(subCommandName);

        if (subCommand == null) {
            sendHelpMessage(player);
            return true;
        }

        if (!hasPermission(player, subCommand.getPermission())) {
            player.sendMessage(MessageUtil.getMessage("no-permission"));
            return true;
        }

        String[] subArgs = Arrays.copyOfRange(args, 1, args.length);

        if (subArgs.length < subCommand.getMinArgs()) {
            player.sendMessage(MessageUtil.getMessage("invalid-usage")
                    .replace("%usage%", subCommand.getSyntax()));
            return true;
        }

        try {
            subCommand.execute(player, subArgs);
        } catch (Exception e) {
            player.sendMessage(ChatColor.RED + "An error occurred while executing this command!");
            e.printStackTrace();
        }

        return true;
    }

    /**
     * Check if player has permission
     */
    private boolean hasPermission(Player player, String permission) {
        return player.hasPermission(permission) || player.hasPermission("koth.admin");
    }

    /**
     * Send help message showing available commands
     */
    private void sendHelpMessage(Player player) {
        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "KOTH Commands:");

        for (SubCommand subCommand : subCommands.values()) {
            if (hasPermission(player, subCommand.getPermission())) {
                player.sendMessage(ChatColor.YELLOW + subCommand.getSyntax() +
                        ChatColor.GRAY + " - " + subCommand.getDescription());
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            return Collections.emptyList();
        }

        Player player = (Player) sender;
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            for (SubCommand subCommand : subCommands.values()) {
                if (hasPermission(player, subCommand.getPermission())) {
                    completions.add(subCommand.getName());
                }
            }
        } else if (args.length > 1) {
            SubCommand subCommand = subCommands.get(args[0].toLowerCase());
            if (subCommand != null && hasPermission(player, subCommand.getPermission())) {
                String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
                List<String> subCompletions = subCommand.getTabCompletions(player, subArgs);
                if (subCompletions != null) {
                    completions.addAll(subCompletions);
                }
            }
        }

        return completions.stream()
                .filter(s -> s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                .sorted()
                .collect(Collectors.toList());
    }
}

