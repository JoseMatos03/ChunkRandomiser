package we.miners.chunkrandomizer.utility;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;

public enum EndChunkBehaviour implements ChunkBehaviour {
    CLEAN_CHUNK {
        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
        }

        @Override
        public void applyOnStand(Player player) {
            player.setGliding(true);
        }
    },
    BOOST_UP {
        @Override
        public void applyOnStand(Player player) {
            player.setGliding(true);
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            player.setVelocity(player.getVelocity().add(new org.bukkit.util.Vector(0, 10, 0)));
        }
    },
    BOOST_FRONT {
        @Override
        public void applyOnStand(Player player) {
            player.setGliding(true);
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            player.setVelocity(player.getVelocity().add(player.getLocation().getDirection().normalize().multiply(2)));
        }
    },
    RANDOM_BLOCK_DROPS {
        @Override
        public void applyOnStand(Player player) {
            player.setGliding(true);
        }

        @Override
        public void applyOnBreak(BlockBreakEvent event, Block block, Player player) {
            event.setDropItems(false);
            block.getWorld().dropItemNaturally(block.getLocation(), new org.bukkit.inventory.ItemStack(org.bukkit.Material.values()[new Random().nextInt(org.bukkit.Material.values().length)]));
        }
    };

    public static EndChunkBehaviour getRandomBehaviour(Random random) {
        if (random.nextInt(100) < 90) {
            return CLEAN_CHUNK;
        } else {
            return values()[random.nextInt(values().length - 1) + 1];
        }
    }

    public void applyOnLoad(Chunk chunk) {
    }

    public void applyOnEnter(Chunk chunk, Player player) {
    }

    public void applyOnStand(Player player) {
    }

    public void applyOnClick(Player player, Block block) {
    }

    public void applyOnBlockPlace(Player player, Block block) {
    }

    public void applyOnHit(Player player, Entity entity) {
    }

    public void applyOnBreak(BlockBreakEvent event, Block block, Player player) {
    }

}
