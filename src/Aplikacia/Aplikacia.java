package Aplikacia;

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

    }
}
