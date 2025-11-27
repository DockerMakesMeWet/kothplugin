package me.pvpclub.koth.commands.subcommands;

import me.pvpclub.koth.Koth;
import me.pvpclub.koth.commands.SubCommand;
import me.pvpclub.koth.utils.MessageUtil;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class StopSubCommand extends SubCommand {

    private final Koth plugin;

    public StopSubCommand(Koth plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, String[] args) {
        String name = args[0];

        if (plugin.getKothManager().getActiveSession(name) == null) {
            player.sendMessage(MessageUtil.getMessage("no-active-koth"));
            return;
        }

        plugin.getKothManager().stopKoth(name);
        player.sendMessage(MessageUtil.getMessage("koth-stopped")
                .replace("%name%", name));
    }

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getDescription() {
        return "Stop a KOTH";
    }

    @Override
    public String getSyntax() {
        return "/koth stop <name>";
    }

    @Override
    public String getPermission() {
        return "koth.stop";
    }

    @Override
    public int getMinArgs() {
        return 1;
    }

    @Override
    public List<String> getTabCompletions(Player player, String[] args) {
        if (args.length == 1) {
            return plugin.getKothManager().getActiveSessions().stream()
                    .map(session -> session.getArea().getName())
                    .collect(Collectors.toList());
        }
        return null;
    }
}

