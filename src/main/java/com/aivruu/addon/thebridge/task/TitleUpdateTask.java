package com.aivruu.addon.thebridge.task;

import com.aivruu.addon.thebridge.model.ScoreboardManagerModel;
import com.aivruu.addon.thebridge.utils.PlaceholderUtils;
import com.google.common.base.Preconditions;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TitleUpdateTask extends BukkitRunnable {
	private final ScoreboardManagerModel scoreboardManagerModel;
	private final List<String> content;
	
	private byte index;
	
	public TitleUpdateTask(final @NotNull ScoreboardManagerModel scoreboardManagerModel, final @NotNull List<String> content, final byte index) {
		this.scoreboardManagerModel = Preconditions.checkNotNull(scoreboardManagerModel, "ScoreboardManagerModel reference cannot be null.");
		this.content = Preconditions.checkNotNull(content, "Text list cannot be null.");
		this.index = index;
	}
	
	@Override
	public void run() {
		final byte size = (byte) content.size();
		// Checks if the update-rate is higher or equal than the collection size.
		if (index >= (size - 1)) {
			index = 0;
		}
		index++;
		for (final FastBoard board : scoreboardManagerModel.boards()) {
			// Checks if the player has disabled their scoreboard.
			if (board.isDeleted()) {
				continue;
			}
			board.updateTitle(PlaceholderUtils.parse(board.getPlayer(), content.get(index)));
		}
	}
}
