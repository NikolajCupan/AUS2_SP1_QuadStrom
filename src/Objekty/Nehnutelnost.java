package Objekty;

import java.util.ArrayList;

public class Nehnutelnost extends Podorys
{
    private int supisneCislo;
    private ArrayList<Parcela> parcely;

    public Nehnutelnost(int supisneCislo, String popis, Suradnica suradnica1, Suradnica suradnica2)
    {
        this.supisneCislo = supisneCislo;
        this.popis = popis;
        this.parcely = new ArrayList<>();
        this.nastavSuradnice(suradnica1, suradnica2);
    }

    // Metoda sa pokusi pridat parcelu do zoznamu parciel, na ktorych lezi nehnutelnost
    // Ak pridanie zlyha (nehnutelnost nelezi na danej parcele), vyhodi sa vynimka
    public void skusPridatParcelu(Parcela parcela)
    {
        if (!this.podorysySaPrekryvaju(parcela))
        {
            throw new RuntimeException("Nehnutelnost nelezi na danej parcele!");
        }

        this.parcely.add(parcela);
    }
}
