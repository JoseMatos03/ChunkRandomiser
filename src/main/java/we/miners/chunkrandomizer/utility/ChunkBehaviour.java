package we.miners.chunkrandomizer.utility;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;

public interface ChunkBehaviour {
    static ChunkBehaviour getRandomBehaviour(Random random) {
        return null;
    }

    void applyOnLoad(Chunk chunk);

    void applyOnEnter(Chunk chunk, Player player);

    void applyOnStand(Player player);

    void applyOnClick(Player player, Block block);

    void applyOnBlockPlace(Player player, Block block);

    void applyOnBreak(BlockBreakEvent event, Block block, Player player);

    void applyOnHit(Player player, Entity entity);

}
