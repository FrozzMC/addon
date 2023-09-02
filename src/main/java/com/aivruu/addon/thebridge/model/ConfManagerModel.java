package com.aivruu.addon.thebridge.model;

import com.aivruu.addon.thebridge.enums.Result;
import com.aivruu.addon.thebridge.model.config.ConfModel;
import org.jetbrains.annotations.NotNull;

public interface ConfManagerModel {
	/**
	 * Returns a boolean value if the configuration were loaded correctly<p>,
	 * and return a {@link Result} enum type.
	 *
	 * @return A {@link Result} enum type.
	 */
	@NotNull Result load();
	
	/**
	 * Returns the {@code ConfModel} object for the configuration. If the object is null (configuration not loaded)<p>
	 * will throw an {@code IllegalStateException} exception.
	 *
	 * @return A {@code ConfModel} object.
	 */
	@NotNull ConfModel config();
}
