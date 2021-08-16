package me.quarry.quarry;


import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.block.Chest;

import java.util.Collection;
import java.util.Map;

public final class Quarry extends JavaPlugin {
    public Quarry quarryThis=this;

    Thread mine;
    quarryMap map=new quarryMap();


    @Override
    public void onEnable() {


        // Plugin startup logic
        ItemStack result= new ItemStack(Material.DIAMOND_PICKAXE,1);

        ItemMeta meta= result.getItemMeta();

        meta.setDisplayName(ChatColor.RED +"Big Dig");

        result.setItemMeta(meta);

        result.addEnchantment(Enchantment.DIG_SPEED,5);
        result.addEnchantment(Enchantment.MENDING,1);
        result.addEnchantment(Enchantment.DURABILITY,3);
        result.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS,3);

        NamespacedKey key=new NamespacedKey(this, "Big_Dig");

        ShapedRecipe recipe=new ShapedRecipe(key,result);
        recipe.shape(
                "DDD",
                " P ",
                "   "
        );
        recipe.setIngredient('D',Material.DIAMOND_BLOCK);
        recipe.setIngredient('P',Material.DIAMOND_PICKAXE);

        Bukkit.addRecipe(recipe);
        //for magic stick

        ItemStack stick= new ItemStack(Material.STICK,1);

        ItemMeta meta3= stick.getItemMeta();

        meta3.setDisplayName(ChatColor.RED +"Marker");

        stick.setItemMeta(meta3);



        NamespacedKey key3=new NamespacedKey(this, "Marker");

        ShapedRecipe recipe3=new ShapedRecipe(key3,stick);
        recipe3.shape(
                " P ",
                " F ",
                "   "
        );
        recipe3.setIngredient('F',Material.STICK);
        recipe3.setIngredient('P',Material.DIAMOND_BLOCK);

        Bukkit.addRecipe(recipe3);
        //testing for new quarry
        ItemStack quarry= new ItemStack(Material.FURNACE,1);

        ItemMeta meta2= quarry.getItemMeta();

        meta2.setDisplayName(ChatColor.RED +"Quarry");

        quarry.setItemMeta(meta2);



        NamespacedKey key2=new NamespacedKey(this, "Quarry");

        ShapedRecipe recipe2=new ShapedRecipe(key2,quarry);
        recipe2.shape(
                " P ",
                " F ",
                "   "
        );
        recipe2.setIngredient('F',Material.FURNACE);
        recipe2.setIngredient('P',Material.DIAMOND_PICKAXE);

        Bukkit.addRecipe(recipe2);


        map=map.readMap();
        initializeRunningQuarrys();
    getServer().getPluginManager().registerEvents(new Miner(quarryThis),this);

    }

    private void initializeRunningQuarrys() {
        for(Map.Entry mapElement:quarryThis.map.map.entrySet()){
            Location loc=(Location)mapElement.getKey();
                minerData miner=quarryThis.map.map.get(loc);
                if(miner.isRunning){
                    quarryThis.runMiner(miner.quarryLocation,miner.chunk,miner.Id);
//
                }


        }
    }

    //makes thread for a miner
    mineChunk miner;

    public void runMiner(Location quarryLoc, Chunk breakChun, Player user, int id){
        this.miner=new mineChunk(quarryLoc,breakChun,user,this,id);
        Thread thread=new Thread(this.miner);
        this.miner.setThread(thread);
        thread.start();
    }
    public void runMiner(Location quarryLoc, Chunk breakChun,int id){
        this.miner=new mineChunk(quarryLoc,breakChun,this,id);
        Thread thread=new Thread(this.miner);
        this.miner.setThread(thread);
        thread.start();
    }
    public void updateMinerStatus(boolean status){
        this.miner.setRunning(status);
    }
    //connects to main thread to updates blocks
    public void changeBlock(Chunk chunl,int x,int y,int z,Location quarryLocation){
        BukkitRunnable runner=new BukkitRunnable() {
            @Override
            public void run() {
                Block bloc=chunl.getBlock(x,y,z);
//                Bukkit.getServer().getWorld("world").loadChunk(chunl);
                if(map.map.get(quarryLocation).chestLocation!=null){
                    Location chestLoc=map.map.get(quarryLocation).chestLocation;
                    if(chestLoc.getBlock().getType()!=Material.CHEST){
                        map.map.get(quarryLocation).chestLocation=null;
                    }else {
//                    Block chestBlock=chestLoc.getBlock();
//                    BlockState chestState = chestBlock.getState();

                        Chest chest = (Chest) chestLoc.getBlock().getState();
                        //TODO add chest is full to sqitch to breaking naturall again

                        //TODO---------------------------------------------------------
//                    Inventory inv=chest.getInventory();
                        Collection<ItemStack> drops = bloc.getDrops();
                        for (ItemStack drop : drops) {
                            chest.getInventory().addItem(drop);
//                            map.map.get(quarryLocation).chest.customChest.addItem(drop);
                        }
                        bloc.setType(Material.AIR);
                    }
                }
                bloc.breakNaturally();
//                bloc.getWorld().unloadChunk(chunl);
//                bloc.getWorld().loadChunk(chunl);
            }
        };runner.runTask(this);

//        Bukkit.broadcastMessage("Updating block");
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        map.saveMap();

    }




}
