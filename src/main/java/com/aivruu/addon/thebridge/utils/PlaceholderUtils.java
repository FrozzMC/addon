package com.aivruu.addon.thebridge.utils;

import com.aivruu.iridiumcolorapi.ColorAPI;
import com.aivruu.iridiumcolorapi.model.ColorAPIModel;
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
	private static final ColorAPIModel MODEL = new ColorAPI();
	private static final boolean PLACEHOLDER_API_AVAILABLE = (Bukkit.getServer()
		.getPluginManager()
		.getPlugin("PlaceholderAPI") != null) && Bukkit.getServer()
		.getPluginManager()
		.isPluginEnabled("PlaceholderAPI");
	
	/**
	 * Process all the formats/codes color in the text string.
	 *
	 * @param text A {@code String} object to process.
	 *
	 * @return A text string with the colors processed.
	 */
	public static @NotNull String colorize(final @NotNull String text) {
		return MODEL.process(text);
	}
	
	/**
	 * Process all the formats/codes color in the text list.
	 *
	 * @param text A {@code List} object of {@code String} type.
	 *
	 * @return A text list with the colors processed.
	 */
	public static @NotNull List<String> colorize(final @NotNull List<String> text) {
		return MODEL.process(text);
	}
	
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
    // Check if the server is using PlaceholderAPI.
    // Else, just apply the colors.
		if (PLACEHOLDER_API_AVAILABLE) {
      content = PlaceholderAPI.setPlaceholders(player, content);
		} else {
      content = MODEL.process(content);
    }
    
		// I know, all below is very ugly :9, but I don't know another way to do this, don't hate me pls.
		// - MrBugs
		final Game game = JavaPlugin.getPlugin(TheBridge.class).GetGameByPlayer(player);
		// The player is in a game?
		if (game == null) return content;
    
		// What?
		for (final TeamBridge bridge : game.teams) {
			content = content.replace("<team-color>", bridge.teamColor.getChatColor());
			content = content.replace("<team-name>", bridge.teamColor.getTeamName());
			content = content.replace("<current-points>", Integer.toString(bridge.getScorePoints()));
		}
		
		final TeamPlayer teamPlayer = game.teamPlayers.get(player.getName());
		return MODEL.process(content.replace("<player-score>", Integer.toString(teamPlayer.scores))
			.replace("<player-kills>", Integer.toString(teamPlayer.kills))
			.replace("<map-name>", game.gameName)
			.replace("<team-amount>", Integer.toString(game.GetTeamsCount()))
			.replace("<max-team-capacity>", Integer.toString(game.maxPlayersPerTeam))
			.replace("<players>", Integer.toString(game.teamPlayers.size()))
			.replace("<max-players>", Integer.toString(game.maxPlayersPerTeam * game.teams.size()))
		);
	}
}
