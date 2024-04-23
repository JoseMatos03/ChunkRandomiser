package we.miners.chunkrandomizer.utility;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.Random;

public enum EndChunkBehaviour {
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
}
