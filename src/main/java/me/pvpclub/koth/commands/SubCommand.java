package me.pvpclub.koth.commands;

import org.bukkit.entity.Player;

import java.util.List;

/**
 * Base class for all KOTH subcommands
 */
public abstract class SubCommand {

    /**
     * Execute the subcommand
     * @param player Player executing the command
     * @param args Command arguments
     */
    public abstract void execute(Player player, String[] args);

    /**
     * @return Subcommand name
     */
    public abstract String getName();

    /**
     * @return Subcommand description
     */
    public abstract String getDescription();

    /**
     * @return Usage syntax
     */
    public abstract String getSyntax();

    /**
     * @return Permission node
     */
    public abstract String getPermission();

    /**
     * @return Tab completions
     */
    public abstract List<String> getTabCompletions(Player player, String[] args);

    /**
     * @return Minimum argument count
     */
    public int getMinArgs() {
        return 0;
    }
}

