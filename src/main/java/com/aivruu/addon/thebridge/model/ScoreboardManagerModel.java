package com.aivruu.addon.thebridge.model;

import eu.mip.alandioda.bridge.spigot.game.Game;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ScoreboardManagerModel {
	/**
	 * Tries to return the {@code FastBoard} object for that id. If not exist will return a null value.
	 *
	 * @param id {@code UUID} as {@code String} of player.
	 *
	 * @return The {@code FastBoard} object or null.
	 */
	@Nullable FastBoard findOrNull(final @NotNull String id);
	
	/**
	 * Creates a new FastBoard object (Scoreboard) to player.
	 *
	 * @param player {@code Player} object for that player.
	 * @param game {@code Game} object for the game where is the player.
	 */
	void create(final @NotNull Player player, final @NotNull Game game);
	
	/**
	 * Removes the scoreboard to player.
	 *
	 * @param id {@code UUID} as {@code String} of player.
	 */
	void remove(final @NotNull String id);
	
	/**
	 * Updates the content for that scoreboard.
	 *
	 * @param game {@code Game} object for that arena.
	 */
	void update(final @NotNull Game game);
	
	/**
	 * Reload all the scoreboards in cache to forceupdate their contents.
	 */
	void reload();
	
	/**
	 * Removes all the objects in cache.
	 */
	void clear();
}
