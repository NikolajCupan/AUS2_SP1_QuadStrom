package Aplikacia;

import Generator.GeneratorDat;
import Objekty.Nehnutelnost;
import Objekty.Parcela;
import Objekty.Polygon;
import Objekty.Suradnica;
import Ostatne.Konstanty;
import QuadStrom.QuadStrom;

public class Aplikacia
{
    private final QuadStrom<Polygon> strom;

    public Aplikacia()
    {
        this.strom = new QuadStrom<Polygon>(Konstanty.X_MIN, Konstanty.Y_MIN, Konstanty.X_MAX, Konstanty.Y_MAX);
    }

    public void vykonavaj()
    {
    }
}
