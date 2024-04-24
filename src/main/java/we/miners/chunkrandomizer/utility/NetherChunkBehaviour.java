package we.miners.chunkrandomizer.utility;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.util.Vector;
import we.miners.chunkrandomizer.ChunkRandomizer;

import java.util.Random;

public enum NetherChunkBehaviour implements ChunkBehaviour {
    CLEAN_CHUNK {
        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            player.setGravity(true);
            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
        }
    },
    BED_TRAP {
        @Override
        public void applyOnBlockPlace(Player player, Block block) {
            block.setType(Material.RED_BED);
            ChunkRandomizer.getInstance().getServer().getScheduler().runTaskLater(ChunkRandomizer.getInstance(), () -> block.getWorld().createExplosion(block.getLocation(), 5, true, true), 2);
        }
    },
    SPAWN_BLAZES {
        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    Location location = chunk.getBlock(x, (int) player.getLocation().getY(), z).getLocation();
                    chunk.getWorld().spawnEntity(location, EntityType.BLAZE);
                }
            }
        }
    },
    GIVE_FIRE_RESISTANCE {
        @Override
        public void applyOnStand(Player player) {
            player.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.FIRE_RESISTANCE, 100, 1));
        }
    },
    SPAWN_MAGMA_CUBES_ON_CLICK {
        @Override
        public void applyOnClick(Player player, Block block) {
            Location location = block.getLocation();
            location.setY(location.getY() + 1);
            block.getWorld().spawnEntity(location, EntityType.MAGMA_CUBE);
        }

        @Override
        public void applyOnHit(Player player, Entity entity) {
            if (entity instanceof MagmaCube) {
                Location location = entity.getLocation();
                entity.getWorld().spawnEntity(location, entity.getType());
            }
        }
    },
    INCREASED_JUMP_HEIGHT {
        @Override
        public void applyOnStand(Player player) {
            player.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.JUMP, 100, 30));
        }
    },
    SPAWN_PIGMEN_ON_CLICK {
        @Override
        public void applyOnClick(Player player, Block block) {
            Location location = block.getLocation();
            location.setY(location.getY() + 1);
            PigZombie pigZombie = (PigZombie) block.getWorld().spawnEntity(location, EntityType.ZOMBIFIED_PIGLIN);
            pigZombie.setTarget(player);
        }

        @Override
        public void applyOnHit(Player player, Entity entity) {
            if (entity instanceof PigZombie) {
                Location location = entity.getLocation();
                PigZombie newPigZombie = (PigZombie) entity.getWorld().spawnEntity(location, entity.getType());
                newPigZombie.setTarget(player);
            }
        }
    },
    SPAWN_PIGS_ON_CLICK {
        @Override
        public void applyOnClick(Player player, Block block) {
            Location location = block.getLocation();
            location.setY(location.getY() + 1);
            block.getWorld().spawnEntity(location, EntityType.PIG);
        }

        @Override
        public void applyOnHit(Player player, Entity entity) {
            if (entity instanceof Pig) {
                Location location = entity.getLocation();
                entity.getWorld().spawnEntity(location, entity.getType());
            }
        }
    },
    SPAWN_WITHER {
        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            player.getWorld().spawnEntity(player.getLocation(), org.bukkit.entity.EntityType.WITHER);
        }
    },
    SPAWN_RANDOM_PASSIVE_MOBS {
        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    Location location = chunk.getBlock(x, 0, z).getLocation();
                    Location surfaceBlockLocation = chunk.getWorld().getHighestBlockAt(location).getLocation();
                    chunk.getWorld().spawnEntity(surfaceBlockLocation, EntityType.values()[new Random().nextInt(17) + 65]);
                }
            }
        }
    },
    ALTERED_GRAVITY {
        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            player.setGravity(false);
        }

        @Override
        public void applyOnStand(Player player) {
            Vector negativeGravity = new Vector(0, 0.15, 0);
            player.setVelocity(player.getVelocity().add(negativeGravity));
        }
    },
    RANDOM_TELEPORT {
        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            Location location = player.getLocation();
            location.setX(location.getX() + (new Random().nextInt(20)));
            location.setZ(location.getZ() + (new Random().nextInt(20)));
            location.setY(location.getY() + (new Random().nextInt(10) - 5));
            player.teleport(location);
        }
    },
    LAUNCH_UP {
        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            player.getWorld().createExplosion(player.getLocation(), 0, true, true);
            player.setVelocity(player.getVelocity().add(new org.bukkit.util.Vector(0, 10, 0)));
        }
    };

    public static NetherChunkBehaviour getRandomBehaviour(Random random) {
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
