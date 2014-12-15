package com.sng.screen.figures;

import java.util.ArrayList;
import java.util.List;

public class Street {
    String cards;
    int bank;
    int pot;
    int stack;
    Position position;
    List<Player> playerList = new ArrayList<Player>();

    public int getPlayersNumber(){
        return playerList.size();
    }

    void addPlayer(Player player){
        playerList.add(player);
    }
}