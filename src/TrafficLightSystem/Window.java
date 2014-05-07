package TrafficLightSystem;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Group 3
 */
public class Window extends JPanel implements KeyListener {

    public static final int RED = 0;
    public static final int REDAMBER = 1;
    public static final int AMBER = 2;
    public static final int GREEN = 3;

    public Image imgTiles;
    public Image imgSprite[];
    public static int mapWidth = 14;
    public static int mapHeigh = 14;
    public static int tileHeight = 32;
    public static int tileWidth = 64;
    public static int xPos = -160;
    public static int yPos = 160;
    public static int speed = 10;
    public static TrafficData carPos[] = new TrafficData[4];
    public static int[] lightColour = new int[4];

    public Window() {
        carPos[0] = new TrafficData(0, 1);
        carPos[1] = new TrafficData(1, 2);
        carPos[2] = new TrafficData(2, 3);
        carPos[3] = new TrafficData(3, 0);
        lightColour[0] = RED;
        lightColour[1] = GREEN;
        lightColour[2] = RED;
        lightColour[3] = GREEN;
        
        
        Path current = Paths.get("");
        String s = current.toAbsolutePath().toString();
        imgTiles = Toolkit.getDefaultToolkit().getImage(s + "\\src\\TrafficLightSystem\\T.png");
        imgSprite = getSpriteImg();
        this.addKeyListener(this);
        this.setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        setBackground(Color.BLACK);
        super.paintComponent(g);
        paintMap(g);
        paintRoadMarkings(g);
        paintTrafficLights(g);
        printCars(g);
    }

