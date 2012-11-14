package view;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.GameProgressSaveInfo;

import view.communication.ClientHandler;
import view.communication.protocol.GameHostInfo;

public class JoinGame {
	
    private JComponent rootComp;
    private final String host = "tintin.cs.indiana.edu:8096";
    private final String path = "/GameMakerServer";
    private final String urlListAllGameBases = "/listAllGameBases";
    private final String urlLoadGameBase = "/loadGameBase";
	
	public JoinGame (JComponent rootComp){
		
		this.rootComp= rootComp;

	}
	
	
	public void displayJoinGames(){
		
		Exception[] exceptions = new Exception[1];
		String[] gameNames={"aaaa","bbbbb"};
		
		String chosen = (String) JOptionPane.showInputDialog(
                rootComp,
                "List of join games :",
                "Join games",
                JOptionPane.PLAIN_MESSAGE,
                null, gameNames,
                null);
		
		/*if (chosen == null) {
            return null;
        }

        String gameData = ClientHandler.loadGameBase(chosen, host, path + urlLoadGameBase, exceptions);

        if (exceptions[0] != null) {
            JOptionPane.showMessageDialog(rootComp, exceptions[0].toString());
            return null;
        }

        GameProgressSaveInfo.getInstance().setLoadedGameName(chosen);

        return gameData;*/
		
	}
	
}