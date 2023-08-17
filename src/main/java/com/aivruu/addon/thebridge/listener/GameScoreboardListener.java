package com.aivruu.addon.thebridge.listener;

import com.aivruu.addon.thebridge.model.ScoreboardManagerModel;
import com.google.common.base.Preconditions;
import eu.mip.alandioda.bridge.spigot.TheBridge;
import eu.mip.alandioda.bridge.spigot.event.GameStateChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;

public class GameScoreboardListener implements Listener {
	private final TheBridge bridgePlugin;
	private final ScoreboardManagerModel scoreboardManager;
	
	public GameScoreboardListener(final @NotNull TheBridge bridgePlugin, final @NotNull ScoreboardManagerModel scoreboardManager) {
		this.bridgePlugin = Preconditions.checkNotNull(bridgePlugin, "TheBridge reference cannot be null.");
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
			scoreboardManager.create(player, bridgePlugin.GetGameByPlayer(player));
			return;
		}
		
		player = null;
	}
	
	@EventHandler (priority = EventPriority.LOW)
	void onStateChange(final GameStateChangeEvent event) {
		scoreboardManager.update(event.getGame());
	}
}
