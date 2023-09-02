package com.aivruu.addon.thebridge.impl;

import com.aivruu.addon.thebridge.ScoreboardAddonPlugin;
import com.aivruu.addon.thebridge.enums.Result;
import com.aivruu.addon.thebridge.model.ScoreboardManagerModel;
import com.aivruu.addon.thebridge.model.config.ConfModel;
import com.aivruu.addon.thebridge.task.FrameUpdateTask;
import com.aivruu.addon.thebridge.task.TitleUpdateTask;
import com.aivruu.addon.thebridge.utils.PlaceholderUtils;
import com.google.common.base.Preconditions;
import eu.mip.alandioda.bridge.spigot.TheBridge;
import eu.mip.alandioda.bridge.spigot.game.Game;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ScoreboardManagerModelImpl implements ScoreboardManagerModel {
	private final ScoreboardAddonPlugin plugin;
	private final ConfModel config;
	private final Map<String, FastBoard> cache;
	
	private BukkitTask titleTask;
	private BukkitTask frameTask;
	
	public ScoreboardManagerModelImpl(final @NotNull ScoreboardAddonPlugin plugin, final @NotNull ConfModel config) {
		this.plugin = Preconditions.checkNotNull(plugin, "ScoreboardAddonPlugin reference cannot be null.");
		this.config = Preconditions.checkNotNull(config, "ConfModel object cannot be null.");
		cache = new HashMap<>();
	}
	
	@Override
	public @Nullable FastBoard findOrNull(final @NotNull String id) {
		return cache.get(id);
	}
	
	@Override
	public @NotNull Collection<FastBoard> boards() {
		return cache.values();
	}
	
	@Override
	public void start() {
		// Checks if the title animation is enabled.
		if (config.enableAnimation()) {
			final byte animationUpdateRate = config.animationRate();
			titleTask = new TitleUpdateTask(this, config.animationContent(), animationUpdateRate)
				.runTaskTimerAsynchronously(plugin, 0L, animationUpdateRate);
		}
		frameTask = new FrameUpdateTask(TheBridge.getPlugin(TheBridge.class), this)
			.runTaskTimerAsynchronously(plugin, 0L, config.contentRate());
	}
	
	@Override
	public void create(final @NotNull Player player) {
		cache.put(player.getUniqueId().toString(), new FastBoard(player));
	}
	
	@Override
	public @NotNull Result remove(final @NotNull String id) {
		FastBoard board = cache.remove(id);
		// Checks if the FastBoard object for that id isn't in cache.
		if (board == null) {
			return Result.NO_SCOREBOARD_REMOVE;
		}
		// Checks if the board wasn't deleted yet.
		if (!board.isDeleted()) {
			board.delete();
		}
		board = null;
		return Result.SUCCESS;
	}
	
	@Override
	public void update(final @NotNull FastBoard board, final @NotNull Game game) {
		final Player player = board.getPlayer();
		switch (game.state) {
			case Waiting:
				board.updateLines(PlaceholderUtils.parse(player, config.scoreboardWaitingFormat()));
				return;
			case Starting:
				board.updateLines(PlaceholderUtils.parse(player, config.scoreboardStartingFormat()));
				return;
			case Playing:
				board.updateLines(PlaceholderUtils.parse(player, config.scoreboardPlayingFormat()));
				return;
			case Ending:
				remove(player.getUniqueId().toString());
			}
	}
	
	@Override
	public void stop() {
		// Checks if the title task is cancelled.
		if (titleTask != null) {
			titleTask.cancel();
			titleTask = null;
		}
		// Checks if the frame task has been cancelled.
		if (frameTask != null) {
			frameTask.cancel();
			frameTask = null;
		}
		cache.clear();
	}
}
