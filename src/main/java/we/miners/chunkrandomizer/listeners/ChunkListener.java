package we.miners.chunkrandomizer.listeners;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.ChunkLoadEvent;
import we.miners.chunkrandomizer.utility.ChunkBehaviour;

import java.util.Map;
import java.util.Random;

public class ChunkListener implements Listener {

    private Map<Chunk, ChunkBehaviour> chunkMap;
    private Random random;

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        // Get the chunk location
        Chunk chunk = event.getChunk();

        // Check if the chunk has a behaviour
        if (chunkMap.containsKey(chunk)) {
            // Apply the behaviour
            chunkMap.get(chunk).apply(chunk);
        } else {
            // Add a new behaviour
            chunkMap.put(chunk, ChunkBehaviour.getRandomBehaviour(random));
        }
    }

    public ChunkListener(Map<Chunk, ChunkBehaviour> chunkMap, Random random) {
        this.chunkMap = chunkMap;
        this.random = random;
    }
}
