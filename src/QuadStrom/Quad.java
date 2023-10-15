package QuadStrom;

import Objekty.Polygon;
import Objekty.Suradnica;
import Ostatne.IPolygon;
import Ostatne.Konstanty;

import java.util.ArrayList;

public class Quad<T extends IPolygon> extends Polygon
{
    // SZ | SV    0 | 1
    // -- . --    - . -
    // JZ | JV    3 | 2
    private static final int POCET_PODQUADOV = 4;

    private final ArrayList<T> data;
    private final int hlbkaQuadu;

    // Quady su v nasledujucom poradi:
    //                                   x  y  i
    // -> Severo-zapad  (vlavo hore)  -> -  +  0
    // -> Severo-vychod (vpravo hore) -> +  +  1
    // -> Juho-vychod   (vpravo dole) -> +  -  2
    // -> Juho-zapad    (vlavo dole)  -> -  -  3
    private Quad<T>[] podquady;

    public Quad(Suradnica surVlavoDole, Suradnica surVpravoHore, int hlbkaQuadu)
    {
        this.surVlavoDole = surVlavoDole;
        this.surVpravoHore = surVpravoHore;

        this.data = new ArrayList<>();
        this.hlbkaQuadu = hlbkaQuadu;
        this.podquady = new Quad[POCET_PODQUADOV];
    }

    // Metoda rozdeli dany quad na 4 rovnako velke oblasti
    // Priklad:
    // -> zaklad: {-180; -90}, {180; 90}
    //    -> SZ:  {-180;   0}, {  0; 90}
    //    -> SV:  {   0;   0}, {180; 90}
    //    -> JV:  {   0; -90}, {180;  0}
    //    -> JZ:  {-180; -90}, {  0;  0}
    public void rozdel()
    {
        if (this.jeRozdeleny())
        {
            throw new RuntimeException("Quad je uz rozdeleny!");
        }

        double stredX = (this.surVlavoDole.getX() + this.surVpravoHore.getX()) / 2;
        double stredY = (this.surVlavoDole.getY() + this.surVpravoHore.getY()) / 2;

        Suradnica SZvlavoDole = new Suradnica(this.surVlavoDole.getX(), stredY);
        Suradnica SZvpravoHore = new Suradnica(stredX, this.surVpravoHore.getY());
        this.podquady[Konstanty.SZ] = new Quad<T>(SZvlavoDole, SZvpravoHore, this.hlbkaQuadu + 1);

        Suradnica SVvlavoDole = new Suradnica(stredX, stredY);
        Suradnica SVvpravoHore = new Suradnica(this.surVpravoHore.getX(), this.surVpravoHore.getY());
        this.podquady[Konstanty.SV] = new Quad<T>(SVvlavoDole, SVvpravoHore, this.hlbkaQuadu + 1);

        Suradnica JVvlavoDole = new Suradnica(stredX, this.surVlavoDole.getY());
        Suradnica JVvpravoHore = new Suradnica(this.surVpravoHore.getX(), stredY);
        this.podquady[Konstanty.JV] = new Quad<T>(JVvlavoDole, JVvpravoHore, this.hlbkaQuadu + 1);

        Suradnica JZvlavoDole = new Suradnica(this.surVlavoDole.getX(), this.surVlavoDole.getY());
        Suradnica JZvpravoHore = new Suradnica(stredX, stredY);
        this.podquady[Konstanty.JZ] = new Quad<T>(JZvlavoDole, JZvpravoHore, this.hlbkaQuadu + 1);
    }

    public void vymazPodquady()
    {
        for (Quad<T> podquad : this.podquady)
        {
            if (!podquad.getData().isEmpty() || podquad.jeRozdeleny())
            {
                throw new RuntimeException("Nie je mozne zmazat quad, ktory obsahuje data!");
            }
        }

        this.podquady = new Quad[POCET_PODQUADOV];
    }

    public boolean jeRozdeleny()
    {
        return this.podquady[Konstanty.SZ] != null ||
               this.podquady[Konstanty.SV] != null ||
               this.podquady[Konstanty.JV] != null ||
               this.podquady[Konstanty.JZ] != null;
    }

    public Quad<T>[] getPodQuady()
    {
        return this.podquady;
    }

    public Quad<T> getPodQuad(int index)
    {
        return this.podquady[index];
    }

    public ArrayList<T> getData()
    {
        return this.data;
    }

    public int getHlbkaQuady()
    {
        return this.hlbkaQuadu;
    }
}
