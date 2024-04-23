package we.miners.chunkrandomizer.utility;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;

public enum NetherChunkBehaviour implements ChunkBehaviour {
    CLEAN_CHUNK {},
    // Spawn pigman when player clicks
    SPAWN_WITHER {
        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            player.getWorld().spawnEntity(player.getLocation(), org.bukkit.entity.EntityType.WITHER);
        }
    };

    public static NetherChunkBehaviour getRandomBehaviour(Random random) {
        if (random.nextInt(100) < 90) {
            return CLEAN_CHUNK;
        } else {
            return values()[random.nextInt(values().length - 1) + 1];
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
