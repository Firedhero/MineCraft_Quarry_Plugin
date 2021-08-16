package me.quarry.quarry;

import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class mineCustom extends Thread{


    public Location getQuarryLocation() {
        return quarryLocation;
    }

    public void setQuarryLocation(Location quarryLocation) {
        this.quarryLocation = quarryLocation;
    }
    Chunk breakChunk;
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

        //take highest y from list
        for (; y > 1; y--) {//no change
            for (; x < 16; x++) {//need to get the marked world positions
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







    mineCustom(Location quarryLoc, Chunk breakChun, Quarry q, int id){

        quarryLocation=quarryLoc;
        breakChunk=breakChun;
        quarry=q;
    }
    int id;
    mineCustom(Location quarryLoc, Chunk breakChun, Player user, Quarry q, int id){

        player=user;
        quarryLocation=quarryLoc;
        breakChunk=breakChun;
        quarry=q;
//        run2();
//        Thread thread=new Thread(new mineChunk(quarryLocation,breakChunk,player,snap,1,q));
//        thread.start();
    }
    mineCustom(){

    }

    public void setThread(Thread thread) {
        this.thread=thread;
    }
}
