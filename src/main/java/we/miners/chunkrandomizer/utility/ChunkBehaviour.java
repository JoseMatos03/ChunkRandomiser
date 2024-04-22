package we.miners.chunkrandomizer.utility;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import java.util.Random;

public enum ChunkBehaviour {
    CLEAN_CHUNK {
        @Override
        public void applyOnLoad(Chunk chunk) {
            for (Entity entity : chunk.getEntities()) {
                if (entity instanceof LivingEntity) {
                    // Reset all effects
                    entity.setGravity(true);
                }
            }
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            player.setGravity(true);
            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
            // stop repeating task
            Bukkit.getScheduler().cancelTasks(Bukkit.getPluginManager().getPlugin("ChunkRandomizer"));
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
                    // Disable gravity and apply negative gravity
                    entity.setGravity(false);
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
        }
    },
    // TODO: fazer
    RANDOM_BLOCK_DROPS {
        @Override
        public void applyOnLoad(Chunk chunk) {
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
        }

        @Override
        public void applyOnStand(Player player) {
        }
    },
    FORCE_FIELD {
        @Override
        public void applyOnLoad(Chunk chunk) {
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
        }

        @Override
        public void applyOnStand(Player player) {
            player.setVelocity(new Vector(new Random().nextInt(10) - 5, 0, new Random().nextInt(10) - 5));
        }
    },
    TNT_TRAP {
        @Override
        public void applyOnLoad(Chunk chunk) {
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    Location location = chunk.getBlock(x, ((int) player.getLocation().getY()), z).getLocation();
                    chunk.getWorld().spawnEntity(location, EntityType.PRIMED_TNT);
                }
            }
        }

        @Override
        public void applyOnStand(Player player) {
        }
    },
    TRIGGER_EXPLOSION {
        @Override
        public void applyOnLoad(Chunk chunk) {
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
        }

        @Override
        public void applyOnStand(Player player) {
            player.getWorld().createExplosion(player.getLocation(), 4, true, true);
        }

    },
    LIGHTNING_TRAP {
        @Override
        public void applyOnLoad(Chunk chunk) {
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
        }

        @Override
        public void applyOnStand(Player player) {
            player.getWorld().strikeLightning(player.getLocation());
        }
    },
    INCREASED_JUMP_HEIGHT {
        @Override
        public void applyOnLoad(Chunk chunk) {
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
        }

        @Override
        public void applyOnStand(Player player) {
            player.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.JUMP, 100, 30));
        }
    },
    CREEPER_NOISE {
        @Override
        public void applyOnLoad(Chunk chunk) {
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 1, 1);
        }

        @Override
        public void applyOnStand(Player player) {
        }
    },
    GHAST_SCREAMS {
        @Override
        public void applyOnLoad(Chunk chunk) {
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
        }

        @Override
        public void applyOnStand(Player player) {
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
        }
    },
    // TODO: 50/50 adicionar slime
    HOLE_TRAP {
        @Override
        public void applyOnLoad(Chunk chunk) {
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
        }
    },
    // TODO ADICIONAR MAIS COISAS
    BLIND_PLAYER {
        @Override
        public void applyOnLoad(Chunk chunk) {
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
        }

        @Override
        public void applyOnStand(Player player) {
            player.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.BLINDNESS, 100, 100));
        }
    },
    // TODO: Fix n dao dano
    FALLING_ANVILS {
        @Override
        public void applyOnLoad(Chunk chunk) {
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
        }
    },
    SPAWN_LAVA {
        @Override
        public void applyOnLoad(Chunk chunk) {
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
        }
    },
    SPAWN_RANDOM_HOSTILE_MOBS {
        @Override
        public void applyOnLoad(Chunk chunk) {
        }

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

        @Override
        public void applyOnStand(Player player) {
        }
    },
    SPAWN_RANDOM_PASSIVE_MOBS {
        @Override
        public void applyOnLoad(Chunk chunk) {
        }

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

        @Override
        public void applyOnStand(Player player) {
        }
    },
    // TODO: SO NO NETHER
    SPAWN_WITHERS {
        @Override
        public void applyOnLoad(Chunk chunk) {
        }

        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            for (int i = 0; i < 4; i++) {
                Location location = player.getLocation();
                Wither wither = (Wither) chunk.getWorld().spawnEntity(location, EntityType.WITHER);
                wither.setAI(true);
            }
        }

        @Override
        public void applyOnStand(Player player) {
        }
    };
    // TODO: TNT sem dano
    // TODO: M1 click spawn piglins
    // TODO: Random blocks

    public static ChunkBehaviour getRandomBehaviour(Random random) {
        if (random.nextInt(100) < 80) {
            return CLEAN_CHUNK;
        } else {
            return values()[random.nextInt(values().length)];
        }
    }

    public abstract void applyOnLoad(Chunk chunk);

    public abstract void applyOnEnter(Chunk chunk, Player player);

    public abstract void applyOnStand(Player player);
}
