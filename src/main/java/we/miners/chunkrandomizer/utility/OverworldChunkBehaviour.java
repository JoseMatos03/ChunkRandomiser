package we.miners.chunkrandomizer.utility;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import java.util.Random;

public enum OverworldChunkBehaviour implements ChunkBehaviour {
    CLEAN_CHUNK {
        @Override
        public void applyOnLoad(Chunk chunk) {
            for (Entity entity : chunk.getEntities()) {
                if (entity instanceof LivingEntity) {
                    entity.setGravity(true);
                }
            }
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            player.setGravity(true);
            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
        }

        @Override
        public void applyOnStand(Player player) {
        }
    },
    ALTERED_GRAVITY {
        @Override
        public void applyOnLoad(Chunk chunk) {
            Vector negativeGravity = new Vector(0, 1, 0);

            for (Entity entity : chunk.getEntities()) {
                if (entity instanceof LivingEntity) {
                    entity.setGravity(false);
                    entity.setVelocity(entity.getVelocity().add(negativeGravity));

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
    },
    // TODO: fazer
    RANDOM_BLOCK_DROPS {
        @Override
        public void applyOnClick(Player player) {
        }
    },
    FORCE_FIELD {
        @Override
        public void applyOnStand(Player player) {
            player.setVelocity(new Vector(new Random().nextInt(10) - 5, 0, new Random().nextInt(10) - 5));
        }
    },
    TNT_TRAP {
        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    Location location = chunk.getBlock(x, ((int) player.getLocation().getY()), z).getLocation();
                    chunk.getWorld().spawnEntity(location, EntityType.PRIMED_TNT);
                }
            }
        }
    },
    TRIGGER_EXPLOSION {
        @Override
        public void applyOnStand(Player player) {
            player.getWorld().createExplosion(player.getLocation(), 4, true, true);
        }
    },
    LIGHTNING_TRAP {
        @Override
        public void applyOnStand(Player player) {
            player.getWorld().strikeLightning(player.getLocation());
        }
    },
    INCREASED_JUMP_HEIGHT {
        @Override
        public void applyOnStand(Player player) {
            player.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.JUMP, 100, 30));
        }
    },
    CREEPER_NOISE {
        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 1, 1);
        }
    },
    GHAST_SCREAMS {
        @Override
        public void applyOnStand(Player player) {
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
        }
    },
    HOLE_TRAP {
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

            // Add 50/50 chance of creating slime at the bottom
            if (new Random().nextBoolean()) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        chunk.getBlock(x, minHeight, z).setType(org.bukkit.Material.SLIME_BLOCK);
                    }
                }
            }
        }
    },
    // TODO ADICIONAR MAIS COISAS
    JUMP_SCARE_PLAYER {
        @Override
        public void applyOnStand(Player player) {
            player.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.BLINDNESS, 100, 100));
        }
    },
    FALLING_ANVILS {
        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    Location location = new Location(chunk.getWorld(), chunk.getX() * 16 + x, 255, chunk.getZ() * 16 + z);
                    FallingBlock fallingBlock = chunk.getWorld().spawnFallingBlock(location, org.bukkit.Material.ANVIL.createBlockData());
                    fallingBlock.setHurtEntities(true);
                }
            }
        }
    },
    SPAWN_LAVA {
        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    Location location = new Location(chunk.getWorld(), chunk.getX() * 16 + x, 255, chunk.getZ() * 16 + z);
                    location.getBlock().setType(org.bukkit.Material.LAVA);
                }
            }
        }
    },
    SPAWN_RANDOM_HOSTILE_MOBS {
        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    Location location = chunk.getBlock(x, 0, z).getLocation();
                    Location surfaceBlockLocation = chunk.getWorld().getHighestBlockAt(location).getLocation();
                    chunk.getWorld().spawnEntity(surfaceBlockLocation, EntityType.values()[new Random().nextInt(13) + 45]);
                }
            }
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
    MOB_MULTIPLIER {

    },
    REPLACE_BLOCKS {
        @Override
        public void applyOnLoad(Chunk chunk) {
            int minHeight = chunk.getWorld().getMinHeight();
            int maxHeight = chunk.getWorld().getMaxHeight();

            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    for (int y = minHeight; y < maxHeight; y++) {
                        if (chunk.getBlock(x, y, z).getType() != org.bukkit.Material.AIR) {
                            chunk.getBlock(x, y, z).setType(org.bukkit.Material.values()[new Random().nextInt(org.bukkit.Material.values().length)]);
                        }
                    }
                }
            }
        }
    };

    public static OverworldChunkBehaviour getRandomBehaviour(Random random) {
        if (random.nextInt(100) < 80) {
            return CLEAN_CHUNK;
        } else {
            return values()[random.nextInt(values().length)];
        }
    }

    public void applyOnLoad(Chunk chunk) {
    }

    public void applyOnEnter(Chunk chunk, Player player) {
    }

    public void applyOnStand(Player player) {
    }

    public void applyOnClick(Player player) {
    }
}
