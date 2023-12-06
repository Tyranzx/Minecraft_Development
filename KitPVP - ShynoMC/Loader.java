package net.stellarcraft.kitpvp;

import net.stellarcraft.kitpvp.animations.PlayerAnimations;
import net.stellarcraft.kitpvp.management.KitsManager;
import net.stellarcraft.kitpvp.management.player.PlayerManager;
import net.stellarcraft.kitpvp.gangs.GangsManager;
import net.stellarcraft.kitpvp.gangs.commands.DefaultGangCommands;
import net.stellarcraft.kitpvp.gangs.commands.PersonalGangCommands;
import net.stellarcraft.kitpvp.holograms.Leaderboards;
import net.stellarcraft.kitpvp.management.SpawnManager;
import net.stellarcraft.kitpvp.management.player.PrivateChests;
import net.stellarcraft.kitpvp.menus.Kits;
import net.stellarcraft.kitpvp.objects.ActionBarLegacy;
import net.stellarcraft.kitpvp.objects.HeaderFooterLegacy;
import net.stellarcraft.kitpvp.objects.TitleLegacy;
import net.stellarcraft.kitpvp.runnables.HologramsTask;
import net.stellarcraft.kitpvp.scoreboard.PlayerBoard;
import net.stellarcraft.kitpvp.commands.AdvancedCommands;
import net.stellarcraft.kitpvp.commands.DefaultCommands;
import net.stellarcraft.kitpvp.commands.PersonalCommands;
import net.stellarcraft.kitpvp.events.EventListener;
import net.stellarcraft.kitpvp.events.advanced.KryptonListener;
import net.stellarcraft.kitpvp.providers.DataProvider;
import net.stellarcraft.kitpvp.scoreboard.PlayerBoardManager;
import net.stellarcraft.kitpvp.tags.TagsManager;
import net.stellarcraft.kitpvp.utilities.StellarSource;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import net.stellarcraft.kitpvp.events.player.management.PlayerManager;
import net.stellarcraft.kitpvp.objects.Tablist;
import net.stellarcraft.kitpvp.utilities.player.PlayerVectors;

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
    public HologramsTask hologramsTask;
    public Leaderboards leaderboards;
    public PrivateChests privateChests;
    public PlayerBoardManager playerBoardManager;
    public TagsManager tagsManager;
    public GangsManager gangsManager;
    public PlayerAnimations playerAnimations;
    public TitleLegacy titleLegacy;
    public ActionBarLegacy actionBarLegacy;
    public HeaderFooterLegacy tablistLegacy;
    public Kits kits;
    public KitsManager kitsManager;
    public Tablist tablist;
    public PlayerVectors playerVectors;

    public static DataProvider settings = DataProvider.getInstance();

    public void initializeK() {

        if (StellarAPI.getVersion() < 8) disableKPVP();

      //--------------------------------------------------------------\\
        /* Management */
      
        settings.setup(this);
        EventListener.registerListeners(this);
      
        kits = new Kits(this);
        kitsManager = new KitsManager(this);
        
        playerManager = new PlayerManager(this);
        playerBoardManager = new PlayerBoardManager(this);
        
        leaderboards = new Leaderboards(this);
        hologramsTask = new HologramsTask(this);

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

        PlayerAnimations.playerHit = "1.8.9";
      
        spawnManager.startdoDaylightCycle();
        spawnManager.startDownFallControl();

        Thread dThread = new Thread(() -> hologramsTask.chekear_holograms("deaths"));

        Thread kThread = new Thread(() -> hologramsTask.chekear_holograms("kills"));

        Thread sThread = new Thread(() -> hologramsTask.chekear_holograms("soldi"));

        dThread.start();
        kThread.start();
        sThread.start();
      
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
