package me.pvpclub.koth.commands.subcommands;

import me.pvpclub.koth.Koth;
import me.pvpclub.koth.commands.SubCommand;
import me.pvpclub.koth.objects.Selection;
import me.pvpclub.koth.utils.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class CreateSubCommand extends SubCommand {

    private final Koth plugin;

    public CreateSubCommand(Koth plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, String[] args) {
        String name = args[0];

        if (plugin.getKothManager().kothExists(name)) {
            player.sendMessage(MessageUtil.getMessage("koth-already-exists"));
            return;
        }

        Selection selection = plugin.getSelectionManager().getSelection(player);
        if (!selection.isComplete()) {
            player.sendMessage(ChatColor.RED + "You must select both positions first!");
            return;
        }

        plugin.getKothManager().createKoth(name, selection.getPos1(), selection.getPos2());
        player.sendMessage(MessageUtil.getMessage("koth-created")
                .replace("%name%", name));

        plugin.getSelectionManager().clearSelection(player);
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Create a new KOTH";
    }

    @Override
    public String getSyntax() {
        return "/koth create <name>";
    }

    @Override
    public String getPermission() {
        return "koth.create";
    }

    @Override
    public int getMinArgs() {
        return 1;
    }

    @Override
    public List<String> getTabCompletions(Player player, String[] args) {
        return Collections.emptyList();
    }
}

