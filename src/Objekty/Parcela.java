package Objekty;

import java.util.ArrayList;

public class Parcela
{
    private int cisloParcely;
    private String popis;
    private ArrayList<Nehnutelnost> nehnutelnosti;

    private Suradnica surVlavoDole;
    private Suradnica surVpravoHore;

    public Parcela(int cisloParcely, String popis, Suradnica suradnica1, Suradnica suradnica2)
    {
        this.cisloParcely = cisloParcely;
        this.popis = popis;
        this.nastavSuradnice(suradnica1, suradnica2);
    }

    private void nastavSuradnice(Suradnica suradnica1, Suradnica suradnica2)
    {
        this.surVlavoDole = new Suradnica();
        this.surVpravoHore = new Suradnica();

        this.surVlavoDole.setPoziciaDlzky(Math.min(suradnica1.getPoziciaDlzky(), suradnica2.getPoziciaDlzky()));
        this.surVlavoDole.setPoziciaSirky(Math.min(suradnica1.getPoziciaSirky(), suradnica2.getPoziciaSirky()));

        this.surVpravoHore.setPoziciaDlzky(Math.max(suradnica1.getPoziciaDlzky(), suradnica2.getPoziciaDlzky()));
        this.surVpravoHore.setPoziciaSirky(Math.max(suradnica1.getPoziciaSirky(), suradnica2.getPoziciaSirky()));
    }

    public Suradnica getSurVlavoDole()
    {
        return this.surVlavoDole;
    }

    public Suradnica getSurVpravoHore()
    {
        return this.surVpravoHore;
    }
}
