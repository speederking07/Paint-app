import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Panel for drawing.
 *
 * @author Marek Bauer
 */
public class DrawingArea extends Panel {
    private ArrayList<Figures> objects;
    private Figures selected;
    private MenuItem m;
    private PopupMenu popupMenu;

    /**
     * Default constructor
     */
    public DrawingArea() {
        super();
        objects = new ArrayList<Figures>();
        DrawingMouseAdapter handler = new DrawingMouseAdapter(this);
        super.addMouseListener(handler);
        super.addMouseMotionListener(handler);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 0, 50);
    }

    /**
     * @return List of objects to draw
     */
    ArrayList<Figures> getObjects() {
        return objects;
    }

    /**
     * @return Reference to selected figure
     */
    Figures getSelected() {
        return selected;
    }

    /**
     * Makes figure selected
     *
     * @param selected - Figure to select
     */
    public void setSelected(Figures selected) {
        if (selected != null) selected.getFocusoutEvent();
        this.selected = selected;
        if (selected != null) selected.getFocusEvent();
    }

    /**
     * Sets popup menu
     *
     * @param popupMenu - popup menu to set
     * @param f         - figure that was clicked
     * @param x         - x mouse position
     * @param y         - y mouse position
     */
    public void setPopupMenu(PopupMenu popupMenu, Figures f, int x, int y) {
        this.popupMenu = popupMenu;
        MenuItem item = new MenuItem("Move to foreground");
        if (f != null) {
            item.addActionListener((ActionEvent a) -> {
                objects.remove(f);
                objects.add(0, f);
            });
            popupMenu.add(item);
            item = new MenuItem("Move to background");
            item.addActionListener((ActionEvent a) -> {
                objects.remove(f);
                objects.add(f);
            });
            popupMenu.add(item);
            item = new MenuItem("Delete");
            item.addActionListener((ActionEvent a) -> {
                if (selected == f) selected = null;
                objects.remove(f);
            });
            popupMenu.add(item);
            item = new MenuItem("Clone");
            item.addActionListener((ActionEvent a) -> {
                try {
                    objects.add(Figures.getFigure(f.toString()));
                } catch (Exception e) {
                    MessageBox msg = new MessageBox("Cloning error: " + e.getMessage(), new String[]{"OK"});
                }
            });
            popupMenu.add(item);
            popupMenu.addSeparator();
        }
        Menu menu = new Menu("Add");
        item = new MenuItem("Rectangle");
        item.addActionListener((ActionEvent a) -> {
            objects.add(0, new Rectangle(x, y, 100, 100, Color.BLUE));
        });
        menu.add(item);
        item = new MenuItem("Ellipse");
        item.addActionListener((ActionEvent a) -> {
            objects.add(0, new Ellipse(x, y, 100, 100, Color.YELLOW));
        });
        menu.add(item);
        menu.add(item);
        item = new MenuItem("Polygon");
        item.addActionListener((ActionEvent a) -> {
            fPolygon poly = new fPolygon();
            objects.add(0, poly);
            selected = poly;
        });
        menu.add(item);
        popupMenu.add(menu);
        add(popupMenu);
    }

    /**
     * Function paints content of DrawingArea
     *
     * @param g - standard graphics object
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int i = objects.size() - 1; i >= 0; i--) {
            objects.get(i).paint(g);
        }
        if (selected != null) selected.paintAsSelected(g);
    }

    /**
     * Deletes all figures
     */
    public void clear() {
        objects.clear();
        selected = null;
    }

    /**
     * Creates string representing content of DrawingArea
     *
     * @return - result string
     */
    public String saveToString() {
        StringBuilder res = new StringBuilder("<Drawing file>\n");
        for (Figures f : objects) {
            if (!f.inCreation())
                res.append(f.toString()).append("\n");
        }
        return res.toString();
    }

    /**
     * Loads figures from string and deletes previous ones
     *
     * @param data - correct string
     */
    public void loadFormString(String data) {
        System.out.print(data);
        String[] d = data.split("\r")[0].split("\n");
        objects.clear();
        selected = null;
        try {
            if (!d[0].equals("<Drawing file>")) throw new Exception("Not correct file");
            for (int i = 1; i < d.length; i++) {
                objects.add(Figures.getFigure(d[i]));
            }
        } catch (Exception e) {
            MessageBox msg = new MessageBox("Loading error: " + e.getMessage(), new String[]{"OK"});
        }
    }
}
