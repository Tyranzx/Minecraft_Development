package me.tyranzx.essentialsz.core;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Loader extends JavaPlugin {

    public static ConfigManager settings = ConfigManager.getInstance();
    private static Loader core;

    private DefaultCommands defaultCommands;
    private PersonalCommands personalCommands;
    private AdvancedCommands advancedCommands;

    @Override
    public void onEnable() {

        core = this;

        Bukkit.getConsoleSender().sendMessage(StellarSource.c("&8&m------------------------------------------"));
        Bukkit.getConsoleSender().sendMessage(StellarSource.c("&aEssentialsZ &fenabled! &8- &7by Jess"));
        Bukkit.getConsoleSender().sendMessage(StellarSource.c("&aVersion: &e")+getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage(StellarSource.c("&8&m------------------------------------------"));

        // config files
        settings.setup(this);

        // register commands
        defaultCommands = new DefaultCommands(this);
        personalCommands = new PersonalCommands(this);
        advancedCommands = new AdvancedCommands(this);

        // register listeners through for
        registerListeners();

    }

    Listener[] listeners = {
            new PlayerListener(this), new ChatListener(this), new EnderPearlRider(this), new ArraysListener(this),
            new SpecialMobs(), new HookAPI(this), new ColoredSigns()
    };
