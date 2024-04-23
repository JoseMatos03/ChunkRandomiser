package we.miners.chunkrandomizer.utility;

import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Random;

public enum NetherChunkBehaviour {
    CLEAN_CHUNK {},
    SPAWN_WITHER {
        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
        }
    };

    public static NetherChunkBehaviour getRandomBehaviour(Random random) {
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
}
