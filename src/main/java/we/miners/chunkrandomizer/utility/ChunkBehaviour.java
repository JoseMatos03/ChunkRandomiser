package we.miners.chunkrandomizer.utility;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Random;

public enum ChunkBehaviour {
    CLEAN_CHUNK {
        @Override
        public void applyOnLoad(Chunk chunk) {
            for (Entity entity : chunk.getEntities()) {
                if (entity instanceof LivingEntity) {
                    // Reset all effects
                    ((LivingEntity) entity).setGravity(true);
                }
            }
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            // Reset all effects
            player.setGravity(true);
            player.removePotionEffect(org.bukkit.potion.PotionEffectType.JUMP);
        }

        @Override
        public void applyOnStand(Player player) {
            return;
        }
    },

    ALTERED_GRAVITY {
        @Override
        public void applyOnLoad(Chunk chunk) {
            Vector negativeGravity = new Vector(0, 1, 0);

            for (Entity entity : chunk.getEntities()) {
                if (entity instanceof LivingEntity) {
                    // Disable gravity and apply negative gravity
                    ((LivingEntity) entity).setGravity(false);
                    entity.setVelocity(entity.getVelocity().add(negativeGravity));

                    // Rotate the entity model
                    entity.setRotation(entity.getLocation().getYaw() + 180, entity.getLocation().getPitch());
                }
            }
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            player.setGravity(false);
        }

        @Override
        public void applyOnStand(Player player) {
            Vector negativeGravity = new Vector(0, 0.5, 0);
            player.setVelocity(player.getVelocity().add(negativeGravity));
        }
    },
    RANDOM_TELEPORT {
        @Override
        public void applyOnLoad(Chunk chunk) {
            Random random = new Random();

            for (Entity entity : chunk.getEntities()) {
                if (entity instanceof LivingEntity) {
                    Location location = entity.getLocation();
                    location.setX(location.getX() + (random.nextInt(20)));
                    location.setY(location.getY() + (random.nextInt(20)) - 10);
                    location.setZ(location.getZ() + (random.nextInt(20)));
                    entity.teleport(location);
                }
            }
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            Location location = player.getLocation();
            location.setX(location.getX() + (new Random().nextInt(20)));
            location.setZ(location.getZ() + (new Random().nextInt(20)));
            player.teleport(location);
        }

        @Override
        public void applyOnStand(Player player) {
            return;
        }
    },
    RANDOM_BLOCK_DROPS {
        @Override
        public void applyOnLoad(Chunk chunk) {
            return;
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            return;
        }

        @Override
        public void applyOnStand(Player player) {
            return;
        }
    },
    FORCE_FIELD {
        @Override
        public void applyOnLoad(Chunk chunk) {
            return;
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            return;
        }

        @Override
        public void applyOnStand(Player player) {
            player.setVelocity(new Vector(new Random().nextInt(10) - 5 , 0, new Random().nextInt(10) - 5));
        }
    },
    TNT_FLOOR {
        @Override
        public void applyOnLoad(Chunk chunk) {
            // On load, replace 1 block beneath the surface with TNT
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    Block surfaceBlock = chunk.getWorld().getHighestBlockAt(chunk.getX(), chunk.getZ());
                    Location location = new Location(chunk.getWorld(), surfaceBlock.getX(), surfaceBlock.getY() - 1, surfaceBlock.getZ());
                    location.getBlock().setType(org.bukkit.Material.TNT);
                }
            }
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            Location location = player.getLocation();
            location.setY(location.getY() - 1);
            location.getBlock().setType(org.bukkit.Material.AIR);
            location.getWorld().spawnEntity(location, EntityType.PRIMED_TNT);
        }

        @Override
        public void applyOnStand(Player player) {
            return;
        }
    },
    TRIGGER_EXPLOSION {
        @Override
        public void applyOnLoad(Chunk chunk) {
            return;
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            return;
        }

        @Override
        public void applyOnStand(Player player) {
            player.getWorld().createExplosion(player.getLocation(), 4, true, true);
        }

    },
    THROW_POTIONS_FROM_SKY {
        @Override
        public void applyOnLoad(Chunk chunk) {
            return;
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            return;
        }

        @Override
        public void applyOnStand(Player player) {
            Location location = player.getLocation();
            location.setY(location.getY() + 20);
            location.getWorld().spawnEntity(location, EntityType.SPLASH_POTION);
        }
    },
    JUMP_SCARE {
        @Override
        public void applyOnLoad(Chunk chunk) {
            return;
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            return;
        }

        @Override
        public void applyOnStand(Player player) {
            player.getWorld().strikeLightning(player.getLocation());
        }
    },
    INCREASED_JUMP_HEIGHT {
        @Override
        public void applyOnLoad(Chunk chunk) {
            return;
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            return;
        }

        @Override
        public void applyOnStand(Player player) {
            player.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.JUMP, 100, 30));
        }
    },
    CREEPER_NOISE_JUMP_SCARE {
        @Override
        public void applyOnLoad(Chunk chunk) {
            return;
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 1, 1);
        }

        @Override
        public void applyOnStand(Player player) {
            return;
        }
    },
    GHAST_SCREAMS {
        @Override
        public void applyOnLoad(Chunk chunk) {
            return;
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            return;
        }

        @Override
        public void applyOnStand(Player player) {
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
        }
    },
    HOLE_TRAP {
        @Override
        public void applyOnLoad(Chunk chunk) {
            return;
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            int minHeight = chunk.getWorld().getMinHeight();
            int maxHeight = chunk.getWorld().getMaxHeight();

            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    for (int y = minHeight; y < maxHeight; y++) {
                        chunk.getBlock(x, y, z).setType(org.bukkit.Material.AIR);
                    }
                }
            }
        }

        @Override
        public void applyOnStand(Player player) {
            return;
        }
    },
    BLIND_PLAYER {
        @Override
        public void applyOnLoad(Chunk chunk) {
            return;
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            return;
        }

        @Override
        public void applyOnStand(Player player) {
            player.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.BLINDNESS, 100, 100));
        }
    },
    FALLING_ANVILS {
        @Override
        public void applyOnLoad(Chunk chunk) {
            return;
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    Location location = new Location(chunk.getWorld(), chunk.getX() * 16 + x, 255, chunk.getZ() * 16 + z);
                    location.getWorld().spawnFallingBlock(location, org.bukkit.Material.ANVIL.createBlockData());
                }
            }
        }

        @Override
        public void applyOnStand(Player player) {
            return;
        }
    },
    SPAWN_LAVA {
        @Override
        public void applyOnLoad(Chunk chunk) {
            return;
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    Location location = new Location(chunk.getWorld(), chunk.getX() * 16 + x, 255, chunk.getZ() * 16 + z);
                    location.getBlock().setType(org.bukkit.Material.LAVA);
                }
            }
        }

        @Override
        public void applyOnStand(Player player) {
            return;
        }
    };

    public abstract void applyOnLoad(Chunk chunk);
    public abstract void applyOnEnter(Chunk chunk, Player player);
    public abstract void applyOnStand(Player player);

    public static ChunkBehaviour getRandomBehaviour(Random random) {
        if (random.nextInt(100) < 80) {
            return CLEAN_CHUNK;
        } else {
            return values()[random.nextInt(values().length)];
        }
    }
}
