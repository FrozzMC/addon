package com.aivruu.addon.thebridge.model;

import com.aivruu.addon.thebridge.enums.Result;
import eu.mip.alandioda.bridge.spigot.game.Game;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface ScoreboardManagerModel {
	/**
	 * Tries to return the {@link  FastBoard} object for that id. If not exist will return a null value.
	 *
	 * @param id Unique player ID as {@link String}.
	 *
	 * @return The {@link FastBoard} object or null.
	 */
	@Nullable FastBoard findOrNull(final @NotNull String id);
	
	/**
	 * Returns a {@link Collection} object with all the {@link FastBoard} registered references (scoreboards).
	 *
	 * @return A {@link Collection} reference.
	 */
	@NotNull Collection<FastBoard> boards();
	
	/**
	 * Starts the required plugin tasks for the future scoreboards.
	 */
	void start();
	
	/**
	 * Stores the scoreboard for this new player.
	 *
	 * @param player player scoreboard holder.
	 */
	void create(final @NotNull Player player);
	
	/**
	 * Tries to remove the scoreboard to player and return a {@link Result} enum type.
	 *
	 * @param id Unique player ID as {@link String}.
	 *
	 * @return A {@link Result} enum type.
	 */
 	@NotNull Result remove(final @NotNull String id);
	
 	/**
	 * Updates the content for that scoreboard.
	 *
	 * @param board {@link FastBoard} object for the update.
	 * @param game {@link Game} object for that arena.
	 */
	void update(final @NotNull FastBoard board, final @NotNull Game game);
	
 	/**
	 * Cancels the current plugin tasks and remove all the scoreboards from memory cache.
	 */
	void stop();
}
