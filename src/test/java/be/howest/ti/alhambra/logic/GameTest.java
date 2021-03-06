package be.howest.ti.alhambra.logic;


import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.BuildingType;
import be.howest.ti.alhambra.logic.building.Walls;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.game.*;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.player.Player;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    AlhambraController controller = new AlhambraController();

    @Test
    void joinGame() {
        controller.initializeLobby();
        Lobby firstGame = controller.getLobbies().get("group01-0");
        assertEquals(0, firstGame.getPlayers().size());
        controller.joinLobby("group01-0", "john");
        assertEquals(1, firstGame.getPlayers().size());

        assertThrows(AlhambraEntityNotFoundException.class, () -> controller.joinLobby("group01-5159", "john"));
    }

    @Test
    void leaveGame() {
        // test when player is still in lobby
        controller.initializeLobby();
        Lobby firstGame = controller.getLobbies().get("group01-0");
        controller.joinLobby("group01-0", "john");
        controller.leaveGame("group01-0", "john");
        assertNull(controller.getLobbies().get("group01-0"));
        // test when player is in game
        controller.initializeLobby();
        controller.joinLobby("group01-1", "john");
        controller.joinLobby("group01-1", "danny");
        controller.setReady("john","group01-1");
        controller.setReady("danny","group01-1");

        System.out.println(controller.getGameState("group01-1"));
        controller.leaveGame("group01-1", "danny");
        assertEquals(1, controller.getOngoingGames().get("group01-1").getPlayers().size());
    }

    @Test
    void startGame(){
        controller.initializeLobby();

        controller.joinLobby("group01-0", "john");
        controller.joinLobby("group01-0", "danny");

        controller.setReady("john","group01-0");
        assertEquals(1, controller.getLobbies().size());

        controller.setReady("danny","group01-0");

        assertEquals(0, controller.getLobbies().size());
        assertEquals(1, controller.getOngoingGames().size());
    }

    @Test
    void receiveStartingMoneyOnGameStart(){
        List<Player> players = new ArrayList<>();
        Player denJohn = new Player("John");
        Player denEddy = new Player("Eddy");
        
        players.add(denJohn);
        players.add(denEddy);

        Game game = new Game(players, "group01-0");

        assertNotNull(denJohn.getMoney());
        List<Coin> johnsCoinsInBag = denJohn.getMoney().getCoinsInBag();
        List<Coin> eddiesCoinsInBag = denEddy.getMoney().getCoinsInBag();

        assertFalse(game.getBank().totalValueCoins(johnsCoinsInBag) < 20);
        assertFalse(game.getBank().totalValueCoins(johnsCoinsInBag) > 28);

        assertNotNull(denEddy.getMoney());
        assertFalse(game.getBank().totalValueCoins(eddiesCoinsInBag) < 20);
        assertFalse(game.getBank().totalValueCoins(eddiesCoinsInBag) > 28);
    }

    @Test
    void changeCurrentPlayer(){
        controller.initializeLobby();

        controller.joinLobby("group01-0", "john");
        controller.joinLobby("group01-0", "danny");

        controller.setReady("john","group01-0");
        controller.setReady("danny","group01-0");

        Game firstGame = controller.getOngoingGames().get("group01-0");

        Player firstPlayer = firstGame.getCurrentPlayer();

        firstGame.changeCurrentPlayer();
        assertNotEquals(firstPlayer, firstGame.getCurrentPlayer());
        firstGame.changeCurrentPlayer();
        assertEquals(firstPlayer, firstGame.getCurrentPlayer());
        firstGame.changeCurrentPlayer();
        assertNotEquals(firstPlayer, firstGame.getCurrentPlayer());

    }

   
    @Test
    void remainingCoins(){
        controller.initializeLobby();

        controller.joinLobby("group01-0", "john");
        controller.joinLobby("group01-0", "danny");
        controller.setReady("john","group01-0");
        controller.setReady("danny","group01-0");

        Game game = controller.getOngoingGames().get("group01-0");

        int allGeneratedCoins = Bank.generateAllCoins().size();

        int amountCoinsP1 = game.getPlayers().get(0).getMoney().getCoinsInBag().size();
        int amountCoinsP2 = game.getPlayers().get(1).getMoney().getCoinsInBag().size();
        int amountCoinsOnBoard = 4;
        int coinsDealt = amountCoinsP1 + amountCoinsP2 + amountCoinsOnBoard;

        assertEquals((allGeneratedCoins - coinsDealt), game.getBank().getAmountOfCoins());


    }

    @Test
    void playerOrderTest(){

        // No idea on how to exactly assert this test
        controller.initializeLobby();

        controller.joinLobby("group01-0", "john");
        controller.joinLobby("group01-0", "freddy");
        controller.joinLobby("group01-0", "danny");
        controller.setReady("john","group01-0");
        controller.setReady("danny","group01-0");
        controller.setReady("freddy","group01-0");

        Game game = controller.getOngoingGames().get("group01-0");

        System.out.println(game.getPlayers().get(0).getName() + game.getPlayers().get(0).getMoney().getCoinsInBag().size() + " " + game.getPlayers().get(0).getMoney().calculateTotalCoinBagValue());
        System.out.println(game.getPlayers().get(1).getName() + game.getPlayers().get(1).getMoney().getCoinsInBag().size() + " " + game.getPlayers().get(1).getMoney().calculateTotalCoinBagValue());
        System.out.println(game.getPlayers().get(2).getName() + game.getPlayers().get(2).getMoney().getCoinsInBag().size() + " " + game.getPlayers().get(2).getMoney().calculateTotalCoinBagValue());
        System.out.println(game.getCurrentPlayer().getName());

    }

    @Test
    void equals() {
        AlhambraController controller = new AlhambraController();
        controller.initializeLobby();
        controller.joinLobby("group01-0", "john");
        controller.joinLobby("group01-0", "danny");
        controller.setReady("john","group01-0");
        controller.setReady("danny","group01-0");

        controller.initializeLobby();
        controller.joinLobby("group01-1", "freddy");
        controller.joinLobby("group01-1", "jeff");
        controller.setReady("freddy","group01-1");
        controller.setReady("jeff","group01-1");
        Game testGame1 = controller.getOngoingGames().get("group01-0");
        Game testGame2 = controller.getOngoingGames().get("group01-0");
        Game testGame3 = controller.getOngoingGames().get("group01-1");
        Game testGame4 = controller.getOngoingGames().get("group01-4");
        assertNotNull(testGame1);
        assertNotEquals(testGame3, testGame1);
        assertEquals(testGame1, testGame2);
        assertFalse(controller.getOngoingGames().containsKey("group01-4"));
    }
    @Test
    void hashCodeTest() {
        AlhambraController controller = new AlhambraController();
        controller.initializeLobby();
        controller.joinLobby("group01-0", "john");
        controller.joinLobby("group01-0", "danny");
        controller.setReady("john","group01-0");
        controller.setReady("danny","group01-0");

        controller.initializeLobby();
        controller.joinLobby("group01-1", "freddy");
        controller.joinLobby("group01-1", "jeff");
        controller.setReady("freddy","group01-1");
        controller.setReady("jeff","group01-1");
        Game testGame1 = controller.getOngoingGames().get("group01-0");
        Game testGame2 = controller.getOngoingGames().get("group01-0");
        Game testGame3 = controller.getOngoingGames().get("group01-1");

        assertNotSame(testGame1, testGame3);
        assertNotEquals(testGame1.hashCode(), testGame3.hashCode());
        assertEquals(testGame1.hashCode(), testGame2.hashCode());
    }
}
