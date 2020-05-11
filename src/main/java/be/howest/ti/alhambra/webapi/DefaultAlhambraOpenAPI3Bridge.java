package be.howest.ti.alhambra.webapi;

import be.howest.ti.alhambra.logic.AlhambraController;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.player.Player;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;

public class DefaultAlhambraOpenAPI3Bridge implements AlhambraOpenAPI3Bridge {


    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAlhambraOpenAPI3Bridge.class);
    private final AlhambraController controller;
    private String playerName = "playerName";
    public DefaultAlhambraOpenAPI3Bridge(){
        this.controller = new AlhambraController();
    }

    public boolean verifyAdminToken(String token) {
        LOGGER.info("verifyPlayerToken");
        return token.contains("admin");
    }

    public boolean verifyPlayerToken(String token, String gameId, String playerName) {
        LOGGER.info("verifyPlayerToken");
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
        return controller.initializeGame();
    }

    public Object clearGames(RoutingContext ctx) {
        LOGGER.info("clearGames");
        controller.clearAllGames();
        return controller.getGames();
    }

    public Object joinGame(RoutingContext ctx) {
        LOGGER.info("joinGame");
        String gameID = ctx.request().getParam("gameId");
        String body = ctx.getBodyAsString();
        JsonObject obj = new JsonObject(body);
        String name = obj.getString("playerName");
        return controller.joinGame(gameID, name);
    }


    public Object leaveGame(RoutingContext ctx) {
        LOGGER.info("leaveGame");
        return null;
    }

    public Object setReady(RoutingContext ctx) {
        LOGGER.info("setReady");
        String name = ctx.request().getParam(playerName);
        return controller.setReadyState(name);
    }

    public Object setNotReady(RoutingContext ctx) {
        LOGGER.info("setNotReady");
        String name = ctx.request().getParam(playerName);
        return controller.setReadyState(name);
    }

    public Object takeMoney(RoutingContext ctx) {
        LOGGER.info("takeMoney");

        String gameId = ctx.request().getParam("gameId");
        String name = ctx.request().getParam(playerName);

        String body = ctx.getBodyAsString();
        Coin[] coins = Json.decodeValue(body, Coin[].class);

        int totalAmount = controller.getTotalAmount(coins);

        return new JsonObject()
                .put("gameId", gameId)
                .put(playerName, name)
                .put("total", totalAmount);
    }

    public Object buyBuilding(RoutingContext ctx) {
        LOGGER.info("buyBuilding");
        return null;
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
        return null;
    }

}
