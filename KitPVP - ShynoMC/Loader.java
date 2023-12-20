package net.stellarcraft.kitpvp;

import net.luckperms.api.LuckPerms;
import net.stellarcraft.kitpvp.animations.PlayerAnimations;
import net.stellarcraft.kitpvp.api.StellarAPI;
import net.stellarcraft.kitpvp.combat.CombatLog;
import net.stellarcraft.kitpvp.crates.CratesMan;
import net.stellarcraft.kitpvp.management.KitManager;
import net.stellarcraft.kitpvp.management.player.ForcedRespawn;
import net.stellarcraft.kitpvp.management.player.PlayerManager;
import net.stellarcraft.kitpvp.management.GangManager;
import net.stellarcraft.kitpvp.holograms.Leaderboards;
import net.stellarcraft.kitpvp.menus.Aste;
import net.stellarcraft.kitpvp.animations.PlayerBoardAnimation;
import net.stellarcraft.kitpvp.sound.PlayerSounds;
import net.stellarcraft.kitpvp.task.*;
import net.stellarcraft.kitpvp.menus.Kits;
import net.stellarcraft.kitpvp.objects.LegacyActionBar;
import net.stellarcraft.kitpvp.objects.LegacyHeadTail;
import net.stellarcraft.kitpvp.objects.LegacyTitle;
import net.stellarcraft.kitpvp.scoreboard.PlayerBoard;
import net.stellarcraft.kitpvp.commands.AdvancedCommands;
import net.stellarcraft.kitpvp.commands.DefaultCommands;
import net.stellarcraft.kitpvp.commands.PersonalCommands;
import net.stellarcraft.kitpvp.events.EventListener;
import net.stellarcraft.kitpvp.providers.DataProvider;
import net.stellarcraft.kitpvp.scoreboard.PlayerBoardManager;
import net.stellarcraft.kitpvp.management.player.TagsManager;
import net.stellarcraft.kitpvp.utilities.StellarSource;

import net.stellarcraft.kitpvp.utilities.player.PlayerVectors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Loader extends JavaPlugin {

    private Loader instance;
    private DefaultCommands defaultCommands;
    private PersonalCommands personalComands;
    private AdvancedCommands advancedCommands;
    private SpawnTask spawnTask;
    private HologramsTask hologramsTask;
    private TagTask tagTask;
    private BoardTask boardTask;
    private Aste aste;

    public PlayerBoard playerBoard;
    public Leaderboards leaderboards;
    public PlayerBoardManager playerBoardManager;
    public TagsManager tagsManager;
    public GangManager gangManager;
    public PlayerAnimations playerAnimations;
    public LegacyTitle legacyTitle;
    public LegacyActionBar legacyActionBar;
    public LegacyHeadTail tablistLegacy;
    public Kits kits;
    public KitManager kitManager;
    public ForcedRespawn forcedRespawn;
    public PlayerVectors playerVectors;
    public PlayerManager playerManager;
    public CombatLog combatLog;
    public PlayerSounds playerSounds;
    public PlayerBoardAnimation playerBoardAnimation;
    public CratesMan cratesManManager;

    public static DataProvider settings = DataProvider.getInstance();
    public static LuckPerms luckPerms;

    public void initializeClasses() {

        if (StellarAPI.getVersion() < 8) disableKPVP();

        settings.setup(this);

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null){
            luckPerms = provider.getProvider();
        }

        EventListener.registerListeners(this);

        playerBoardManager = new PlayerBoardManager(this);
        playerManager = new PlayerManager(this);
        tagsManager = new TagsManager(this);
        gangManager = new GangManager(this);
        kitManager = new KitManager(this);

        leaderboards = new Leaderboards(this);
        playerBoard = new PlayerBoard(this);
        playerBoardAnimation = new PlayerBoardAnimation(this);
        hologramsTask = new HologramsTask(this);
        spawnTask = new SpawnTask(this);
        tagTask = new TagTask(this);
        boardTask = new BoardTask(this);
        kits = new Kits(this);
        forcedRespawn = new ForcedRespawn(this);
        combatLog = new CombatLog(this);
        cratesManManager = new CratesMan(this);

        defaultCommands = new DefaultCommands(this);
        personalComands = new PersonalCommands(this);
        advancedCommands = new AdvancedCommands(this);

        playerVectors = new PlayerVectors();
        aste = new Aste();
        playerSounds = new PlayerSounds();

        Thread Leaderboards_Deaths_Thread = new Thread(() -> hologramsTask.chekear_holograms("deaths"));

        Thread Leaderboards_Kills_Thread = new Thread(() -> hologramsTask.chekear_holograms("kills"));

        Thread Leaderboards_Coins_Thread = new Thread(() -> hologramsTask.chekear_holograms("soldi"));

        Leaderboards_Deaths_Thread.start();
        Leaderboards_Coins_Thread.start();
        Leaderboards_Kills_Thread.start();

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(instance, () -> tagTask.gangsTagsTask(),0,15);

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(instance, () -> tagTask.suffixTagsTask(),0,15);

        spawnTask.startdoDaylightCycle();
        spawnTask.startDownFallControl();

        if (getVersion() > 9){
            EventListener.registerListenerExact(this, PlayerAnimations.class); // Hit Fix
            playerAnimations = new PlayerAnimations(this);
            playerManager.setHits(70); // 60 - 100
        }
        else {
            legacyTitle = new LegacyTitle(this);
            legacyActionBar = new LegacyActionBar(this);
            tablistLegacy = new LegacyHeadTail(this);
        }

        List<Player> online = (List<Player>) Bukkit.getOnlinePlayers();
        if (Bukkit.getServer().getOnlinePlayers().size() == 0) return;

        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> online.forEach(playerBoard::sendScoreboard), 5L);
        online.forEach(playerManager::updatePlayer);
        online.forEach(p -> CombatLog.scoreboardUpdated.put(p.getName(), false));
        online.forEach(gangManager::reset_player_gangs_requests);

        online.forEach(p -> {
            if (!p.hasPermission("stellarsquad.staff")) return;
            p.sendMessage(StellarSource.c("&c[&fStellarSquad&c] &fStellar KitPVP enabled."));
        });

    }

    @Override
    public void onEnable() {
        instance = this;
      initializeClasses();
      initializeK();
    }

    
    private void initializeK() {
        Bukkit.getConsoleSender().sendMessage(" ");
        StellarSource.printKPVPLogo();
        Bukkit.getConsoleSender().sendMessage(StellarSource.c("&fVersion: &a")+this.getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage(StellarSource.c("&fDesc: &a")+this.getDescription().getDescription());
        Bukkit.getConsoleSender().sendMessage(" ");
    }

    private int getVersion(){
        return Integer.parseInt(Bukkit.getServer().getBukkitVersion().split("\\.")[1]);
    }

    private void disableKPVP(){
        Bukkit.getConsoleSender().sendMessage(StellarSource.c("&c[!] &&eKitPVP &ffatto per le versioni superiori a 1.8"));
        Bukkit.getPluginManager().disablePlugin(this);
    }

}
