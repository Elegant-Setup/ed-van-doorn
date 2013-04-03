package mondriaan;

import java.awt.*;
import java.util.*;

public class Rechthoek implements Constantes
{
    private Point                top;
    private Dimension            dimension;
    private Color                color;
    private ArrayList<Rechthoek> subRechthoeken;
    private Random               random = new Random();

    Rechthoek(Point t, Dimension d)
    {
        top            = new Point(t);
        dimension      = new Dimension(d);
        color          = maakKleur();
        subRechthoeken = new ArrayList<Rechthoek>();
    }

    public void draw(Graphics g)
    {
        g.setColor(color);
        g.fillRect(top.x, top.y, dimension.width, dimension.height);

        for (int i = 0; i < subRechthoeken.size(); i++)
            subRechthoeken.get(i).draw(g);
    }

    public void opdelen()
    {
        if (dimension.width == BREEDTELIJN || dimension.height == BREEDTELIJN)
            vulMetBlokjes();
        else
            vulGrootBlok();
    }

    private void vulMetBlokjes()
    {
        int x, y;

        x = top.x;
        y = top.y;

        if (dimension.width == BREEDTELIJN)
        {
            int h = dimension.height;

            while (h > 2 * BREEDTELIJN)
            {
                subRechthoeken.add(new Rechthoek(new Point(x, y), new Dimension(BREEDTELIJN, BREEDTELIJN)));
                y += BREEDTELIJN;
                h -= BREEDTELIJN;
            }
            subRechthoeken.add(new Rechthoek(new Point(x, y), new Dimension(BREEDTELIJN, h)));
        }
        else
        {
            int w = dimension.width;

            while (w > 2 * BREEDTELIJN)
            {
                subRechthoeken.add(new Rechthoek(new Point(x, y), new Dimension(BREEDTELIJN, BREEDTELIJN)));
                x += BREEDTELIJN;
                w -= BREEDTELIJN;
            }
            subRechthoeken.add(new Rechthoek(new Point(x, y), new Dimension(w, BREEDTELIJN)));
        }
    }

    private void vulGrootBlok()
    {
        Rechthoek r1, r2;
           
        if (random.nextDouble() < Constantes.SPLITSKANS)
            // rechthoek splitsen
        {          
            if (dimension.width >= 2 * Constantes.BREEDTELIJN && random.nextDouble() < 0.5)
                // Verticaal splitsen
            {
                int b1, b2;
                
                b1 =  dimension.width / 2;
                b2 =  dimension.width - b1;
                
                r1 = new Rechthoek(new Point(top),               new Dimension(b1, dimension.height));
                r2 = new Rechthoek(new Point(top.x + b1, top.y), new Dimension(b2, dimension.height));

                subRechthoeken.add(r1);
                subRechthoeken.add(r2);
                
                r1.opdelen();
                r2.opdelen();  
            }     
            else if (dimension.height >= 2 * Constantes.BREEDTELIJN)
            {
                // Horizontaal splitsen
                int h1, h2;
                
                h1 = dimension.height / 2;
                h2 = dimension.height - h1;
                
                r1 = new Rechthoek(new Point(top),               new Dimension(dimension.width, h1));
                r2 = new Rechthoek(new Point(top.x, top.y + h1), new Dimension(dimension.width, h2));
                
                subRechthoeken.add(r1);
                subRechthoeken.add(r2);
                
                r1.opdelen();
                r2.opdelen(); 
            }
        }
        else
           if (random.nextDouble() <      Constantes.BLOKJEKANS  &&
               dimension.width     >= 2 * Constantes.BREEDTELIJN &&
               dimension.height    >= 2 * Constantes.BREEDTELIJN)
           {
               // nog een blokje in het centrum?
               subRechthoeken.add(new Rechthoek(new Point(top.x + dimension.width / 2, top.y + dimension.height / 2),
                       new Dimension((int)(1.4 * BREEDTELIJN), BREEDTELIJN)));
           }
        // nog een blok in het centrum?  
    }

    private Color maakKleur()
    {
        if (dimension.width < 1.5 * BREEDTELIJN && dimension.height < 1.5 * BREEDTELIJN)
        {
            // zwart is toegestaan en komt vaak voor in kleine blokjes;
            int k = random.nextInt(Constantes.KLEUREN.length + 3);

            return k >= Constantes.KLEUREN.length ? Color.black : KLEUREN[k];

        }

        return KLEUREN[random.nextInt(Constantes.KLEUREN.length)];

    }

    public void wijzigY(int y)
    {
        dimension.height -= y - top.y;
        top.y             = y;
    }
    
    public Point getTop()
    {
        return new Point(top);
    }

    public int getTopX()
    {
        return top.x;
    }

    public int getBreedte()
    {
        return dimension.width;
    }

    public void setBreedte(int w)
    {
       dimension.width = w;
    }
}
