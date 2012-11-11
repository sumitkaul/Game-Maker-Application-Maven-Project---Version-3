package action;

import java.awt.Rectangle;
import java.util.List;

import javax.swing.JOptionPane;

import utility.SpriteList;
import view.Design;

import model.SpriteModel;

public class ActionChangeGameStatus implements GameAction {

    private final boolean YES = true;
    private final boolean NO = false;

    private boolean isGameStopCommandIssued = NO;

    String message = "";

    public ActionChangeGameStatus(boolean isWin) {
	if (isWin)
	    message = "You Win";
	else
	    message = "You Lose";
    }

    @Override
    public void doAction(SpriteModel model) {
	double xSpeed = model.getSpeedX();
	double ySpeed = model.getSpeedY();

	Rectangle xReversed = (Rectangle) model.getBoundingBox();
	Rectangle yReversed = (Rectangle) model.getBoundingBox();
	xReversed.x -= xSpeed;
	yReversed.y -= ySpeed;
	List<SpriteModel> spriteModels = SpriteList.getInstance().getSpriteList();
	for (SpriteModel obj : spriteModels) {
	    if (obj.equals(spriteModels))
		continue;
	    if (model.intersects(obj.getBoundingBox())) {
		/*
		 * used a boolean isGameStopCommandIssued so that the
		 * showMessageDialog is not executed again for this for loop.
		 */
		if (!isGameStopCommandIssued) {
		    if (!xReversed.intersects(obj.getBoundingBox())) {
			JOptionPane.showMessageDialog(null, message);
			Design.getInstance().getFacade().stopGame();
			isGameStopCommandIssued = YES;
		    }
		    if (!yReversed.intersects(obj.getBoundingBox())) {
			JOptionPane.showMessageDialog(null, message);
			Design.getInstance().getFacade().stopGame();
			isGameStopCommandIssued = YES;
		    } else if (yReversed.intersects(obj.getBoundingBox()) && xReversed.intersects(obj.getBoundingBox())) {
			JOptionPane.showMessageDialog(null, message);
			Design.getInstance().getFacade().stopGame();
			isGameStopCommandIssued = YES;
		    }
		}

	    }
	}
    }

}