package QuadStrom;

import Objekty.Suradnica;
import Ostatne.Konstanty;

public class Quad<T>
{
    private Suradnica surVlavoDole;
    private Suradnica surVpravoHore;

    private T data;

    private Quad SZ;
    private Quad SV;
    private Quad JV;
    private Quad JZ;

    public Quad()
    {
        this.surVlavoDole = new Suradnica(Konstanty.DLZKA_MIN, Konstanty.SIRKA_MIN);
        this.surVpravoHore = new Suradnica(Konstanty.DLZKA_MAX, Konstanty.SIRKA_MAX);

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
            throw new RuntimeException("Oblast uz je rozdelena!");
        }

        double stredDlzka = (this.surVlavoDole.getPoziciaDlzky() + this.surVpravoHore.getPoziciaDlzky()) / 2;
        double stredSirka = (this.surVlavoDole.getPoziciaSirky() + this.surVpravoHore.getPoziciaSirky()) / 2;

        Suradnica SZvlavoDole = new Suradnica(this.surVlavoDole.getPoziciaDlzky(), stredSirka);
        Suradnica SZvpravoHore = new Suradnica(stredDlzka, this.surVpravoHore.getPoziciaSirky());
        this.SZ = new Quad<T>(SZvlavoDole, SZvpravoHore);

        Suradnica SVvlavoDole = new Suradnica(stredDlzka, stredSirka);
        Suradnica SVvpravoHore = new Suradnica(this.surVpravoHore.getPoziciaDlzky(), this.surVpravoHore.getPoziciaSirky());
        this.SV = new Quad<T>(SVvlavoDole, SVvpravoHore);

        Suradnica JVvlavoDole = new Suradnica(stredDlzka, this.surVlavoDole.getPoziciaSirky());
        Suradnica JVvpravoHore = new Suradnica(this.surVpravoHore.getPoziciaDlzky(), stredSirka);
        this.JV = new Quad<T>(JVvlavoDole, JVvpravoHore);

        Suradnica JZvlavoDole = new Suradnica(this.surVlavoDole.getPoziciaDlzky(), this.surVpravoHore.getPoziciaSirky());
        Suradnica JZvpravoHore = new Suradnica(stredDlzka, stredSirka);
        this.JZ = new Quad<T>(JZvlavoDole, JZvpravoHore);
    }
}
