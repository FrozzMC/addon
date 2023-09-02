package com.aivruu.addon.thebridge.cmds;

import com.aivruu.addon.thebridge.Constants;
import com.aivruu.addon.thebridge.enums.Result;
import com.aivruu.addon.thebridge.model.ConfManagerModel;
import com.aivruu.addon.thebridge.model.ScoreboardManagerModel;
import com.aivruu.addon.thebridge.utils.PlaceholderUtils;
import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class MainCommand implements CommandExecutor {
	private final ConfManagerModel confManager;
	private final ScoreboardManagerModel scoreboardManager;
	
	public MainCommand(final @NotNull ConfManagerModel confManager, final @NotNull ScoreboardManagerModel scoreboardManager) {
		this.confManager = Preconditions.checkNotNull(confManager, "ConfManagerModel object cannot be null.");
		this.scoreboardManager = Preconditions.checkNotNull(scoreboardManager, "ScoreboardManagerModel object cannot be null.");
	}
	
	@Override
	public boolean onCommand(
		final @NotNull CommandSender sender,
		final @NotNull Command command,
		final @NotNull String label,
		final @NotNull String[] args
	) {
		if (!(sender instanceof Player)) {
			return false;
		}
		final Player player = (Player) sender;
		// The player has permission for this?
		if (!player.hasPermission(Constants.HANDLE_COMMAND_PERM)) {
			player.sendMessage(PlaceholderUtils.colorize("&c&l| &cYou don't have permission for this!"));
			return false;
		}
		// Checks if the command have arguments.
		if (args.length == 0) {
			PlaceholderUtils.colorize(Arrays.asList(
				"&a&lTheBridgeAddon &8| &aAdmin Help Commands",
				"&7 - &b/tba help &fShows this message.",
				"&7 - &b/tba reload &fReloads the plugin."
			)).forEach(player::sendMessage);
			return false;
		}
		// Checks if the first argument is 'about'.
		// Else, check if the argument is 'reload'.
		if (args[0].equalsIgnoreCase("about")) {
			player.sendMessage(PlaceholderUtils.colorize(String.format("&a&l| TheBridgeAddon &ais running on Bukkit - &a&l%s", Bukkit.getBukkitVersion())));
			player.sendMessage(PlaceholderUtils.colorize(String.format("&a&l| &aAddon current version: &e%s", Constants.VERSION)));
			return false;
		} else if (args[0].equalsIgnoreCase("reload")) {
			sender.sendMessage(PlaceholderUtils.colorize("&c&l| &cBy do this could happen an error during this process. We recommend restart the server for this."));
			// The configuration was reloaded correctly?
			if (confManager.load() == Result.NO_CONFIG_LOAD) {
				sender.sendMessage(PlaceholderUtils.colorize("&c&l| &cConfiguration could not be loaded correctly."));
				return false;
			}
			for (final Player online : Bukkit.getOnlinePlayers()) {
				scoreboardManager.remove(online.getUniqueId().toString());
				scoreboardManager.create(online);
			}
			sender.sendMessage(PlaceholderUtils.colorize("&a&l| &aScoreboards and Configuraton has been reloaded correctly!"));
			return false;
		}
		player.sendMessage(PlaceholderUtils.colorize("&c&l| Unknown sub-command specified!"));
		return false;
	}
}
