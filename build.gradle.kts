plugins {
	id("java")
	id("com.github.johnrengelman.shadow") version("8.1.1")
	id("net.minecrell.plugin-yml.bukkit") version("0.5.3")
	id("net.kyori.blossom") version("1.2.0")
}

val directory = property("group") as String
val release = property("version") as String

repositories {
	mavenCentral()
	mavenLocal()
	maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
	maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
	maven("https://nexus.iridiumdevelopment.net/repository/maven-releases/")
	maven("https://jitpack.io/")
	maven("https://repo.alessiodp.com/releases")
}

dependencies {
	compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
	compileOnly("me.clip:placeholderapi:2.11.3")
	
	compileOnly("net.byteflux:libby-bukkit:1.2.0")

	compileOnly("space.arim.dazzleconf:dazzleconf-ext-snakeyaml:1.3.0-M2")
	compileOnly("com.iridium:IridiumColorAPI:1.0.6")
	compileOnly("fr.mrmicky:fastboard:2.0.0")

	implementation(files("libs/TheBridge_18+.jar"))
}

tasks {
	shadowJar {
		archiveClassifier.set("")
		archiveFileName.set("scoreboard-addon-v$release.jar")
		
		destinationDirectory.set(file("$rootDir/bin/"))
	}
	
	withType<JavaCompile> {
		options.encoding = "UTF-8"
	}
	
	clean {
		delete("$rootDir/bin/")
	}
}

bukkit {
	name = "TheBridgeAddon"
	main = "com.aivruu.addon.thebridge.ScoreboardAddonPlugin"
	authors = listOf("Qekly")
	
	apiVersion = "1.13"
	version = release
	
	depend = listOf("TheBridge")
	softDepend = listOf("PlaceholderAPI")
	
	commands {
		register("thebridgeaddon") {
			description = "Admin command"
			aliases = listOf("tba")
		}
	}
}

blossom {
	val tokenRoute = "src/main/java/com/aivruu/addon/thebridge/Constants.java"
	replaceToken("{version}", release, tokenRoute)
}