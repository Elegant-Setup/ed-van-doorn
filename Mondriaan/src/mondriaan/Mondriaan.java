package mondriaan;

import java.awt.*;
import java.util.*;

public class Mondriaan implements Constantes
{
    private ArrayList<Rechthoek> rechthoeken;
    private int topX, topY, breedte, hoogte;

    Mondriaan(int x, int y)
    {
        topX = x;
        topY = y;
        rechthoeken = new ArrayList<Rechthoek>();
    }

    public void maakMondriaan(int b, int h)
    {
        breedte = b;
        hoogte  = h;

        rechthoeken.clear();
        maakVertikaleLijnen();
        maakHorizontaleLijnen();
        verdeelRechthoeken();
    }

    private void maakVertikaleLijnen()
    {
        int       x, xOud, afstand;
        Rechthoek r;

        afstand = (breedte - N_LIJNEN_VER * BREEDTELIJN) / (N_LIJNEN_VER + 1);
        // gemiddelde afstand tussen twee lijnen/rechthoeken

        xOud = topX;
        for (int i = 0; i < N_LIJNEN_VER; i++)
        {
            // x-coordinaar linkerkant van rechthoek bepalen
            x = bepaalCoordinaat(afstand, i, topX);
          
            // maak linker rechthoek
            r = new Rechthoek(new Point(xOud, topY), new Dimension(x - xOud, hoogte));
            rechthoeken.add(r);

            // maak lijnrechthoek
            r = new Rechthoek(new Point(x, topY), new Dimension(BREEDTELIJN, hoogte));
            rechthoeken.add(r);

            xOud = x + BREEDTELIJN;
        }

        // maak rechter rechthoek
        r = new Rechthoek(new Point(xOud, topY), new Dimension(breedte + MARGE - xOud, hoogte));

        rechthoeken.add(r);
    }

    private void maakHorizontaleLijnen()
    {
        int       y, yOud, afstand;
        Rechthoek r;
        Random    random = new Random();
        ArrayList<Rechthoek> rects;
    
        // maakVerticaleLijnen moet aangeroepen zijn.
        
        // Grote idee: "eet" de rechthoeken in de arraylist rechthoeken langzaam op
        // Hierdoor ontstaat een nieuwe arraylist met rechthoeken
        
        rects = new ArrayList<Rechthoek>();
        
        // bepaal gemiddelde afstand tussen lijnen/rechthoeken        
        afstand = (hoogte - N_LIJNEN_HOR * BREEDTELIJN) / (N_LIJNEN_HOR + 1);

        yOud = topY;
        for (int i = 0; i < N_LIJNEN_HOR; i++)
        {
            // y-coordinaat bovenkant van rechthoek bepalen
            y = bepaalCoordinaat(afstand, i, topY);
            for (int j = 0; j < 2 * N_LIJNEN_VER + 1; j++)
            {
                r = rechthoeken.get(j);
                
                // splits rechthoek in boven-, lijn- en onderrechtoek
                // onder wordt in de volgende for iteratie nader opgesplitst

                rects.add(new Rechthoek(r.getTop(), new Dimension(r.getBreedte(), y - yOud)));
                rects.add(new Rechthoek(new Point(r.getTopX(), y), new Dimension(r.getBreedte(), BREEDTELIJN)));

                r.wijzigY(y + BREEDTELIJN);
            }
            yOud = y;
        }

        // maak onderste rechthoeken
        for (int j = 0; j < 2 * N_LIJNEN_VER + 1; j++)
            rects.add(rechthoeken.get(j));
        
        // kopieer hier gemaakte rechthoeken naar rechthoeken
        rechthoeken.clear();
        for (int i = 0; i < rects.size(); i++)
            rechthoeken.add(rects.get(i));
    }
    
    private int bepaalCoordinaat(int afstand, int index, int top)
    {
        Random    random = new Random();

        return top + ((index + 1) * afstand + index * BREEDTELIJN) + 
                (int)((-0.4 + 0.8 * random.nextDouble())* afstand);
        // lijnen liggen "afstand" minimaal 1 - 2 * 0.4 = 0.2 afstand van elkaar
    }

    private void verdeelRechthoeken()
    {
        Rechthoek r;
        
        for (int i = 0; i < rechthoeken.size(); i++)
           rechthoeken.get(i).opdelen();
    }
    
    public void draw(Graphics g)
    {
        g.setColor(ACHTERGRONDKLEUR);
        g.fillRect(0, 0, BREEDTE, HOOGTE);

        for (int i = 0; i < rechthoeken.size(); i++)
            rechthoeken.get(i).draw(g);
    }
}
