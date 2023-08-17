package com.aivruu.addon.thebridge.model.config;

import org.jetbrains.annotations.NotNull;
import space.arim.dazzleconf.annote.*;
import space.arim.dazzleconf.serialiser.URLValueSerialiser;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

import java.util.List;

@ConfSerialisers(URLValueSerialiser.class)
@ConfHeader({
	"TheBridgeScoreboard | By Qekly",
	"Allows customize fully the scoreboard on the distinct game status."
})
public interface ConfModel {
	@AnnotationBasedSorter.Order(20)
	@ConfKey("config.title")
	@ConfComments({
		"Static title text for un-animated titles.",
		"This title will appears in all scoreboard formats."
	})
	@ConfDefault.DefaultString("&e&lTHEBRIDGE &8(&7%server_online%&8)")
	@NotNull String scoreboardTitle();

	@AnnotationBasedSorter.Order(40)
	@ConfKey("config.scoreboard.waiting")
	@ConfComments("Scoreboard format when game state is 'waiting'.")
	@ConfDefault.DefaultStrings({
		"",
		"&8 | &fMap: &e<map-name>",
		"",
		"&8 | &fPlayers: &e<players>/&e<max-players>",
		"&8 | &fState: &aWaiting",
		"",
		"&e play.velex.es"
	})
	@NotNull List<String> scoreboardWaitingFormat();
	
	@AnnotationBasedSorter.Order(50)
	@ConfKey("config.scoreboard.starting")
	@ConfComments("Scoreboard format when game state is 'starting'.")
	@ConfDefault.DefaultStrings({
		"",
		"   &eThe game will start soon!",
		"",
		"&8 | &fMap: &e<map-name>",
		"",
		"&8 | &fPlayers: &e<players>/&e<max-players>",
		"&8 | &fState: &aStarting",
		"",
		"&e play.velex.es"
	})
	@NotNull List<String> scoreboardStartingFormat();
	
	@AnnotationBasedSorter.Order(60)
	@ConfKey("config.scoreboard.playing")
	@ConfComments("Scoreboard format when game state is 'playing'.")
	@ConfDefault.DefaultStrings({
		"",
		"&8 | &fYour team: <team> &8(&7<team-amount>/<max-team-capacity>&8)",
		"&8 | &fYour kills: &e<player-kills>",
		"&8 | &fYour score: &e<player-score>",
		"",
		"&8 | &fMap: &e<map-name>",
		"",
		"&8 | &fPlayers: &e<players>",
		"&8 | &fState: &aPlaying",
		"",
		"&e play.velex.es"
	})
	@NotNull List<String> scoreboardPlayingFormat();
}
