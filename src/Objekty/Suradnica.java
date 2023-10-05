package Objekty;

import Ostatne.Konstanty;

// Dlzka: <-180; 180> => X suradnica
// Sirka: < -90;  90> => Y suradnica
public class Suradnica
{
    public static final char VYCHOD = 'V';
    public static final char ZAPAD = 'Z';
    public static final char SEVER = 'S';
    public static final char JUH = 'J';

    private double x;
    private double y;
    private char dlzka;
    private char sirka;

    public Suradnica(double x, double y)
    {
        // kontrola, ci su zadane udaje validne
        this.validujX(x);
        this.validujY(y);

        this.x = x;
        this.y = y;
        this.dlzka = (x < 0) ? ZAPAD : VYCHOD;
        this.sirka = (y < 0) ? JUH : SEVER;
    }

    public Suradnica()
    {
        this.x = 0;
        this.y = 0;
        this.dlzka = VYCHOD;
        this.sirka = SEVER;
    }

    private void validujX(double x)
    {
        if (x < Konstanty.X_MIN || x > Konstanty.X_MAX)
        {
            throw new IllegalArgumentException("Nespravna pozicia x (dlzka), musi byt z rozsahu <" + Konstanty.X_MIN + ", " + Konstanty.X_MAX + ">!");
        }
    }

    private void validujY(double y)
    {
        if (y < Konstanty.Y_MIN || y > Konstanty.Y_MAX)
        {
            throw new IllegalArgumentException("Nespravna pozicia y (sirka), musi byt z rozsahu <" + Konstanty.Y_MIN + ", " + Konstanty.Y_MAX + ">!");
        }
    }

    public double getX()
    {
        return this.x;
    }

    public double getY()
    {
        return this.y;
    }

    public void setX(double x)
    {
        this.validujX(x);

        this.x = x;
        this.dlzka = (x < 0) ? ZAPAD : VYCHOD;
    }

    public void setY(double y)
    {
        this.validujY(y);

        this.y = y;
        this.sirka = (y < 0) ? JUH : SEVER;
    }
}
