package QuadStrom;

import Objekty.Podorys;
import Objekty.Suradnica;
import Ostatne.IPodorys;
import Ostatne.Konstanty;

import java.util.*;

public class Quad implements Iterable<Quad>
{
    // SZ | SV    0 | 1
    // -- . --    - . -
    // JZ | JV    3 | 2
    private static final int POCET_PODOBLASTI = 4;
    private static final int SZ = 0;
    private static final int SV = 1;
    private static final int JV = 2;
    private static final int JZ = 3;

    private Suradnica surVlavoDole;
    private Suradnica surVpravoHore;

    private ArrayList<Podorys> data;

    // Oblasti su v nasledujucom poradi:
    //                                   x  y  i
    // -> Severo-zapad  (vlavo hore)  -> -  +  0
    // -> Severo-vychod (vpravo hore) -> +  +  1
    // -> Juho-vychod   (vpravo dole) -> +  -  2
    // -> Juho-zapad    (vlavo dole)  -> -  -  3
    private final Quad[] podoblasti;

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

    public <U extends IPodorys> ArrayList<U> vyhladajObjekty(Suradnica suradnica, U hladanyTyp)
    {
        // Array podorysov pretypunem na Array U
        // U je potomkom Podorysu, cize to moze byt Nehnutelnost alebo Parcela
        return (ArrayList<U>)this.vyhladatPodorysyPodstrom(this, suradnica, hladanyTyp);
    }

    private ArrayList<Podorys> vyhladatPodorysyPodstrom(Quad oblast, Suradnica suradnica, Object hladanyTyp)
    {
        ArrayList<Podorys> podorysy = new ArrayList<>();

        // Kazdy podorys v danej oblasti je kandidatom
        for (Podorys podorys : oblast.data)
        {
            if (podorys.getClass().equals(hladanyTyp.getClass()) && podorys.jeSuradnicaVPodoryse(suradnica))
            {
                podorysy.add(podorys);
            }
        }

        // Oblast nemusi byt rozdelena
        if (oblast.jeRozdelena())
        {
            for (Quad podoblast : oblast.podoblasti)
            {
                if (podoblast.jeSuradnicaVOblasti(suradnica))
                {
                    podorysy.addAll(this.vyhladatPodorysyPodstrom(podoblast, suradnica, hladanyTyp));
                }
            }
        }

        return podorysy;
    }

    public int getPocetElementov()
    {
        return getPocetElementovPodstrom(this);
    }

    private int getPocetElementovPodstrom(Quad oblast)
    {
        int pocetPodstrom = 0;
        pocetPodstrom += oblast.data.size();

        if (oblast.jeRozdelena())
        {
            for (Quad podoblast : oblast.podoblasti)
            {
                pocetPodstrom += getPocetElementovPodstrom(podoblast);
            }
        }

        return pocetPodstrom;
    }

    public void vloz(Podorys pridavany)
    {
        Quad curOblast = this;

        while (true)
        {
            if (!curOblast.jeRozdelena())
            {
                curOblast.rozdel();
            }

            boolean vPodoblasti = false;
            for (Quad podoblast : curOblast.podoblasti)
            {
                // Podorys sa moze nachadzat v maximalne 1 podoblasti
                if (podoblast.jePodorysVOblasti(pridavany))
                {
                    curOblast = podoblast;
                    vPodoblasti = true;
                    break;
                }
            }

            // Ziadna podoblast nevyhovuje
            if (!vPodoblasti)
            {
                if (curOblast.jePodorysVOblasti(pridavany))
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

    // Metoda vrati true ak sa dana suradnica nachadza v oblasti
    private boolean jeSuradnicaVOblasti(Suradnica suradnica)
    {
        return suradnica.getX() >= this.surVlavoDole.getX() && suradnica.getY() >= this.surVlavoDole.getY() &&
               suradnica.getX() <= this.surVpravoHore.getX() && suradnica.getY() <= this.surVpravoHore.getY();
    }

    // Metoda vrati true ak sa dany podorys nachadza vo vnutri oblasti
    // Cely obdlznik, ktory podorys vytvara musi byt v oblasti
    private boolean jePodorysVOblasti(Podorys vnutorny)
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
        this.podoblasti[SZ] = new Quad(SZvlavoDole, SZvpravoHore);

        Suradnica SVvlavoDole = new Suradnica(stredX, stredY);
        Suradnica SVvpravoHore = new Suradnica(this.surVpravoHore.getX(), this.surVpravoHore.getY());
        this.podoblasti[SV] = new Quad(SVvlavoDole, SVvpravoHore);

        Suradnica JVvlavoDole = new Suradnica(stredX, this.surVlavoDole.getY());
        Suradnica JVvpravoHore = new Suradnica(this.surVpravoHore.getX(), stredY);
        this.podoblasti[JV] = new Quad(JVvlavoDole, JVvpravoHore);

        Suradnica JZvlavoDole = new Suradnica(this.surVlavoDole.getX(), this.surVlavoDole.getY());
        Suradnica JZvpravoHore = new Suradnica(stredX, stredY);
        this.podoblasti[JZ] = new Quad(JZvlavoDole, JZvpravoHore);
    }

    private boolean jeRozdelena()
    {
        if (this.podoblasti[SZ] == null && this.podoblasti[SV] == null && this.podoblasti[JV] == null && this.podoblasti[JZ] == null)
        {
            return false;
        }

        return true;
    }

    @Override
    public Iterator<Quad> iterator()
    {
        return new PreOrderIterator(this);
    }

    private class PreOrderIterator implements Iterator<Quad>
    {
        private Queue<Quad> front;

        public PreOrderIterator(Quad koren)
        {
            this.front = new LinkedList<>();
            this.naplnFront(koren, front);
        }

        private void naplnFront(Quad oblast, Queue<Quad> front)
        {
            front.add(oblast);

            if (oblast.jeRozdelena())
            {
                for (Quad podoblast : oblast.podoblasti)
                {
                    this.naplnFront(podoblast, front);
                }
            }
        }

        @Override
        public boolean hasNext()
        {
            return this.front.peek() != null;
        }

        @Override
        public Quad next()
        {
            if (!this.hasNext())
            {
                throw new NoSuchElementException();
            }

            return this.front.remove();
        }
    }
}
