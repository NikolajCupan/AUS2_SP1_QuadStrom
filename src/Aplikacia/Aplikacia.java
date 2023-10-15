package Aplikacia;

import Generator.GeneratorDat;
import Objekty.Nehnutelnost;
import Objekty.Parcela;
import Objekty.Polygon;
import Objekty.Suradnica;
import Ostatne.Konstanty;
import QuadStrom.QuadStrom;

import java.util.Random;

import static java.lang.Math.abs;

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
            System.out.println(j);
            GeneratorDat g = new GeneratorDat(1, 1, -100, -100, 100, 100, 10, j);
            for (int i = 1; i <= 10000; i++)
            {
                Polygon p = g.getPolygon();
                Operacie.vlozPolygon(this.strom, p);
            }
        }

        Operacie.skontrolujStrukturu(this.strom);
    }
}
