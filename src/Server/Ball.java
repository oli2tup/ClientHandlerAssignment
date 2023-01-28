package Server;

public class Ball {

    private static int ballPlayer = 10000;

    public static int getBallPlayer(){
        return ballPlayer;
    }

    public static void setBallPlayer(int playerId){
        ballPlayer = playerId;
    }
}
