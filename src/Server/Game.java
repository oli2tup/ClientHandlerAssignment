package Server;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Game {

    private final Set<Integer> inGamePlayers = new HashSet<>();
    private final Set<Integer> allPlayerIds = new HashSet<>();

    //public int playerId;
    private final int maxId = 9999;


    public int createId() {

        Random random = new Random();
        int randomId = random.nextInt(maxId);

        while (allPlayerIds.contains(randomId)){
            randomId = random.nextInt(maxId);
        }

        allPlayerIds.add(randomId);
        inGamePlayers.add(randomId);
        return randomId;
    }

    public void printPlayers(){
        for (int playerIds : inGamePlayers)
            System.out.println(playerIds);
    }

    public void removePlayer(int playerId) {
        inGamePlayers.remove(playerId);
    }

    public int getNextPlayer(){
        int id = 0;
        for (int tempId : inGamePlayers) {
            if (tempId != Ball.getBallPlayer()) {
                id = tempId;
                return id;
            }
        }
        return id;
    }

    public void passBall(int fromPlayerId, int toPlayerId) throws Exception {

        if (Ball.getBallPlayer() != fromPlayerId)
            throw new Exception("Player: "+ fromPlayerId +" doesn't have the ball");

        Ball.setBallPlayer(toPlayerId);
        System.out.println(fromPlayerId+" passed ball to "+toPlayerId);
    }

    public int checkBallPlayer(){
        return Ball.getBallPlayer();
    }

    public void playerDisconnected(int playerId) {
        if (Ball.getBallPlayer() == playerId){
            if (inGamePlayers.size() > 1){
                int newBallPlayer = getNextPlayer();
                removePlayer(playerId);
                Ball.setBallPlayer(newBallPlayer);
                System.out.println("The server has passed the ball to: "+ newBallPlayer);
            }
        }
        removePlayer(playerId);
    }

    public Set<Integer> getInGamePlayers() {
        return inGamePlayers;
    }

    public Set<Integer> getAllPlayerIds() {
        return allPlayerIds;
    }
}

