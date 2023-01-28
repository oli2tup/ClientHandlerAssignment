package Client;

import java.util.Scanner;

public class ClientProgram {

    public static void main(String[] args){

            try(Client client = new Client()) {
                System.out.println("Welcome to the game");
                Scanner in = new Scanner(System.in);
                while (true) {

                    System.out.println("""

                            Choose between the following options:
                            1: List all players
                            2: Show ball player
                            3: Pass ball
                            Press 1, 2 or 3""");
                    int option = Integer.parseInt(in.nextLine());

                    if (option == 1) {
                        var allPlayerIds = client.allPlayerIds();
                        System.out.println("""
                                    -------------------------------------------------------
                                                    Players in the Game""");
                        for (int Id : allPlayerIds) {

                            if ( client.getBallPlayer() == Id && client.getPlayerId() == Id)
                                System.out.println("Player: " + Id + " you |BALL|");

                            if ( !(client.getBallPlayer() == Id) && client.getPlayerId() == Id)
                                System.out.println("Player: " + Id + " you");

                            if ( client.getBallPlayer() == Id && !(client.getPlayerId() == Id))
                                System.out.println("Player: " + Id + " |BALL|");

                            if ( !(client.getBallPlayer() == Id) && !(client.getPlayerId() == Id))
                                System.out.println("Player: " + Id);
                        }

                        System.out.println("""
                                    -------------------------------------------------------""");
                    }

                    if (option == 2) {
                        System.out.printf("""
                                    -------------------------------------------------------
                                            The player with the ball is Player: %d
                                    -------------------------------------------------------                
                                                    """, client.getBallPlayer());
                    }
                    else if (option == 3) {
                        if (client.getPlayerId() == client.getBallPlayer()) {
                            System.out.println("Enter the ID of player you'd like to pass the ball to.");
                            int toId = Integer.parseInt(in.nextLine());

                            if (toId > 99999 || toId < 0)
                                continue;
                            if (client.isInGame(toId)) {
                                client.passBall(toId);
                            }else{
                                System.out.println("Player not in game");
                            }
                        }
                        else {
                            System.out.println("You don't have the ball");
                        }
                    }
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }

    }
}
