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
        GeneratorDat gen = new GeneratorDat(1, 1,Konstanty.X_MIN, Konstanty.Y_MIN, Konstanty.X_MAX, Konstanty.Y_MAX, 10);

        for (int i = 1; i <= 100000; i++)
        {
            if (Math.random() < 0.5)
            {
                Nehnutelnost n = gen.getNehnutelnost();
                Operacie.vlozNehnutelnost(this.strom, n);
            }
            else
            {
                Parcela p = gen.getParcela();
                Operacie.vlozParcelu(this.strom, p);
            }

            if (i % 1000 == 0)
            {
                int el = this.strom.getPocetElementov();
                System.out.println(i + " " + el);
            }
        }

        int x = this.strom.getPocetElementov();
    }
}
