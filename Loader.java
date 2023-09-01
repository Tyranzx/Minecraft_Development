// My default main class
package us.com.stellarsquad.stellarcraft;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import us.com.stellarsquad.stellarcraft.addons.config.updateConfig;
import us.com.stellarsquad.stellarcraft.addons.HookAPI;
import us.com.stellarsquad.stellarcraft.api.PlaceholderAPI;
import us.com.stellarsquad.stellarcraft.cache.PlayerCache;
import us.com.stellarsquad.stellarcraft.commands.AdvancedCommands;
import us.com.stellarsquad.stellarcraft.commands.DefaultCommands;
import us.com.stellarsquad.stellarcraft.commands.PersonalCommands;
import us.com.stellarsquad.stellarcraft.providers.DataProvider;
import us.com.stellarsquad.stellarcraft.providers.SQLProvider;
import us.com.stellarsquad.stellarcraft.events.EventListener;
import us.com.stellarsquad.stellarcraft.events.player.ArraysListener;
import us.com.stellarsquad.stellarcraft.player.PlayerListener;

import org.jetbrains.annotations.NotNull;

public final class Loader extends JavaPlugin 
{

    private PersonalCommands personalCommands;
    private DefaultCommands defautCommands;
    private AdvancedCommands advancedCommands;
    private PlaceholderAPI phapi;
    private SQLProvider sqlp;
    private updateConfig updateFiles;
    private boolean placeholderAPIEnabled;
    private boolean multiverseEnabled;
    private DataProvider dataprovider;
    private Loader core;

    private final Class<?>[] listeners = { ArraysListener.class, PlayerListener.class, HookAPI.class };
    
    @Override
    public void onEnable() 
    { 
        core = this;
        initializeCore();
    }
    
    private void initializeCore()
    {
         this.dataprovider = DataProvider.getInstance().setup(this);
        
        loggerInfo(StellarSource.c("&7&m----------------------------------"));
        loggerInfo(StellarSource.c("&dStellarCraft - Essentials"));
        loggerInfo(StellarSource.c("&eVersion: &a") + getDescription().getVersion());
        loggerInfo(StellarSource.c("&eDesc: &a") + getDescription().getDescription());
        loggerInfo(StellarSource.c("&eAutor: &aTyranzx"));

        if (getVersion() <= 9) 
        {
            loggerInfo("&eStellarCore es para la versión &61.20.1");
            loggerInfo("&c- StellarAPI 1.20.1 desactivado");
            loggerInfo("&7(Si no se instala en un servidor v1.20.1, varias opciones o funcionalidades serán desactivadas. Esto porque el core se pensó para 1.20.1 pero utilizable para 1.8.8).");
        }
      
        multiverseEnabled = getServer().getPluginManager().isPluginEnabled("Multiverse-Core");
        if (multiverseEnabled) 
        {
            multiverseCore = (MultiverseCore) getServer().getPluginManager().getPlugin("Multiverse-Core");
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) 
        {
            placeholderAPIEnabled = true;
            new PlaceholderAPI(this).register();
        } else {
            placeholderAPIEnabled = false;
            loggerInfo(StellarSource.c("&ePlacerHolderAPI no está activado!"));
        }

        this.registerSQLManager();
        this.registerCommands();
        this.registerAddons();
        this.registerEvents();

        loggerInfo(StellarSource.c("&7&m----------------------------------"));
    }

    
    @NotNull 
    public PlaceholderAPI getPHAPI()
    {
        return phapi;
    }
    
    @NotNull
    public SQLProvider getSQLProvider()
    {
        return sqlp;
    }
    
    @NotNull
    public DataProvider getDataProvider()
    {
        return dataprovider;
    }
    
    private void registerCommands() 
    {

        personalCommands = new PersonalCommands(this);
        defautCommands = new DefaultCommands(this);
        advancedCommands = new AdvancedCommands(this);

    }
    
     private void registerSQLManager() 
    {
        sqlp = new SQLProvider(this).mysqlSetup();
     } 
    
    private void registerAddons()
    {
        updateFiles = new updateConfig(this);
        updateFiles.task();
    }
    
    public void onDisable()
    {
        Backpacks.saveEntryMap();
        PlayerCache pc = new PlayerCache();
        pc.deletePlayerCache(this);
    }
    
    private void registerEvents()
    {
        EventListener.registerListeners(this, listeners);
    }
    
}
