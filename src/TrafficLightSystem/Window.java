package TrafficLightSystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author Group 3
 */
public class Window extends JPanel implements ActionListener, ItemListener {

    public static final int RED = 0;
    public static final int REDAMBER = 1;
    public static final int AMBER = 2;
    public static final int GREEN = 3;

    TrafficLightSystem tls;
    public Image imgTiles;
    public Image imgSprite[];
    public int mapWidth = 14;
    public int mapHeigh = 14;
    public int tileHeight = 32;
    public int tileWidth = 64;
    public int xPos = -160;
    public int yPos = 160;
    public TrafficData carPos[] = new TrafficData[4];
    public int[] lightColour = new int[4];
    JButton button;
    JCheckBox hold;
    Timer t;

    public Window(TrafficLightSystem _tls) {
        tls = _tls;
        carPos[0] = new TrafficData(0, 1);
        carPos[1] = new TrafficData(1, 2);
        carPos[2] = new TrafficData(2, 3);
        carPos[3] = new TrafficData(3, 0);
        lightColour[0] = RED;
        lightColour[1] = GREEN;
        lightColour[2] = RED;
        lightColour[3] = GREEN;
        JPanel ControlPanel = new JPanel();
        hold = new JCheckBox("Hold Lights");
        hold.addItemListener(this);

        button = new JButton("Pedestrian Crossing");
        button.addActionListener(this);

        ControlPanel.add(hold);
        ControlPanel.add(button);

        JFrame aFrame = new JFrame();
        //Create a split pane with the two scroll panes in it.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this, ControlPanel);
        splitPane.setOneTouchExpandable(false);
        splitPane.setDividerLocation(322);
        aFrame.add(splitPane);
        aFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aFrame.pack();
        aFrame.setSize(655, 450);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - (aFrame.getWidth())) / 2;
        int y = (screenSize.height - (aFrame.getHeight())) / 2;
        aFrame.setLocation(x, y);
        aFrame.setResizable(false);
        aFrame.setVisible(true);

