package com.sweattypalms.skyblock;

import com.sweattypalms.skyblock.commands.MainCommandHandler;
import com.sweattypalms.skyblock.commands.UtilCommandHandler;
import com.sweattypalms.skyblock.core.items.ItemManager;
import com.sweattypalms.skyblock.core.items.builder.reforges.ReforgeManager;
import com.sweattypalms.skyblock.core.mobs.builder.MobManager;
import com.sweattypalms.skyblock.core.mobs.builder.dragons.DragonManager;
import com.sweattypalms.skyblock.core.player.SkyblockPlayer;
import com.sweattypalms.skyblock.core.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Set;

import static com.sweattypalms.skyblock.core.items.builder.reforges.ReforgeManager.REFORGES_LIST;

public final class SkyBlock extends JavaPlugin {

    private static SkyBlock instance;

    public boolean debug = true;

    public static SkyBlock getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        long start = System.currentTimeMillis();
        registerListeners();
        registerCommands();
        registerCraft();
        registerServer();
        long end = System.currentTimeMillis() - start;
        if (debug) {
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Skyblock has been enabled! This took " + ChatColor.YELLOW + end + "ms");
        }

//        drawAscii();

        Bukkit.getOnlinePlayers().forEach(SkyblockPlayer::newPlayer);
        configs();
    }

    private void registerServer() {
        WorldManager.init();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, WorldManager::tick, 0L, 1L);

        new DragonManager();
    }

    private void registerCraft() {
        System.out.println("Registering items...");
        ItemManager.init();
        System.out.println(ChatColor.GREEN + "Successfully loaded " + ItemManager.ITEMS_LIST.size() + " items.");

        System.out.println("Registering mobs...");
        MobManager.init();
        System.out.println(ChatColor.GREEN + "Successfully loaded " + MobManager.MOBS_LIST.size() + " mobs.");

        System.out.println("Registering reforges...");
        ReforgeManager.init();
        System.out.println(ChatColor.GREEN + "Successfully loaded " + REFORGES_LIST.size() + " reforges.");
    }

    public void registerListeners() {
        System.out.println("Registering listeners...");
        Reflections reflections = new Reflections("com.sweattypalms.skyblock.core.events.listeners");
        Set<Class<? extends Listener>> listenerClasses = reflections.getSubTypesOf(org.bukkit.event.Listener.class);

        for (Class<? extends org.bukkit.event.Listener> clazz : listenerClasses) {
            try {
                org.bukkit.event.Listener listenerInstance = clazz.getDeclaredConstructor().newInstance();
                Bukkit.getPluginManager().registerEvents(listenerInstance, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(ChatColor.GREEN + "Successfully registered " + listenerClasses.size() + " listeners.");
    }

    @SuppressWarnings("ConstantConditions")
    public void registerCommands() {
        MainCommandHandler mainCommandHandler = new MainCommandHandler();
        getCommand("test").setExecutor(mainCommandHandler);
        getCommand("item").setExecutor(mainCommandHandler);
        getCommand("mob").setExecutor(mainCommandHandler);
        getCommand("stat").setExecutor(mainCommandHandler);
        getCommand("upgrade").setExecutor(mainCommandHandler);
        getCommand("reforge").setExecutor(mainCommandHandler);
        getCommand("debug").setExecutor(mainCommandHandler);

        UtilCommandHandler utilCommandHandler = new UtilCommandHandler();
        getCommand("gms").setExecutor(utilCommandHandler);
        getCommand("gmc").setExecutor(utilCommandHandler);
        getCommand("gmss").setExecutor(utilCommandHandler);
    }

    private void configs() {
        File configuration = new File(this.getDataFolder(), "skyblock_config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configuration);
        if (!configuration.exists()) {
            config.set("ratio", true);
            try {
                config.save(configuration);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void drawAscii(){
        int width = 220;
        int height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setFont(new Font("SansSerif", Font.BOLD, 24));
        graphics.drawString("SKYITEMS", 10, 20);



        System.out.println("\n\n\n");
        for (int y = 0; y < height; y++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int x = 0; x < width; x++) {
                stringBuilder.append(image.getRGB(x, y) == -16777216 ? " " : "*");
            }
            if (stringBuilder.toString().trim().isEmpty()) {
                continue;
            }
            System.out.println(stringBuilder);
        }
    }

    @Override
    public void onDisable() {

    }

}
