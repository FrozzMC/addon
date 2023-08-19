package com.aivruu.addon.thebridge.cmds;

import com.aivruu.addon.thebridge.Constants;
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
		final @NotNull Command cmd,
		final @NotNull String label,
		final @NotNull String[] args
	) {
		if (!(sender instanceof Player)) {
			handleConsoleSender(sender, args);
			return false;
		}
		
		final Player player = (Player) sender;
		
		if (args.length == 0) {
			player.sendMessage(PlaceholderUtils.colorize(String.format(
				"&a&l| TheBridgeAddon &ais running on &a&lBukkit-%s",
				Bukkit.getBukkitVersion()
			)));
			player.sendMessage(PlaceholderUtils.colorize(String.format(
				"&a&l| &aAddon current version: &e%s",
				Constants.VERSION
			)));
			return false;
		}
		
		switch (args[0]) {
			default:
				player.sendMessage(PlaceholderUtils.colorize("&c&l| &cUnknown sub-command specified."));
				break;
			case "help":
				PlaceholderUtils.colorize(Arrays.asList(
					"&a&lTheBridgeAddon &8| &aAdmin Help Commands",
					"&7 - &b/tba help &fShows this message.",
					"&7 - &b/tba reload &fReloads the plugin."
				)).forEach(player::sendMessage);
				break;
			case "reload":
				if (args.length == 1) {
					player.sendMessage(PlaceholderUtils.colorize("&c&l| &cBy do this could happen an error during this process. We recommend restart the server for this."));
					player.sendMessage(PlaceholderUtils.colorize("&c&l| &cIf you want to reload the plugin, type &e/tba reload confirm"));
					break;
				}
				
				if (args[1].equals("confirm")) {
					if (!confManager.wasLoaded()) {
						player.sendMessage(PlaceholderUtils.colorize("&c&l| &cConfiguration could not be loaded correctly."));
						break;
					}
					
					scoreboardManager.reload();
					player.sendMessage(PlaceholderUtils.colorize("&a&l| &aPlugin reloaded correctly!"));
					break;
				}
				
				player.sendMessage(PlaceholderUtils.colorize("&c&l| &cReload confirmation cancelled."));
		}
		
		return false;
	}
	
	private void handleConsoleSender(final @NotNull CommandSender sender, final @NotNull String[] args) {
		if (args.length == 0) {
			sender.sendMessage(PlaceholderUtils.colorize(String.format(
				"&a&l| TheBridgeAddon &ais running on &a&lBukkit-%s",
				Bukkit.getBukkitVersion()
			)));
			sender.sendMessage(PlaceholderUtils.colorize(String.format(
				"&a&l| &aAddon current version: &e%s",
				Constants.VERSION
			)));
			return;
		}
		
		switch (args[0]) {
			default:
				sender.sendMessage(PlaceholderUtils.colorize("&c&l| &cUnknown sub-command specified."));
				return;
			case "help":
				PlaceholderUtils.colorize(Arrays.asList(
					"&a&lTheBridgeAddon &8| &aAdmin Help Commands",
					"&7 - &b/tba help &fShows this message.",
					"&7 - &b/tba reload &fReloads the plugin."
				)).forEach(sender::sendMessage);
				return;
			case "reload":
				if (args.length == 1) {
					sender.sendMessage(PlaceholderUtils.colorize("&c&l| &cBy do this could happen an error during this process. We recommend restart the server for this."));
					sender.sendMessage(PlaceholderUtils.colorize("&c&l| &cIf you want to reload the plugin, type &e/tba reload confirm"));
					return;
				}
				
				if (args[1].equals("confirm")) {
					if (!confManager.wasLoaded()) {
						sender.sendMessage(PlaceholderUtils.colorize("&c&l| &cConfiguration could not be reloaded correctly."));
						return;
					}
					
					// Checks if there's players connected.
					if (Bukkit.getOnlinePlayers().size() >= 1) {
						scoreboardManager.reload();
					}
					
					sender.sendMessage(PlaceholderUtils.colorize("&a&l| &aPlugin reloaded correctly!"));
					return;
				}
				
				sender.sendMessage(PlaceholderUtils.colorize("&c&l| &cReload confirmation cancelled."));
		}
	}
}
