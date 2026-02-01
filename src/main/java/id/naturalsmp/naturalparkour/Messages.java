package id.naturalsmp.naturalparkour;

import id.naturalsmp.naturalparkour.utils.ChatUtils;
import id.naturalsmp.naturalparkour.utils.NPLogger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Messages {

	private static Messages instance = null;
	private final Main plugin;
	private final File file;
	private YamlConfiguration msgs;

	private final List<String> noprefix = Arrays.asList(
			"score",
			"placeholders.stats.no-data",
			"placeholders.current.no-data",
			"placeholders.stats.time-format",
			"fall.force.reasons.teleport",
			"fall.force.reasons.afk",
			"gui.selector.title",
			"gui.selector.items.random.title",
			"gui.selector.items.random.lore",
			"gui.selector.items.selected.lore",
			"gui.selector.items.nextpage.name",
			"gui.selector.items.prevpage.name",
			"gui.main.title");

	public Messages(Main pl) {
		this.plugin = pl;
		this.file = new File(pl.getDataFolder(), "messages.yml");
		this.msgs = YamlConfiguration.loadConfiguration(file);
		instance = this;
		setupDefaults();
	}

	public static Messages getInstance() {
		return instance;
	}

	public String get(String key) {
		return get(key, null);
	}

	public String get(String key, Player p) {
		String raw;
		if (msgs.isSet(key)) {
			raw = msgs.getString(key);
			if (!noprefix.contains(key) && !key.equals("prefix")) {
				raw = get("prefix") + raw;
			}
		} else {
			raw = "<red>| <red>Could not find the message '" + key + "'! <red>| ";
		}

		if (plugin.papi && p != null) {
			raw = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(p, raw);
		}

		return ChatUtils.colorize(raw);
	}

	public String color(String text) {
		return ChatUtils.colorize(text);
	}

	public void reload() {
		msgs = YamlConfiguration.loadConfiguration(file);
	}

	private void setupDefaults() {
		Map<String, String> msgDefaults = new LinkedHashMap<>();
		msgDefaults.put("prefix",
				"<gradient:#00FF00:#00AA00><bold>NaturalParkour</bold></gradient> <dark_gray>» <gray>");
		msgDefaults.put("alreadyin", "<red>You are already in parkour!");
		msgDefaults.put("start.score",
				"<green>Started parkour! <gray>Try to beat your high-score of <white>{SCORE}<gray>!");
		msgDefaults.put("start.first", "<green>Started parkour!");
		msgDefaults.put("noperm", "<red>You do not have permission to do that!");
		msgDefaults.put("score", "<gray>Score <white><bold>{SCORE}");
		msgDefaults.put("beatrecord", "<green>Congrats! <gray>You beat your previous score of <white>{SCORE}<gray>!");
		msgDefaults.put("beatrecord-ingame",
				"<green>Congrats! <gray>You just beat your previous highscore! Keep going!");
		msgDefaults.put("disabledworld", "<red>You cannot start parkour from this world!");
		msgDefaults.put("block.potion", "<red>Nope!");
		msgDefaults.put("block.place", "<red>Nope!");
		msgDefaults.put("block.break", "<red>Nope!");
		msgDefaults.put("numberformatexception", "<red>That is not a valid number!");
		msgDefaults.put("areafull", "<red>Could not start parkour because the area is full! <gray>Try again later.");
		msgDefaults.put("must-be-ingame", "<red>You must be in-game to do that!");
		msgDefaults.put("not-in-game", "<red>You must do this command from the console!");
		msgDefaults.put("couldnt-find-player", "<red>Could not find player {PLAYER}");
		msgDefaults.put("list.header", "<blue>Players playing parkour:");
		msgDefaults.put("list.format", "<yellow>{NAME} <gray>- <gold>{SCORE} jumps");
		msgDefaults.put("list.none", "<gray>None");
		msgDefaults.put("nobodys-played-yet", "<red>Nobody has played parkour yet!");
		msgDefaults.put("first-position", "<yellow>First position set!");
		msgDefaults.put("second-position", "<yellow>Second position set!");
		msgDefaults.put("setup-done", "<green>You can now do <dark_green>/npk start<green> to do parkour!");
		msgDefaults.put("reload.usage", "<red>Usage: /{CMD} reload <file><gray> Files: {POSS}");
		msgDefaults.put("fallpos.set", "<green>Fall position set!");
		msgDefaults.put("fallpos.removed", "<green>Fall position removed!");
		msgDefaults.put("top.header", "<blue>Top Scores:");
		msgDefaults.put("top.header-area", "<blue>Top Scores for {AREA}:");
		msgDefaults.put("top.format", "<green>{#}. <yellow>{NAME} <gold>{SCORE} jumps");
		msgDefaults.put("fall.normal", "<red>You fell! <gray>Your score was <white>{SCORE}");
		msgDefaults.put("fall.force.base", "<red>Your parkour game was ended because ");
		msgDefaults.put("fall.force.reasons.teleport", "you teleported");
		msgDefaults.put("fall.force.reasons.afk", "you went AFK.");
		msgDefaults.put("errors.blocknotair.base", "<red><bold>An error occured:<reset><red> The block is not air!");
		msgDefaults.put("errors.blocknotair.player", "<gold>Tell an admin, and try again later.");
		msgDefaults.put("errors.blocknotair.admin", "<gold>Make sure the parkour area is completly air.");
		msgDefaults.put("errors.notsetup.base", "<red>NaturalParkour has not been set up yet.");
		msgDefaults.put("errors.notsetup.player", "<gold>Ask an admin to set it up!");
		msgDefaults.put("errors.notsetup.admin", "<gold>For info on how to set it up, do /{CMD} setup");
		msgDefaults.put("errors.too-many-players",
				"<red>There are too many players in parkour right now. Try again later.");
		msgDefaults.put("migrate.more-args", "<red>Please provide the source to migrate from!");
		msgDefaults.put("migrate.success", "<green>Migrated {COUNT} scores!");
		msgDefaults.put("migrate.error", "<red>Something went wrong. Check console.");

		msgDefaults.put("placeholders.stats.no-data", "---");
		msgDefaults.put("placeholders.current.no-data", "0");
		msgDefaults.put("placeholders.stats.time-format", "{m}m {s}s");

		msgDefaults.put("commands.help.header",
				"<gradient:#00FF00:#00AA00><bold>NaturalParkour</bold></gradient> <gray>by <dark_green>NaturalDev");
		msgDefaults.put("commands.help.start", "<gold> /{CMD} start <gray>- <blue>Start parkour!");

		msgDefaults.put("gui.selector.title", "Block Selector");
		msgDefaults.put("gui.selector.items.random.title", "<white>Random Blocks");
		msgDefaults.put("gui.selector.items.random.lore", "<gray>Will pick a random block.");
		msgDefaults.put("gui.selector.items.selected.lore", "<green>Currently Selected");
		msgDefaults.put("gui.selector.items.nextpage.name", "<green><bold>Next Page");
		msgDefaults.put("gui.selector.items.prevpage.name", "<green><bold>Previous Page");
		msgDefaults.put("gui.main.title", "Parkour Menu");

		msgDefaults.put("items.blockselector.name", "<green>Block Selector");

		boolean modified = false;
		for (String key : msgDefaults.keySet()) {
			if (!msgs.isSet(key)) {
				msgs.set(key, msgDefaults.get(key));
				modified = true;
			}
		}

		if (modified) {
			try {
				msgs.save(file);
			} catch (IOException e) {
				NPLogger.error("Could not save messages file!");
			}
		}
	}
}
