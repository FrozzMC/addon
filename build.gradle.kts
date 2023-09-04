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
  maven("https://jitpack.io/")
	maven("https://repo.alessiodp.com/releases")
}

dependencies {
	compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
	compileOnly("me.clip:placeholderapi:2.11.3")

	compileOnly(files("libs/TheBridge.jar"))

	compileOnly("org.spongepowered:configurate-gson:4.1.2")
	compileOnly("fr.mrmicky:fastboard:2.0.0")
	compileOnly("com.github.VelexNetwork:iridium-color-api:1.2.0")
	
	implementation("net.byteflux:libby-bukkit:1.2.0")
}

tasks {
	shadowJar {
		archiveFileName.set("addon-v$release.jar")
		
		destinationDirectory.set(file("$rootDir/bin/"))
		minimize()

		val libsRoute = "$directory.libs"
		relocate("net.byteflux.libby", "$libsRoute.libby")
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
