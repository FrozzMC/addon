package com.aivruu.addon.thebridge.task;

import com.aivruu.addon.thebridge.utils.PlaceholderUtils;
import com.google.common.base.Preconditions;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TitleUpdateTask extends BukkitRunnable {
	private final FastBoard board;
	private final List<String> content;
	private final int titleSize;
	
	private int rate;
	
	public TitleUpdateTask(final @NotNull FastBoard board, final @NotNull List<String> content, final int rate) {
		this.board = Preconditions.checkNotNull(board, "CachedBoardModel object cannot be null.");
		this.content = Preconditions.checkNotNull(content, "Text list cannot be null.");
		this.rate = rate;
		titleSize = content.size();
	}
	
	@Override
	public void run() {
		// Checks if the update-rate is higher or equal than the collection size.
		if (rate >= (titleSize - 1)) {
			rate = 0;
		}
		
		rate++;
		
		// The board has been deleted?
		if (!board.isDeleted()) {
			board.updateTitle(PlaceholderUtils.parse(board.getPlayer(), content.get(rate)));
		} else {
			cancel();
		}
	}
}
