package we.miners.chunkrandomizer.listeners;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import we.miners.chunkrandomizer.utility.ChunkBehaviour;
import we.miners.chunkrandomizer.utility.EndChunkBehaviour;
import we.miners.chunkrandomizer.utility.NetherChunkBehaviour;

import java.util.Map;
import java.util.Random;

// TODO: Abstrair
public class ChunkListener implements Listener {

    private final Map<Chunk, ChunkBehaviour> chunkMap;
    private final Map<Chunk, NetherChunkBehaviour> netherChunkMap;
    private final Map<Chunk, EndChunkBehaviour> endChunkMap;

    private final Random random;

    public ChunkListener(Map<Chunk, ChunkBehaviour> chunkMap, Map<Chunk, NetherChunkBehaviour> netherChunkMap, Map<Chunk, EndChunkBehaviour> endChunkMap, Random random) {
        this.chunkMap = chunkMap;
        this.netherChunkMap = netherChunkMap;
        this.endChunkMap = endChunkMap;
        this.random = random;
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        // Get the chunk location
        Chunk chunk = event.getChunk();
        if (chunk.getWorld().getName().equals("world")) {
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
        } else if (chunk.getWorld().getName().equals("world_nether")) {
            // Check if the chunk has a behaviour
            if (netherChunkMap.containsKey(chunk)) {
                // Apply the behaviour
                netherChunkMap.get(chunk).applyOnLoad(chunk);
            } else {
                // Add a new behaviour
                netherChunkMap.put(chunk, NetherChunkBehaviour.getRandomBehaviour(random));

                // Apply the behaviour
                netherChunkMap.get(chunk).applyOnLoad(chunk);
            }
        } else if (chunk.getWorld().getName().equals("world_the_end")) {
            // Check if the chunk has a behaviour
            if (endChunkMap.containsKey(chunk)) {
                // Apply the behaviour
                endChunkMap.get(chunk).applyOnLoad(chunk);
            } else {
                // Add a new behaviour
                endChunkMap.put(chunk, EndChunkBehaviour.CLEAN_CHUNK.getRandomBehaviour(random));

                // Apply the behaviour
                endChunkMap.get(chunk).applyOnLoad(chunk);
            }
        }
    }
}
