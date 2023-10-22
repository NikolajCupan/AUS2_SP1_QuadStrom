package Objekty;

import java.util.ArrayList;

public class Nehnutelnost extends Polygon
{
    private int supisneCislo;
    private String popis;
    // Zoznam parciel, na ktorych lezi dana nehnutelnost
    private ArrayList<Parcela> parcely;

    public Nehnutelnost(int supisneCislo, String popis, Suradnica suradnica1, Suradnica suradnica2)
    {
        this.nastavSuradnice(suradnica1, suradnica2);

        this.supisneCislo = supisneCislo;
        this.popis = popis;
        this.parcely = new ArrayList<>();
    }

    // Metoda sa pokusi pridat parcelu do zoznamu parciel, na ktorych lezi nehnutelnost,
    // ak pridanie zlyha (nehnutelnost nelezi na danej parcele), vyhodi sa vynimka
    public void skusPridatParcelu(Parcela parcela)
    {
        if (!this.prekryva(parcela))
        {
            throw new RuntimeException("Nehnutelnost nelezi na danej parcele!");
        }

        this.parcely.add(parcela);
    }

    // Metoda sa pokusi odobrat parcelu zo zoznamu parciel, na ktorych lezi nehnutelnost,
    // ak odobratie zlyha (nehnutelnost nelezi na danej parcele), vyhodi sa vynimka
    public void skusOdobratParcelu(Parcela parcela)
    {
        if (!this.prekryva(parcela))
        {
            throw new RuntimeException("Nehnutelnost nelezi na danej parcele!");
        }

        this.parcely.remove(parcela);
    }

    public ArrayList<Parcela> getParcely()
    {
        return this.parcely;
    }

    @Override
    public int getKluc()
    {
        return this.supisneCislo;
    }
}
