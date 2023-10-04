package Objekty;

import java.util.ArrayList;

public class Nehnutelnost
{
    private int supisneCislo;
    private String popis;
    private ArrayList<Parcela> parcely;

    private Suradnica surVlavoDole;
    private Suradnica surVpravoHore;

    public Nehnutelnost(int supisneCislo, String popis, Suradnica suradnica1, Suradnica suradnica2)
    {
        this.supisneCislo = supisneCislo;
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

    public void pridajParcelu(Parcela parcela)
    {

    }

    // Metoda vrati true ak this nehnutelnost lezi na danej parcele
    public boolean leziNaParcele(Parcela parcela)
    {
        if (this.surVlavoDole.getPoziciaDlzky() > parcela.getSurVpravoHore().getPoziciaDlzky() ||
            this.surVlavoDole.getPoziciaSirky() > parcela.getSurVpravoHore().getPoziciaSirky() ||
            parcela.getSurVlavoDole().getPoziciaDlzky() > this.surVpravoHore.getPoziciaDlzky() ||
            parcela.getSurVlavoDole().getPoziciaSirky() > this.surVpravoHore.getPoziciaSirky())
        {
            return false;
        }

        return true;
    }
}
