package me.pvpclub.koth.commands.subcommands;

import me.pvpclub.koth.Koth;
import me.pvpclub.koth.commands.SubCommand;
import me.pvpclub.koth.objects.KothArea;
import me.pvpclub.koth.utils.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ListSubCommand extends SubCommand {

    private final Koth plugin;

    public ListSubCommand(Koth plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, String[] args) {
        if (plugin.getKothManager().getAllKoths().isEmpty()) {
            player.sendMessage(ChatColor.RED + "No KOTHs have been created yet!");
            return;
        }

        String list = plugin.getKothManager().getAllKoths().stream()
                .map(KothArea::getName)
                .collect(Collectors.joining(", "));

        player.sendMessage(MessageUtil.getMessage("koth-list")
                .replace("%list%", list));
    }

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "List all KOTHs";
    }

    @Override
    public String getSyntax() {
        return "/koth list";
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

