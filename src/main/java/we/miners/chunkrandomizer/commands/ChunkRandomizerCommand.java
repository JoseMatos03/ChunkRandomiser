package we.miners.chunkrandomizer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import we.miners.chunkrandomizer.ChunkRandomizer;

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
        } else {
            sender.sendMessage("Usage: /chunkrandomizer start");
        }

        if (args[0].equalsIgnoreCase("stop")) {
            ChunkRandomizer.getInstance().disablePlugin();
            sender.sendMessage("Chunk randomizer disabled!");
        } else {
            sender.sendMessage("Usage: /chunkrandomizer stop");
        }
        return true;
    }
}
