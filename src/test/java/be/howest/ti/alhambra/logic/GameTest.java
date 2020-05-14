package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.game.Game;
import be.howest.ti.alhambra.logic.game.Lobby;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;
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
        assertEquals(0, firstGame.getPlayers().size());
        // test when player is in game
        controller.joinLobby("group01-0", "john");
        controller.joinLobby("group01-0", "danny");
        controller.setReady("john","group01-0");
        controller.setReady("danny","group01-0");

        System.out.println(controller.getGameState("group01-0"));
        controller.leaveGame("group01-0", "danny");
        assertEquals(1, controller.getOngoingGames().get("group01-0").getPlayers().size());
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

        assertNotNull(denJohn.getBag());
        List<Coin> johnsCoinsInBag = denJohn.getBag().getCoinsInBag();
        List<Coin> eddiesCoinsInBag = denEddy.getBag().getCoinsInBag();

        assertFalse(game.getBank().totalValueCoins(johnsCoinsInBag) < 20);
        assertFalse(game.getBank().totalValueCoins(johnsCoinsInBag) > 28);

        assertNotNull(denEddy.getBag());
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

        assertEquals("john", firstGame.getCurrentPlayer().getName());
        firstGame.changeCurrentPlayer();
        assertEquals("danny", firstGame.getCurrentPlayer().getName());
        firstGame.changeCurrentPlayer();
        assertEquals("john", firstGame.getCurrentPlayer().getName());
    }
}
