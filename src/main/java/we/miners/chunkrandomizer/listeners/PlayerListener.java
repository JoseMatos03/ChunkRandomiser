package we.miners.chunkrandomizer.listeners;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import we.miners.chunkrandomizer.utility.ChunkBehaviour;

import java.util.Map;
import java.util.Random;

public class PlayerListener implements Listener {

    private final Map<Chunk, ChunkBehaviour> chunkMap;
    private final Random random;

    public PlayerListener(Map<Chunk, ChunkBehaviour> chunkMap, Random random) {
        this.chunkMap = chunkMap;
        this.random = random;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        // Get the player location
        Player player = event.getPlayer();
        Chunk fromChunk = event.getFrom().getChunk();
        Chunk toChunk = event.getTo().getChunk();

        if (fromChunk.equals(toChunk)) {
            chunkMap.get(fromChunk).applyOnStand(player);
        } else {
            chunkMap.get(toChunk).applyOnEnter(toChunk, player);
        }
    }
}
