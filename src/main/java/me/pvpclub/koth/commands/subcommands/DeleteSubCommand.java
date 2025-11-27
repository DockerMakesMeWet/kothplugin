package me.pvpclub.koth.commands.subcommands;

import me.pvpclub.koth.Koth;
import me.pvpclub.koth.commands.SubCommand;
import me.pvpclub.koth.objects.KothArea;
import me.pvpclub.koth.utils.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class DeleteSubCommand extends SubCommand {

    private final Koth plugin;

    public DeleteSubCommand(Koth plugin) {
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

        plugin.getKothManager().deleteKoth(name);
        player.sendMessage(ChatColor.GREEN + "KOTH " + ChatColor.YELLOW + name + ChatColor.GREEN + " has been deleted!");
    }

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getDescription() {
        return "Delete a KOTH";
    }

    @Override
    public String getSyntax() {
        return "/koth delete <name>";
    }

    @Override
    public String getPermission() {
        return "koth.delete";
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

