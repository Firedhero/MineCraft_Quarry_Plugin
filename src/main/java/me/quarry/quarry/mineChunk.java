package me.quarry.quarry;

import com.google.common.util.concurrent.AbstractScheduledService;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.graalvm.compiler.lir.alloc.lsra.LinearScan;

public class mineChunk extends Thread {
    Chunk breakChunk;
    ChunkSnapshot snap;
    Chunk clone;

    public Location getQuarryLocation() {
        return quarryLocation;
    }

    public void setQuarryLocation(Location quarryLocation) {
        this.quarryLocation = quarryLocation;
    }

    Location quarryLocation;
    Player player;
    Quarry quarry;
    Thread thread;


    public void setRunning(boolean running) {
        isRunning = running;
        if(running==true) {
            thread.interrupt();
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    private boolean isRunning;
    public void run() {
        isRunning=true;

//        BlockData broken;
        Block block;
        int[]leftOff=quarry.map.map.get(quarryLocation).getPos();
        int x,y,z;
        x=leftOff[0];
        y=leftOff[1];
        z=leftOff[2];


        for (; y > 1; y--) {
            for (; x < 16; x++) {
                for (; z < 16; z++) {
                    try {


                        if (quarry.map.map.get(quarryLocation) != null) {
                            while (!quarry.map.map.get(quarryLocation).isRunning) {
                                try {

                                    sleep(500);
                                } catch (InterruptedException e) {
    //                            e.printStackTrace();
                                }
                            }

    //                    if(quarryLocation==null){
    //                        Bukkit.broadcastMessage("Quarry broke digging stopped");
    ////                        Bukkit.broadcastMessage(quarryLocation.getWorld().getBlockAt(quarryLocation.getBlockX(),quarryLocation.getBlockY(),quarryLocation.getBlockZ()).getMetadata(ChatColor.RED+"Quarry").toString());
    //                        y=0;x=16;z=16;
    //                        continue;
    //                    }
                            Chunk chunk = breakChunk.getWorld().getChunkAt(quarryLocation);
                            quarry.changeBlock(chunk, x, y, z,quarryLocation);
                            quarry.map.map.get(quarryLocation).setPos(x,y,z);
//                            Bukkit.broadcastMessage("Mining x:"+x+" y:"+y+" z:"+z);
    //                        world.unloadChunk(chunk);
    //                        world.loadChunk(chunk);
    //                    block=chunk.getBlock(x,y,z);
    //                    block.setType(Material.AIR);
    //                    broken.breakNaturally();
    //                    changeBlock(chunk,x,y,z)
    //                    chunk.getBlock(x,y,z).setType(Material.AIR);

    //                    quarry.getServer().getWorld(breakChunk.getWorld().getName()).getChunkAt(quarryLocation).getBlock(x,y,z).setType(Material.AIR);//.setType(Material.AIR);

    //                    quarry.getServer().getScheduler().callSyncMethod(this,  );


    //        bloc.getDrops().clear();
    //                    Bukkit.broadcastMessage(Thread.currentThread().toString());
    ////                    Bukkit.broadcastMessage("Updating block");


    //                    broken.setType(Material.AIR);
    //                    player.sendMessage("breaking block");
                            try {
                                sleep(100);
                            } catch (InterruptedException e) {
    //                            e.printStackTrace();
                            }
                        }
                    }catch (Exception e){

                    }
//                    Bukkit.broadcastMessage("Finished Z");
                }
                z=0;
//                Bukkit.broadcastMessage("Finished X");
//                    quarry.getServer().getWorld(breakChunk.getWorld().getName()).getChunkAt(quarryLocation).load();

                }
            x=0;
//            Bukkit.broadcastMessage("Finished Y");

        }
        quarry.map.map.get(quarryLocation).isRunning=false;
    }
    void changeBlock(Chunk chunk,int x,int y,int z){
        Block bloc=chunk.getBlock(x,y,z);
        bloc.setType(Material.AIR);
        bloc.getWorld().unloadChunk(chunk);
        bloc.getWorld().loadChunk(chunk);
    }
    mineChunk(Location quarryLoc, Chunk breakChun,Quarry q,int id){

        quarryLocation=quarryLoc;
        breakChunk=breakChun;
        quarry=q;
    }
    int id;
    mineChunk(Location quarryLoc, Chunk breakChun, Player user,Quarry q,int id){

        player=user;
        quarryLocation=quarryLoc;
        breakChunk=breakChun;
        quarry=q;
//        run2();
//        Thread thread=new Thread(new mineChunk(quarryLocation,breakChunk,player,snap,1,q));
//        thread.start();
    }
    mineChunk(){

    }

    public void setThread(Thread thread) {
            this.thread=thread;
    }
}