    private void printCars(Graphics g) {
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

        repaint();
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
        for (int i = 0; i < mapHeigh; i++) {
            for (int j = mapWidth; j >= 0; j--) {

                int x = ((j * tileWidth / 2) + (i * tileWidth / 2)) + xPos;
                int y = ((i * tileHeight / 2) - (j * tileHeight / 2)) + yPos;

                if (i == 4 && j <= 10 && j >= 5) {
                    // topH
                    g.drawLine(0 + x, tileHeight / 2 + y, tileHeight + x, 0 + y);
                }

                if (i == 9 && j <= 10 && j >= 5) {
                    // bottomH
                    g.drawLine(tileWidth + x, tileHeight / 2 + y, tileHeight + x, tileHeight + y);
                }

                if (j == 4 && i <= 9 && i >= 4) {
                    // LeftV
                    g.drawLine(tileHeight + x, 0 + y, tileWidth + x, tileHeight / 2 + y);
                }

                if (j == 11 && i <= 9 && i >= 4) {
                    // rightV
                    g.drawLine(tileHeight + x, tileHeight + y, 0 + x, tileHeight / 2 + y);
                }

                // Vertical Road Markings
                if ((j == 7) && (((i > 9) && (i % 2 != 0)) || ((i < 4) && (i % 2 == 0)))) {
                    // leftV
                    g.drawLine(tileHeight + x, 0 + y, tileWidth + x, tileHeight / 2 + y);
                }

                // Horizontal Road Markings
                if ((i == 7) && (((j > 10) && (j % 2 == 0)) || ((j < 4) && (j % 2 != 0)))) {
                    // leftV
                    g.drawLine(0 + x, tileHeight / 2 + y, tileHeight + x, 0 + y);
                }
            }
        }
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
            return null;
        }
    }

    public void animateCars(int start, int end) {

        carPos[start] = new TrafficData(start, end);

        if (start == 0 && end == 1) {
            animateCars0to1();
        } else if (start == 0 && end == 2) {
            animateCars0to2();
        } else if (start == 0 && end == 3) {
            animateCars0to3();
        } else if (start == 1 && end == 2) {
            animateCars1to2();
        } else if (start == 1 && end == 3) {
            animateCars1to3();
        } else if (start == 1 && end == 0) {
            animateCars1to0();
        } else if (start == 2 && end == 3) {
            animateCars2to3();
        } else if (start == 2 && end == 0) {
            animateCars2to0();
        } else if (start == 2 && end == 1) {
            animateCars2to1();
        } else if (start == 3 && end == 0) {
            animateCars3to0();
        } else if (start == 3 && end == 1) {
            animateCars3to1();
        } else if (start == 3 && end == 2) {
            animateCars3to2();
        }

    }

    private void animateCars0to1() {
        Thread t = new Thread(new Runnable() {
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
                        Thread.sleep(9);
                    } catch (InterruptedException ex) {
                    }
                    repaint();
                }
                carPos[0] = new TrafficData(0, 1);
            }
        });
        t.start();
    }

    private void animateCars0to2() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 140; i++) {
                    carPos[0].setLocationX(carPos[0].getLocationX() + 0.1);
                    try {
                        Thread.sleep(6);
                    } catch (InterruptedException ex) {
                    }
                    repaint();
                }
                carPos[0] = new TrafficData(0, 2);
            }
        });
        t.start();
    }

    private void animateCars0to3() {
        Thread t = new Thread(new Runnable() {
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
                    } catch (InterruptedException ex) {
                    }
                    repaint();
                }
                carPos[0] = new TrafficData(0, 3);
            }
        });
        t.start();
    }

    private void animateCars1to2() {
        Thread t = new Thread(new Runnable() {
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
                        Thread.sleep(9);
                    } catch (InterruptedException ex) {
                    }
                    repaint();
                }
                carPos[1] = new TrafficData(1, 2);
            }
        });
        t.start();
    }

    private void animateCars1to3() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 140; i++) {
                    carPos[1].setLocationY(carPos[1].getLocationY() + 0.1);
                    try {
                        Thread.sleep(6);
                    } catch (InterruptedException ex) {
                    }
                    repaint();
                }
                carPos[1] = new TrafficData(1, 3);
            }
        });
        t.start();
    }

    private void animateCars1to0() {
        Thread t = new Thread(new Runnable() {
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
                    } catch (InterruptedException ex) {
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
                        Thread.sleep(9);
                    } catch (InterruptedException ex) {
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
            public void run() {
                for (int i = 0; i < 140; i++) {
                    carPos[2].setLocationX(carPos[2].getLocationX() - 0.1);
                    try {
                        Thread.sleep(6);
                    } catch (InterruptedException ex) {
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
            public void run() {
                for (int i = 0; i < 160; i++) {
                    if (i > 80) {
                        carPos[2].setLocationY(carPos[2].getLocationY() - 0.1);
                    } else if (i == 80) {
                        carPos[2].setDirection(TrafficData.fromN);
                    } else {
                        carPos[2].setLocationX(carPos[2].getLocationX() - 0.1);
                    }
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException ex) {
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
                        Thread.sleep(9);
                    } catch (InterruptedException ex) {
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
            public void run() {
                for (int i = 0; i < 140; i++) {
                    carPos[3].setLocationY(carPos[3].getLocationY() - 0.1);
                    try {
                        Thread.sleep(6);
                    } catch (InterruptedException ex) {
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
                    } catch (InterruptedException ex) {
                    }
                    repaint();
                }
                carPos[3] = new TrafficData(3, 2);
            }
        });
        t.start();
    }

    private void plusOffsetX() {

        if ((this.getWidth() / 2) > (((0 * tileWidth / 2) + (0 * tileWidth / 2)) + xPos)) {
            xPos = xPos + speed;
        }
    }

    private void plusOffsetY() {
        if ((this.getHeight() / 2) > (((0 * tileHeight / 2) - (mapWidth * tileHeight / 2)) + yPos)) {
            yPos = yPos + speed;
        }
    }

    private void minusOffsetX() {
        if ((this.getWidth() / 2) < (((mapHeigh * tileWidth / 2) + (mapWidth * tileWidth / 2)) + xPos)) {
            xPos = xPos - speed;
        }
    }

    private void minusOffsetY() {
        if ((this.getHeight() / 2) < (((mapHeigh * tileHeight / 2) - (0 * tileHeight / 2)) + yPos)) {
            yPos = yPos - speed;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                plusOffsetX();
                break;
            case KeyEvent.VK_RIGHT:
                minusOffsetX();
                break;
            case KeyEvent.VK_UP:
                plusOffsetY();
                break;
            case KeyEvent.VK_DOWN:
                minusOffsetY();
                break;
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                plusOffsetX();
                break;
            case KeyEvent.VK_RIGHT:
                minusOffsetX();
                break;
            case KeyEvent.VK_UP:
                plusOffsetY();
                break;
            case KeyEvent.VK_DOWN:
                minusOffsetY();
                break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
