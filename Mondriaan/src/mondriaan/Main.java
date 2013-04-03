package mondriaan;

import java.awt.*;
import java.awt.event.*;

public class Main extends WindowAdapter implements Constantes, MouseListener
{
    private Frame     omlijsting;
    private Panel     panel;
    private Graphics  graphics;
    private Mondriaan mondriaan;
    private int       breedte, hoogte;

    Main()
    {
        omlijsting = new Frame();
        omlijsting.setSize(BREEDTE, HOOGTE);
        omlijsting.addWindowListener(this);
        omlijsting.setVisible(true);

        panel      = new Panel();
        omlijsting.add(panel);
        graphics   = panel.getGraphics();
        mondriaan  = new Mondriaan(MARGE, MARGE);

        panel.addMouseListener(this);
    }

    public static void main(String[] args)
    {
        Main main = new Main();
        
        main.toonMondriaan(); // dirty trick om breedte en hoogte te bepalen
        main.maakMondriaan(); // breedte en hoogte worden hier gebruikt
        main.toonMondriaan(); 
    }

    private void maakMondriaan()
    {
        mondriaan.maakMondriaan(breedte, hoogte);
    }
    
    private void toonMondriaan()
    {
        // Het is blijkbaar nodig om even te wachten en daarna
        // de mondriaan weer te geven.
        try
        {
            Thread.sleep(200);
        }
        catch(Exception e) 
        {
        }

        windowActivated(null);
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
        System.exit(0);
    }

    @Override
    public void windowActivated(WindowEvent e)
    {
        if (graphics == null)
            return;

        try
        {
            mondriaan.draw(graphics);
            snijTotRuit();
        }
        catch(Exception ex)
        {
            // om onduidelijke redenenen wordt soms een foutmelding gegenereerd.
        }
    }

    public void mouseClicked(MouseEvent e)
    {
        maakMondriaan();
        windowActivated(null);
    }
    
    public void mouseEntered(MouseEvent e)  {}
    public void mouseExited(MouseEvent e)   {}
    public void mousePressed(MouseEvent e)  {}
    public void mouseReleased(MouseEvent e) {}

    private void snijTotRuit()
    {
        Rectangle r;

        int x[] = new int[4];
        int y[] = new int[4];      

        r       = panel.getBounds();
        breedte = (int)r.getWidth()  - 2 * MARGE;
        hoogte  = (int)r.getHeight() - 2 * MARGE;
        
        graphics.setColor(ACHTERGRONDKLEUR);

        //linksboven
        x[0] = x[3]  = MARGE;             y[0] = y[3]  = MARGE;
        x[1] = MARGE + breedte/2;         y[1] = MARGE;
        x[2] = MARGE;                     y[2] = MARGE + hoogte/2;

        graphics.fillPolygon(x, y, x.length);

        //rechtsboven
        x[0] = x[3]  = MARGE + breedte/2; y[0] = y[3]  = MARGE;
        x[1] = MARGE + breedte;           y[1] = MARGE;
        x[2] = MARGE + breedte;           y[2] = MARGE + hoogte/2;

        graphics.fillPolygon(x, y, x.length);

        //rechtsonder
        x[0] = x[3]  = MARGE + breedte;   y[0] = y[3]  = MARGE + hoogte/2;
        x[1] = MARGE + breedte;           y[1] = MARGE + hoogte;
        x[2] = MARGE + breedte/2;         y[2] = MARGE + hoogte;

        graphics.fillPolygon(x, y, x.length);

        //linksonder
        x[0] = x[3] = MARGE + breedte/2;  y[0] = y[3]  = MARGE + hoogte;
        x[1] = MARGE;                     y[1] = MARGE + hoogte;
        x[2] = MARGE;                     y[2] = MARGE + hoogte/2;

        graphics.fillPolygon(x, y, x.length);
        
        graphics.setColor(Color.white);
        graphics.drawString("E.M. van Doorn", (int)(0.8 * BREEDTE), (int)(0.9 * HOOGTE));
    }
}