        Path current = Paths.get("");
        String s = current.toAbsolutePath().toString();
        try {
            imgTiles = ImageIO.read(new File(s + "\\src\\TrafficLightSystem\\T.png"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        imgSprite = getSpriteImg();
    }

    @Override
    public void paintComponent(Graphics g) {
        setBackground(Color.BLACK);
        super.paintComponent(g);
        paintMap(g);
        paintRoadMarkings(g);
        paintTrafficLights(g);
        paintQueueText(g);
        paintCars(g);
        repaint();
    }

    private void paintCars(Graphics g) {
        for (int loop = 0; loop < 4; loop++) {
            if (!((carPos[loop].getLocationX() == new TrafficData(loop, 0).getLocationX())
                    && (carPos[loop].getLocationY() == new TrafficData(loop, 0).getLocationY()))) {
                double x = ((carPos[loop].getLocationX() * tileWidth / 2) + (carPos[loop].getLocationY() * tileWidth / 2)) + xPos;
                double y = ((carPos[loop].getLocationY() * tileHeight / 2) - (carPos[loop].getLocationX() * tileHeight / 2)) + yPos;
                g.drawImage(imgSprite[carPos[loop].getDirection()], (int) x, (int) y, this);
            }
        }
    }

    private void paintTrafficLights(Graphics g) {

        int i = 5;
        int j = 4;
        int x = ((j * tileWidth / 2) + (i * tileWidth / 2)) + xPos - 8;
        int y = ((i * tileHeight / 2) - (j * tileHeight / 2)) + yPos - 8;
        g.setColor(Color.red);
        if (lightColour[0] == RED || lightColour[0] == REDAMBER) {
            g.fillOval(x, y, 16, 16);
        } else {
            g.drawOval(x, y, 16, 16);
        }

        i = 5;
        j = 3;
        x = ((j * tileWidth / 2) + (i * tileWidth / 2)) + xPos - 8;
        y = ((i * tileHeight / 2) - (j * tileHeight / 2)) + yPos - 8;
        g.setColor(Color.yellow);
        if (lightColour[0] == AMBER || lightColour[0] == REDAMBER) {
            g.fillOval(x, y, 16, 16);
        } else {
            g.drawOval(x, y, 16, 16);
        }

        i = 5;
        j = 2;
        x = ((j * tileWidth / 2) + (i * tileWidth / 2)) + xPos - 8;
        y = ((i * tileHeight / 2) - (j * tileHeight / 2)) + yPos - 8;
        g.setColor(Color.green);
        if (lightColour[0] == GREEN) {
            g.fillOval(x, y, 16, 16);
        } else {
            g.drawOval(x, y, 16, 16);
        }
//==============================================================================
        i = 10;
        j = 11;
        x = ((j * tileWidth / 2) + (i * tileWidth / 2)) + xPos - 8;
        y = ((i * tileHeight / 2) - (j * tileHeight / 2)) + yPos - 8;
        g.setColor(Color.red);
        if (lightColour[2] == RED || lightColour[2] == REDAMBER) {
            g.fillOval(x, y, 16, 16);
        } else {
            g.drawOval(x, y, 16, 16);
        }

        i = 10;
        j = 12;
        x = ((j * tileWidth / 2) + (i * tileWidth / 2)) + xPos - 8;
        y = ((i * tileHeight / 2) - (j * tileHeight / 2)) + yPos - 8;
        g.setColor(Color.yellow);
        if (lightColour[2] == AMBER || lightColour[2] == REDAMBER) {
            g.fillOval(x, y, 16, 16);
        } else {
            g.drawOval(x, y, 16, 16);
        }

        i = 10;
        j = 13;
        x = ((j * tileWidth / 2) + (i * tileWidth / 2)) + xPos - 8;
        y = ((i * tileHeight / 2) - (j * tileHeight / 2)) + yPos - 8;
        g.setColor(Color.green);
        if (lightColour[2] == GREEN) {
            g.fillOval(x, y, 16, 16);
        } else {
            g.drawOval(x, y, 16, 16);
        }

        //======================================================================
        i = 11;
        j = 5;
        x = ((j * tileWidth / 2) + (i * tileWidth / 2)) + xPos - 8;
        y = ((i * tileHeight / 2) - (j * tileHeight / 2)) + yPos - 8;
        g.setColor(Color.red);
        if (lightColour[3] == RED || lightColour[3] == REDAMBER) {
            g.fillOval(x, y, 16, 16);
        } else {
            g.drawOval(x, y, 16, 16);
        }

        i = 12;
        j = 5;
        x = ((j * tileWidth / 2) + (i * tileWidth / 2)) + xPos - 8;
        y = ((i * tileHeight / 2) - (j * tileHeight / 2)) + yPos - 8;
        g.setColor(Color.yellow);
        if (lightColour[3] == AMBER || lightColour[3] == REDAMBER) {
            g.fillOval(x, y, 16, 16);
        } else {
            g.drawOval(x, y, 16, 16);
        }

        i = 13;
        j = 5;
        x = ((j * tileWidth / 2) + (i * tileWidth / 2)) + xPos - 8;
        y = ((i * tileHeight / 2) - (j * tileHeight / 2)) + yPos - 8;
        g.setColor(Color.green);
        if (lightColour[3] == GREEN) {
            g.fillOval(x, y, 16, 16);
        } else {
            g.drawOval(x, y, 16, 16);
        }

        //======================================================================
        i = 4;
        j = 10;
        x = ((j * tileWidth / 2) + (i * tileWidth / 2)) + xPos - 8;
        y = ((i * tileHeight / 2) - (j * tileHeight / 2)) + yPos - 8;
        g.setColor(Color.red);
        if (lightColour[1] == RED || lightColour[1] == REDAMBER) {
            g.fillOval(x, y, 16, 16);
        } else {
            g.drawOval(x, y, 16, 16);
        }

        i = 3;
        j = 10;
        x = ((j * tileWidth / 2) + (i * tileWidth / 2)) + xPos - 8;
        y = ((i * tileHeight / 2) - (j * tileHeight / 2)) + yPos - 8;
        g.setColor(Color.yellow);
        if (lightColour[1] == AMBER || lightColour[1] == REDAMBER) {
            g.fillOval(x, y, 16, 16);
        } else {
            g.drawOval(x, y, 16, 16);
        }

        i = 2;
        j = 10;
        x = ((j * tileWidth / 2) + (i * tileWidth / 2)) + xPos - 8;
        y = ((i * tileHeight / 2) - (j * tileHeight / 2)) + yPos - 8;
        g.setColor(Color.green);
        if (lightColour[1] == GREEN) {
            g.fillOval(x, y, 16, 16);
        } else {
            g.drawOval(x, y, 16, 16);
        }
    }

    private void paintMap(Graphics g) {
        int x, y;
        for (int i = 0; i < mapHeigh - 1; i++) {
            for (int j = mapWidth - 1; j > 0; j--) {
                x = ((j * tileWidth / 2) + (i * tileWidth / 2)) + xPos;
                y = ((i * tileHeight / 2) - (j * tileHeight / 2)) + yPos - 16;

                if ((i < 4 && j > 9)
                        || (i > 8 && j < 5)
                        || (i > 8 && j > 9)
                        || (i < 4 && j < 5)) {
                    continue;
                }
                g.drawImage(imgTiles, x, y, this);
            }
        }
    }

    private void paintRoadMarkings(Graphics g) {
        g.setColor(Color.yellow);
        int x, y;
        for (int i = 0; i < mapHeigh; i++) {
            for (int j = mapWidth; j >= 0; j--) {

                x = ((j * tileWidth / 2) + (i * tileWidth / 2)) + xPos;
                y = ((i * tileHeight / 2) - (j * tileHeight / 2)) + yPos;

                // North junction markings
                if (i == 4 && j <= 10 && j >= 5) {
                    g.drawLine(0 + x, tileHeight / 2 + y, tileHeight + x, 0 + y);
                }

                // South junction markings
                if (i == 9 && j <= 10 && j >= 5) {
                    g.drawLine(tileWidth + x, tileHeight / 2 + y, tileHeight + x, tileHeight + y);
                }

                // West junction markings
                if (j == 4 && i <= 9 && i >= 4) {
                    g.drawLine(tileHeight + x, 0 + y, tileWidth + x, tileHeight / 2 + y);
                }

                // East junction markings
                if (j == 11 && i <= 9 && i >= 4) {
                    g.drawLine(tileHeight + x, tileHeight + y, 0 + x, tileHeight / 2 + y);
                }

                // Vertical Road Markings
                if ((j == 7) && (((i > 9) && (i % 2 != 0)) || ((i < 4) && (i % 2 == 0)))) {
                    g.drawLine(tileHeight + x, 0 + y, tileWidth + x, tileHeight / 2 + y);
                }

                // Horizontal Road Markings
                if ((i == 7) && (((j > 10) && (j % 2 == 0)) || ((j < 4) && (j % 2 != 0)))) {
                    g.drawLine(0 + x, tileHeight / 2 + y, tileHeight + x, 0 + y);
                }
            }
        }
    }

    private void paintQueueText(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Font font = new Font(null, Font.PLAIN, 16);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(26), 0, 0);
        Font rotatedFont = font.deriveFont(affineTransform);
        g2.setFont(rotatedFont);

        int i = 5;
        int j = 1;
        int x = ((j * tileWidth / 2) + (i * tileWidth / 2)) + xPos + 4;
        int y = ((i * tileHeight / 2) - (j * tileHeight / 2)) + yPos + 16;
        g2.drawString(tls.Light1.size() + " cars", x, y);

        i = 8;
        j = 14;
        x = ((j * tileWidth / 2) + (i * tileWidth / 2)) + xPos + 24;
        y = ((i * tileHeight / 2) - (j * tileHeight / 2)) + yPos + 10;
        g2.drawString(tls.Light3.size() + " cars", x, y);

        //==============================================================
        font = new Font(null, Font.PLAIN, 16);
        affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(-26), 0, 0);
        rotatedFont = font.deriveFont(affineTransform);
        g2.setFont(rotatedFont);

        i = 1;
        j = 9;
        x = ((j * tileWidth / 2) + (i * tileWidth / 2)) + xPos - 16;
        y = ((i * tileHeight / 2) - (j * tileHeight / 2)) + yPos + 8;
        g2.drawString(tls.Light2.size() + " cars", x, y);

        i = 14;
        j = 6;
        x = ((j * tileWidth / 2) + (i * tileWidth / 2)) + xPos - 2;
        y = ((i * tileHeight / 2) - (j * tileHeight / 2)) + yPos + 16;
        g2.drawString(tls.Light4.size() + " cars", x, y);
    }

