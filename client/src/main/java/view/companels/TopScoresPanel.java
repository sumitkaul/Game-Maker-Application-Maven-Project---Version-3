package view.companels;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import view.communication.ClientHandler;
import view.communication.protocol.GameSaveInfo;

public class TopScoresPanel {

    private JComponent rootComp;
    private final String host = "tintin.cs.indiana.edu:8096";
    private final String path = "/GameMakerServer";
    private final String urlListAllGameBases = "/listAllGameBases";
    private final String urlListTopScores = "/listTopScores";

    public TopScoresPanel(JComponent rootComp) {
        this.rootComp = rootComp;
    }

    public void readGameScoresFromRemoteList() {
        Exception[] exceptions = new Exception[1];
        String[] gameNames = ClientHandler.listAllGameBases(host, path + urlListAllGameBases, exceptions);

        if (exceptions[0] != null) {
            JOptionPane.showMessageDialog(rootComp, exceptions[0].toString());
            return;
        }

        String chosen = (String) JOptionPane.showInputDialog(
                rootComp,
                "Select Game from " + host,
                "Select a Game First",
                JOptionPane.PLAIN_MESSAGE,
                null, gameNames,
                null);

        if (chosen == null) {
            return;
        }

        GameSaveInfo[] gameSaves = ClientHandler.listTopScores(chosen, host, path + urlListTopScores, exceptions);

        if (exceptions[0] != null) {
            JOptionPane.showMessageDialog(rootComp, exceptions[0].toString());
            return;
        }

        if (gameSaves.length == 0) {
            JOptionPane.showMessageDialog(rootComp, "There is no records for this game yet");
            return;
        }

        String[] columnNames = {"Rank", "Player", "Score"};

        Object[][] data = new Object[gameSaves.length][];
        for (int i = 0; i < gameSaves.length; i++) {
            data[i] = new Object[]{gameSaves[i].getRank(), gameSaves[i].getGamePlayer(), gameSaves[i].getGameScore()};
        }

        JTable table = new JTable(data, columnNames);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);

        JOptionPane.showMessageDialog(rootComp, scrollPane, "Top Scores for " + chosen, JOptionPane.PLAIN_MESSAGE);
    }
}