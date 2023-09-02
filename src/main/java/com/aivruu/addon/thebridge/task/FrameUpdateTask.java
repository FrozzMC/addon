package com.aivruu.addon.thebridge.task;

import com.aivruu.addon.thebridge.model.ScoreboardManagerModel;
import com.google.common.base.Preconditions;
import eu.mip.alandioda.bridge.spigot.TheBridge;
import eu.mip.alandioda.bridge.spigot.game.Game;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class FrameUpdateTask extends BukkitRunnable {
	private final TheBridge bridgePlugin;
	private final ScoreboardManagerModel scoreboardManagerModel;
	
	public FrameUpdateTask(final @NotNull TheBridge bridgePlugin, final @NotNull ScoreboardManagerModel scoreboardManagerModel) {
		this.bridgePlugin = Preconditions.checkNotNull(bridgePlugin, "TheBridge plugin reference cannot be null.");
		this.scoreboardManagerModel = Preconditions.checkNotNull(scoreboardManagerModel, "ScoreboardManagerModel reference cannot be null.");
	}
	
	@Override
	public void run() {
		for (final FastBoard board : scoreboardManagerModel.boards()) {
			// Checks if the player has disabled their scoreboard.
			if (board.isDeleted()) {
				continue;
			}
			Game game = bridgePlugin.GetGameByPlayer(board.getPlayer());
			// Checks if the player is playing.
			if (game == null) {
				continue;
			}
			scoreboardManagerModel.update(board, game);
			game = null;
		}
	}
}
