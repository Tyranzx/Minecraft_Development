package net.stellarcraft.kitpvp;

import net.stellarcraft.kitpvp.animations.PlayerAnimations;
import net.stellarcraft.kitpvp.api.StellarAPI;
import net.stellarcraft.kitpvp.combat.CombatLog;
import net.stellarcraft.kitpvp.management.KitsManager;
import net.stellarcraft.kitpvp.management.player.ForcedRespawn;
import net.stellarcraft.kitpvp.management.player.PlayerManager;
import net.stellarcraft.kitpvp.gangs.GangsManager;
import net.stellarcraft.kitpvp.commands.gangs.DefaultGangCommands;
import net.stellarcraft.kitpvp.commands.gangs.PersonalGangCommands;
import net.stellarcraft.kitpvp.holograms.Leaderboards;
import net.stellarcraft.kitpvp.task.*;
import net.stellarcraft.kitpvp.management.player.PrivateChests;
import net.stellarcraft.kitpvp.menus.Kits;
import net.stellarcraft.kitpvp.objects.ActionBarLegacy;
import net.stellarcraft.kitpvp.objects.HeaderFooterLegacy;
import net.stellarcraft.kitpvp.objects.TitleLegacy;
import net.stellarcraft.kitpvp.scoreboard.PlayerBoard;
import net.stellarcraft.kitpvp.commands.AdvancedCommands;
import net.stellarcraft.kitpvp.commands.DefaultCommands;
import net.stellarcraft.kitpvp.commands.PersonalCommands;
import net.stellarcraft.kitpvp.events.EventListener;
import net.stellarcraft.kitpvp.events.advanced.ChronosListener;
import net.stellarcraft.kitpvp.providers.DataProvider;
import net.stellarcraft.kitpvp.scoreboard.PlayerBoardManager;
import net.stellarcraft.kitpvp.tags.TagsManager;
import net.stellarcraft.kitpvp.utilities.StellarSource;
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
    private SpawnTask spawnTask;
    private GangsTask gangsTask;
    private HologramsTask hologramsTask;
    private TaglieTask taglieTask;

    public PlayerBoard playerBoard;
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
    public ForcedRespawn forcedRespawn;
    public PlayerVectors playerVectors;

    public static DataProvider settings = DataProvider.getInstance();

    public void initializeK() {

        if (StellarAPI.getVersion() < 8) disableKPVP();

      //--------------------------------------------------------------\\
        /* Management */
      
        settings.setup(this);
        EventListener.registerListeners(this);

        if (getVersion() > 9){
            EventListener.registerListenerExact(this, PlayerAnimations.class); // 1.20 Hit Fix

            playerAnimations = new PlayerAnimations(this);
            playerAnimations.setHitDelay("1.8");

            Bukkit.getScheduler().scheduleSyncDelayedTask(this, () ->  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule doImmediateRespawn"), 2L);
        }
        else {
            titleLegacy = new TitleLegacy(this);
            actionBarLegacy = new ActionBarLegacy(this);
            tablistLegacy = new HeaderFooterLegacy(this);
        }
      
        kits = new Kits(this);
        kitsManager = new KitsManager(this);
        privateChests = new PrivateChests(this);
        
        playerManager = new PlayerManager(this);
        playerBoardManager = new PlayerBoardManager(this);
        
        leaderboards = new Leaderboards(this);
        hologramsTask = new HologramsTask(this);
        spawnTask = new SpawnTask(this);
        gangsTask = new GangsTask(this);

        tagsManager = new TagsManager(this);
        gangsManager = new GangsManager(this);
        spawnManager = new SpawnManager(this);
      
        /* Management */
      //--------------------------------------------------------------\\
        /* Commands */
      
        defaultCommands = new DefaultCommands(this);
        personalComands = new PersonalCommands(this);
        advancedCommands = new AdvancedCommands(this);
      
        /* Commands */
      //--------------------------------------------------------------\\
        /* Addons */
      
        playerVectors = new PlayerVectors(this);
        playerBoard = new PlayerBoard(this);
        forcedRespawn = new ForcedRespawn(this);


        Thread dThread = new Thread(() -> hologramsTask.chekear_hologramas("deaths"));

        Thread kThread = new Thread(() -> hologramsTask.chekear_hologramas("kills"));

        Thread sThread = new Thread(() -> hologramsTask.chekear_hologramas("soldi"));
        
        spawnTask.startdoDaylightCycle();
        spawnTask.startDownFallControl();
        gangsTask.gangs_task();
        
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
