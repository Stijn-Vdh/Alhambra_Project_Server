package be.howest.ti.alhambra.webapi;

import be.howest.ti.alhambra.logic.AlhambraController;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import java.util.*;

public class DefaultAlhambraOpenAPI3Bridge implements AlhambraOpenAPI3Bridge {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAlhambraOpenAPI3Bridge.class);
    private final AlhambraController controller;
    private static final String PLAYER_NAME = "playerName";
    private static final String GAME_ID = "gameId";
    public DefaultAlhambraOpenAPI3Bridge(){
        this.controller = new AlhambraController();
    }

    public boolean verifyAdminToken(String token) {
        LOGGER.info("verifyPlayerToken");
        return token.contains("VandenDriessche");
    }

    public boolean verifyPlayerToken(String token, String gameId, String playerName) {
        LOGGER.info("verifyPlayerToken");
        if (playerName == null) {
            int index = token.indexOf('+');
            playerName = token.substring(index+1);
        }

        return token.equals(gameId + "+" + playerName);
    }

    public Object getBuildings(RoutingContext ctx) {
        LOGGER.info("getBuildings");
        return controller.getAllBuildings();
    }

    public Object getAvailableBuildLocations(RoutingContext ctx) {
        LOGGER.info("getAvailableBuildLocations");
        return null;
    }

    public Object getBuildingTypes(RoutingContext ctx) {
        LOGGER.info("getBuildingTypes");
        return controller.getBuildingTypes();
    }

    public Object getCurrencies(RoutingContext ctx) {
        LOGGER.info("getCurrencies");
        return controller.getCurrencies();
    }

    public Object getScoringTable(RoutingContext ctx) {
        LOGGER.info("getScoringTable");
        return null;
    }

    public Object getGames(RoutingContext ctx) {
        LOGGER.info("getGames");
        return controller.getGameIds();
    }

    public Object createGame(RoutingContext ctx) {
        LOGGER.info("createGame");
        return controller.initializeLobby();
    }

    public Object clearGames(RoutingContext ctx) {
        LOGGER.info("clearGames");
        controller.clearAllGames();
        return controller.getLobbies();
    }

    public Object joinGame(RoutingContext ctx) {
        LOGGER.info("joinGame");
        String id = ctx.request().getParam(GAME_ID);
        String body = ctx.getBodyAsString();
        JsonObject obj = new JsonObject(body);
        String name = obj.getString(PLAYER_NAME);
        return controller.joinLobby(id, name);
    }


    public Object leaveGame(RoutingContext ctx) {
        LOGGER.info("leaveGame");
        String id = ctx.request().getParam(GAME_ID);
        String name = ctx.request().getParam(PLAYER_NAME);
        return controller.leaveGame(id, name);
    }

    public Object setReady(RoutingContext ctx) {
        LOGGER.info("setReady");
        String name = ctx.request().getParam(PLAYER_NAME);
        String gameID = ctx.request().getParam(GAME_ID);
        return controller.setReady(name,gameID);
    }

    public Object setNotReady(RoutingContext ctx) {
        LOGGER.info("setNotReady");
        String name = ctx.request().getParam(PLAYER_NAME);
        return controller.setNotReady(name);
    }

    public Object takeMoney(RoutingContext ctx) {
        LOGGER.info("takeMoney");

        String gameId = ctx.request().getParam(GAME_ID);
        String name = ctx.request().getParam(PLAYER_NAME);

        String body = ctx.getBodyAsString();
        Coin[] coins = Json.decodeValue(body, Coin[].class);
        List<Coin> selectedCoins = new ArrayList<>(Arrays.asList(coins));

        return controller.takeMoney(name, gameId, selectedCoins);
    }

    public Object buyBuilding(RoutingContext ctx) {
        LOGGER.info("buyBuilding");
        String gameId = ctx.request().getParam(GAME_ID);
        String name = ctx.request().getParam(PLAYER_NAME);

        JsonObject body = ctx.getBodyAsJson();

        Currency currency = Currency.valueOf(body.getString("currency").toUpperCase());
        Coin[] coins = Json.decodeValue(body.getJsonArray("coins").toString(), Coin[].class);

        List<Coin> selectedCoins = new ArrayList<>(Arrays.asList(coins));
        return controller.buyBuilding(gameId, name, currency, selectedCoins);
    }

    public Object redesign(RoutingContext ctx) {
        LOGGER.info("redesign");
        return null;
    }

    public Object build(RoutingContext ctx) {
        LOGGER.info("build");
        return null;
    }

    public Object getGame(RoutingContext ctx) {
        LOGGER.info("getGame");
        return controller.getGameState(ctx.request().getParam(GAME_ID));
    }

}
