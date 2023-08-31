// Simple reflection example
package us.com.stellarsquad.stellarcraft.interfaces;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/*
import java.lang.reflect.Field;
import java.lang.reflect.Method;
*/

public interface Reflection extends StellarSource
{
    default void sendPacket(Player player, Object packet)
  {
        try 
          {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) 
          {
            e.printStackTrace();
        }
    }

    default Class<?> getNMSClass(String name) throws ClassNotFoundException
  {
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e)
          {
            e.printStackTrace();
            return null;
        }
    }
}
