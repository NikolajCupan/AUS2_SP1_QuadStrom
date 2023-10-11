package QuadStrom;

import Objekty.Polygon;
import Objekty.Suradnica;

import java.util.ArrayList;

public class Quad<T> extends Polygon
{
    // SZ | SV    0 | 1
    // -- . --    - . -
    // JZ | JV    3 | 2
    private static final int POCET_PODQUADOV = 4;
    private static final int SZ = 0;
    private static final int SV = 1;
    private static final int JV = 2;
    private static final int JZ = 3;

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
    public int getPocetElementov()
    {
        return getPocetElementovPodstrom(this);
    }

    private int getPocetElementovPodstrom(Quad oblast)
    {
        int pocetPodstrom = 0;
        pocetPodstrom += oblast.data.size();

        if (oblast.jeRozdeleny())
        {
            for (Quad podoblast : oblast.podquady)
            {
                pocetPodstrom += getPocetElementovPodstrom(podoblast);
            }
        }

        return pocetPodstrom;
    }

    public void vloz(T pridavany)
    {
        Quad<T> curQuad = this;

        while (true)
        {
            if (!curQuad.jeRozdeleny())
            {
                curQuad.rozdel();
            }

            boolean vPodquade = false;
            for (Quad<T> podquad : curQuad.podquady)
            {
                // Polygon sa moze nachadzat v maximalne 1 podquade
                if (podquad.leziVnutri(pridavany))
                {
                    curQuad = podquad;
                    vPodquade = true;
                    break;
                }
            }

            // Ziadny podquad nevyhovuje
            if (!vPodquade)
            {
                if (curQuad.leziVnutri(pridavany))
                {
                    curQuad.data.add(pridavany);
                    break;
                }
                else
                {
                    throw new RuntimeException("Neplatny vkladany element!");
                }
            }
        }
    }

    // Metoda rozdeli dany quad na 4 rovnako velke oblasti
    // Priklad:
    // -> zaklad: {-180; -90}, {180; 90}
    //    -> SZ:  {-180;   0}, {  0; 90}
    //    -> SV:  {   0;   0}, {180; 90}
    //    -> JV:  {   0; -90}, {180;  0}
    //    -> JZ:  {-180; -90}, {  0;  0}
    private void rozdel()
    {
        if (this.jeRozdeleny())
        {
            throw new RuntimeException("Quad je uz rozdeleny!");
        }

        double stredX = (this.surVlavoDole.getX() + this.surVpravoHore.getX()) / 2;
        double stredY = (this.surVlavoDole.getY() + this.surVpravoHore.getY()) / 2;

        Suradnica SZvlavoDole = new Suradnica(this.surVlavoDole.getX(), stredY);
        Suradnica SZvpravoHore = new Suradnica(stredX, this.surVpravoHore.getY());
        this.podquady[SZ] = new Quad<T>(SZvlavoDole, SZvpravoHore);

        Suradnica SVvlavoDole = new Suradnica(stredX, stredY);
        Suradnica SVvpravoHore = new Suradnica(this.surVpravoHore.getX(), this.surVpravoHore.getY());
        this.podquady[SV] = new Quad<T>(SVvlavoDole, SVvpravoHore);

        Suradnica JVvlavoDole = new Suradnica(stredX, this.surVlavoDole.getY());
        Suradnica JVvpravoHore = new Suradnica(this.surVpravoHore.getX(), stredY);
        this.podquady[JV] = new Quad<T>(JVvlavoDole, JVvpravoHore);

        Suradnica JZvlavoDole = new Suradnica(this.surVlavoDole.getX(), this.surVlavoDole.getY());
        Suradnica JZvpravoHore = new Suradnica(stredX, stredY);
        this.podquady[JZ] = new Quad<T>(JZvlavoDole, JZvpravoHore);
    }

    private boolean jeRozdeleny()
    {
        return this.podquady[SZ] != null ||
               this.podquady[SV] != null ||
               this.podquady[JV] != null ||
               this.podquady[JZ] != null;
    }
}
