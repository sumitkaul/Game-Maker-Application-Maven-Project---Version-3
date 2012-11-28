package view;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import loader.GameDataPackageIO;
import model.GameProgressSaveInfo;
import multiplayer.Receiver;
import utility.Constants;
import view.communication.ClientHandler;

public class JoinGame {

    private JComponent rootComp;
    private final String host = Constants.HOST;
    private final String path = Constants.PATH;
    private final String urlListAllHostGames = "/loadHostGames";
    private final String urlloadHostGames = "/loadGameBase";
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(JoinGame.class);

    public JoinGame(JComponent rootComp) {

        this.rootComp = rootComp;

    }

    public String displayJoinGames() {
        String[] gameNames = null;
        try {
            gameNames = ClientHandler.loadHostGames(host, path + urlListAllHostGames);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootComp, ex.toString());
            return null;
        }
        LOG.info("The gamenames are " + gameNames);
        String chosen = (String) JOptionPane.showInputDialog(
                rootComp,
                "List of join games :",
                "Join games",
                JOptionPane.PLAIN_MESSAGE,
                null, gameNames,
                null);

        if (chosen == null) {
            return null;
        }

        String gameData;
        try {
            gameData = ClientHandler.loadGameBase(chosen, host, path + urlloadHostGames);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootComp, ex.toString());
            return null;
        }

        GameProgressSaveInfo.getInstance().setLoadedGameName(chosen);
        GameDataPackageIO.loadGamePackageFromFile(gameData);
        return chosen;

    }
}
