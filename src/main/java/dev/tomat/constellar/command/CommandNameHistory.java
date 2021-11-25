package dev.tomat.constellar.command;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.tomat.common.utils.HttpUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

import java.util.List;

public class CommandNameHistory extends CommandBase {
    public String getCommandName()
    {
        return "namehistory";
    }

    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    public String getCommandUsage(ICommandSender sender)
    {
        return "commands.namehistory.usage";
    }

    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        // todo: probably want to use a mojang api lib in the future, or write our own.

        if (args.length < 1) {
            throw new WrongUsageException("commands.namehistory.usage");
        }

        // going to sleep, pushing before stevie
        // smites me. notes for when i wake up:
        // command dies when executed for seemingly no reason

        // the getJson helper method isnt executed: breakpoints inside it arent hit, yet command fails
        String uuidJsonString = HttpUtils.getJson("https://api.mojang.com/users/profiles/minecraft/" + args[1]);
        // any code below this is not executed
        JsonObject uuidJson = new JsonParser().parse(uuidJsonString).getAsJsonObject();
        String uuid = uuidJson.get("id").getAsString();

        String nameHistoryJsonString = HttpUtils.getJson("https://api.mojang.com/user/profiles/" + uuid + "/names");
        JsonArray nameHistoryJson = new JsonParser().parse(nameHistoryJsonString).getAsJsonArray();


        sender.addChatMessage(new ChatComponentText("[debug] first name of " + args[1] + ": " + nameHistoryJson.get(0)));
    }

    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
    {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
    }
}
