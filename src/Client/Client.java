package Client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements AutoCloseable{
    final int port = 8888;
    int ballPlayer;
    int playerId;
    private final Scanner reader;
    private final PrintWriter writer;

    public Client() throws Exception{

        Socket socket = new Socket("localhost", port);
        reader = new Scanner(socket.getInputStream());

        writer = new PrintWriter(socket.getOutputStream(), true);

        String line = reader.nextLine();
        if (line.trim().compareToIgnoreCase("success") != 0)
            throw new Exception(line);
    }

    public int[] allPlayerIds() {
        writer.println("all_players");

        String line = reader.nextLine();
        int numOfIds = Integer.parseInt(line);

        int[] playerIds = new int[numOfIds];
        for (int i = 0; i < numOfIds; i++) {
            line = reader.nextLine();
            playerIds[i] = Integer.parseInt(line);
        }
        return playerIds;
    }

    public boolean isInGame(int playerId){
        writer.println("all_players");

        String line = reader.nextLine();
        int numOfIds = Integer.parseInt(line);
        boolean id = false;


        int[] playerIds = new int[numOfIds];
        for (int i = 0; i < numOfIds; i++) {
            line = reader.nextLine();
            playerIds[i] = Integer.parseInt(line);
        }

        for (Integer a : playerIds){
            if ( a == playerId) {
                id = true;
                break;
            }
        }

        return id;
    }

    public int getBallPlayer() {
        writer.println("ball_holder");

        String line = reader.nextLine();
        ballPlayer = Integer.parseInt(line);

        return ballPlayer;
    }

    public void passBall(int playerId) throws Exception {
        writer.println("pass_ball " + ballPlayer + " " + playerId);

        String line = reader.nextLine();
        if (line.trim().compareToIgnoreCase("success") != 0)
            throw new Exception(line);
    }

    public int getPlayerId() {
        writer.println("player_id");

        String line = reader.nextLine();
        playerId = Integer.parseInt(line);

        return playerId;
    }

    @Override
    public void close() throws Exception {
        try {
            reader.close();
            writer.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
