package QuadStrom;

import Objekty.Polygon;
import Objekty.Suradnica;
import Ostatne.IPolygon;
import Ostatne.Konstanty;

import java.util.ArrayList;
import java.util.Stack;

public class Quad<T extends IPolygon> extends Polygon
{
    // SZ | SV    0 | 1
    // -- . --    - . -
    // JZ | JV    3 | 2
    private static final int POCET_PODQUADOV = 4;

    private ArrayList<T> data;

    // Quady su v nasledujucom poradi:
    //                                   x  y  i
    // -> Severo-zapad  (vlavo hore)  -> -  +  0
    // -> Severo-vychod (vpravo hore) -> +  +  1
    // -> Juho-vychod   (vpravo dole) -> +  -  2
    // -> Juho-zapad    (vlavo dole)  -> -  -  3
    private final Quad<T>[] podquady;

    public Quad(Suradnica surVlavoDole, Suradnica surVpravoHore)
    {
        this.surVlavoDole = surVlavoDole;
        this.surVpravoHore = surVpravoHore;

        this.data = new ArrayList<>();
        this.podquady = new Quad[POCET_PODQUADOV];
    }

    /*
    private ArrayList<T> vyhladaj(Quad oblast, Suradnica suradnica)
    {
        ArrayList<T> prvky = new ArrayList<>();

        // Kazdy podorys v danej oblasti je kandidatom
        for (T prvok : oblast.data)
        {

        }
        for (T prvok : oblast.data)
        {
            if (prvok.leziVnutri(suradnica))
            {
                podorysy.add(prvok);
            }
        }

        // Oblast nemusi byt rozdelena
        if (oblast.jeRozdelena())
        {
            for (Quad podoblast : oblast.podoblasti)
            {
                if (podoblast.jeSuradnicaVOblasti(suradnica))
                {
                    podorysy.addAll(this.vyhladaj(podoblast, suradnica, hladanyTyp));
                }
            }
        }

        return podorysy;
    }
    */

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
        this.podquady[Konstanty.SZ] = new Quad<T>(SZvlavoDole, SZvpravoHore);

        Suradnica SVvlavoDole = new Suradnica(stredX, stredY);
        Suradnica SVvpravoHore = new Suradnica(this.surVpravoHore.getX(), this.surVpravoHore.getY());
        this.podquady[Konstanty.SV] = new Quad<T>(SVvlavoDole, SVvpravoHore);

        Suradnica JVvlavoDole = new Suradnica(stredX, this.surVlavoDole.getY());
        Suradnica JVvpravoHore = new Suradnica(this.surVpravoHore.getX(), stredY);
        this.podquady[Konstanty.JV] = new Quad<T>(JVvlavoDole, JVvpravoHore);

        Suradnica JZvlavoDole = new Suradnica(this.surVlavoDole.getX(), this.surVlavoDole.getY());
        Suradnica JZvpravoHore = new Suradnica(stredX, stredY);
        this.podquady[Konstanty.JZ] = new Quad<T>(JZvlavoDole, JZvpravoHore);
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
}
