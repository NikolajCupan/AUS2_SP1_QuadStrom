package QuadStrom;

import Objekty.Suradnica;
import Ostatne.Konstanty;

public class Quad<T>
{
    private Suradnica surVlavoDole;
    private Suradnica surVpravoHore;

    private T data;

    private Quad<T> SZ;
    private Quad<T> SV;
    private Quad<T> JV;
    private Quad<T> JZ;

    public Quad()
    {
        this.surVlavoDole = new Suradnica(Konstanty.X_MIN, Konstanty.Y_MIN);
        this.surVpravoHore = new Suradnica(Konstanty.X_MAX, Konstanty.Y_MAX);

        this.data = null;

        this.rozdel();
    }

    public Quad(Suradnica surVlavoDole, Suradnica surVpravoHore)
    {
        this.surVlavoDole = surVlavoDole;
        this.surVpravoHore = surVpravoHore;

        this.data = null;

        this.SZ = null;
        this.SV = null;
        this.JV = null;
        this.JZ = null;
    }

    // Metoda rozdeli dany usek na 4 rovnako velke oblasti
    // Priklad:
    // -> zaklad: {-180; -90}, {180; 90}
    //    -> SZ:  {-180;   0}, {  0; 90}
    //    -> SV:  {   0;   0}, {180; 90}
    //    -> JV:  {   0; -90}, {180;  0}
    //    -> JZ:  {-180; -90}, {  0;  0}
    private void rozdel()
    {
        if (this.SZ != null || this.SV != null || this.JV != null || this.JZ != null)
        {
            throw new RuntimeException("Oblast je uz rozdelena!");
        }

        double stredX = (this.surVlavoDole.getX() + this.surVpravoHore.getX()) / 2;
        double stredY = (this.surVlavoDole.getY() + this.surVpravoHore.getY()) / 2;

        Suradnica SZvlavoDole = new Suradnica(this.surVlavoDole.getX(), stredY);
        Suradnica SZvpravoHore = new Suradnica(stredX, this.surVpravoHore.getY());
        this.SZ = new Quad<>(SZvlavoDole, SZvpravoHore);

        Suradnica SVvlavoDole = new Suradnica(stredX, stredY);
        Suradnica SVvpravoHore = new Suradnica(this.surVpravoHore.getX(), this.surVpravoHore.getY());
        this.SV = new Quad<>(SVvlavoDole, SVvpravoHore);

        Suradnica JVvlavoDole = new Suradnica(stredX, this.surVlavoDole.getY());
        Suradnica JVvpravoHore = new Suradnica(this.surVpravoHore.getX(), stredY);
        this.JV = new Quad<>(JVvlavoDole, JVvpravoHore);

        Suradnica JZvlavoDole = new Suradnica(this.surVlavoDole.getX(), this.surVpravoHore.getY());
        Suradnica JZvpravoHore = new Suradnica(stredX, stredY);
        this.JZ = new Quad<>(JZvlavoDole, JZvpravoHore);
    }
}