    private Image[] getSpriteImg() {
        Image[] local = new Image[4];
        try {
            Path current = Paths.get("");
            String s = current.toAbsolutePath().toString();
            BufferedImage a = ImageIO.read(new File(s + "\\src\\TrafficLightSystem\\cars.png"));
            local[0] = a.getSubimage(150, 0, 50, 30);
            local[1] = a.getSubimage(0, 0, 50, 30);
            local[2] = a.getSubimage(50, 0, 50, 30);
            local[3] = a.getSubimage(100, 0, 50, 30);
            return local;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public void animateCars(int start, int end) {

        carPos[start] = new TrafficData(start, end);

        if (start == 0 && end == 1) {
            animateCars0to1();
            System.out.println("0:1;");
        } else if (start == 0 && end == 2) {
            animateCars0to2();
            System.out.println("0:2;");
        } else if (start == 0 && end == 3) {
            animateCars0to3();
            System.out.println("0:3;");
        } else if (start == 1 && end == 2) {
            animateCars1to2();
            System.out.println("1:2;");
        } else if (start == 1 && end == 3) {
            animateCars1to3();
            System.out.println("1:3;");
        } else if (start == 1 && end == 0) {
            animateCars1to0();
            System.out.println("1:0;");
        } else if (start == 2 && end == 3) {
            animateCars2to3();
            System.out.println("2:3;");
        } else if (start == 2 && end == 0) {
            animateCars2to0();
            System.out.println("2:0;");
        } else if (start == 2 && end == 1) {
            animateCars2to1();
            System.out.println("2:1;");
        } else if (start == 3 && end == 0) {
            animateCars3to0();
            System.out.println("3:0;");
        } else if (start == 3 && end == 1) {
            animateCars3to1();
            System.out.println("3:1;");
        } else if (start == 3 && end == 2) {
            animateCars3to2();
            System.out.println("3:2;");
        }
    }

    private void animateCars0to1() {
        carPos[0] = new TrafficData(0, 1);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    if (i < 50) {
                        carPos[0].setLocationX(carPos[0].getLocationX() + 0.1);
                    } else if (i == 50) {
                        carPos[0].setDirection(TrafficData.fromS);
                    } else {
                        carPos[0].setLocationY(carPos[0].getLocationY() - 0.1);
                    }
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        System.err.println(e.getMessage());
                    }
                    repaint();
                }
                carPos[0] = new TrafficData(0, 1);
            }
        });
        t.start();
    }

    private void animateCars0to2() {
        carPos[0] = new TrafficData(0, 2);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 140; i++) {
                    carPos[0].setLocationX(carPos[0].getLocationX() + 0.1);
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        System.err.println(e.getMessage());
                    }
                    repaint();
                }
                carPos[0] = new TrafficData(0, 2);
            }
        });
        t.start();
    }

    private void animateCars0to3() {
        carPos[0] = new TrafficData(0, 3);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 160; i++) {
                    if (i > 80) {
                        carPos[0].setLocationY(carPos[0].getLocationY() + 0.1);
                    } else if (i == 80) {
                        carPos[0].setDirection(TrafficData.fromN);
                    } else {
                        carPos[0].setLocationX(carPos[0].getLocationX() + 0.1);
                    }
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        System.err.println(e.getMessage());
                    }
                    repaint();
                }
                carPos[0] = new TrafficData(0, 3);
            }
        });
        t.start();
    }

    private void animateCars1to2() {
        carPos[1] = new TrafficData(1, 2);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    if (i > 50) {
                        carPos[1].setLocationX(carPos[1].getLocationX() + 0.1);

                    } else if (i == 50) {
                        carPos[1].setDirection(TrafficData.fromW);
                    } else {
                        carPos[1].setLocationY(carPos[1].getLocationY() + 0.1);
                    }
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        System.err.println(e.getMessage());
                    }
                    repaint();
                }
                carPos[1] = new TrafficData(1, 2);
            }
        });
        t.start();
    }

    private void animateCars1to3() {
        carPos[1] = new TrafficData(1, 3);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 140; i++) {
                    carPos[1].setLocationY(carPos[1].getLocationY() + 0.1);
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        System.err.println(e.getMessage());
                    }
                    repaint();
                }
                carPos[1] = new TrafficData(1, 3);
            }
        });
        t.start();
    }

    private void animateCars1to0() {
        carPos[1] = new TrafficData(1, 0);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 160; i++) {
                    if (i > 80) {
                        carPos[1].setLocationX(carPos[1].getLocationX() - 0.1);

                    } else if (i == 80) {
                        carPos[1].setDirection(TrafficData.fromE);
                    } else {
                        carPos[1].setLocationY(carPos[1].getLocationY() + 0.1);
                    }
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        System.err.println(e.getMessage());
                    }
                    repaint();
                }
                carPos[1] = new TrafficData(1, 0);
            }
        });
        t.start();
    }

    private void animateCars2to3() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    if (i > 50) {
                        carPos[2].setLocationY(carPos[2].getLocationY() + 0.1);
                    } else if (i == 50) {
                        carPos[2].setDirection(TrafficData.fromN);
                    } else {
                        carPos[2].setLocationX(carPos[2].getLocationX() - 0.1);
                    }
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        System.err.println(e.getMessage());
                    }
                    repaint();
                }
                carPos[2] = new TrafficData(2, 3);
            }
        });
        t.start();
    }

    private void animateCars2to0() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 140; i++) {
                    carPos[2].setLocationX(carPos[2].getLocationX() - 0.1);
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        System.err.println(e.getMessage());
                    }
                    repaint();
                }
                carPos[2] = new TrafficData(2, 0);
            }
        });
        t.start();
    }

    private void animateCars2to1() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 160; i++) {
                    if (i > 80) {
                        carPos[2].setLocationY(carPos[2].getLocationY() - 0.1);
                    } else if (i == 80) {
                        carPos[2].setDirection(TrafficData.fromS);
                    } else {
                        carPos[2].setLocationX(carPos[2].getLocationX() - 0.1);
                    }
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        System.err.println(e.getMessage());
                    }
                    repaint();
                }
                carPos[2] = new TrafficData(2, 1);
            }
        });
        t.start();
    }

    private void animateCars3to0() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    if (i > 50) {
                        carPos[3].setLocationX(carPos[3].getLocationX() - 0.1);
                    } else if (i == 50) {
                        carPos[3].setDirection(TrafficData.fromE);
                    } else {
                        carPos[3].setLocationY(carPos[3].getLocationY() - 0.1);
                    }
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        System.err.println(e.getMessage());
                    }
                    repaint();
                }
                carPos[3] = new TrafficData(3, 0);
            }
        });
        t.start();
    }

    private void animateCars3to1() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 140; i++) {
                    carPos[3].setLocationY(carPos[3].getLocationY() - 0.1);
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        System.err.println(e.getMessage());
                    }
                    repaint();
                }
                carPos[3] = new TrafficData(3, 1);
            }
        });
        t.start();
    }

    private void animateCars3to2() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 160; i++) {
                    if (i > 80) {
                        carPos[3].setLocationX(carPos[3].getLocationX() + 0.1);
                    } else if (i == 80) {
                        carPos[3].setDirection(TrafficData.fromW);
                    } else {
                        carPos[3].setLocationY(carPos[3].getLocationY() - 0.1);
                    }
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        System.err.println(e.getMessage());
                    }
                    repaint();
                }
                carPos[3] = new TrafficData(3, 2);
            }
        });
        t.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            tls.controlSystem.buttonPushed = true;
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            tls.controlSystem.hold = true;
            t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    //JOptionPane.showMessageDialog(null,,,JOptionPane.WARNING_MESSAGE);

                    Object[] options = {"Release Lights",
                        "Keep Holding Lights"};
                    int n = JOptionPane.showOptionDialog(null,
                            "This has been held for a long time. \nWould you like to release it?",
                            "Release Lights?",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE,
                            null, //do not use a custom Icon
                            options, //the titles of buttons
                            options[0]); //default button title

                    if (n == JOptionPane.YES_OPTION) {
                        tls.Map.hold.setSelected(false);
                    }
                }
            }, 20 * 1000);

        } else if (e.getStateChange() == ItemEvent.DESELECTED) {
            tls.controlSystem.hold = false;
            t.cancel();
        }
    }
}
