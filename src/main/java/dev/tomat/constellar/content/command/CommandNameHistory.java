package dev.tomat.constellar.content.command;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import dev.tomat.common.utils.HttpUtils;
import dev.tomat.mojang.NameHistoryEntry;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

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
        // note: probably want to use a mojang api lib in the future, or write our own.
        // todo: want to format better and add color to look better

        if (args.length < 1) {
            throw new WrongUsageException("commands.namehistory.usage");
        }

        // very, very slow and not sure why
        (new Thread(() -> {
            // Query users/profiles/minecraft/ to grab the uuid of a username
            String uuidJsonString = HttpUtils.getJson("https://api.mojang.com/users/profiles/minecraft/" + args[0]);
            // Parse json string to json object
            JsonObject uuidJson = new JsonParser().parse(uuidJsonString).getAsJsonObject();
            // Parse json object to final uuid string
            String uuid = uuidJson.get("id").getAsString();

            // Query user/profiles/:uuid/names to grab the name history of a uuid
            String nameHistoryJsonString = HttpUtils.getJson("https://api.mojang.com/user/profiles/" + uuid + "/names");
            // Parse the raw json string to a json array
            JsonArray nameHistoryJson = new JsonParser().parse(nameHistoryJsonString).getAsJsonArray();
            // Parse the json array to a NameHistoryEntry array
            Type type = new TypeToken<ArrayList<NameHistoryEntry>>(){}.getType();
            ArrayList<NameHistoryEntry> nameHistoryEntries = new Gson().fromJson(nameHistoryJson, type);

            // Note: probably a better way to send chat messages...

            // Send the title to chat
            sender.addChatMessage(new ChatComponentTranslation("commands.namehistory.title", args[0]));

            // Loop through name history entries and add them to a string
            for (NameHistoryEntry nameHistory : nameHistoryEntries) {
                if (nameHistory.getChangedToAt() == 0) {
                    sender.addChatMessage(new ChatComponentTranslation("commands.namehistory.firstname", nameHistory.getName()));
                    continue;
                }

                // Generate the date string
                Date date = new Date(nameHistory.getChangedToAt());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                // Send the chat message
                sender.addChatMessage(new ChatComponentTranslation("commands.namehistory.entry", nameHistory.getName(), dateFormat.format(date)));
            }
        })).start();
    }

    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
    {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
    }
}

