package com.aivruu.addon.thebridge.listener;

import com.aivruu.addon.thebridge.model.ScoreboardManagerModel;
import com.google.common.base.Preconditions;
import eu.mip.alandioda.bridge.spigot.event.GameStateChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;

public class GameStateListener implements Listener {
	private final ScoreboardManagerModel scoreboardManager;
	
	public GameStateListener(final @NotNull ScoreboardManagerModel scoreboardManager) {
		this.scoreboardManager = Preconditions.checkNotNull(scoreboardManager, "ScoreboardManagerModel object cannot be null.");
	}
	
	@EventHandler (priority = EventPriority.LOW)
	void onGameJoin(final PlayerCommandPreprocessEvent event) {
		if (event.isCancelled()) return;
		
		Player player = event.getPlayer();
		final String id = player.getUniqueId().toString();
		
		if (event.getMessage().contains("join") && scoreboardManager.findOrNull(id) == null) {
			// Removes old scoreboard.
			player.setScoreboard(null);
			// Creates new scoreboard to player.
			scoreboardManager.create(id);
			return;
		}
		
		player = null;
	}
	
	@EventHandler (priority = EventPriority.LOW)
	void onStateChange(final GameStateChangeEvent event) {
		scoreboardManager.update(event.getGame());
	}
}
