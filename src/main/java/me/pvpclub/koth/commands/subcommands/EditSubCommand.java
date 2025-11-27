package me.pvpclub.koth.commands.subcommands;

import me.pvpclub.koth.Koth;
import me.pvpclub.koth.commands.SubCommand;
import me.pvpclub.koth.guis.EditGUI;
import me.pvpclub.koth.objects.KothArea;
import me.pvpclub.koth.utils.MessageUtil;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class EditSubCommand extends SubCommand {

    private final Koth plugin;

    public EditSubCommand(Koth plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, String[] args) {
        String name = args[0];
        KothArea area = plugin.getKothManager().getKoth(name);

        if (area == null) {
            player.sendMessage(MessageUtil.getMessage("koth-not-found")
                    .replace("%name%", name));
            return;
        }

        new EditGUI(plugin, area).open(player);
    }

    @Override
    public String getName() {
        return "edit";
    }

    @Override
    public String getDescription() {
        return "Edit KOTH settings";
    }

    @Override
    public String getSyntax() {
        return "/koth edit <name>";
    }

    @Override
    public String getPermission() {
        return "koth.edit";
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

