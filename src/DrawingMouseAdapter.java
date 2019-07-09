import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * MouseAdapter for DrawingArea
 * <p>
 * t.repaint(300); is commented because I'm still not certain when to refresh DrawingArea
 */
public class DrawingMouseAdapter extends MouseAdapter {
    private DrawingArea t; //DrawingArea No idea why t

    public DrawingMouseAdapter(DrawingArea com) {
        t = com;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (t.getSelected() != null) {
            t.getSelected().getMouseMoveEvent(e);
            //t.repaint(300);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK) {
            if (t.getSelected() != null)
                t.getSelected().getDragEvent(e);
            //t.repaint(70);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //t.repaint();
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (t.getSelected() != null &&
                    (t.getSelected().inAreaAsSelected(e.getX(), e.getY()) || t.getSelected().inArea(e.getX(), e.getY()))) {
                t.getSelected().getReleasedEvent(e);
                //t.repaint(70);
                return;
            }
            for (Figures f : t.getObjects()) {
                if (f.inArea(e.getX(), e.getY())) {
                    f.getReleasedEvent(e);
                    break;
                }
            }
            //t.repaint(70);
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            for (Figures f : t.getObjects()) {
                if (f.inArea(e.getX(), e.getY())) {
                    PopupMenu p = f.getPopupMenu();
                    t.setPopupMenu(p, f, e.getX(), e.getY());
                    p.show(t, e.getX(), e.getY());
                    return;
                }
            }
            PopupMenu p = new PopupMenu();
            t.setPopupMenu(p, null, e.getX(), e.getY());
            p.show(t, e.getX(), e.getY());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (e.getButton() == MouseEvent.BUTTON1) {
            if (t.getSelected() != null) {
                if (t.getSelected().inAreaAsSelected(e.getX(), e.getY()) || t.getSelected().inArea(e.getX(), e.getY())) {
                    t.getSelected().getPressedEvent(e);
                    //t.repaint(70);
                    return;
                } else {
                    t.setSelected(null);
                }
            }
            for (Figures f : t.getObjects()) {
                if (f.inArea(e.getX(), e.getY())) {
                    t.setSelected(f);
                    f.getReleasedEvent(e);
                    break;
                }
            }
            //t.repaint(70);
        }
    }
}
