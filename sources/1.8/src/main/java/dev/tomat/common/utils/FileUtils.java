package dev.tomat.common.utils;

import net.minecraft.util.Util;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Sys;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class FileUtils {
    // TODO: Use an access transformer to access this ourselves.
    public static void OpenFile(File file, Logger logger) {
        String filePath = file.getAbsolutePath();

        if (Util.getOSType() == Util.EnumOS.OSX)
        {
            try
            {
                logger.info(filePath);
                Runtime.getRuntime().exec(new String[] {"/usr/bin/open", filePath});
                return;
            }
            catch (IOException ioException)
            {
                logger.error("Couldn't open file", ioException);
            }
        }
        else if (Util.getOSType() == Util.EnumOS.WINDOWS)
        {
            try
            {
                logger.info(filePath);
                Runtime.getRuntime().exec(String.format("cmd.exe /C start \"Open file\" \"%s\"", filePath));
                return;
            }
            catch (IOException ioException)
            {
                logger.error("Couldn't open file", ioException);
            }
        }

        boolean failed = false;

        try
        {
            Class<?> desktopClass = Class.forName("java.awt.Desktop");
            Object desktop = desktopClass.getMethod("getDesktop", new Class[0]).invoke(null);
            desktopClass.getMethod("browse", new Class[] {URI.class}).invoke(desktop, file.toURI());
        }
        catch (Throwable throwable)
        {
            logger.error("Couldn't open link", throwable);
            failed = true;
        }

        // last fallback option
        if (failed)
        {
            logger.info("Opening via system class!");
            Sys.openURL("file://" + filePath);
        }
    }
}
