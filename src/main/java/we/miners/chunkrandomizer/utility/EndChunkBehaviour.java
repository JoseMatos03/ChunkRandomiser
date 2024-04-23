package we.miners.chunkrandomizer.utility;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;

public enum EndChunkBehaviour implements ChunkBehaviour {
    CLEAN_CHUNK {};

    public static EndChunkBehaviour getRandomBehaviour(Random random) {
        if (random.nextInt(100) < 80) {
            return CLEAN_CHUNK;
        } else {
            return values()[random.nextInt(values().length)];
        }
    }

    public void applyOnLoad(Chunk chunk){
    };

    public void applyOnEnter(Chunk chunk, Player player){
    };

    public void applyOnStand(Player player){
    };

    public void applyOnClick(Player player){
    };

    public void applyOnHit(Player player, Entity entity){
    };

    public void applyOnBreak(BlockBreakEvent event, Block block, Player player) {
    };
}
