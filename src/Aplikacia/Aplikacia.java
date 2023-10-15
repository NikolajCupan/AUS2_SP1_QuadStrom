package Aplikacia;

import Generator.GeneratorDat;
import Objekty.Polygon;
import Ostatne.Konstanty;
import QuadStrom.QuadStrom;

public class Aplikacia
{
    private QuadStrom<Polygon> strom;

    public Aplikacia()
    {
        this.strom = new QuadStrom<Polygon>(Konstanty.X_MIN, Konstanty.Y_MIN, Konstanty.X_MAX, Konstanty.Y_MAX);
    }

    public void vykonavaj()
    {
        for (int j = 1; j <= 1; j++)
        {
            this.strom = new QuadStrom<Polygon>(Konstanty.X_MIN, Konstanty.Y_MIN, Konstanty.X_MAX, Konstanty.Y_MAX);
            GeneratorDat g = new GeneratorDat(1, 1, -100, -100, 100, 100, 10);
            for (int i = 1; i <= 5000; i++)
            {
                Polygon p = g.getPolygon();
                Operacie.vlozPolygon(this.strom, p);
            }
        }

        Operacie.skontrolujStrukturu(this.strom);
    }
}
