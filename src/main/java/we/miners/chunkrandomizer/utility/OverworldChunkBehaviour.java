package we.miners.chunkrandomizer.utility;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import we.miners.chunkrandomizer.ChunkRandomizer;

import java.util.Random;

public enum OverworldChunkBehaviour implements ChunkBehaviour {
    CLEAN_CHUNK {
        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            player.setGravity(true);
            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
            ChunkRandomizer.getInstance().getServer().getScheduler().cancelTasks(ChunkRandomizer.getInstance());
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
    RANDOM_BLOCK_DROPS {
        @Override
        public void applyOnBreak(BlockBreakEvent event, Block block, Player player) {
            event.setDropItems(false);
            block.getWorld().dropItemNaturally(block.getLocation(), new org.bukkit.inventory.ItemStack(org.bukkit.Material.values()[new Random().nextInt(org.bukkit.Material.values().length)]));
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
        public void applyOnEnter(Chunk chunk, Player player) {
            player.getWorld().createExplosion(player.getLocation(), 4, true, true);
        }
    },
    TRIGGER_LIGHTNING {
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

        @Override
        public void applyOnStand(Player player) {
            Location location = player.getLocation();
            location.setY(location.getY() - 1);
            Block block = location.getBlock();
            if (block.getType().equals(org.bukkit.Material.SLIME_BLOCK)) {
                // execute this 3 times every 10 ticks, then stop
                new org.bukkit.scheduler.BukkitRunnable() {
                    int count = 0;

                    @Override
                    public void run() {
                        if (count < 8) {
                            player.setVelocity(new Vector(0, 10, 0));
                            count++;
                        } else {
                            cancel();
                        }
                    }
                }.runTaskTimer(ChunkRandomizer.getInstance(), 0L, 5L);
            }
        }
    },
    JUMP_SCARE_PLAYER {
        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            Location location = player.getLocation().add(player.getLocation().getDirection().normalize().multiply(5));
            player.getWorld().spawnEntity(location, EntityType.WARDEN);

            new org.bukkit.scheduler.BukkitRunnable() {
                final Sound[] sounds = new Sound[]{
                        Sound.ENTITY_ENDERMAN_STARE,
                        Sound.ENTITY_ENDERMAN_SCREAM,
                        Sound.ENTITY_ENDERMAN_TELEPORT,
                        Sound.ENTITY_GHAST_HURT,
                        Sound.ENTITY_GHAST_SHOOT,
                        Sound.ENTITY_CREEPER_PRIMED,
                        Sound.ENTITY_SPIDER_AMBIENT,
                        Sound.ENTITY_SPIDER_STEP,
                        Sound.ENTITY_WITHER_AMBIENT,
                        Sound.ENTITY_ZOMBIE_HURT,
                        Sound.ENTITY_ZOMBIE_INFECT,
                        Sound.ENTITY_ZOMBIE_STEP,
                        Sound.ENTITY_ZOMBIE_VILLAGER_AMBIENT,
                        Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED,
                        Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR,
                        Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR,
                        Sound.ENTITY_BLAZE_SHOOT,
                        Sound.ENTITY_BLAZE_AMBIENT,
                        Sound.ENTITY_DROWNED_AMBIENT_WATER,
                        Sound.ENTITY_GUARDIAN_AMBIENT,
                        Sound.ENTITY_GUARDIAN_ATTACK,
                        Sound.ENTITY_WARDEN_ANGRY,
                        Sound.ENTITY_WARDEN_AGITATED,
                        Sound.ENTITY_WARDEN_EMERGE,
                        Sound.AMBIENT_CAVE,
                };

                @Override
                public void run() {
                    player.getWorld().playSound(player.getLocation(), sounds[new Random().nextInt(sounds.length)], 1, 1);
                }
            }.runTaskTimer(ChunkRandomizer.getInstance(), 0L, 40L); // 40 ticks = 2 seconds
        }

        @Override
        public void applyOnStand(Player player) {
            player.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.BLINDNESS, 100, 3));
            player.addPotionEffect(new org.bukkit.potion.PotionEffect(PotionEffectType.DARKNESS, 100, 1));
            player.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.CONFUSION, 100, 1));
            player.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.SLOW, 100, 3));
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
                    fallingBlock.setDamagePerBlock(10);
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
        @Override
        public void applyOnHit(Player player, Entity entity) {
            if (entity instanceof LivingEntity) {
                Location location = entity.getLocation();
                entity.getWorld().spawnEntity(location, entity.getType());
            }
        }
    },
    LAUNCH_UP {
        @Override
        public void applyOnEnter(Chunk chunk, Player player) {
            player.getWorld().createExplosion(player.getLocation(), 0, true, true);
            player.setVelocity(player.getVelocity().add(new org.bukkit.util.Vector(0, 10, 0)));
        }
    };

    public static OverworldChunkBehaviour getRandomBehaviour(Random random) {
        if (random.nextInt(100) < 80) {
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
