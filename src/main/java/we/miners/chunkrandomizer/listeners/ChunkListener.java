package we.miners.chunkrandomizer.listeners;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import we.miners.chunkrandomizer.ChunkRandomizer;
import we.miners.chunkrandomizer.utility.ChunkBehaviour;

import java.util.Map;
import java.util.Random;

// TODO: Abstrair
public class ChunkListener implements Listener {

    private final Map<Chunk, ChunkBehaviour> chunkMap;
    private final Map<Chunk, ChunkBehaviour> netherChunkMap;
    private final Map<Chunk, ChunkBehaviour> endChunkMap;

    private final Random random;

    public ChunkListener() {
        this.chunkMap = ChunkRandomizer.getInstance().getOverworldChunkMap();
        this.netherChunkMap = ChunkRandomizer.getInstance().getNetherChunkMap();
        this.endChunkMap = ChunkRandomizer.getInstance().getEndChunkMap();
        this.random = ChunkRandomizer.getInstance().getRandom();
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        Chunk chunk = event.getChunk();

        if (chunk.getWorld().getName().equals("world")) {
            handleChunkLoad(chunk, chunkMap);
        } else if (chunk.getWorld().getName().equals("world_nether")) {
            handleChunkLoad(chunk, netherChunkMap);
        } else if (chunk.getWorld().getName().equals("world_the_end")) {
            handleChunkLoad(chunk, endChunkMap);
        }
    }

    private void handleChunkLoad(Chunk chunk, Map<Chunk, ChunkBehaviour> chunkMap) {
        // Check if the chunk has a behaviour
        if (chunkMap.containsKey(chunk)) {
            // Apply the behaviour
            chunkMap.get(chunk).applyOnLoad(chunk);
        } else {
            // Add a new behaviour
            chunkMap.put(chunk, ChunkBehaviour.getRandomBehaviour(random));

            // Apply the behaviour
            chunkMap.get(chunk).applyOnLoad(chunk);
        }
    }
}
