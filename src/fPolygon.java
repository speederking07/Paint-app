import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class fPolygon extends Figures {
    Polygon poly, original; //poly - current polygon; original - almost original polygon
    boolean inCreation;
    ArrayList<Integer> tempX, tempY; //list of points
    int mX, mY; //mouse position

    public fPolygon() {
        inCreation = true;
        color = Color.RED;
        relX = -1;
        relY = -1;
        resizeCorner = Corner.None;
        tempX = new ArrayList<Integer>();
        tempY = new ArrayList<Integer>();
    }

    public fPolygon(String data) throws Exception {
        String[] d = data.split(" ");
        if (!d[0].equals("pol")) throw new Exception("Wrong figure");
        int n = Integer.parseInt(d[1]);
        int[] pX = new int[n];
        int[] pY = new int[n];
        int[] oX = new int[n];
        int[] oY = new int[n];
        for (int i = 0; i < n; i++) {
            pX[i] = Integer.parseInt(d[i * 4 + 2]);
            pY[i] = Integer.parseInt(d[i * 4 + 3]);
            oX[i] = Integer.parseInt(d[i * 4 + 4]);
            oY[i] = Integer.parseInt(d[i * 4 + 5]);
        }
        poly = new Polygon(pX, pY, n);
        original = new Polygon(oX, oY, n);
        color = new Color(Integer.parseInt(d[n * 4 + 2]));
        inCreation = false;
        relX = -1;
        relY = -1;
        resizeCorner = Corner.None;
        java.awt.Rectangle bounds = poly.getBounds();
        x = bounds.x;
        y = bounds.y;
        width = bounds.width;
        height = bounds.height;
    }

    @Override
    boolean inArea(int x, int y) {
        if (poly == null) return false;
        else return poly.contains(x, y);
    }

    @Override
    public boolean inAreaAsSelected(int x, int y) {
        if (inCreation) return true;
        else return super.inAreaAsSelected(x, y);
    }

    @Override
    public void getReleasedEvent(MouseEvent e) {
        if (inCreation) {
            if (!tempX.isEmpty() && e.getX() >= tempX.get(0) - 4 && e.getY() >= tempY.get(0) - 4 &&
                    e.getX() <= tempX.get(0) + 4 && e.getY() <= tempY.get(0) + 4)
                endCreation();
            else {
                tempX.add(e.getX());
                tempY.add(e.getY());
            }
        } else super.getReleasedEvent(e);
    }

    public void getDragEvent(MouseEvent e) {
        if (!inCreation) {
            if (resizeCorner != Corner.None) resize(e.getX(), e.getY());
            else {
                if (relX == -1) {
                    relX = e.getX() - x;
                    relY = e.getY() - y;
                }
                x = e.getX() - relX;
                y = e.getY() - relY;
                fit();
            }
        }
    }

    @Override
    void resize(int newX, int newY) {
        super.resize(newX, newY);
        fit();
    }

    @Override
    public void getFocusoutEvent() {
        if (inCreation) endCreation();
        else super.getFocusoutEvent();
    }

    @Override

    public PopupMenu getPopupMenu() {
        PopupMenu popup = new PopupMenu();
        MenuItem item;
        item = new MenuItem("Black");
        Menu menu = new Menu("Change color");
        item.addActionListener((ActionEvent a) -> {
            this.color = Color.BLACK;
        });
        menu.add(item);
        item = new MenuItem("Red");
        item.addActionListener((ActionEvent a) -> {
            this.color = Color.RED;
        });
        menu.add(item);
        item = new MenuItem("Blue");
        item.addActionListener((ActionEvent a) -> {
            this.color = Color.BLUE;
        });
        menu.add(item);
        item = new MenuItem("Green");
        item.addActionListener((ActionEvent a) -> {
            this.color = Color.GREEN;
        });
        menu.add(item);
        item = new MenuItem("Yellow");
        item.addActionListener((ActionEvent a) -> {
            this.color = Color.YELLOW;
        });
        menu.add(item);
        item = new MenuItem("Pink");
        item.addActionListener((ActionEvent a) -> {
            this.color = Color.PINK;
        });
        menu.add(item);
        item = new MenuItem("Orange");
        item.addActionListener((ActionEvent a) -> {
            this.color = Color.ORANGE;
        });
        menu.add(item);
        item = new MenuItem("Cyan");
        item.addActionListener((ActionEvent a) -> {
            this.color = Color.CYAN;
        });
        menu.add(item);
        item = new MenuItem("Magenta");
        item.addActionListener((ActionEvent a) -> {
            this.color = Color.MAGENTA;
        });
        menu.add(item);
        item = new MenuItem("White");
        item.addActionListener((ActionEvent a) -> {
            this.color = Color.WHITE;
        });
        menu.add(item);
        item = new MenuItem("Gray");
        item.addActionListener((ActionEvent a) -> {
            this.color = Color.GRAY;
        });
        menu.add(item);
        popup.add(menu);
        item = new MenuItem("Flip Vertical");
        item.addActionListener((ActionEvent a) -> {
            flipVertical();
        });
        popup.add(item);
        item = new MenuItem("Flip Horizontal");
        item.addActionListener((ActionEvent a) -> {
            flipHorizontal();
        });
        popup.add(item);
        item = new MenuItem("Expand");
        item.addActionListener((ActionEvent a) -> {
            this.width += 50;
            this.height += 50;
            super.normalisation();
            fit();
        });
        popup.add(item);
        item = new MenuItem("Shrink");
        item.addActionListener((ActionEvent a) -> {
            this.width -= 50;
            this.height -= 50;
            super.normalisation();
            fit();
        });
        popup.add(item);
        popup.addSeparator();
        return popup;
    }

    @Override
    public void paint(Graphics g) {
        if (!inCreation) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(color);
            g2d.fill(poly);
        }
    }

    @Override
    public void getMouseMoveEvent(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();
    }

    /**
     * Changes ArrayList to int[]
     *
     * @param list - list
     * @return - array of ints
     */
    private int[] toIntArray(ArrayList<Integer> list) {
        int[] ret = new int[list.size()];
        for (int i = 0; i < ret.length; i++)
            ret[i] = list.get(i);
        return ret;
    }

    /**
     * It just filps polygon
     * Just an excuse to write popups the way I did it
     */
    public void flipVertical() {
        java.awt.Rectangle bounds = original.getBounds();
        int bY = bounds.y;
        int bHeight = bounds.height;
        int axis = bHeight / 2;
        int[] arrayY = original.ypoints;
        int[] resY = new int[original.npoints];
        for (int i = 0; i < original.npoints; i++) {
            resY[i] = Math.round(-(arrayY[i] - (bY + axis)) + (bY + axis));
        }
        original = new Polygon(original.xpoints, resY, poly.npoints);
        fit();
    }

    /**
     * It just filps polygon
     * Just an excuse to write popups the way I did it
     */
    public void flipHorizontal() {
        java.awt.Rectangle bounds = original.getBounds();
        int bX = bounds.x;
        int axis = bounds.width / 2;
        int[] arrayX = original.xpoints;
        int[] resX = new int[original.npoints];
        for (int i = 0; i < original.npoints; i++) {
            resX[i] = Math.round(-(arrayX[i] - (bX + axis)) + (bX + axis));
        }
        original = new Polygon(resX, original.ypoints, poly.npoints);
        fit();
    }

    /**
     * Changes poly to fit width and height params
     */
    private void fit() {
        java.awt.Rectangle bounds = original.getBounds();
        int bX = bounds.x;
        int bY = bounds.y;
        int bWidth = bounds.width;
        int bHeight = bounds.height;
        double scaleX = width * 1.0 / bWidth, scaleY = height * 1.0 / bHeight;
        //int moveX = bX - x, moveY = bY - y;
        int[] arrayX = original.xpoints;
        int[] arrayY = original.ypoints;
        int[] resX = new int[original.npoints];
        int[] resY = new int[original.npoints];
        for (int i = 0; i < original.npoints; i++) {
            resX[i] = (int) Math.round((arrayX[i] - bX) * scaleX + x);
            resY[i] = (int) Math.round((arrayY[i] - bY) * scaleY + y);
        }
        poly = new Polygon(resX, resY, poly.npoints);
    }

    void endCreation() {
        inCreation = false;
        poly = new Polygon(toIntArray(tempX), toIntArray(tempY), tempX.size());
        original = poly;
        java.awt.Rectangle bounds = poly.getBounds();
        x = bounds.x;
        y = bounds.y;
        width = bounds.width;
        height = bounds.height;
    }

    @Override
    boolean inCreation() {
        return inCreation;
    }

    @Override
    public void paintAsSelected(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (inCreation) {
            g2d.setColor(Color.BLACK);
            if (!tempX.isEmpty()) {
                int prevX = tempX.get(0), prevY = tempY.get(0);
                for (int i = 1; i < tempX.size(); i++) {
                    g2d.drawLine(prevX, prevY, tempX.get(i), tempY.get(i));
                    prevX = tempX.get(i);
                    prevY = tempY.get(i);
                }
                g2d.setColor(Color.GREEN);
                g2d.drawLine(prevX, prevY, mX, mY);
                g2d.setColor(Color.RED);
                g2d.fillRect(tempX.get(0) - 3, prevY = tempY.get(0) - 3, 7, 7);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(tempX.get(0) - 3, prevY = tempY.get(0) - 3, 7, 7);
            }
        } else {
            g2d.setColor(Color.GREEN);
            g2d.draw(poly);
            super.paintAsSelected(g);
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("pol " + poly.npoints);
        for (int i = 0; i < poly.npoints; i++) {
            res.append(" ").append(poly.xpoints[i]).append(" ").append(poly.ypoints[i]).append(" ")
                    .append(original.xpoints[i]).append(" ").append(original.ypoints[i]);
        }
        res.append(" ").append(color.hashCode());
        return res.toString();
    }
}

