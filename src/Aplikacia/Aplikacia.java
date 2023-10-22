package Aplikacia;

import Ostatne.Konstanty;

public class Aplikacia
{
    private final Logika logika;

    public Aplikacia()
    {
        this.logika = new Logika(Konstanty.QS_X_MIN, Konstanty.QS_Y_MIN, Konstanty.QS_X_MAX, Konstanty.QS_Y_MAX, Konstanty.QS_DEFAULT_MAX_HLBKA);
    }

    public void vykonavaj()
    {
    }
}
