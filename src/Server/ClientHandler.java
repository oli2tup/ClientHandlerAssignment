package Server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Set;


public class ClientHandler implements Runnable{

    private final Socket socket;
    private Game game;

    public ClientHandler(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
    }

    public void run() {
        int playerId = 0;
        try (
                Scanner scanner = new Scanner(socket.getInputStream());
                PrintWriter writer = new PrintWriter(socket.getOutputStream(),true)) {
            try {
                playerId = game.createId();

                writer.println("SUCCESS");
                System.out.println("Player " + playerId + " has joined the Game");

                if (game.getInGamePlayers().size() == 1) {
                    Ball.setBallPlayer(playerId);
                }

                System.out.println("All players in game:");
                game.printPlayers();

                while (true){
                    String line = scanner.nextLine();
                    String[] substrings = line.split(" ");
                    switch (substrings[0].toLowerCase()){

                        case "player_id":
                            writer.println(playerId);
                            break;

                        case "all_players":
                            Set<Integer> allPlayerInGame = game.getInGamePlayers();
                            writer.println(allPlayerInGame.size());
                            for (Integer player : allPlayerInGame) {
                                writer.println(player);
                            }
                            break;

                        case "pass_ball":
                            int fromPlayerId = Integer.parseInt(substrings[1]);
                            int toPlayerId = Integer.parseInt(substrings[2]);
                            game.passBall(fromPlayerId,toPlayerId);
                            writer.println("SUCCESS");
                            break;

                        case "ball_holder":
                            writer.println(game.checkBallPlayer());
                            break;

                        default:
                            throw new Exception("Unknown command: " + substrings[0]);
                    }
                }
            } catch (Exception e) {
                writer.println("Error "+ e.getMessage());
                socket.close();
            }
        } catch (Exception e) {
        }finally {
            System.out.println("Player: "+ playerId + " disconnected");
            try {
                game.playerDisconnected(playerId);
                game.printPlayers();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}