package we.miners.chunkrandomizer.listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import we.miners.chunkrandomizer.utility.ChunkBehaviour;

import java.util.Map;
import java.util.Random;

public class PlayerListener implements Listener {

    private Map<Location, ChunkBehaviour> chunkMap;
    private Random random;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        // Get the player location
        Location location = event.getPlayer().getLocation();

        // Check if the chunk has a behaviour
        if (chunkMap.containsKey(location)) {
            // Apply the behaviour
            chunkMap.get(location).apply(event.getPlayer());
        }
    }

    public PlayerListener(Map<Location, ChunkBehaviour> chunkMap, Random random) {
        this.chunkMap = chunkMap;
        this.random = random;
    }
}
