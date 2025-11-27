package me.pvpclub.koth.commands.subcommands;

import me.pvpclub.koth.Koth;
import me.pvpclub.koth.commands.SubCommand;
import me.pvpclub.koth.guis.RewardGUI;
import me.pvpclub.koth.objects.KothArea;
import me.pvpclub.koth.utils.MessageUtil;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RewardSubCommand extends SubCommand {

    private final Koth plugin;

    public RewardSubCommand(Koth plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args.length < 2 || !args[0].equalsIgnoreCase("add")) {
            player.sendMessage(MessageUtil.getMessage("invalid-usage")
                    .replace("%usage%", getSyntax()));
            return;
        }

        String name = args[1];
        KothArea area = plugin.getKothManager().getKoth(name);

        if (area == null) {
            player.sendMessage(MessageUtil.getMessage("koth-not-found")
                    .replace("%name%", name));
            return;
        }

        new RewardGUI(plugin, area).open(player);
    }

    @Override
    public String getName() {
        return "reward";
    }

    @Override
    public String getDescription() {
        return "Set KOTH rewards";
    }

    @Override
    public String getSyntax() {
        return "/koth reward add <name>";
    }

    @Override
    public String getPermission() {
        return "koth.reward";
    }

    @Override
    public int getMinArgs() {
        return 2;
    }

    @Override
    public List<String> getTabCompletions(Player player, String[] args) {
        if (args.length == 1) {
            return Collections.singletonList("add");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("add")) {
            return plugin.getKothManager().getAllKoths().stream()
                    .map(KothArea::getName)
                    .collect(Collectors.toList());
        }
        return null;
    }
}

