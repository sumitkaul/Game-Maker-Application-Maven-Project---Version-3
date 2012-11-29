package action;

import eventlistener.EventListener;
import eventlistener.NewFrameEventListener;
import java.io.Serializable;
import java.util.List;
import model.SpriteModel;
import utility.Helper;
import utility.SpriteList;
import view.GameMakerView;

@SuppressWarnings("serial")
public class ActionCreateSpriteModel implements GameAction, Serializable {

    @Override
    public void doAction(SpriteModel spriteModel) {

        double heading = spriteModel.getHeading();
        double cosValue = Math.cos(heading * Math.PI / 180) * 10;
        double sinValue = Math.sin(heading * Math.PI / 180) * 10;

        double x = sinValue / 10;
        double y = -cosValue / 10;

        SpriteModel newSpriteModel = new SpriteModel((spriteModel.getPosX() + (spriteModel.getWidth() / 2)), (spriteModel.getPosY() + 5), x, y, 7, 12, spriteModel.getImageUrlString(), spriteModel.getLayer(), spriteModel.getImageId());

        SpriteList.getInstance().addSprite(newSpriteModel);
        GameMakerView.getInstance().getFacade().addSpriteModelToView(newSpriteModel);
        newSpriteModel.setGroupId("Bomb");


        boolean foundBombMoveListener = false;
        List<EventListener> listenerList = GameMakerView.getInstance().getFacade().getGameController().getEvents();
        for (EventListener listener : listenerList) {
            if (listener instanceof NewFrameEventListener) {
                NewFrameEventListener newFrameListerner = (NewFrameEventListener) listener;
                String groupId = newFrameListerner.getRegisteredGroupId();
                if (groupId.equalsIgnoreCase("Bomb")) {
                    foundBombMoveListener = true;
                }
            }

        }

        if (!foundBombMoveListener) {
            EventListener listener = Helper.getsharedHelper().getEventListenerForString("New Frame", "Move", newSpriteModel, null);
            GameMakerView.getInstance().getFacade().getGameController().registerListener(listener);
        }



        for (SpriteModel sprite : SpriteList.getInstance().getSpriteList()) {
            if ((!sprite.getGroupId().equalsIgnoreCase("Bomb")) && sprite != spriteModel) {
                EventListener listener1 = Helper.getsharedHelper().getEventListenerForString("Collision", "remove", newSpriteModel, sprite);
                GameMakerView.getInstance().getFacade().getGameController().registerListener(listener1);
            }
        }


        for (SpriteModel sprite : SpriteList.getInstance().getSpriteList()) {

            if (!sprite.getGroupId().equalsIgnoreCase("Bomb")) {
                if (!sprite.equals(spriteModel)) {
                    EventListener listener1 = Helper.getsharedHelper().getEventListenerForString("Collision", "remove", sprite, newSpriteModel);
                    GameMakerView.getInstance().getFacade().getGameController().registerListener(listener1);
                }
            }


        }
    }
}