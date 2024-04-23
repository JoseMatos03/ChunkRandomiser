package we.miners.chunkrandomizer.listeners;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import we.miners.chunkrandomizer.ChunkRandomizer;
import we.miners.chunkrandomizer.utility.ChunkBehaviour;

import java.util.Map;
import java.util.Random;

public class BlockBreakListener implements Listener {
    private final Map<Chunk, ChunkBehaviour> chunkMap;
    private final Map<Chunk, ChunkBehaviour> netherChunkMap;
    private final Map<Chunk, ChunkBehaviour> endChunkMap;

    private final Random random;

    public BlockBreakListener() {
        this.chunkMap = ChunkRandomizer.getInstance().getOverworldChunkMap();
        this.netherChunkMap = ChunkRandomizer.getInstance().getNetherChunkMap();
        this.endChunkMap = ChunkRandomizer.getInstance().getEndChunkMap();
        this.random = ChunkRandomizer.getInstance().getRandom();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Chunk chunk = player.getLocation().getChunk();
        if (chunk.getWorld().getName().equals("world")) {
            handleBlockBreak(chunk, chunkMap, event, block, player);
        } else if (chunk.getWorld().getName().equals("world_nether")) {
            handleBlockBreak(chunk, netherChunkMap, event, block, player);
        } else if (chunk.getWorld().getName().equals("world_the_end")) {
            handleBlockBreak(chunk, endChunkMap, event, block, player);
        }
    }

    private void handleBlockBreak(Chunk chunk, Map<Chunk, ChunkBehaviour> chunkMap, BlockBreakEvent event, Block block, Player player) {
        chunkMap.get(chunk).applyOnBreak(event, block, player);
    }
}
