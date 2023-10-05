package QuadStrom;

import Objekty.Nehnutelnost;
import Objekty.Podorys;
import Objekty.Suradnica;
import Ostatne.IPodorys;
import Ostatne.Konstanty;

import java.util.ArrayList;

public class Quad<T extends IPodorys>
{
    private static final int POCET_PODOBLASTI = 4;
    private static final int SV = 0;
    private static final int SZ = 1;
    private static final int JV = 2;
    private static final int JZ = 3;

    private Suradnica surVlavoDole;
    private Suradnica surVpravoHore;

    private ArrayList<T> data;

    // Oblasti su v nasledujucom poradi:
    //                                   x  y  i
    // -> Severo-vychod (vlavo hore)  -> -  +  0
    // -> Severo-zapad  (vpravo hore) -> +  +  1
    // -> Juho-zapad    (vpravo dole) -> +  -  2
    // -> Juho-vychod   (vlavo dole)  -> -  -  3
    private Quad<T>[] podoblasti;

    public Quad()
    {
        this.surVlavoDole = new Suradnica(Konstanty.X_MIN, Konstanty.Y_MIN);
        this.surVpravoHore = new Suradnica(Konstanty.X_MAX, Konstanty.Y_MAX);

        this.data = new ArrayList<>();
        this.podoblasti = new Quad[POCET_PODOBLASTI];
    }

    public Quad(Suradnica surVlavoDole, Suradnica surVpravoHore)
    {
        this.surVlavoDole = surVlavoDole;
        this.surVpravoHore = surVpravoHore;

        this.data = new ArrayList<>();
        this.podoblasti = new Quad[POCET_PODOBLASTI];
    }

    public int getPocetElementov()
    {
        return getPocetElementovPodstrom(this);
    }

    private int getPocetElementovPodstrom(Quad<T> oblast)
    {
        int pocetPodstrom = 0;
        pocetPodstrom += oblast.data.size();

        if (oblast.jeRozdelena())
        {
            for (Quad<T> podoblast : oblast.podoblasti)
            {
                pocetPodstrom += getPocetElementovPodstrom(podoblast);
            }
        }

        return pocetPodstrom;
    }

    public ArrayList<T> vyhladajNehnutelnosti(Suradnica suradnica)
    {
        Quad<T> curOblast = this;
        ArrayList<T> nehnutelnosti = new ArrayList<>();
        
        while (true)
        {
            for (T element : curOblast.data)
            {
                if (!(element instanceof Nehnutelnost))
                {
                    continue;
                }

                if (element.jeVnutri(suradnica))
                {
                    nehnutelnosti.add(element);
                }
            }

            break;
        }

        return nehnutelnosti;
    }

    public void vloz(T pridavany)
    {
        Quad<T> curOblast = this;

        while (true)
        {
            if (!curOblast.jeRozdelena())
            {
                curOblast.rozdel();
            }

            boolean vPodoblasti = false;
            for (Quad<T> podoblast : curOblast.podoblasti)
            {
                // Podorys sa moze nachadzat v maximalne 1 podoblasti
                if (podoblast.jeVOblasti(pridavany))
                {
                    curOblast = podoblast;
                    vPodoblasti = true;
                }
            }

            // Ziadna podoblast nevyhovuje
            if (!vPodoblasti)
            {
                if (curOblast.jeVOblasti(pridavany))
                {
                    curOblast.data.add(pridavany);
                    break;
                }
                else
                {
                    throw new RuntimeException("Neplatny vkladany element!");
                }
            }
        }
    }

    private void vlozDoOblasti(T pridavany)
    {
        this.data.add(pridavany);
    }

    private boolean jeVOblasti(T vnutorny)
    {
        if (vnutorny.getSurVlavoDole().getX() >= this.surVlavoDole.getX() &&
            vnutorny.getSurVlavoDole().getY() >= this.surVlavoDole.getY() &&
            vnutorny.getSurVpravoHore().getX() <= this.surVpravoHore.getX() &&
            vnutorny.getSurVpravoHore().getY() <= this.surVpravoHore.getY())
        {
            return true;
        }

        return false;
    }

    // Metoda rozdeli danu oblast na 4 rovnako velke oblasti
    // Priklad:
    // -> zaklad: {-180; -90}, {180; 90}
    //    -> SZ:  {-180;   0}, {  0; 90}
    //    -> SV:  {   0;   0}, {180; 90}
    //    -> JV:  {   0; -90}, {180;  0}
    //    -> JZ:  {-180; -90}, {  0;  0}
    private void rozdel()
    {
        if (this.jeRozdelena())
        {
            throw new RuntimeException("Oblast je uz rozdelena!");
        }

        double stredX = (this.surVlavoDole.getX() + this.surVpravoHore.getX()) / 2;
        double stredY = (this.surVlavoDole.getY() + this.surVpravoHore.getY()) / 2;

        Suradnica SZvlavoDole = new Suradnica(this.surVlavoDole.getX(), stredY);
        Suradnica SZvpravoHore = new Suradnica(stredX, this.surVpravoHore.getY());
        this.podoblasti[SV] = new Quad<>(SZvlavoDole, SZvpravoHore);

        Suradnica SVvlavoDole = new Suradnica(stredX, stredY);
        Suradnica SVvpravoHore = new Suradnica(this.surVpravoHore.getX(), this.surVpravoHore.getY());
        this.podoblasti[SZ] = new Quad<>(SVvlavoDole, SVvpravoHore);

        Suradnica JVvlavoDole = new Suradnica(stredX, this.surVlavoDole.getY());
        Suradnica JVvpravoHore = new Suradnica(this.surVpravoHore.getX(), stredY);
        this.podoblasti[JV] = new Quad<>(JVvlavoDole, JVvpravoHore);

        Suradnica JZvlavoDole = new Suradnica(this.surVlavoDole.getX(), this.surVlavoDole.getY());
        Suradnica JZvpravoHore = new Suradnica(stredX, stredY);
        this.podoblasti[JZ] = new Quad<>(JZvlavoDole, JZvpravoHore);
    }

    private boolean jeRozdelena()
    {
        if (this.podoblasti[SV] == null && this.podoblasti[SZ] == null && this.podoblasti[JV] == null && this.podoblasti[JZ] == null)
        {
            return false;
        }

        return true;
    }

    public Suradnica getSurVlavoDole()
    {
        return this.surVlavoDole;
    }

    public Suradnica getSurVpravoHore()
    {
        return this.surVpravoHore;
    }
}
