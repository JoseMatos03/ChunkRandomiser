package we.miners.chunkrandomizer.utility;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.util.Vector;

import java.util.Random;

public interface ChunkBehaviour {
    public void applyOnLoad(Chunk chunk);

    public void applyOnEnter(Chunk chunk, Player player);

    public void applyOnStand(Player player);

    public void applyOnClick(Player player);

    public void applyOnBreak(BlockBreakEvent event, Block block, Player player);

    public void applyOnHit(Player player, Entity entity);

    public static ChunkBehaviour getRandomBehaviour(Random random){ return null; };
}
