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

public class DataProvider
{
    private DataProvider(
    ) { }

    private final File langDir;
    private final Lang lang;

    private File cfile;
    private File lfile;
    private File hfile;
    private File wfile;
    private File jfile;
    private File pfile;
    private File bfile;
    private File tfile;

    private File enfile;
    private File espfile;

    private FileConfiguration config;
    private FileConfiguration locations;
    private FileConfiguration homes;
    private FileConfiguration warps;
    private FileConfiguration jails;
    private FileConfiguration players;
    private FileConfiguration backpacks;
    private FileConfiguration tablist;
    
    private FileConfiguration en;
    private FileConfiguration esp;
    private FileConfiguration fr;
    private FileConfiguration it;
    private FileConfiguration pr;
    private FileConfiguration rs;
    
    static DataProvider instance = new DataProvider();

    @NotNull
    public static DataProvider getInstance() { return instance; }

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
        this.bfile = new File(core.getDataFolder(), "backpacks.yml");
        this.tfile = new File(core.getDataFolder(), "tablist.yml");
        
        this.espfile = lang.createLangFile("es.yml");
        this.enfile = lang.createLangFile("en.yml");
        this.frfile = lang.createLangFile("fr.yml");
        this.itfile = lang.createLangFile("it.yml");
        this.prpfile = lang.createLangFile("pr.yml");
        this.rsfile = lang.createLangFile("rs.yml");

        config = core.getConfig();
        core.saveDefaultConfig();

        if (
                !lfile.exists() ||
                !hfile.exists() ||
                !jfile.exists() ||
                !wfile.exists() ||
                !espfile.exists() ||
                !enfile.exists() ||
                !pfile.exists() ||
                !bfile.exists() ||
                !tfile.exists()
        )
        {
            try 
            {
                lfile.createNewFile();
                hfile.createNewFile();
                jfile.createNewFile();
                wfile.createNewFile();
                pfile.createNewFile();
                bfile.createNewFile();
                tfile.createNewFile();
                createNewFile(core, "en.yml", lang.createLangFile("en.yml"));
                createNewFile(core, "es.yml", lang.createLangFile("es.yml"));
                createNewFile(core, "fr.yml", lang.createLangFile("fr.yml"));
                createNewFile(core, "it.yml", lang.createLangFile("it.yml"));
                createNewFile(core, "pr.yml", lang.createLangFile("pr.yml"));
                createNewFile(core, "rs.yml", lang.createLangFile("rs.yml"));
            } catch
            (
                    IOException ex
            )
            {
                Bukkit.getConsoleSender().sendMessage(StellarSource.c("&cError: &fThere were an error during creating config files:"));
                ex.printStackTrace();
            }
        }
        
        // OTHER CONFIGURATIONS
        locations = YamlConfiguration.loadConfiguration(lfile);
        homes = YamlConfiguration.loadConfiguration(hfile);
        warps = YamlConfiguration.loadConfiguration(wfile);
        jails = YamlConfiguration.loadConfiguration(jfile);
        players = YamlConfiguration.loadConfiguration(pfile);
        backpacks = YamlConfiguration.loadConfiguration(bfile);
        tablist = YamlConfiguration.loadConfiguration(tfile);

        // LANGUAGES
        esp = YamlConfiguration.loadConfiguration(espfile);
        en = YamlConfiguration.loadConfiguration(enfile);
        fr = YamlConfiguration.loadConfiguration(frfile);
        it = YamlConfiguration.loadConfiguration(itfile);
        pr = YamlConfiguration.loadConfiguration(prfile);
        rs = YamlConfiguration.loadConfiguration(rsfile);

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
    @NotNull
    public FileConfiguration getConfig()
    {
        return config;
    }

    @NotNull
    public FileConfiguration getMessages() 
    {
        switch (this.getConfig().getString("lang")) 
        {
            case "en":
                {
                en = YamlConfiguration.loadConfiguration(lang.createLangFile("en_messages.yml"));
                return en;
            }
            case "es": 
                {
                esp = YamlConfiguration.loadConfiguration(lang.createLangFile("es_messages.yml"));
                return esp;
            }
            case "fr": 
                {
                fr = YamlConfiguration.loadConfiguration(lang.createLangFile("fr_messages.yml"));
                return fr;
            }
            case "it": 
                {
                it = YamlConfiguration.loadConfiguration(lang.createLangFile("it_messages.yml"));
                return it;
            }
            case "pr": 
                {
                pr = YamlConfiguration.loadConfiguration(lang.createLangFile("pr_messages.yml"));
                return pr;
            }
            case "rs": 
                {
                rs = YamlConfiguration.loadConfiguration(lang.createLangFile("rs_messages.yml"));
                return rs;
            }
            default: 
                {
                Bukkit.getConsoleSender().sendMessage(StellarSource.c("&cThe language has been specified wrongly."));
                return en;
            }
        }
    }

    @NotNull
    public FileConfiguration getLocations() 
    {
        return locations;
    }

    @NotNull
    public FileConfiguration getHomes() 
    {
        return homes;
    }

    @NotNull
    public ConfigurationSection getJails() 
    {
        return jails;
    }
    
    @NotNull
    public ConfigurationSection getWarps() 
    {
        return warps;
    }
    
    @NotNull
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
    
    @NotNull
    public FileConfiguration getTablist()
    { 
            return tablist; 
    }
    
    public void saveTablist()
    {
        try {
            tablist.save(tfile);
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void reloadTablist() 
    {
        tablist = YamlConfiguration.loadConfiguration(tfile);
    }
    
    public void saveBackpacks() 
    {
        try {
            backpacks.save(bfile);
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void reloadBackpacks() 
    {
        backpacks = YamlConfiguration.loadConfiguration(bfile);
    }
    
    @NotNull
    public FileConfiguration getBackpacks()
    {
        return backpacks;
    }
    
}
