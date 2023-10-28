package Objekty;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Parcela extends Polygon
{
    private int cisloParcely;
    private String popis;
    // Zoznam nehnutelnosti, ktore lezia na danej parcele
    private ArrayList<Nehnutelnost> nehnutelnosti;

    public Parcela(int cisloParcely, String popis, Suradnica suradnica1, Suradnica suradnica2)
    {
        this.nastavSuradnice(suradnica1, suradnica2);

        this.cisloParcely = cisloParcely;
        this.popis = popis;
        this.nehnutelnosti = new ArrayList<>();
    }

    // Metoda sa pokusi pridat nehnutelnost do zoznamu nehnutelnosti, ktore lezia na parcele,
    // ak pridanie zlyha (na parcele nelezi dana nehnutelnost), vyhodi sa vynimka
    public void skusPridatNehnutelnost(Nehnutelnost nehnutelnost)
    {
        if (!this.prekryva(nehnutelnost))
        {
            throw new RuntimeException("Na parcele nelezi dana nehnutelnost!");
        }

        this.nehnutelnosti.add(nehnutelnost);
    }

    // Metoda sa pokusi odobrat nehnutelnost zo zoznamu nehnutelnosti, ktore lezia na parcele
    // ak odobratie zlyha (na parcele nelezi dana nehnutelnost), vyhodi sa vynimka
    public void skusOdobratNehnutelnost(Nehnutelnost nehnutelnost)
    {
        if (!this.prekryva(nehnutelnost))
        {
            throw new RuntimeException("Na parcele nelezi dana nehnutelnost!");
        }

        this.nehnutelnosti.remove(nehnutelnost);
    }

    public void setCisloParcely(int cisloParcely)
    {
        this.cisloParcely = cisloParcely;
    }

    public void setPopis(String popis)
    {
        this.popis = popis;
    }

    public ArrayList<Nehnutelnost> getNehnutelnosti()
    {
        return this.nehnutelnosti;
    }

    public String getPopis()
    {
        return popis;
    }

    @Override
    public int getKluc()
    {
        return this.cisloParcely;
    }

    @Override
    public String toString()
    {
        DecimalFormat formatovac = new DecimalFormat("#.##");
        return "Parcela: " + this.cisloParcely + " {" + formatovac.format(this.getVlavoDoleX()) + ", " + formatovac.format(this.getVlavoDoleY()) +
                "}, {" + formatovac.format(this.getVpravoHoreX()) + ", " + formatovac.format(this.getVpravoHoreY()) + "}";
    }
}
