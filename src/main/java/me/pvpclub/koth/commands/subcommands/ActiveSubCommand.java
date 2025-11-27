package me.pvpclub.koth.commands.subcommands;

import me.pvpclub.koth.Koth;
import me.pvpclub.koth.commands.SubCommand;
import me.pvpclub.koth.utils.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ActiveSubCommand extends SubCommand {

    private final Koth plugin;

    public ActiveSubCommand(Koth plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, String[] args) {
        if (plugin.getKothManager().getActiveSessions().isEmpty()) {
            player.sendMessage(ChatColor.RED + "No KOTHs are currently active!");
            return;
        }

        String list = plugin.getKothManager().getActiveSessions().stream()
                .map(session -> session.getArea().getName())
                .collect(Collectors.joining(", "));

        player.sendMessage(MessageUtil.getMessage("active-koth-list")
                .replace("%list%", list));
    }

    @Override
    public String getName() {
        return "active";
    }

    @Override
    public String getDescription() {
        return "List active KOTHs";
    }

    @Override
    public String getSyntax() {
        return "/koth active";
    }

    @Override
    public String getPermission() {
        return "koth.list";
    }

    @Override
    public List<String> getTabCompletions(Player player, String[] args) {
        return Collections.emptyList();
    }
}

