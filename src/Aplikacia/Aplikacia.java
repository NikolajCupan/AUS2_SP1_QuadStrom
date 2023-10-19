package Aplikacia;

import Generator.GeneratorDat;
import Objekty.Polygon;
import Ostatne.Konstanty;

public class Aplikacia
{
    private final Logika logika;

    public Aplikacia()
    {
        this.logika = new Logika(Konstanty.X_MIN, Konstanty.Y_MIN, Konstanty.X_MAX, Konstanty.Y_MAX);
    }

    public void vykonavaj()
    {
        GeneratorDat generatorDat = new GeneratorDat(1, 1, Konstanty.X_MIN, Konstanty.Y_MIN, Konstanty.X_MAX, Konstanty.Y_MAX, 10, 10);

        for (int i = 1; i <= 20000; i++)
        {
            Polygon polygon = generatorDat.getPolygon();
            this.logika.vlozPolygon(polygon);

            if (i % 1000 == 0)
            {
                System.out.println(i + ".");
            }
        }

        this.logika.kontrola();
    }
}
