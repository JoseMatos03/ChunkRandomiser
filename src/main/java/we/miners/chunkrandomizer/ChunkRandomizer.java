package we.miners.chunkrandomizer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import we.miners.chunkrandomizer.commands.ChunkRandomizerCommand;
import we.miners.chunkrandomizer.listeners.ChunkListener;
import we.miners.chunkrandomizer.listeners.PlayerListener;
import we.miners.chunkrandomizer.utility.ChunkBehaviour;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class ChunkRandomizer extends JavaPlugin {

    private Map<Location, ChunkBehaviour> chunkMap = new HashMap<>();
    private Random random = new Random();

    @Override
    public void onEnable() {
        getCommand("chunkrandomizer").setExecutor(new ChunkRandomizerCommand());
    }

    public void enablePlugin() {
        generateChunkMap();
        getServer().getPluginManager().registerEvents(new ChunkListener(chunkMap, random), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(chunkMap, random), this);
    }

    private void generateChunkMap() {
        for (int x = -100; x < 100; x++) {
            for (int z = -100; z < 100; z++) {
                Location location = new Location(Bukkit.getWorld("world"), x * 16, 0, z * 16);
                ChunkBehaviour chunkBehaviour = ChunkBehaviour.getRandomBehaviour(random);
                chunkMap.put(location, chunkBehaviour);
            }
        }
    }

    public static ChunkRandomizer getInstance() {
        return getPlugin(ChunkRandomizer.class);
    }

}
