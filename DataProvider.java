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
    private DataProvider() { }

    private File langDir;
    private Lang lang;
    
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
    
    private FileConfiguration[] languages;
    
    static DataProvider instance = new DataProvider();

    @NotNull
    public static DataProvider getInstance() { return instance; }

    public void setup(
        @NotNull Loader core
    )
    {
        lang = new Lang();
        langDir = lang.getLangDir();
        
        if (!core.getDataFolder().exists())
        {
            core.getDataFolder().mkdir();
        }
        
        if (!langDir.exists()){
            langDir.mkdir();
        }

        config = core.getConfig();
        core.saveDefaultConfig();

        loadLanguages();
        
        this.cfile = new File(core.getDataFolder(), "config.yml");
        this.lfile = new File(core.getDataFolder(), "locations.yml");
        this.hfile = new File(core.getDataFolder(), "homes.yml");
        this.wfile = new File(core.getDataFolder(), "warps.yml");
        this.jfile = new File(core.getDataFolder(), "jails.yml");
        this.pfile = new File(core.getDataFolder(), "players.yml");
        this.bfile = new File(core.getDataFolder(), "backpacks.yml");
        this.tfile = new File(core.getDataFolder(), "tablist.yml");

        this.espfile = new File(getLangDir().getPath()+"es_messages.yml", "es_messages.yml");
        this.enfile = new File(getLangDir().getPath()+"en_messages.yml", "en_messages.yml");
        this.frfile = new File(getLangDir().getPath()+"fr_messages.yml", "fr_messages.yml");
        this.itfile = new File(getLangDir().getPath()+"it_messages.yml", "it_messages.yml");
        this.prfile = new File(getLangDir().getPath()+"pr_messages.yml", "pr_messages.yml");
        this.rsfile = new File(getLangDir().getPath()+"rs_messages.yml", "rs_messages.yml");

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
        loadLanguages();
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

    private void loadLanguages()
    {
        lang = new Lang();
        languages = new FileConfiguration[]
        {
                esp = YamlConfiguration.loadConfiguration(lang.createFile("esp_messages.yml")),
                en = YamlConfiguration.loadConfiguration(lang.createFile("en_messages.yml")),
                fr = YamlConfiguration.loadConfiguration(lang.createFile("fr_messages.yml")),
                it = YamlConfiguration.loadConfiguration(lang.createFile("it_messages.yml")),
                pr = YamlConfiguration.loadConfiguration(lang.createFile("pr_messages.yml")),
                rs = YamlConfiguration.loadConfiguration(lang.createFile("rs_messages.yml"))
        };
    }

    // getting methods
    
    @NotNull
    public FileConfiguration getMessages() 
    {
            switch (getConfig().getString("lang")){
            case "en":{
                return en;
            }
            case "es": {
                return esp;
            }
            case "fr": {
                return fr;
            }
            case "it": {
                return it;
            }
            case "pr": {
                return pr;
            }
            case "rs": {
                return rs;
            }
            default: {
                Bukkit.getConsoleSender().sendMessage(StellarSource.c("&cThe language has been specified wrongly."));
                return en;
            }
        }
    /*    
     String selectedLang = this.getConfig().getString("lang");
        for (FileConfiguration lang : languages)
        {
            String name = lang.getName().replace("_messages.yml", "");
            if (name.equals(selectedLang))
            {
                return lang;
            }
        }
        Bukkit.getConsoleSender().sendMessage(StellarSource.c("&cThe language has been specified wrongly."));
        return en; */
    }
    
    @NotNull 
    public FileConfiguration getConfig() { return config; }
    @NotNull 
    public FileConfiguration getLocations() { return locations; }
    @NotNull 
    public FileConfiguration getTablist() { return tablist; }
    @NotNull 
    public FileConfiguration getHomes() { return homes; }
    @NotNull 
    public FileConfiguration getPlayers() { return players; }
    @NotNull 
    public FileConfiguration getBackpacks() { return backpacks; }

    @NotNull 
    public ConfigurationSection getJails() { return jails; }
    @NotNull 
    public ConfigurationSection getWarps() { return warps; }
    
    @NotNull
    public File getLangDir(){ return langDir; }
    
    // saving methods
        
    public void saveBackpacks() 
    {
        try {
            backpacks.save(bfile);
        } catch (IOException ex){
            ex.printStackTrace();
        }
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
    
    public void saveTablist()
    {
        try {
            tablist.save(tfile);
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
    
    // reloading methods
    
    public void reloadConfig() { config = YamlConfiguration.loadConfiguration(cfile); }
    public void reloadLocations() { locations = YamlConfiguration.loadConfiguration(lfile); }
    public void reloadHomes() { homes = YamlConfiguration.loadConfiguration(hfile); }
    public void reloadTablist() { tablist = YamlConfiguration.loadConfiguration(tfile); } 
    public void reloadBackpacks()  { backpacks = YamlConfiguration.loadConfiguration(bfile); }
    
    public void reloadMessages() 
    {
        en = YamlConfiguration.loadConfiguration(enfile);
        esp = YamlConfiguration.loadConfiguration(espfile);
        fr = YamlConfiguration.loadConfiguration(frfile);
        it = YamlConfiguration.loadConfiguration(itfile);
        pr = YamlConfiguration.loadConfiguration(prfile);
        rs = YamlConfiguration.loadConfiguration(rsfile);
    }
    
}
