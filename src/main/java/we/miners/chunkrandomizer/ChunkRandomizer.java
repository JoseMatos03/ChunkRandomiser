package we.miners.chunkrandomizer;

import org.bukkit.Chunk;
import org.bukkit.plugin.java.JavaPlugin;
import we.miners.chunkrandomizer.commands.ChunkRandomizerCommand;
import we.miners.chunkrandomizer.listeners.ChunkListener;
import we.miners.chunkrandomizer.listeners.PlayerListener;
import we.miners.chunkrandomizer.utility.ChunkBehaviour;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class ChunkRandomizer extends JavaPlugin {

    private final Map<Chunk, ChunkBehaviour> chunkMap = new HashMap<>();
    private final Random random = new Random();

    public static ChunkRandomizer getInstance() {
        return getPlugin(ChunkRandomizer.class);
    }

    @Override
    public void onEnable() {
        getCommand("chunkrandomizer").setExecutor(new ChunkRandomizerCommand());
    }

    public void enablePlugin() {
        Arrays.stream(getServer().getWorld("world").getLoadedChunks()).forEach(chunk -> {
            chunkMap.put(chunk, ChunkBehaviour.getRandomBehaviour(random));
        });

        getServer().getPluginManager().registerEvents(new ChunkListener(chunkMap, random), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(chunkMap, random), this);
    }

    public void disablePlugin() {
        chunkMap.clear();

        getServer().getPluginManager().disablePlugin(this);
    }

    public Map<Chunk, ChunkBehaviour> getChunkMap() {
        return chunkMap;
    }
}
