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
        /*
        GeneratorDat g = new GeneratorDat(1, 1, -100, -100, 100, 100, 10, 1250);

        for (int i = 0; i < 20; i++)
        {
            Polygon p = g.getPolygon();
            Operacie.vlozPolygon(this.strom, p);

            if (i % 1000 == 0)
            {
                System.out.println(i);
            }
        }
        */

        Nehnutelnost n1 = new Nehnutelnost(1, "abc", new Suradnica(-99, 1), new Suradnica(-1, 99));
        Nehnutelnost n2 = new Nehnutelnost(1, "abc", new Suradnica(-99, 1), new Suradnica(-1, 99));
        //Nehnutelnost n2 = new Nehnutelnost(1, "abc", new Suradnica(-74, 51), new Suradnica(-51, 74));

        this.strom.vloz(n1);
        this.strom.vloz(n2);

        int h = this.strom.getPocetElementov();
        System.out.println("Pocet elementov: " + h);
    }
}
