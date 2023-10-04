package Objekty;

public class Suradnica
{
    public static final char VYCHOD = 'V';
    public static final char ZAPAD = 'Z';
    public static final char SEVER = 'S';
    public static final char JUH = 'J';

    public static final double DLZKA_MIN = -180;
    public static final double DLZKA_MAX = 180;
    public static final double SIRKA_MIN = -90;
    public static final double SIRKA_MAX = 90;

    private double poziciaDlzky;
    private double poziciaSirky;
    private char dlzka;
    private char sirka;

    public Suradnica(double poziciaDlzky, double poziciaSirky)
    {
        // kontrola, ci su zadane udaje validne
        this.validujDlzku(poziciaDlzky);
        this.validujSirku(poziciaSirky);

        this.poziciaDlzky = poziciaDlzky;
        this.poziciaSirky = poziciaSirky;
        this.dlzka = (poziciaDlzky < 0) ? ZAPAD : VYCHOD;
        this.sirka = (poziciaSirky < 0) ? JUH : SEVER;
    }

    public Suradnica()
    {
        this.poziciaDlzky = 0;
        this.poziciaSirky = 0;
        this.dlzka = VYCHOD;
        this.sirka = SEVER;
    }

    private void validujDlzku(double poziciaDlzky)
    {
        if (poziciaDlzky < DLZKA_MIN || poziciaDlzky > DLZKA_MAX)
        {
            throw new IllegalArgumentException("Nespravna pozicia dlzky, musi byt z rozsahu <-180; 180>!");
        }
    }

    private void validujSirku(double poziciaSirky)
    {
        if (poziciaSirky < SIRKA_MIN || poziciaSirky > SIRKA_MAX)
        {
            throw new IllegalArgumentException("Nespravna pozicia sirky, musi byt z rozsahu <-90; 90>!");
        }
    }

    public double getPoziciaDlzky()
    {
        return this.poziciaDlzky;
    }

    public double getPoziciaSirky()
    {
        return this.poziciaSirky;
    }

    public void setPoziciaDlzky(double poziciaDlzky)
    {
        this.validujDlzku(poziciaDlzky);

        this.poziciaDlzky = poziciaDlzky;
        this.dlzka = (poziciaDlzky < 0) ? ZAPAD : VYCHOD;
    }

    public void setPoziciaSirky(double poziciaSirky)
    {
        this.validujSirku(poziciaSirky);

        this.poziciaSirky = poziciaSirky;
        this.sirka = (poziciaSirky < 0) ? JUH : SEVER;
    }
}
