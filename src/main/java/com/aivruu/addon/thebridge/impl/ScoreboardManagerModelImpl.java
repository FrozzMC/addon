package com.aivruu.addon.thebridge.impl;

import com.aivruu.addon.thebridge.model.ScoreboardManagerModel;
import com.aivruu.addon.thebridge.model.config.ConfModel;
import com.aivruu.addon.thebridge.utils.PlaceholderUtils;
import com.google.common.base.Preconditions;
import eu.mip.alandioda.bridge.spigot.game.Game;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardManagerModelImpl implements ScoreboardManagerModel {
	private final ConfModel config;
	private final BukkitScheduler scheduler;
	private final Map<String, FastBoard> cache;
	
	public ScoreboardManagerModelImpl(final @NotNull ConfModel config, final @NotNull BukkitScheduler scheduler) {
		this.config = Preconditions.checkNotNull(config, "ConfModel object cannot be null.");
		this.scheduler = Preconditions.checkNotNull(scheduler, "BukkitScheduler object cannot be null.");
		cache = new HashMap<>();
	}
	
	@Override
	public @Nullable FastBoard findOrNull(final @NotNull String id) {
		return cache.get(id);
	}
	
	@Override
	public void create(final @NotNull String id) {
	
	}
	
	@Override
	public void remove(final @NotNull String id) {
		FastBoard board = cache.remove(id);
		// Checks if the FastBoard object for that id isn't in cache.
		if (board == null) return;
		
		// Checks if the board wasn't deleted yet.
		if (!board.isDeleted()) {
			board.delete();
		}

		board = null;
	}
	
	@Override
	public void update(final @NotNull Game game) {
		Player player;
		for (final FastBoard board : cache.values()) {
			player = board.getPlayer();
			
			switch (game.state) {
				case Waiting:
					board.updateLines(PlaceholderUtils.parse(player, config.scoreboardWaitingFormat()));
					break;
				case Starting:
					board.updateLines(PlaceholderUtils.parse(player, config.scoreboardStartingFormat()));
					break;
				case Playing:
					board.updateLines(PlaceholderUtils.parse(player, config.scoreboardPlayingFormat()));
					break;
				case Ending:
					for (final String playerName : game.teamPlayers.keySet()) {
						remove(Bukkit.getPlayer(playerName).getUniqueId().toString());
					}
			}
		}
	}
	
	@Override
	public void reload() {
		// Removes all the scoreboards to the players.
		for (Player player : Bukkit.getOnlinePlayers()) {
			remove(player.getUniqueId().toString());
			player = null;
		}
		
		// Creates again the scoreboard to the players.
		for (Player player : Bukkit.getOnlinePlayers()) {
			create(player.getUniqueId().toString());
			player = null;
		}
	}
	
	@Override
	public void clear() {
		for (final String id : cache.keySet()) {
			remove(id);
		}
		
		cache.clear();
	}
}
