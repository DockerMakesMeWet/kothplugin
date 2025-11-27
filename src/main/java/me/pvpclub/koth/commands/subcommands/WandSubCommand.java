package me.pvpclub.koth.commands.subcommands;

import me.pvpclub.koth.Koth;
import me.pvpclub.koth.commands.SubCommand;
import me.pvpclub.koth.utils.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WandSubCommand extends SubCommand {

    private final Koth plugin;

    public WandSubCommand(Koth plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, String[] args) {
        ItemStack wand = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta meta = wand.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lKOTH Wand"));
        meta.setLore(Arrays.asList(
                ChatColor.GRAY + "Left click to set position 1",
                ChatColor.GRAY + "Right click to set position 2"
        ));
        wand.setItemMeta(meta);

        player.getInventory().addItem(wand);
        player.sendMessage(MessageUtil.getMessage("wand-given"));
    }

    @Override
    public String getName() {
        return "wand";
    }

    @Override
    public String getDescription() {
        return "Get the selection wand";
    }

    @Override
    public String getSyntax() {
        return "/koth wand";
    }

    @Override
    public String getPermission() {
        return "koth.wand";
    }

    @Override
    public List<String> getTabCompletions(Player player, String[] args) {
        return Collections.emptyList();
    }
}

