package DEBUG;

import TrafficLightSystem.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Group 3
 */
public class guiTest {

    private static Window Map;

    public static void main(String arg[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JPanel ControlPanel = new JPanel();

                JButton holdWE = new JButton("Hold Lights 1 & 3");
                holdWE.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Hold Lights 1 & 3");
                    }
                });

                JButton holdNS = new JButton("Hold Lights 2 & 4");
                holdNS.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Hold Lights 2 & 4");
                    }
                });

                JButton stats = new JButton("Stats");
                stats.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Reporting Stats");
                    }
                });

                ControlPanel.add(holdWE);
                ControlPanel.add(holdNS);
                ControlPanel.add(stats);

                Map = new Window(null);
                JFrame aFrame = new JFrame();
                //Create a split pane with the two scroll panes in it.
                JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, Map, ControlPanel);
                splitPane.setOneTouchExpandable(false);
                splitPane.setDividerLocation(322);
                aFrame.add(splitPane);
                aFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                aFrame.pack();
                aFrame.setSize(655, 450);
                aFrame.setResizable(false);
                aFrame.setVisible(true);
            }
        });

        while (true) {
            waitFor();
            Map.animateCars(0, 1);
            waitFor();
            Map.animateCars(0, 2);
            waitFor();
            Map.animateCars(0, 3);
            waitFor();
            Map.animateCars(1, 2);
            waitFor();
            Map.animateCars(1, 3);
            waitFor();
            Map.animateCars(1, 0);
            waitFor();
            Map.animateCars(2, 3);
            waitFor();
            Map.animateCars(2, 0);
            waitFor();
            Map.animateCars(2, 1);
            waitFor();
            Map.animateCars(3, 0);
            waitFor();
            Map.animateCars(3, 1);
            waitFor();
            Map.animateCars(3, 2);
            waitFor();
            Map.animateCars(0, 2);
            Map.animateCars(2, 0);
            Map.animateCars(1, 3);
            Map.animateCars(3, 1);
            waitFor();
            Map.animateCars(3, 0);
            Map.animateCars(2, 3);
            Map.animateCars(1, 2);
            Map.animateCars(0, 1);
            waitFor();
            Map.animateCars(0, 3);
            Map.animateCars(3, 2);
            Map.animateCars(2, 1);
            Map.animateCars(1, 0);
            waitFor();
            Map.animateCars(0, 1);
            Map.animateCars(3, 1);
            Map.animateCars(2, 1);
            waitFor();
            Map.animateCars(0, 2);
            Map.animateCars(3, 2);
            Map.animateCars(1, 2);
            waitFor();
            Map.animateCars(0, 3);
            Map.animateCars(2, 3);
            Map.animateCars(1, 3);
            waitFor();
            Map.animateCars(3, 0);
            Map.animateCars(2, 0);
            Map.animateCars(1, 0);
        }
    }

    private static void waitFor() {
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < (1000)) {
            //just keep looping...
        }
    }
}
