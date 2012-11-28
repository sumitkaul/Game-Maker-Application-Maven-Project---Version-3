package multiplayer;

import action.GameAction;
import java.util.Collection;
import java.util.HashMap;
import java.util.Queue;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.SlickException;

import facade.Facade;
import game.engine.slick2d.player.GameEngineController;
import loader.GamePackage;
import model.SpriteModel;
import utility.ClockDisplay;
import utility.Helper;
import utility.Layers;
import utility.SpriteList;
import view.ButtonPanel;
import view.GamePlayerView;

public class Protocol {

    private ObjectMessage msg;
    private TextMessage text;
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ButtonPanel.class);

    public ObjectMessage createDataAsHost() {
    	GamePlayerView gamePlayerView = Helper.getsharedHelper().getGamePlayerView();
    	GameEngineController gameEngine = gamePlayerView.getGameEnginePanel().getGame();
        GamePackage game = new GamePackage(SpriteList.getInstance().getSpriteList(), gameEngine.getEventsForGameController(), gameEngine.getKeyEvents(), null, false);
        try {
            msg = SessionFactory.getInstanceOf().getSession().createObjectMessage();
            msg.setObject(game);
            msg.setJMSType("Sending as Host");
        } catch (JMSException e) {
            LOG.info("sending falied as host");
        }
        return msg;

    }

    public ObjectMessage createData(GameAction action, SpriteModel model) {
        HashMap<GameAction, SpriteModel> map = new HashMap<GameAction, SpriteModel>();
        map.put(action, model);

        try {


            msg = SessionFactory.getInstanceOf().getSession().createObjectMessage();
            msg.setObject(map);
            msg.setJMSType("Sending Actions");
        } catch (JMSException e) {
            LOG.info("sending Actions");
        }
        return msg;
    }
    public ObjectMessage createAcknowledgement(String data) {
        try {
            msg = SessionFactory.getInstanceOf().getSession().createObjectMessage(data);
        } catch (JMSException e) {
            LOG.info("creating Acknowledgement failed");
        }
        return msg;
    }

    public void setGameState(GamePackage game) {
        LOG.debug("load done");
//        Collection<SpriteModel> allSpriteModels = game.getSpriteList();
//        game.getLayers();
//        ClockDisplay.getInstance().setVisible(game.isClockDisplayable());
//        // SpriteList.getInstance().setSpriteList(allSpriteModels);
//        SpriteModel m = (SpriteModel) ((Queue) allSpriteModels).peek();
//        SpriteList.getInstance().setSelectedSpriteModel(m);
//        
//        GamePlayerView gamePlayerView = (GamePlayerView)Helper.getsharedHelper().getGamePlayerView();
////        Facade facade = gamePlayerView.getFacade();
////        
////        facade.getGameController().setEvents(game.getEventsForGameController());
////        facade.getKeyListenerController().setKeyEvents(game.getEventsForKeyController());
////        facade.createViewsForModels(game.getSpriteList());
//        GameEngineController gameEngine = gamePlayerView.getGameEnginePanel().getGame();
//        gameEngine.setEventsForGameController(game.getEventsForGameController());
//        gameEngine.setKeyEvents(game.getEventsForKeyController());
//        for (SpriteModel model : allSpriteModels) {
//            SpriteList.getInstance().addSprite(model);
//            SpriteList.getInstance().setSelectedSpriteModel(model);
//            LOG.info("The id of the object is " + model.getId());
//        }
        try {
        GameEngineController gameData = new GameEngineController(Subscribe.getInstanceOf().getQueueName().toString().substring(0, Subscribe.getInstanceOf().getQueueName().toString().indexOf("#")-1), game);
        CanvasGameContainer app;
		app = new CanvasGameContainer(gameData);
        app.getContainer().setTargetFrameRate(60);
        GamePlayerView gamePlayerView = Helper.getsharedHelper().getGamePlayerView();
        gamePlayerView.getGameEnginePanel().addGame(app);
        } catch (SlickException e) {
			e.printStackTrace();
		}
    }

    public void setMultiplayerAction(HashMap<GameAction, SpriteModel> map) {
        SpriteModel model = null;
        LOG.info("Setting multiplayer action");
        for (GameAction action : map.keySet()) {
            model = map.get(action);
            action.doAction(model);

        }

    }

	public ObjectMessage createStartSignal(String data) {
		try {
            msg = SessionFactory.getInstanceOf().getSession().createObjectMessage(data);
        } catch (JMSException e) {
            LOG.info("creating start signal failed");
        }
        return msg;
	}
}
