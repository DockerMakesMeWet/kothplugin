package me.pvpclub.koth.commands.subcommands;

import me.pvpclub.koth.Koth;
import me.pvpclub.koth.commands.SubCommand;
import me.pvpclub.koth.objects.KothArea;
import me.pvpclub.koth.utils.MessageUtil;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class StartSubCommand extends SubCommand {

    private final Koth plugin;

    public StartSubCommand(Koth plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, String[] args) {
        String name = args[0];

        if (!plugin.getKothManager().kothExists(name)) {
            player.sendMessage(MessageUtil.getMessage("koth-not-found")
                    .replace("%name%", name));
            return;
        }

        if (plugin.getKothManager().getActiveSession(name) != null) {
            player.sendMessage(MessageUtil.getMessage("koth-already-active")
                    .replace("%name%", name));
            return;
        }

        int maxActive = plugin.getConfig().getInt("settings.max-active-koths", 3);
        if (plugin.getKothManager().getActiveSessions().size() >= maxActive) {
            player.sendMessage(MessageUtil.getMessage("max-koths-active"));
            return;
        }

        plugin.getKothManager().startKoth(name);
    }

    @Override
    public String getName() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Start a KOTH";
    }

    @Override
    public String getSyntax() {
        return "/koth start <name>";
    }

    @Override
    public String getPermission() {
        return "koth.start";
    }

    @Override
    public int getMinArgs() {
        return 1;
    }

    @Override
    public List<String> getTabCompletions(Player player, String[] args) {
        if (args.length == 1) {
            return plugin.getKothManager().getAllKoths().stream()
                    .map(KothArea::getName)
                    .collect(Collectors.toList());
        }
        return null;
    }
}

