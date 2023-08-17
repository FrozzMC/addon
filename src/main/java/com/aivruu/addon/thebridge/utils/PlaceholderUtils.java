package com.aivruu.addon.thebridge.utils;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import eu.mip.alandioda.bridge.spigot.TheBridge;
import eu.mip.alandioda.bridge.spigot.game.Game;
import eu.mip.alandioda.bridge.spigot.game.TeamBridge;
import eu.mip.alandioda.bridge.spigot.game.TeamPlayer;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class PlaceholderUtils {
	private static final boolean PLACEHOLDER_API_AVAILABLE = (Bukkit.getServer()
		.getPluginManager()
		.getPlugin("PlaceholderAPI") != null) && Bukkit.getServer()
		.getPluginManager()
		.isPluginEnabled("PlaceholderAPI");
	
	/**
	 * Replaces all the placeholders (from PlaceholderAPI) that there into the list.
	 *
	 * @param player Player for the placeholder replacement.
	 * @param content List with placeholders to replace.
	 *
	 * @return A {@code List} with replaced placeholders, if PlaceholderAPI isn't available, just will be returned<p>
	 * with the colors translated.
	 */
	public static @NotNull List<String> parse(final @NotNull Player player, final @NotNull List<String> content) {
		return content.stream()
			.map(text -> parse(player, text))
			.collect(Collectors.toList());
	}
	
	/**
	 * Replaces all the placeholders (from PlaceholderAPI) that there into the string.
	 *
	 * @param player {@code Player} for the placeholder replacement.
	 * @param content {@code String} with placeholders to replace.
	 *
	 * @return A {@code String} with replace placeholders, if PlaceholderAPI isn't available, just will be returned<p>
	 * with the colors translated.
	 */
	public static @NotNull String parse(final @NotNull Player player, @NotNull String content) {
		// I know, all below is very ugly :9, but I don't know another way to do this, don't hate me pls.
		// - MrBugs
		final Game game = JavaPlugin.getPlugin(TheBridge.class).GetGameByPlayer(player);
		
		// The player is in a game?
		if (game == null) {
			// This server is using PlaceholderAPI?
			if (PLACEHOLDER_API_AVAILABLE) {
				return IridiumColorAPI.process(PlaceholderAPI.setPlaceholders(player, content));
			}
			
			return IridiumColorAPI.process(content);
		}
		
		// What?
		for (final TeamBridge bridge : game.teams) {
			content = content.replace("<team-color>", bridge.teamColor.getChatColor());
			content = content.replace("<team-name>", bridge.teamColor.getTeamName());
		}
		
		final TeamPlayer teamPlayer = game.teamPlayers.get(player.getName());
		
		// Check again if the server is using PlaceholderAPI :8
		if (PLACEHOLDER_API_AVAILABLE) {
			return IridiumColorAPI.process(PlaceholderAPI.setPlaceholders(
				player,
				content.replace("<player-score>", Integer.toString(teamPlayer.scores))
					.replace("<player-kills>", Integer.toString(teamPlayer.kills))
					.replace("<map-name>", game.gameName)
					.replace("<team-amount>", Integer.toString(game.GetTeamsCount()))
					.replace("<max-team-players>", Integer.toString(game.maxPlayersPerTeam))
			));
		}
	
		return IridiumColorAPI.process(content.replace("<player-score>", Integer.toString(teamPlayer.scores))
			.replace("<player-kills>", Integer.toString(teamPlayer.kills))
			.replace("<map-name>", game.gameName)
			.replace("<team-amount>", Integer.toString(game.GetTeamsCount()))
			.replace("<max-team-capacity>", Integer.toString(game.maxPlayersPerTeam))
		);
	}
}
