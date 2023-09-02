package com.aivruu.addon.thebridge.listener;

import com.aivruu.addon.thebridge.model.ScoreboardManagerModel;
import com.google.common.base.Preconditions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;

public class GameScoreboardListener implements Listener {
	private final ScoreboardManagerModel scoreboardManager;
	
	public GameScoreboardListener(final @NotNull ScoreboardManagerModel scoreboardManager) {
		this.scoreboardManager = Preconditions.checkNotNull(scoreboardManager, "ScoreboardManagerModel object cannot be null.");
	}
	
	@EventHandler (priority = EventPriority.LOW)
	void onGameJoin(final PlayerCommandPreprocessEvent event) {
		// Checks if the event was cancelled.
		if (event.isCancelled()) {
			return;
		}
		final Player player = event.getPlayer();
		// The command executed contains the 'join' argument?
		// Also, check if the player have a scoreboard created (by this plugin).
		if (!event.getMessage().contains("join") || scoreboardManager.findOrNull(player.getUniqueId().toString()) != null) {
			return;
		}
		player.setScoreboard(null);
		scoreboardManager.create(player);
	}
}
