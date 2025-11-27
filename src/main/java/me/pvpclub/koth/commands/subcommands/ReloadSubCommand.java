package me.pvpclub.koth.commands.subcommands;

import me.pvpclub.koth.Koth;
import me.pvpclub.koth.commands.SubCommand;
import me.pvpclub.koth.utils.MessageUtil;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class ReloadSubCommand extends SubCommand {

    private final Koth plugin;

    public ReloadSubCommand(Koth plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, String[] args) {
        plugin.reloadConfig();
        plugin.getKothManager().loadKoths();
        player.sendMessage(MessageUtil.getMessage("config-reloaded"));
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reload the plugin";
    }

    @Override
    public String getSyntax() {
        return "/koth reload";
    }

    @Override
    public String getPermission() {
        return "koth.reload";
    }

    @Override
    public List<String> getTabCompletions(Player player, String[] args) {
        return Collections.emptyList();
    }
}

