package dev.tomat.constellar.command;

import dev.tomat.constellar.Constellar;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentTranslation;

public class VersionCommand extends CommandBase
{
    public String getCommandName()
    {
        return "version";
    }

    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    public String getCommandUsage(ICommandSender sender)
    {
        return "commands.version.usage";
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
        sender.addChatMessage(new ChatComponentTranslation("commands.version.content", Constellar.ClientNameReadable, Constellar.ClientVersion));
    }
}
