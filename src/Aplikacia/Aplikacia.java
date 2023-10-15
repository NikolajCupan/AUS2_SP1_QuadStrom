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
//        GeneratorDat g = new GeneratorDat(1, 1, -100, -100, 100, 100, 10, 1250);
//
//        for (int i = 0; i < 20; i++)
//        {
//            Polygon p = g.getPolygon();
//            Operacie.vlozPolygon(this.strom, p);
//
//            if (i % 1000 == 0)
//            {
//                System.out.println(i);
//            }
//        }
        for (int i = 0; i < 25000; i++)
        {
            Operacie.vlozNehnutelnost(this.strom, new Nehnutelnost(1, "a", new Suradnica(-100, 100), new Suradnica(-99.99, 99.99)));
        }

        int h = this.strom.getPocetElementov();
        System.out.println("Pocet elementov: " + h);
    }
}
