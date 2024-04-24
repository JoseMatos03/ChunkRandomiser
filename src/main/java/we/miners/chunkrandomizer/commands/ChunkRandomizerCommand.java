package we.miners.chunkrandomizer.commands;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import we.miners.chunkrandomizer.ChunkRandomizer;
import we.miners.chunkrandomizer.utility.ChunkBehaviour;
import we.miners.chunkrandomizer.utility.EndChunkBehaviour;
import we.miners.chunkrandomizer.utility.NetherChunkBehaviour;
import we.miners.chunkrandomizer.utility.OverworldChunkBehaviour;

public class ChunkRandomizerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /chunkrandomizer start");
            return true;
        }

        if (args[0].equalsIgnoreCase("start")) {
            ChunkRandomizer.getInstance().enablePlugin();
            sender.sendMessage("Chunk randomizer enabled!");
            return true;
        }

        if (args[0].equalsIgnoreCase("stop")) {
            ChunkRandomizer.getInstance().disablePlugin();
            sender.sendMessage("Chunk randomizer disabled!");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            ChunkRandomizer.getInstance().disablePlugin();
            ChunkRandomizer.getInstance().enablePlugin();
            sender.sendMessage("Chunk randomizer reloaded!");
            return true;
        }

        if (args[0].equalsIgnoreCase("get")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Chunk chunk = player.getLocation().getChunk();
                if (chunk.getWorld().getName().equals("world")) {
                    ChunkBehaviour behaviour = ChunkRandomizer.getInstance().getOverworldChunkMap().get(chunk);
                    sender.sendMessage("Chunk behaviour: " + behaviour);
                } else if (chunk.getWorld().getName().equals("world_nether")) {
                    ChunkBehaviour behaviour = ChunkRandomizer.getInstance().getNetherChunkMap().get(chunk);
                    sender.sendMessage("Chunk behaviour: " + behaviour);
                } else if (chunk.getWorld().getName().equals("world_the_end")) {
                    ChunkBehaviour behaviour = ChunkRandomizer.getInstance().getEndChunkMap().get(chunk);
                    sender.sendMessage("Chunk behaviour: " + behaviour);
                } else {
                    sender.sendMessage("Invalid world!");
                    return true;
                }
                return true;
            }
            return false;
        }

        if (args[0].equalsIgnoreCase("set")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Chunk chunk = player.getLocation().getChunk();

                if (chunk.getWorld().getName().equals("world")) {
                    ChunkBehaviour behaviour = OverworldChunkBehaviour.valueOf(args[1].toUpperCase());
                    ChunkRandomizer.getInstance().getOverworldChunkMap().put(chunk, behaviour);
                } else if (chunk.getWorld().getName().equals("world_nether")) {
                    ChunkBehaviour behaviour = NetherChunkBehaviour.valueOf(args[1].toUpperCase());
                    ChunkRandomizer.getInstance().getNetherChunkMap().put(chunk, behaviour);
                } else if (chunk.getWorld().getName().equals("world_the_end")) {
                    ChunkBehaviour behaviour = EndChunkBehaviour.valueOf(args[1].toUpperCase());
                    ChunkRandomizer.getInstance().getEndChunkMap().put(chunk, behaviour);
                } else {
                    sender.sendMessage("Invalid world!");
                    return true;
                }

                sender.sendMessage("Chunk behaviour set!");
                return true;
            }
        }

        return false;
    }
}
