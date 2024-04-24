package we.miners.chunkrandomizer;

import org.bukkit.Chunk;
import org.bukkit.plugin.java.JavaPlugin;
import we.miners.chunkrandomizer.commands.ChunkRandomizerCommand;
import we.miners.chunkrandomizer.listeners.BlockBreakListener;
import we.miners.chunkrandomizer.listeners.ChunkListener;
import we.miners.chunkrandomizer.listeners.EntityDamageListener;
import we.miners.chunkrandomizer.listeners.PlayerListener;
import we.miners.chunkrandomizer.utility.ChunkBehaviour;
import we.miners.chunkrandomizer.utility.EndChunkBehaviour;
import we.miners.chunkrandomizer.utility.NetherChunkBehaviour;
import we.miners.chunkrandomizer.utility.OverworldChunkBehaviour;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class ChunkRandomizer extends JavaPlugin {

    private final Map<Chunk, ChunkBehaviour> overworldChunkMap = new HashMap<>();
    private final Map<Chunk, ChunkBehaviour> netherChunkMap = new HashMap<>();
    private final Map<Chunk, ChunkBehaviour> endChunkMap = new HashMap<>();

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
            overworldChunkMap.put(chunk, OverworldChunkBehaviour.getRandomBehaviour(random));
        });
        Arrays.stream(getServer().getWorld("world_nether").getLoadedChunks()).forEach(chunk -> {
            netherChunkMap.put(chunk, NetherChunkBehaviour.getRandomBehaviour(random));
        });
        Arrays.stream(getServer().getWorld("world_the_end").getLoadedChunks()).forEach(chunk -> {
            endChunkMap.put(chunk, EndChunkBehaviour.getRandomBehaviour(random));
        });

        getServer().getPluginManager().registerEvents(new ChunkListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
    }

    public void disablePlugin() {
        overworldChunkMap.clear();

        getServer().getPluginManager().disablePlugin(this);
    }

    public Map<Chunk, ChunkBehaviour> getOverworldChunkMap() {
        return overworldChunkMap;
    }

    public Map<Chunk, ChunkBehaviour> getNetherChunkMap() {
        return netherChunkMap;
    }

    public Map<Chunk, ChunkBehaviour> getEndChunkMap() {
        return endChunkMap;
    }

    public Random getRandom() {
        return random;
    }
}
