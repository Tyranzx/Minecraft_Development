package me.tyranzx.essentialsz.core.management;

import com.sun.istack.internal.NotNull;
import me.tyranzx.essentialsz.core.Loader;
import me.tyranzx.essentialsz.core.objects.Lang;
import me.tyranzx.essentialsz.core.utils.StellarSource;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigManager
{
    private ConfigManager(
    ) { }

    private File langDir;
    private Lang lang;

    private File cfile;
    private File lfile;
    private File hfile;
    private File wfile;
    private File jfile;
    private File pfile;

    private File enfile;
    private File espfile;

    private FileConfiguration config;
    private FileConfiguration locations;
    private FileConfiguration homes;
    private FileConfiguration warps;
    private FileConfiguration jails;
    private FileConfiguration players;

    private FileConfiguration en;
    private FileConfiguration esp;

    static ConfigManager instance = new ConfigManager();

    public static ConfigManager getInstance() { return instance; }

    public void setup(@NotNull Loader core)
    {
        if (!core.getDataFolder().exists())
        {
            core.getDataFolder().mkdir();
        }

       langDir = lang.getlangDir();

        if (!langDir.exists()){
            langDir.mkdir();
        }

        this.cfile = new File(core.getDataFolder(), "config.yml");
        this.lfile = new File(core.getDataFolder(), "locations.yml");
        this.hfile = new File(core.getDataFolder(), "homes.yml");
        this.wfile = new File(core.getDataFolder(), "warps.yml");
        this.jfile = new File(core.getDataFolder(), "jails.yml");
        this.pfile = new File(core.getDataFolder(), "players.yml");

        this.espfile = lang.createLangFile("es.yml");
        this.enfile = lang.createLangFile("en.yml");

        config = core.getConfig();
        core.saveDefaultConfig();

        if (
                !lfile.exists() ||
                !hfile.exists() ||
                !jfile.exists() ||
                !wfile.exists() ||
                !espfile.exists() ||
                !enfile.exists() ||
                !pfile.exists()        
        )
        {
            try 
            {
                lfile.createNewFile();
                hfile.createNewFile();
                jfile.createNewFile();
                wfile.createNewFile();
                pfile.createNewFile();
                createNewFile(core, "en.yml", lang.createLangFile("en.yml"));
                createNewFile(core, "es.yml", lang.createLangFile("es.yml"));
            } catch
            (
                    IOException ex
            )
            {
                Bukkit.getConsoleSender().sendMessage(StellarSource.c("&cError: &fThere were an error during creating config files:"));
                ex.printStackTrace();
            }
        }

        locations = YamlConfiguration.loadConfiguration(lfile);
        homes = YamlConfiguration.loadConfiguration(hfile);
        warps = YamlConfiguration.loadConfiguration(wfile);
        jails = YamlConfiguration.loadConfiguration(jfile);
        players = YamlConfiguration.loadConfiguration(pfile);

        esp = YamlConfiguration.loadConfiguration(espfile);
        en = YamlConfiguration.loadConfiguration(enfile);

    }
    public FileConfiguration createNewFile
            (
            @NotNull final Loader core, 
            @NotNull String resource,
            @NotNull File out
            ) 
            throws IOException 
    {
        InputStream in = core.getResource(resource);
        if (!out.exists()) 
        {
            out.createNewFile();
        }
        FileConfiguration file = YamlConfiguration.loadConfiguration(out);
        if (in != null)
        {
        InputStreamReader reader = new InputStreamReader(in);
            file.setDefaults(YamlConfiguration.loadConfiguration(reader));
            file.options().copyDefaults(true);
            file.save(out);
        }
        return file;
    }
    public FileConfiguration getConfig()
    {
        return config;
    }

    public FileConfiguration getMessages() 
    {
        if (
                Loader.settings.getConfig().getString("lang").equalsIgnoreCase("en")
        ) {
            return en;
        } else if (
                Loader.settings.getConfig().getString("lang").equalsIgnoreCase("es")
        ) {
            return esp;
        } else
        {
            Bukkit.getConsoleSender().sendMessage(StellarSource.c("&cThe language was specified wrongly in configuration file config.yml!"));
            return null;
        }
    }

    public FileConfiguration getLocations() 
    {
        return locations;
    }

    public FileConfiguration getHomes() 
    {
        return homes;
    }

    public ConfigurationSection getJails() 
    {
        return jails;
    }
    public ConfigurationSection getWarps() 
    {
        return warps;
    }
    public FileConfiguration getPlayers()
    {
        return players;
    }

    public void saveLocations()
    {
        try 
        {
            locations.save(lfile);
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public void saveHomes()
    {
        try 
        {
            homes.save(hfile);
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public void saveWarps()
    {
        try 
        {
            warps.save(wfile);
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public void saveJails()
    {
        try 
        {
            jails.save(jfile);
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public void reloadConfig()
    {
        config = YamlConfiguration.loadConfiguration(cfile);
    }

    public void reloadMessages() 
    {
        en = YamlConfiguration.loadConfiguration(enfile);
        esp = YamlConfiguration.loadConfiguration(espfile);
    }

    public void reloadLocations() 
    {
        locations = YamlConfiguration.loadConfiguration(lfile);
    }

    public void reloadHomes() 
    {
        homes = YamlConfiguration.loadConfiguration(hfile);
    }
}
