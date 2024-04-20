package we.miners.chunkrandomizer.utility;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Random;

public enum ChunkBehaviour {
    ALTERED_GRAVITY {
        @Override
        public void apply(Chunk chunk) {
            Vector negativeGravity = new Vector(0, -0.1, 0);

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
        public void apply(Player player) {
            Vector negativeGravity = new Vector(0, -0.1, 0);

            // Disable gravity and apply negative gravity
            player.setGravity(false);
            player.setVelocity(player.getVelocity().add(negativeGravity));

            // Rotate the player model
            player.setRotation(player.getLocation().getYaw() + 180, player.getLocation().getPitch());
        }
    },
    RANDOM_TELEPORT {
        @Override
        public void apply(Chunk chunk) {
            Random random = new Random();

            for (Entity entity : chunk.getEntities()) {
                if (entity instanceof LivingEntity) {
                    Location location = entity.getLocation();
                    location.setX(location.getX() + (random.nextInt(20)));
                    location.setZ(location.getZ() + (random.nextInt(20)));
                    entity.teleport(location);
                }
            }
        }

        @Override
        public void apply(Player player) {
            Location location = player.getLocation();
            location.setX(location.getX() + (new Random().nextInt(20)));
            location.setZ(location.getZ() + (new Random().nextInt(20)));
            player.teleport(location);
        }
    },
    RANDOM_BLOCK_DROPS {
        @Override
        public void apply(Chunk chunk) {

        }

        @Override
        public void apply(Player player) {

        }
    };

    public abstract void apply(Chunk chunk);
    public abstract void apply(Player player);

    public static ChunkBehaviour getRandomBehaviour(Random random) {
        return values()[random.nextInt(values().length)];
    }
}
