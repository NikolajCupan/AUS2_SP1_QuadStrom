package Objekty;

import java.text.DecimalFormat;
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

    public void setSupisneCislo(int supisneCislo)
    {
        this.supisneCislo = supisneCislo;
    }

    public void setPopis(String popis)
    {
        this.popis = popis;
    }

    public ArrayList<Parcela> getParcely()
    {
        return this.parcely;
    }

    public String getPopis()
    {
        return popis;
    }

    public int getKluc()
    {
        return this.supisneCislo;
    }

    @Override
    public String toString()
    {
        DecimalFormat formatovac = new DecimalFormat("#.##");
        return "Nehnuteľnost: " + this.supisneCislo + " {" + formatovac.format(this.getVlavoDoleX()) + ", " + formatovac.format(this.getVlavoDoleY()) +
               "}, {" + formatovac.format(this.getVpravoHoreX()) + ", " + formatovac.format(this.getVpravoHoreY()) + "}";
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Nehnutelnost nehnutelnost))
        {
            return false;
        }

        if (this.getVlavoDoleX() == nehnutelnost.getVlavoDoleX() &&
            this.getVlavoDoleY() == nehnutelnost.getVlavoDoleY() &&
            this.getVpravoHoreX() == nehnutelnost.getVpravoHoreX() &&
            this.getVpravoHoreY() == nehnutelnost.getVpravoHoreY() &&
            this.getKluc() == nehnutelnost.getKluc())
        {
            return true;
        }

        return false;
    }
}
