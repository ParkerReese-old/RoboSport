package view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import controller.GameInitializer;
import controller.Referee;
import models.BoardPosition;
import models.GameBoard;
import models.Robot;

/**
 * A panel for displaying the game board in.
 * 
 * @author Dylan McInnes
 *
 */
public class BoardDisplay extends JPanel {
    private static final long serialVersionUID = 1L;

    public static boolean TESTING = false;

    public static final double ROOT3 = Math.pow(3, 0.5);

    private static final BufferedImage[] RBT_ICONS = new BufferedImage[3];

    protected volatile Referee ref;

    protected final int vWidth, vHeight, cSize;

    protected final double CELL_WID, CELL_SIDEH, CELL_TIPH;
    protected final double DELTA_X, DELTA_Y;

    protected int side;
    protected Hex[][] hexes;

    /**
     * 
     * @param viewWidth
     * @param viewHeight
     * @param cellSize : must be an even number
     */
    public BoardDisplay(int viewWidth, int viewHeight, int cellSize) {
        if (cellSize < 1)
            throw new RuntimeException("cellSize must be greater than 0");
        this.vWidth = viewWidth;
        this.vHeight = viewHeight;
        this.cSize = cellSize;

        this.CELL_WID = this.cSize * ROOT3 / 2;
        this.CELL_SIDEH = this.cSize / 2.0;
        this.CELL_TIPH = this.CELL_SIDEH + (0.5 * this.cSize);

        this.DELTA_X = this.CELL_WID * 2; // +1 ?= space between
        this.DELTA_Y = this.CELL_SIDEH * 3; // +1 ?= space between

        
        
       
        // Populate the icons
        try {
            RBT_ICONS[Robot.Type.SCOUT.ordinal()] = ImageIO.read(
                    BoardDisplay.class.getResource("Scout.png"));
            RBT_ICONS[Robot.Type.SNIPER.ordinal()] = ImageIO.read(
                    BoardDisplay.class.getResource("Sniper.png"));
            RBT_ICONS[Robot.Type.TANK.ordinal()] = ImageIO.read(
                    BoardDisplay.class.getResource("Tank.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        board(3);
        this.ref = null;
    }

    /**
     * Sets up an empty hex board with a side length of 'side'
     * 
     * @param side : the side length of the board (in number of hexes)
     */
    private void board(int side) {
        boolean outside;
        hexes = new Hex[(side - 1) * 2 + 1][(side - 1) * 2 + 1];
        this.side = side;
        for (int x = 0; x <= (side - 1) * 2; x++)
            for (int y = 0; y <= (side - 1) * 2; y++) {
                outside = false;
                for (int i = 0; i < side; i++) {
                    outside = outside | y - x == side + i | x - y == side + i;
                }
                if (!outside)
                    hexes[x][y] = new Hex(x, y);
                else
                    hexes[x][y] = null;
            }
    }

    /**
     * Draws the given GameBoard
     * 
     * @param toDraw : the GameBoard to draw
     */
    public void draw(Referee toDraw) {
        if (toDraw == null)
            throw new NullPointerException("toDraw must not be null");
        this.ref = toDraw;
        board(toDraw.getGameBoard().getSize());

        repaint();
    }
    
    private void clear() {
        for (Hex[] hr : hexes)
            for (Hex h : hr)
                if (h!=null)
                    h.clear();
    }

    /**
     * Redraws the board from the point of view of viewer
     * 
     * @param viewer : the "first-person" robot
     */
    public void redraw(Robot viewer) {
        clear();
        BoardPosition rp, vp;
        if (viewer!=null) {
            vp = viewer.getLocation();
            for (Robot r : this.ref.getGameBoard().getAllRobots()) {
                if (r!=viewer && !r.isDead()) {
                    rp = r.getLocation();
                    if (vp.distanceTo(rp)<=viewer.getRange())
                        hexes[rp.getX()][rp.getY()].on(r, 0);
                }
            }
            // Fog of War:
            for (int x = 0; x <= (side - 1) * 2; x++)
                for (int y = 0; y <= (side - 1) * 2; y++)
                    if (hexes[x][y]!=null)
                        if (vp.distanceTo(new BoardPosition(x,y,0))>viewer.getRange())
                            hexes[x][y].highlight(Color.LIGHT_GRAY);
            if (!viewer.isDead())
                hexes[vp.getX()][vp.getY()].on(viewer, 17);
        }
        else {
            for (Robot r : this.ref.getGameBoard().getAllRobots()) {
                rp = r.getLocation();
                if (!r.isDead())
                    hexes[rp.getX()][rp.getY()].on(r, 0); // TODO add priority
            }
        }
        repaint();
    }

    /**
     * Draws all the cells onto the screen
     * 
     * @param g : the Graphics object to draw with/on
     */
    protected void drawCells(Graphics g) {
        for (int i = 0; i <= (side - 1) * 2; i++)
            for (int j = 0; j <= (side - 1) * 2; j++)
                if (hexes[i][j] != null)
                    hexes[i][j].drawHex(g);

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Do some drawing stuff
        drawCells(g);
        // g.drawRect(vWidth/2 -3, vHeight/2 -3, 6, 6);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.vWidth, this.vHeight);
    }

    /**
     * Returns the coordinates of the hex at (x,y).
     * 
     * @param x : the x position of the "cursor"
     * @param y : the y position of the "cursor"
     * @return {hex x, hex y} if (x,y) is within a hex. null otherwise
     */
    protected int[] getHexCoordinate(Point p) {
        for (Hex[] hr : hexes)
            for (Hex h : hr)
                if (h != null)
                    if (h.getPoly().contains(p))
                        return new int[] {h.getX(), h.getY()};
        return null;
    }
    

    public static GraphicsConfiguration getGraphicsConfigurationCustom() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    }

    public static BufferedImage createCompatibleImage(int width, int height, int transparency) {
        BufferedImage image = getGraphicsConfigurationCustom().createCompatibleImage(width, height, transparency);
        image.coerceData(true);
        return image;
    }

    public static void applyQualityRenderingHints(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }

    public static BufferedImage generateMask(BufferedImage imgSource, Color color, float alpha) {
        int imgWidth = imgSource.getWidth();
        int imgHeight = imgSource.getHeight();

        BufferedImage imgMask = createCompatibleImage(imgWidth, imgHeight, Transparency.TRANSLUCENT);
        Graphics2D g2 = imgMask.createGraphics();
        applyQualityRenderingHints(g2);

        g2.drawImage(imgSource, 0, 0, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, alpha));
        g2.setColor(color);

        g2.fillRect(0, 0, imgSource.getWidth(), imgSource.getHeight());
        g2.dispose();

        return imgMask;
    }

    public static BufferedImage tint(BufferedImage master, BufferedImage tint) {
        int imgWidth = master.getWidth();
        int imgHeight = master.getHeight();

        BufferedImage tinted = createCompatibleImage(imgWidth, imgHeight, Transparency.TRANSLUCENT);
        Graphics2D g2 = tinted.createGraphics();
        applyQualityRenderingHints(g2);
        g2.drawImage(master, 0, 0, null);
        g2.drawImage(tint, 0, 0, null);
        g2.dispose();

        return tinted;
    }
    
    public static BufferedImage colorImage(BufferedImage img, Color c) {
        return tint(img, generateMask(img, c, 0.3f));
    }

    /**
     * An internal class that holds information necessary for drawing the hex grid
     * 
     * @author Dylan McInnes
     *
     */
    private class Hex {
        private int x, y; // position
        private Color[] background;
        private Color highlight;

        private Polygon myHex;
        private Polygon[] myHexBackgrounds;
        
        private Robot myRobot;
        private int priority;

        public Hex(int x, int y) {
            this.x = x;
            this.y = y;
            this.background = new Color[6];
            clear();
        }

        public void clear() {
            this.highlight = null;
            for (int i = 0; i < 6; i++)
                this.background[i] = Color.WHITE;
            this.myRobot=null;
            this.priority=0;
        }
        
        /**
         * Returns the background index
         * @return
         */
        private int backInd(Robot bot) {
            int i = bot.getTeam().getColor().getDirection();
            return 5-i;
        }

        /**
         * Adds a robot to the hex
         * @param bot : the robot to add to the hex
         * @param priority : the level of priority the robot has in drawing [0-17]
         */
        public void on(Robot bot, int priority) {
            background[backInd(bot)] = bot.getTeam().getColor().getColor();
            if (priority>=this.priority) {
                this.myRobot=bot;
                this.priority=priority;
            }
        }

        public void highlight(Color col) {
            this.highlight = col;
        }

        public Polygon getPoly() {
            if (myHex == null) {
                double cx = (x - side + 1) * DELTA_X - (y - side + 1) * CELL_WID + vWidth / 2.0;
                double cy = (y - side + 1) * DELTA_Y + vHeight / 2.0;

                int[] periX = new int[6];
                int[] periY = new int[6];
                periX[0] = (int) (cx + CELL_WID);
                periY[0] = (int) (cy - CELL_SIDEH);
                periX[1] = (int) (cx);
                periY[1] = (int) (cy - CELL_TIPH);
                periX[2] = (int) (cx - CELL_WID);
                periY[2] = (int) (cy - CELL_SIDEH);
                periX[3] = (int) (cx - CELL_WID);
                periY[3] = (int) (cy + CELL_SIDEH);
                periX[4] = (int) (cx);
                periY[4] = (int) (cy + CELL_TIPH);
                periX[5] = (int) (cx + CELL_WID);
                periY[5] = (int) (cy + CELL_SIDEH);


                myHex = new Polygon();
                myHex.addPoint(periX[0], periY[0]); // North-West
                myHex.addPoint(periX[1], periY[1]); // North
                myHex.addPoint(periX[2], periY[2]); // North-East
                myHex.addPoint(periX[3], periY[3]); // South-East
                myHex.addPoint(periX[4], periY[4]); // South
                myHex.addPoint(periX[5], periY[5]); // South-West


                myHexBackgrounds = new Polygon[6];
                for (int i = 0; i < 6; i++) {
                    myHexBackgrounds[i] = new Polygon();
                    myHexBackgrounds[i].addPoint(periX[i], periY[i]);
                    myHexBackgrounds[i].addPoint(periX[i == 5 ? 0 : i + 1],
                            periY[i == 5 ? 0 : i + 1]);
                    myHexBackgrounds[i].addPoint((int) cx, (int) cy);
                }
            }
            return this.myHex;
        }

        /**
         * Draws the contents of one cell centered about position (x,y)
         * 
         * @param g : the graphics object to draw with/onto
         */
        protected void drawHex(Graphics g) {
            Polygon p = getPoly();
            Color old = g.getColor();
            if (highlight == null)
                for (int i = 0; i < 6; i++) {
                    if (background != null & background[i] != null & myHexBackgrounds[i] != null) {
                        g.setColor(background[i]);
                        g.fillPolygon(myHexBackgrounds[i]);
                    }
                }
            else {
                g.setColor(highlight);
                g.fillPolygon(p);
            }
            g.setColor(Color.BLACK);
            g.drawPolygon(p);
            
            double cx = (x - side + 1) * DELTA_X - (y - side + 1) * CELL_WID + vWidth / 2.0;
            double cy = (y - side + 1) * DELTA_Y + vHeight / 2.0;
            if (TESTING) {
                g.drawString(x + "," + y, (int) (cx - 10), (int) cy + 5);
            }
            if (myRobot!=null) {
                BufferedImage img = BoardDisplay.colorImage(
                        RBT_ICONS[myRobot.getType().ordinal()],
                        myRobot.getTeam().getColor().getColor());
                g.drawImage(img,
                        (int)cx-side*2, (int)cy-side*2, (int)cx+side*2, (int)cy+side*2,
                        0, 0, img.getWidth(), img.getHeight(), null);
            }

            g.setColor(old);
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }
    }

    /**
     * A method that allows manual testing of this class
     * 
     * @param args : the command-line arguments
     */
    public static void main(String[] args) {
        TESTING = true;
        BoardDisplay bds = new BoardDisplay(500, 500, 20);
        
        GameBoard gb = (new GameInitializer()).initializeGame();
        Referee ref = new Referee(gb);
        bds.draw(ref);
        bds.redraw(null);


        SwingOps.newFrame(bds);
        ref.startGame(gb);

    }
}
