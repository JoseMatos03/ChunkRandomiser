package we.miners.chunkrandomizer.listeners;

import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.ChunkLoadEvent;
import we.miners.chunkrandomizer.utility.ChunkBehaviour;

import java.util.Map;
import java.util.Random;

public class ChunkListener implements Listener {

    private Map<Location, ChunkBehaviour> chunkMap;
    private Random random;

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        // Get the chunk location
        Location location = event.getChunk().getBlock(0, 0, 0).getLocation();

        // Check if the chunk has a behaviour
        if (chunkMap.containsKey(location)) {
            // Apply the behaviour
            chunkMap.get(location).apply(event.getChunk());
        }
    }

    public ChunkListener(Map<Location, ChunkBehaviour> chunkMap, Random random) {
        this.chunkMap = chunkMap;
        this.random = random;
    }
}
