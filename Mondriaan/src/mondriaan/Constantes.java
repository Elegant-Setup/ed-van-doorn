package mondriaan;

import java.awt.Color;

public interface Constantes
{
    final int    BREEDTE          = 800;
    final int    HOOGTE           = 800;
    final int    N_LIJNEN_HOR     = 7;
    final int    N_LIJNEN_VER     = 4;
    final int    BREEDTELIJN      = 10; // breedte verticale of horizontale rechthoek
    final int    MARGE            = 10; // afstand tot de rand
    final Color  KLEUREN[]        = {Color.blue, Color.red, Color.yellow, Color.gray, Color.orange, Color.white, Color.white};
                                  // 2 * keer zo veel kans op wit.
    final Color  ACHTERGRONDKLEUR = Color.black;
    final double SPLITSKANS       = 0.6;
    final double BLOKJEKANS       = 0.2;
}
