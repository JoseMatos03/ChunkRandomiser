package we.miners.chunkrandomizer.listeners;

import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import we.miners.chunkrandomizer.ChunkRandomizer;
import we.miners.chunkrandomizer.utility.ChunkBehaviour;

import java.util.Map;
import java.util.Random;

public class EntityDamageListener implements Listener {
    private final Map<Chunk, ChunkBehaviour> chunkMap;
    private final Map<Chunk, ChunkBehaviour> netherChunkMap;
    private final Map<Chunk, ChunkBehaviour> endChunkMap;

    private final Random random;

    public EntityDamageListener() {
        this.chunkMap = ChunkRandomizer.getInstance().getOverworldChunkMap();
        this.netherChunkMap = ChunkRandomizer.getInstance().getNetherChunkMap();
        this.endChunkMap = ChunkRandomizer.getInstance().getEndChunkMap();
        this.random = ChunkRandomizer.getInstance().getRandom();
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            Entity entity = event.getEntity();
            Chunk chunk = player.getLocation().getChunk();
            if (chunk.getWorld().getName().equals("world")) {
                handleEntityDamage(chunk, chunkMap, player, entity);
            } else if (chunk.getWorld().getName().equals("world_nether")) {
                handleEntityDamage(chunk, netherChunkMap, player, entity);
            } else if (chunk.getWorld().getName().equals("world_the_end")) {
                handleEntityDamage(chunk, endChunkMap, player, entity);
            }
        }
    }

    private void handleEntityDamage(Chunk chunk, Map<Chunk, ChunkBehaviour> chunkMap, Player player, Entity entity) {
        chunkMap.get(chunk).applyOnHit(player, entity);
    }
}
