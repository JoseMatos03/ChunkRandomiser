package we.miners.chunkrandomizer.commands;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import we.miners.chunkrandomizer.ChunkRandomizer;
import we.miners.chunkrandomizer.utility.ChunkBehaviour;

public class ChunkRandomizerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // TODO: Implement command logic
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
                ChunkBehaviour behaviour = ChunkRandomizer.getInstance().getChunkMap().get(chunk);
                sender.sendMessage("Chunk behaviour: " + behaviour);
                return true;
            }
        }

        if (args[0].equalsIgnoreCase("set")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Chunk chunk = player.getLocation().getChunk();
                ChunkBehaviour behaviour = ChunkBehaviour.valueOf(args[1].toUpperCase());
                ChunkRandomizer.getInstance().getChunkMap().put(chunk, behaviour);
                sender.sendMessage("Chunk behaviour set!");
                return true;
            }
        }

        if (args[0].equalsIgnoreCase("list")) {
            ChunkRandomizer.getInstance().getChunkMap().forEach((chunk, behaviour) -> {
                sender.sendMessage("Chunk: " + chunk + ", Behaviour: " + behaviour);
            });
            return true;
        }

        return false;
    }
}
