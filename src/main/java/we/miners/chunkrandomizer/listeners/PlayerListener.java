package we.miners.chunkrandomizer.listeners;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import we.miners.chunkrandomizer.utility.ChunkBehaviour;

import java.util.Map;
import java.util.Random;

public class PlayerListener implements Listener {

    private Map<Chunk, ChunkBehaviour> chunkMap;
    private Random random;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        // Get the player location
        Chunk chunk = event.getPlayer().getLocation().getChunk();

        // Check if the chunk has a behaviour
        if (chunkMap.containsKey(chunk)) {
            // Apply the behaviour
            chunkMap.get(chunk).apply(event.getPlayer());
        }
    }

    public PlayerListener(Map<Chunk, ChunkBehaviour> chunkMap, Random random) {
        this.chunkMap = chunkMap;
        this.random = random;
    }
}
