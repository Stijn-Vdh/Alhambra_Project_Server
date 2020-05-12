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
        controller.joinGame("group01-0", "john");
        assertEquals(1, firstGame.getPlayers().size());

        assertThrows(AlhambraEntityNotFoundException.class, () -> controller.joinGame("group01-5159", "john"));
    }

    @Test
    void startGame(){
        controller.initializeLobby();

        controller.joinGame("group01-0", "john");
        controller.joinGame("group01-0", "danny");

        controller.setReadyState("john","group01-0");
        assertEquals(1, controller.getLobbies().size());

        controller.setReadyState("danny","group01-0");

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
        assertFalse(denJohn.getBag().computeTotalCoinsValue() < 20);
        assertFalse(denJohn.getBag().computeTotalCoinsValue() > 28);

        assertNotNull(denEddy.getBag());
        assertFalse(denEddy.getBag().computeTotalCoinsValue() < 20);
        assertFalse(denEddy.getBag().computeTotalCoinsValue() > 28);
    }

    @Test
    void changeCurrentPlayer(){
        controller.initializeLobby();

        controller.joinGame("group01-0", "john");
        controller.joinGame("group01-0", "danny");

        controller.setReadyState("john","group01-0");
        controller.setReadyState("danny","group01-0");

        Game firstGame = controller.getOngoingGames().get("group01-0");

        assertEquals("john", firstGame.getCurrentPlayer().getName());
        firstGame.changeCurrentPlayer();
        assertEquals("danny", firstGame.getCurrentPlayer().getName());
        firstGame.changeCurrentPlayer();
        assertEquals("john", firstGame.getCurrentPlayer().getName());
    }

    @Test
    void takeMoneyTest(){

        List<Coin> coins = new ArrayList<>();

        controller.initializeLobby();

        controller.joinGame("group01-0", "john");
        controller.joinGame("group01-0", "danny");
        controller.setReadyState("john","group01-0");
        controller.setReadyState("danny","group01-0");

        Game game = controller.getOngoingGames().get("group01-0");
        Coin firstCoin = game.getBank().getCoinsOnBoard().get(0);

        coins.add(firstCoin);
        Player firstPlayer = game.getPlayers().get(0);

        int bagSize = firstPlayer.getBag().getCoins().size(); 
        controller.takeMoney(firstPlayer.getName(), "group01-0", coins);
        assertEquals(bagSize+1 , firstPlayer.getBag().getCoins().size());

        Coin fakeCoin = new Coin(Currency.YELLOW, 99);
        coins.add(fakeCoin);

        assertThrows(AlhambraGameRuleException.class, () -> controller.takeMoney("danny", "group01-0", coins));
        assertThrows(AlhambraGameRuleException.class, () -> controller.takeMoney("John", "group01-0", coins));


    }


}
