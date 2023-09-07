package us.com.stellarsquad.stellarcraft.addons.config;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import us.com.stellarsquad.stellarcraft.Loader;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

// Should this work XD?

public class ConfigUpdater
{
    public static FileConfiguration update(@NotNull Loader core, @NotNull String resource, @NotNull File toUpdate) {

        Preconditions.checkArgument(toUpdate.exists(), "El archivo para actualizar no existe.");

        FileConfiguration finalfc;

        return toUpdate.exists() 
                ? finalfc = YamlConfiguration.loadConfiguration(toUpdate) 
                : YamlConfiguration.loadConfiguration(new InputStreamReader(core.getResource(resource), StandardCharsets.UTF_8));

    }
}
