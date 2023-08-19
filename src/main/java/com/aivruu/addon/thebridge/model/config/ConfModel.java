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
	@AnnotationBasedSorter.Order(10)
	@ConfKey("config.enable-animation")
	@ConfComments("Do you want to enable the animation for the title?")
	@ConfDefault.DefaultBoolean(true)
	boolean enableAnimation();
	
	@AnnotationBasedSorter.Order(20)
	@ConfKey("config.animation-update-rate")
	@ConfComments({
		"Update-rate for the title animation.",
		"Ticks: 20 = 1 second."
	})
	@ConfDefault.DefaultInteger(7)
	int animationRate();
	
	@AnnotationBasedSorter.Order(30)
	@ConfKey("config.content-update-rate")
	@ConfComments("Update-rate for the scoreboard content (below the title).")
	@ConfDefault.DefaultInteger(20)
	int contentRate();
	
	@AnnotationBasedSorter.Order(40)
	@ConfKey("config.title.static")
	@ConfComments({
		"Static title text for un-animated titles.",
		"This title will appears in all scoreboard formats."
	})
	@ConfDefault.DefaultString("&e&lTHEBRIDGE &8(&7%server_online%&8)")
	@NotNull String scoreboardTitle();
	
	@AnnotationBasedSorter.Order(50)
	@ConfKey("config.title.animation")
	@ConfComments("Content for the animation on animated scoreboards (titles).")
	@ConfDefault.DefaultStrings({
		"&e&lT&f&lHEBRIDGE &8(&7%server_online%&8)",
		"&f&lT&e&lH&f&lEBRIDGE &8(&7%server_online%&8)",
		"&f&lTH&e&lE&f&lBRIDGE &8(&7%server_online%&8)",
		"&f&lTHE&e&lB&f&lRIDGE &8(&7%server_online%&8)",
		"&f&lTHEB&e&lR&f&lIDGE &8(&7%server_online%&8)",
		"&f&lTHEBR&e&lI&f&lDGE &8(&7%server_online%&8)",
		"&f&lTHEBRI&e&lD&f&lGE &8(&7%server_online%&8)",
		"&f&lTHEBRID&e&lG&f&lE &8(&7%server_online%&8)",
		"&f&lTHEBRIDG&e&lE &8(&7%server_online%&8)",
		"&e&lTHEBRIDGE &8(&7%server_online%&8)",
		"&6&lTHEBRIDGE &8(&7%server_online%&8)",
		"&e&lTHEBRIDGE &8(&7%server_online%&8)",
		"&6&lTHEBRIDGE &8(&7%server_online%&8)",
		"&e&lTHEBRIDGE &8(&7%server_online%&8)",
		"&6&lTHEBRIDGE &8(&7%server_online%&8)",
		"&e&lTHEBRIDGE &8(&7%server_online%&8)"
	})
	@NotNull List<String> animationContent();
	
	@AnnotationBasedSorter.Order(60)
	@ConfKey("config.scoreboard.waiting")
	@ConfComments("Scoreboard format when game state is 'waiting'.")
	@ConfDefault.DefaultStrings({
		"",
		"&8 | &fMap: &e<map-name>",
		"",
		"&8 | &fPlayers: &e<players>/<max-players>",
		"&8 | &fState: &aWaiting",
		"",
		"&e play.velex.es"
	})
	@NotNull List<String> scoreboardWaitingFormat();
	
	@AnnotationBasedSorter.Order(70)
	@ConfKey("config.scoreboard.starting")
	@ConfComments("Scoreboard format when game state is 'starting'.")
	@ConfDefault.DefaultStrings({
		"",
		"   &eThe game will start soon!",
		"",
		"&8 | &fMap: &e<map-name>",
		"",
		"&8 | &fPlayers: &e<players>/<max-players>",
		"&8 | &fState: &aStarting",
		"",
		"&e play.velex.es"
	})
	@NotNull List<String> scoreboardStartingFormat();
	
	@AnnotationBasedSorter.Order(80)
	@ConfKey("config.scoreboard.playing")
	@ConfComments("Scoreboard format when game state is 'playing'.")
	@ConfDefault.DefaultStrings({
		"",
		"&8 | &fYour team: <team> &8(&7<current-points>&8)",
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
