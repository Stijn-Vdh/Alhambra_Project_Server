package be.howest.ti.alhambra.webapi;

import be.howest.ti.alhambra.logic.AlhambraController;
import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.game.Location;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultAlhambraOpenAPI3Bridge implements AlhambraOpenAPI3Bridge {


    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAlhambraOpenAPI3Bridge.class);
    private final AlhambraController controller;
    private static final String PLAYER_NAME = "playerName";
    private static final String GAME_ID = "gameId";
    public DefaultAlhambraOpenAPI3Bridge(){
        this.controller = new AlhambraController();
    }

    public boolean verifyAdminToken(String token) {
        LOGGER.info("\nverifyAdminToken \n");
        return token.contains("d49aedca2af303f3439c9ddcfaa6c534");
    }

    public boolean verifyPlayerToken(String token, String gameId, String playerName) {
        LOGGER.info("\nverifyPlayerToken \n");
        if (playerName == null) {
            int index = token.indexOf('+');
            playerName = token.substring(index+1);
        }

        return token.equals(gameId + "+" + playerName);
    }

    public Object getBuildings(RoutingContext ctx) {
        LOGGER.info("\nReturning all buildings \n");
        return controller.getAllBuildings();
    }

    public Object getAvailableBuildLocations(RoutingContext ctx) {
        LOGGER.info("\nReturning all available build locations \n");
        return null;
    }

    public Object getBuildingTypes(RoutingContext ctx) {
        LOGGER.info("\nReturning all building types \n");
        return controller.getBuildingTypes();
    }

    public Object getCurrencies(RoutingContext ctx) {
        LOGGER.info("\nReturning all currencies \n");
        return controller.getCurrencies();
    }

    public Object getScoringTable(RoutingContext ctx) {
        LOGGER.info("\nReturned scoring table \n");
        return null;
    }

    public Object getGames(RoutingContext ctx) {
        LOGGER.info("\nReturned all current games \n");
        return controller.getGameIds();
    }

    public Object createGame(RoutingContext ctx) {
        LOGGER.info("\n\nNew game has been created \n");
        return controller.initializeLobby();
    }

    public Object clearGames(RoutingContext ctx) {
        LOGGER.info("\nClearing all current games from the server \n");
        controller.clearAllGames();
        return controller.getLobbies();
    }

    public Object joinGame(RoutingContext ctx) {
        String id = ctx.request().getParam(GAME_ID);
        String body = ctx.getBodyAsString();
        JsonObject obj = new JsonObject(body);
        String name = obj.getString(PLAYER_NAME);
        LOGGER.info("\nPlayer("+name+") has joined the lobby("+id+") \n");
        return controller.joinLobby(id, name);
    }


    public Object leaveGame(RoutingContext ctx) {
        String id = ctx.request().getParam(GAME_ID);
        String name = ctx.request().getParam(PLAYER_NAME);
        LOGGER.info("\nPlayer("+name+") has left the game("+id+") \n");
        return controller.leaveGame(id, name);
    }

    public Object setReady(RoutingContext ctx) {
        String name = ctx.request().getParam(PLAYER_NAME);
        String gameID = ctx.request().getParam(GAME_ID);
        LOGGER.info("\nPlayer("+name+") is ready \n");
        return controller.setReady(name,gameID);
    }

    public Object setNotReady(RoutingContext ctx) {
        String name = ctx.request().getParam(PLAYER_NAME);
        LOGGER.info("\nPlayer("+name+") is no longer ready \n");
        return controller.setNotReady(name);
    }

    public Object takeMoney(RoutingContext ctx) {
        String gameId = ctx.request().getParam(GAME_ID);
        String name = ctx.request().getParam(PLAYER_NAME);

        String body = ctx.getBodyAsString();
        Coin[] coins = Json.decodeValue(body, Coin[].class);
        List<Coin> selectedCoins = new ArrayList<>(Arrays.asList(coins));

        LOGGER.info("\nPlayer("+name+") took money from the gameBoard \n");
        return controller.takeMoney(name, gameId, selectedCoins);
    }

    public Object buyBuilding(RoutingContext ctx) {
        String gameId = ctx.request().getParam(GAME_ID);
        String name = ctx.request().getParam(PLAYER_NAME);

        String body = ctx.getBodyAsString();
        Currency currency = Json.decodeValue(body, Currency.class);
        Coin[] coins = Json.decodeValue(body, Coin[].class);
        List<Coin> selectedCoins = new ArrayList<>(Arrays.asList(coins));

        LOGGER.info("\nPlayer("+name+") bought a building from the gameBoard \n");
        return controller.buyBuilding(gameId, name, currency, selectedCoins);
    }


    public Object redesign(RoutingContext ctx) {
        String name = ctx.request().getParam(PLAYER_NAME);
        LOGGER.info("\nPlayer("+name+") redesigned his city \n");
        return null;
    }

    public Object build(RoutingContext ctx) {
        String name = ctx.request().getParam(PLAYER_NAME);
        String gameId = ctx.request().getParam(GAME_ID);

        String body = ctx.getBodyAsString();
        Building building = Json.decodeValue(body, Building.class);
        Location location = Json.decodeValue(body, Location.class);

        LOGGER.info("\nPlayer("+name+") build a building in his city \n");
        return controller.placeBuildingOnBoard(gameId, name, building, location);
    }

    public Object getGame(RoutingContext ctx) {
        String gameId = ctx.request().getParam(GAME_ID);
        LOGGER.info("\nReturning game state for game("+gameId+") \n");
        return controller.getGameState(gameId);
    }

}
