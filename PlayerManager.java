package us.com.stellarsquad.stellarcraft.management.player;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;
import us.com.stellarsquad.stellarcraft.StellarCore;
import us.com.stellarsquad.stellarcraft.management.ConfigManager;
import us.com.stellarsquad.stellarcraft.utils.StellarSource;
import us.com.stellarsquad.stellarcraft.utils.entity.player.PlayerSocket;
import us.com.stellarsquad.stellarcraft.utils.entity.player.RestorePlayer;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.Collection;
import java.util.UUID;

public class PlayerManager extends Reflection {

    private final StellarCore core;

    private final Player p;
    private final Player killer;
    private final Entity entitykiller;

    private final Server playerserver;
    private final String name;
    private final String worldname;
    private final String host;
    private final String ip;
    private final UUID uuid;

    private final Inventory inventory;
    private final ItemStack itemInHand;
    private final World world;
    private final Location location;

    private final double food;
    private final double health;
    private final double maxhealth;
    private final float experiencia;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;
    private final long timeAlived;
    private final long lastplayed;

    private final Collection<PotionEffect> potionEffects;
    private final Set<String> proxiesblacklist;
    private final ItemStack[] inventoryContents;

    private final PlayerSocket ps;

    private File pfile;
    private FileConfiguration players;

    public PlayerManager(StellarCore core, Player p){
        this.core = core;
        this.p = p;
        this.experiencia = p.getExp();
        this.name = p.getName();
        this.worldname = p.getWorld().getName();
        this.ip = ps.getPlayerIP(p);
        this.potionEffects = p.getActivePotionEffects();
        this.proxiesblacklist = ps.proxyIPSet;
        this.inventoryContents = p.getInventory().getContents();
        this.host = p.getAddress().getHostName();
        this.playerserver = p.getServer();
        this.food = p.getFoodLevel();
        this.health = p.getHealth();
        this.maxhealth = p.getMaxHealth();
        this.x = p.getLocation().getX();
        this.y = p.getLocation().getY();
        this.z = p.getLocation().getZ();
        this.yaw = p.getLocation().getYaw();
        this.pitch = p.getLocation().getPitch();
        this.inventory = p.getInventory();
        this.world = p.getWorld();
        this.location = p.getLocation();
        this.itemInHand = p.getInventory().getItemInHand();
        this.killer = p.getKiller().getPlayer();
        this.entitykiller = p.getKiller();
        this.timeAlived = p.getTicksLived();
        this.uuid = p.getUniqueId();
        this.lastplayed =  p.getLastPlayed();
        this.ps = new PlayerSocket();
    }

    public void sendPlayerListMessage(){
        // EJ: Lista de jugadores (5): Juancito233, Tyranzx, ElBro__02, Ramss22_x, Nefruti.
        if (!p.hasPermission("essentials.staff")) {
            p.sendMessage(StellarSource.c("&cNo tienes permiso para ejecutar este comando."));
        }
        p.sendMessage(StellarSource.c("&7Lista de jugadores &e("+StellarSource.online_players_size+"): &f")+StellarSource.name_for_each_player+".");
    }

    @NotNull
    public String getIp() {
     //   return p.getAddress(); WHAT IS THIS SHEET? XD
        return ip; // <- Getting ip address from external player socket
    }

    @NotNull
    public Object getPing(Player p){
        return getPing(p);
    }

    public void addEfectos(PotionEffect[] effects) {
        for (PotionEffect efectos : effects){
            p.addPotionEffect(efectos);
        }
    }

    @NotNull
    public Collection<PotionEffect> getPotionEffects() {
        return potionEffects;
    }

    @NotNull
    public Player getPlayer() {
        return p;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public Set<String> getProxiesBlackList(){
        return proxiesblacklist;
    }

    @NotNull
    public ItemStack[] getInvContent() {
        return inventoryContents;
    }
    
    @NotNull
    public World getWorld(){
        return world;
    }
    public String getWorldname() {
        return worldname;
    }

    public String getHost() {
        return host;
    }

    @NotNull
    public Server getServer() {
        return playerserver;
    }

    @NotNull
    public float getExperience() {
        return experiencia;
    }

    public void setExperience(float experiencia) {
        p.setExp(experiencia);
    }

    @NotNull
    public double getHambre() {
        return food;
    }

    public void setHambre(int food) {
        p.setFoodLevel(food);
    }

    @NotNull
    public double getVida() {
        return health;
    }

    public void setVida(double vida) {
        p.setHealth(vida);
    }

    @NotNull
    public double getVidamax() {
        return maxhealth;
    }

    public void setVidamax(double vidamax) {
        p.setMaxHealth(vidamax);
    }

    @NotNull
    public double getX() {
        return x;
    }

    public void setX(double x) {
        p.getLocation().setX(x);
    }

    @NotNull
    public double getY() {
        return y;
    }

    public void setY(double y) {
        p.getLocation().setY(y);
    }

    @NotNull
    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        p.getLocation().setZ(z);
    }

    @NotNull
    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        p.getLocation().setYaw(yaw);
    }

    @NotNull
    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        p.getLocation().setPitch(pitch);
    }

    @NotNull
    public Inventory getInventario() {
        return inventory;
    }

    public void openInv(Inventory inventario) {
        p.openInventory(inventario);
    }

    @NotNull
    public Location getLocation() {
        return location;
    }

    public void teleportLocation(Location loc) {
        p.teleport(loc);
    }

    @NotNull
    public ItemStack getItemInHand() {
        return itemInHand;
    }

    public void setItemInHand(ItemStack itemInHand) {
        p.getInventory().setItemInHand(itemInHand);
    }
    
    public void addItem(ItemStack item) {
        inventory.addItem(item);
    }

    @NotNull
    public Player getPlayerKiller(){
        return (Player) killer;
    }

    @NotNull
    public long getTiempoVivo(){
        return timeAlived;
    }

    public boolean hasPerm(String permiso){
        return p.hasPermission(permiso);
    }

    @NotNull
    public UUID getUUID(){
        return uuid;
    }

    public long lastTimePlayed(){
        return lastplayed;
    }

    public void sendMessage(String text) { p.sendMessage(c(text)); }
    
    @NotNull
    public Location getPreviousLocation(){
        return RestorePlayer.getPreviousLocation(p);
    }

    @NotNull
    public ItemStack[] getPreviousInventory(){
        return RestorePlayer.getPreviousInventory(p);
    }

    public void saveCurrentLocation(){
        RestorePlayer.savePreviousLocation(p);
    }
    public void saveCurrentInventory(){
        RestorePlayer.savePreviousInventory(p);
    }

    public void giveToInventory(ItemStack item){
        p.getInventory().addItem(item);
    }

    public void setToInventory(int slot, ItemStack item){
        p.getInventory().setItem(slot, item);
    }

    public void replaceToInventory(ItemStack[] contenido){
        RestorePlayer.savePreviousInventory(p);
        p.getInventory().clear();
        p.getInventory().setContents(contenido);
    }
}
