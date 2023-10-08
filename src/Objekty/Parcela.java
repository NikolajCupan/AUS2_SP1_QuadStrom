package Objekty;

import java.util.ArrayList;

public class Parcela extends Podorys
{
    private int cisloParcely;
    // Zoznam nehnutelnosti, ktore lezia na danej parcele
    private ArrayList<Nehnutelnost> nehnutelnosti;

    public Parcela(int cisloParcely, String popis, Suradnica suradnica1, Suradnica suradnica2)
    {
        this.cisloParcely = cisloParcely;
        this.popis = popis;
        this.nehnutelnosti = new ArrayList<>();
        this.nastavSuradnice(suradnica1, suradnica2);
    }

    // Metoda sa pokusi pridat nehnutelnost do zoznamu nehnutelnosti, ktore lezia na parcele
    // Ak pridanie zlyha (na parcele nelezi dana nehnutelnost), vyhodi sa vynimka
    public void skusPridatNehnutelnost(Nehnutelnost nehnutelnost)
    {
        if (!this.podorysySaPrekryvaju(nehnutelnost))
        {
            throw new RuntimeException("Na parcele nelezi dana nehnutelnost!");
        }

        this.nehnutelnosti.add(nehnutelnost);
    }
}
