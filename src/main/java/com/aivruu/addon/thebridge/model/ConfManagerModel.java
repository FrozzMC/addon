package com.aivruu.addon.thebridge.model;

import com.aivruu.addon.thebridge.model.config.ConfModel;
import org.jetbrains.annotations.NotNull;

public interface ConfManagerModel {
	boolean load();
	
	@NotNull ConfModel config();
}
