package id.naturalsmp.naturalparkour;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import id.naturalsmp.naturalparkour.game.JumpManager;
import id.naturalsmp.naturalparkour.game.Manager;
import id.naturalsmp.naturalparkour.top.TopManager;
import id.naturalsmp.naturalparkour.utils.config.Config;

import java.util.*;
import java.util.Map.Entry;

public class Main extends JavaPlugin {

	private static Main INSTANCE;

	public static Main getInstance() {
		return INSTANCE;
	}

	public boolean papi = false;
	public Messages msgs;
	public Scores scores;
	public Manager man;

	public AreaStorage areaStorage;

	public Config config = null;

	public Commands cmds;

	public BlockSelector selector;

	public Rewards rewards;

	public Placeholders placeholders;

	@Override
	public void onEnable() {
		INSTANCE = this;

		// BungeeCord ChatColor check removed as it's standard in 1.21+

		ConfigUpdater.updateConfig(this, "config.yml");
		ConfigUpdater.updateConfig(this, "messages.yml");
		ConfigUpdater.updateConfig(this, "blocks.yml");
		ConfigUpdater.updateConfig(this, "jumps.yml");
		ConfigUpdater.updateConfig(this, "rewards.yml");

		config = new Config(this);

		msgs = new Messages(this);
		scores = new Scores(this);

		man = new Manager(this);

		selector = new BlockSelector(this);

		areaStorage = new AreaStorage(this);

		rewards = new Rewards(this);

		TopManager.getInstance(this);

		JumpManager.getInstance(this);

		Bukkit.getScheduler().runTaskLaterAsynchronously(this, () -> {
			areaStorage.getAreas();
			areaStorage.getPortals();
		}, 10);

		getCommand("NaturalParkour").setTabCompleter(new CommandComplete(this));

		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			placeholders = new Placeholders(this);
			placeholders.register();
			this.papi = true;
		}

		getServer().getPluginManager().registerEvents(man, this);
		getServer().getPluginManager().registerEvents(selector, this);
		getServer().getPluginManager().registerEvents(new id.naturalsmp.naturalparkour.gui.GUIListener(this), this);

		cmds = new Commands(this);

		new Metrics(this);

		NPLogger.log("&aNaturalParkour &2v" + this.getPluginMeta().getVersion()
				+ " &aby &2NaturalDev &7has been &aenabled!");
	}

	public Config getAConfig() {
		return this.config;
	}

	public LinkedHashMap<String, Double> sortByValue(HashMap<String, Double> passedMap) {
		List<String> mapKeys = new ArrayList<>(passedMap.keySet());
		List<Double> mapValues = new ArrayList<>(passedMap.values());
		Collections.sort(mapValues);
		// Collections.sort(mapKeys);

		LinkedHashMap<String, Double> sortedMap = new LinkedHashMap<>();

		for (Double val : mapValues) {
			Iterator<String> keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				String key = keyIt.next();
				Double comp1 = passedMap.get(key);

				if (comp1.equals(val)) {
					keyIt.remove();
					sortedMap.put(key, val);
					break;
				}
			}
		}
		LinkedHashMap<String, Double> reverseMap = new LinkedHashMap<>();
		List<Entry<String, Double>> list = new ArrayList<>(sortedMap.entrySet());

		for (int i = list.size() - 1; i >= 0; i--) {
			Entry<String, Double> e = list.get(i);
			reverseMap.put(e.getKey(), e.getValue());
		}
		return reverseMap;
	}

	public LinkedHashMap<Object, Double> sortByValueWithObjectKey(HashMap<Object, Double> passedMap) {
		return sortByValueWithObjectKey(passedMap, true);
	}

	public LinkedHashMap<Object, Double> sortByValueWithObjectKey(HashMap<Object, Double> passedMap, boolean reverse) {
		List<Object> mapKeys = new ArrayList<>(passedMap.keySet());
		List<Double> mapValues = new ArrayList<>(passedMap.values());
		Collections.sort(mapValues);

		LinkedHashMap<Object, Double> sortedMap = new LinkedHashMap<>();

		for (Double val : mapValues) {
			Iterator<Object> keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				Object key = keyIt.next();
				Double comp1 = passedMap.get(key);

				if (comp1.equals(val)) {
					keyIt.remove();
					sortedMap.put(key, val);
					break;
				}
			}
		}
		if (reverse) {
			LinkedHashMap<Object, Double> reverseMap = new LinkedHashMap<>();
			List<Entry<Object, Double>> list = new ArrayList<>(sortedMap.entrySet());

			for (int i = list.size() - 1; i >= 0; i--) {
				Entry<Object, Double> e = list.get(i);
				reverseMap.put(e.getKey(), e.getValue());
			}
			return reverseMap;
		} else {
			return sortedMap;
		}
	}

	@Override
	public void onDisable() {
		man.disable();
		scores.disable();
		NPLogger.log("&cNaturalParkour &4v" + this.getPluginMeta().getVersion()
				+ " &bby &2NaturalDev &7has been &cdisabled!");
	}

	final private List<String> reloadable = new LinkedList<>(
			Arrays.asList("config", "areas", "messages", "blocks", "rewards", "jumps"));

	public List<String> getReloadable() {
		return new ArrayList<>(reloadable);
	}

	public void reload(String key, CommandSender sender) {
		if (sender == null) {
			sender = Bukkit.getConsoleSender();
		}
		switch (key) {
			case "config":
				getAConfig().reload();
				break;
			case "areas":
				areaStorage.reload();
				break;
			case "messages":
				msgs.reload();
				break;
			case "blocks":
				selector.reloadTypes();
				break;
			case "rewards":
				rewards.reload();
				break;
			case "jumps":
				JumpManager.getInstance().reload();
				break;
			default:
				sender.sendMessage("&cCould not find file for " + key + "!");
				return;
		}

		sender.sendMessage(msgs.color("&aReloaded " + key + "!"));
	}

	public static int random(int min, int max) {

		if (min > max) {
			throw new IllegalArgumentException("max must be greater than min: " + min + "-" + max);
		} else if (min == max) {
			return min;
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

}
