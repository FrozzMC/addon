package com.aivruu.addon.thebridge;

import net.byteflux.libby.BukkitLibraryManager;
import net.byteflux.libby.Library;
import org.jetbrains.annotations.NotNull;

public class LibraryHandler {
	private static final BukkitLibraryManager MANAGER = new BukkitLibraryManager(ScoreboardAddonPlugin.get(), "libs");
	private static final Library.Builder BUILDER = Library.builder();
	
	/**
	 * Add a custom repository for search by possible dependencies.
	 *
	 * @param url The repository url.
	 */
	public static void addCustomRepo(final @NotNull String url) {
		MANAGER.addRepository(url);
	}

   /**
	 * Add the JitPack repository for dependencies search.
	 */   
	public static void addJitPack() {
		MANAGER.addJitPack();
	}

	/** 
	 * Add the maven central repository for dependencies search.
	 */
	public static void addMavenCentral() {
		MANAGER.addMavenCentral();
	}
	
	/**
	 * Load a library from the Maven central repository.
	 *
	 * @param format The library to load.
	 */
	public static void loadLibrary(final @NotNull String format) {
		final String[] parts = format.split(":", 3);
		
		final Library lib = BUILDER.groupId(parts[0])
			.artifactId(parts[1])
			.version(parts[2])
			.id(String.format("%s-lib", parts[1]))
			.build();
	
		MANAGER.loadLibrary(lib);
	}
	
	/**
	 * Loads all the libraries with the format given in the array.
	 *
	 * @param formats The libraries to load.
	 */
	public static void loadLibraries(final String @NotNull... formats) {
		for (final String format : formats) {
			loadLibrary(format);
		}
	}
}
