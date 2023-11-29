package net.stellarcraft.kitpvp;

import net.stellarcraft.kitpvp.animations.PlayerAnimations;
import net.stellarcraft.kitpvp.combat.CombatLog;
import net.stellarcraft.kitpvp.events.player.management.PlayerManager;
import net.stellarcraft.kitpvp.gangs.GangsManager;
import net.stellarcraft.kitpvp.gangs.commands.DefaultGangCommands;
import net.stellarcraft.kitpvp.gangs.commands.PersonalGangCommands;
import net.stellarcraft.kitpvp.management.SpawnManager;
import net.stellarcraft.kitpvp.menus.Kits;
import net.stellarcraft.kitpvp.objects.ActionBarLegacy;
import net.stellarcraft.kitpvp.objects.TitleLegacy;
import net.stellarcraft.kitpvp.objects.Tablist;
import net.stellarcraft.kitpvp.scoreboard.PlayerBoard;
import net.stellarcraft.kitpvp.api.StellarAPI;
import net.stellarcraft.kitpvp.commands.AdvancedCommands;
import net.stellarcraft.kitpvp.commands.DefaultCommands;
import net.stellarcraft.kitpvp.commands.PersonalCommands;
import net.stellarcraft.kitpvp.events.EventListener;

import net.stellarcraft.kitpvp.events.advanced.KryptonListener;
import net.stellarcraft.kitpvp.menus.Aste;
import net.stellarcraft.kitpvp.providers.DataProvider;
import net.stellarcraft.kitpvp.scoreboard.PlayerBoardManager;
import net.stellarcraft.kitpvp.tags.TagsManager;
import net.stellarcraft.kitpvp.utilities.StellarSource;
import net.stellarcraft.kitpvp.utilities.player.PlayerVectors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

@SuppressWarnings("deprecation")
public final class Loader extends JavaPlugin {

    private Loader core;
    private DefaultCommands defaultCommands;
    private PersonalCommands personalComands;
    private AdvancedCommands advancedCommands;
    private DefaultGangCommands defaultGangCommands;
    private PersonalGangCommands personalGangCommands;
    private SpawnManager spawnManager;
    public PlayerBoard playerBoard;
    public Tablist tablist;
    public PlayerBoardManager playerBoardManager;
    public PlayerManager playerManager;
    public TagsManager tagsManager;
    public GangsManager gangsManager;
    public PlayerAnimations playerAnimations;
    public PlayerVectors playerVectors;
    public TitleLegacy titleLegacy;
    public ActionBarLegacy actionBarLegacy;
    public Kits kits;

    public static DataProvider settings = DataProvider.getInstance();

    public void initializeK() {

        if (StellarAPI.getVersion() < 8) disableKPVP();
      
        settings.setup(this);

        for (Player online : Bukkit.getServer().getOnlinePlayers()){
            PlayerManager.checkforPlayer(online);
        }

      //--------------------------------------------------------------\\
        /* Management */
      
        EventListener.registerListeners(this);
      
        kits = new Kits(this);
        playerManager = new PlayerManager(this);
        playerBoardManager = new PlayerBoardManager(this);
        tagsManager = new TagsManager(this);
        gangsManager = new GangsManager(this);
        spawnManager = new SpawnManager(this);
      
        /* Management */
      //--------------------------------------------------------------\\
        /* Commands */
      
        defaultCommands = new DefaultCommands(this);
        personalComands = new PersonalCommands(this);
        advancedCommands = new AdvancedCommands(this);

        defaultGangCommands = new DefaultGangCommands(this);
        personalGangCommands = new PersonalGangCommands(this);
      
        /* Commands */
      //--------------------------------------------------------------\\
        /* Addons */
      
        playerAnimations = new PlayerAnimations(this);
        playerVectors = new PlayerVectors(this);
      
        tablist = new Tablist(this);
        playerBoard = new PlayerBoard(this);
        titleLegacy = new TitleLegacy(this);
        actionBarLegacy = new ActionBarLegacy(this);

        PlayerAnimations.hitDelay = "1.8.9";
      
        spawnManager.startdoDaylightCycle();
        spawnManager.startDownFallControl();
      
        /* Addons */
      //--------------------------------------------------------------\\
      
        List<Player> online = (List<Player>) Bukkit.getOnlinePlayers();

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, () -> online.forEach(jugador -> {

      //--------------------------------------------------------------\\
      //--------------------------------------------------------------\\
            /*               K R Y P T O N              */
            
      if (StellarChronos.krypton().isPluginEnabled()){
            if (!jugador.hasPermission("stellarsquad.staff")) return;
            jugador.sendMessage(StellarSource.c("&c[&fStellarSquad&c] &fStellar KitPVP enabled."));
        }), 10L);
            /*               K R Y P T O N              */
        
      //--------------------------------------------------------------\\
      //--------------------------------------------------------------\\

        
      if (Bukkit.getServer().getOnlinePlayers().size() == 0) return;
            online.forEach(playerBoard::sendScoreboard);
    }
      
    }

    @Override
    public void onEnable() {
        core = this;
        initializeClasses();
    }

    private void initializeClasses() {
        Bukkit.getConsoleSender().sendMessage(" ");
        StellarSource.printKPVPLogo();
        Bukkit.getConsoleSender().sendMessage(StellarSource.c("&fVersion: &a")+this.getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage(StellarSource.c("&fDesc: &a")+this.getDescription().getDescription());
        Bukkit.getConsoleSender().sendMessage(" ");
    }

    private void disableKPVP(){
        Bukkit.getConsoleSender().sendMessage(StellarSource.c("&c[!] &&eKitPVP &ffatto per le versioni superiori a 1.8"));
        Bukkit.getPluginManager().disablePlugin(this); 
    }
}
