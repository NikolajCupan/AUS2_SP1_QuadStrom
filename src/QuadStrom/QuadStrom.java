package QuadStrom;

import Objekty.Polygon;
import Objekty.Suradnica;
import Ostatne.IPolygon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class QuadStrom<T extends IPolygon> implements Iterable<Quad<T>>
{
    // V pripade ak quady na urovni najhlbsiaUroven obsahuju menej ako PRILIS_PRAZDNE
    // percent dat, tak tato uroven je zrusena; naopak v pripade ak quady na urovni
    // najhlbsiaUroven obsahuju viac ako PRILIS_PLNE percent dat, tak je pridana dalsia uroven
    private static final int PRILIS_PLNE = 20;
    private static final int PRILIS_PRAZDNE = 1;

    private int maxUroven;
    private final Quad<T> rootQuad;

    public QuadStrom(double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY, int maxUroven)
    {
        this.maxUroven = maxUroven;
        Suradnica suradnica1 = new Suradnica(vlavoDoleX, vlavoDoleY);
        Suradnica suradnica2 = new Suradnica(vpravoHoreX, vpravoHoreY);
        this.rootQuad = new Quad<T>(suradnica1, suradnica2, 0);
    }

    public int getPocetElementov()
    {
        int pocet = 0;

        for (Quad<T> quad : this)
        {
           pocet += quad.getData().size();
        }

        return pocet;
    }

    public void vloz(T pridavany)
    {
        Quad<T> curQuad = this.rootQuad;

        while (true)
        {
            // Dosiahnuta maximalna uroveb => nejde sa hlbsie
            if (curQuad.getUrovenQuadu() >= this.maxUroven)
            {
                curQuad.getData().add(pridavany);
                break;
            }

            if (!curQuad.jeRozdeleny() && curQuad.getData().isEmpty())
            {
                // Dostal som sa na list, ktory je prazdny
                // Nie je nutne ist hlbsie
                curQuad.getData().add(pridavany);
                break;
            }

            // Ak je quad uz teraz rozdeleny, tak nebudem moct zrusit jeho podQuady
            boolean podQuadyPrazdne = !curQuad.jeRozdeleny();

            if (!curQuad.jeRozdeleny())
            {
                // Dostal som sa na list, ktory nie je prazdny
                curQuad.rozdel();

                // Ak sa v liste nachadza iba 1 element, tak je mozne,
                // ze tento bude mozne vlozit hlbsie
                if (curQuad.getData().size() == 1)
                {
                    // Vytlaceny element hned vlozim
                    podQuadyPrazdne = this.vlozVytlaceny(curQuad, curQuad.getData().remove(0));
                }
            }

            // Zistim, kde bude vlozeny novy element (parameter metody)
            boolean novyVPodQuade = false;
            for (Quad<T> podQuad : curQuad.getPodQuady())
            {
                // Polygon sa moze nachadzat maximalne v 1 podQuade
                if (podQuad.leziVnutri(pridavany))
                {
                    curQuad = podQuad;
                    novyVPodQuade = true;
                    break;
                }
            }

            if (!novyVPodQuade)
            {
                // Ziadny podquad nevyhovuje
                if (curQuad.leziVnutri(pridavany))
                {
                    // Ak vytlaceny element nebol vlozeny do podQuadu, tak nie je nutne, aby tieto existovali
                    if (podQuadyPrazdne)
                    {
                        curQuad.vymazPodQuady();
                    }

                    curQuad.getData().add(pridavany);
                    break;
                }
                else
                {
                    throw new RuntimeException("Neplatny vkladany element!");
                }
            }
        }
    }

    // False -> element bol vlozeny do podquadu
    // True  -> element bol vlozeny do quadu
    private boolean vlozVytlaceny(Quad<T> quad, T vytlaceny)
    {
        // Quad bol rozdeleny pred zavolanim tejto metody
        for (Quad<T> podQuad : quad.getPodQuady())
        {
            if (podQuad.leziVnutri(vytlaceny))
            {
                podQuad.getData().add(vytlaceny);
                return false;
            }
        }

        // Vytlaceny element sa nezmesti do ziadneho podquadu
        quad.getData().add(vytlaceny);
        return true;
    }

    // Vyhladavanie podla suradnice
    public ArrayList<T> vyhladaj(double x, double y)
    {
        ArrayList<T> najdene = new ArrayList<>();
        Quad<T> curQuad = this.rootQuad;

        while (true)
        {
            for (T element : curQuad.getData())
            {
                if (element.leziVnutri(x, y))
                {
                    najdene.add(element);
                }
            }

            // Quad nemusi byt rozdeleny
            if (curQuad.jeRozdeleny())
            {
                // Suradnica moze lezat maximalne v 1 podquade
                for (Quad<T> podQuad : curQuad.getPodQuady())
                {
                    if (podQuad.leziVnutri(x, y))
                    {
                        curQuad = podQuad;
                        break;
                    }
                }
            }
            else
            {
                break;
            }
        }

        return najdene;
    }

    // Vyhladavenie podla polygonu
    public ArrayList<T> vyhladaj(double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY)
    {
        Polygon prehladavanaOblast = new Polygon();
        prehladavanaOblast.nastavSuradnice(new Suradnica(vlavoDoleX, vlavoDoleY),
                                           new Suradnica(vpravoHoreX, vpravoHoreY));

        ArrayList<T> najdene = new ArrayList<>();
        Stack<Quad<T>> zasobnik = new Stack<>();
        zasobnik.push(this.rootQuad);

        while (!zasobnik.isEmpty())
        {
            Quad<T> curQuad = zasobnik.pop();

            for (T element : curQuad.getData())
            {
                if (element.prekryva(prehladavanaOblast))
                {
                    najdene.add(element);
                }
            }

            // Quad nemusi byt rozdeleny
            if (curQuad.jeRozdeleny())
            {
                for (Quad<T> podQuad : curQuad.getPodQuady())
                {
                    // Do zasobnika vlozim vsetky quady, ktore sa prekryvaju s prehladavanou oblastou
                    if (podQuad.prekryva(prehladavanaOblast))
                    {
                        zasobnik.push(podQuad);
                    }
                }
            }
        }

        return najdene;
    }

    // Metoda vrati zmazany element
    // V pripade ak ziadny zmazany nebol, tak vrati null
    public T vymaz(double x, double y, double hladanyKluc)
    {
        Quad<T> curQuad = this.rootQuad;
        Stack<Quad<T>> cesta = new Stack<>();

        while (true)
        {
            cesta.push(curQuad);

            for (T element : curQuad.getData())
            {
                if (element.leziVnutri(x, y) && element.getKluc() == hladanyKluc)
                {
                    curQuad.getData().remove(element);
                    this.vymazPrazdneQuady(cesta);
                    return element;
                }
            }

            if (!curQuad.jeRozdeleny())
            {
                // Nie je mozne dalej hladat, dany element neexistuje
                return null;
            }

            for (Quad<T> podQuad : curQuad.getPodQuady())
            {
                if (podQuad.leziVnutri(x, y))
                {
                    curQuad = podQuad;
                    break;
                }
            }
        }
    }

    // Po zmazani elementu sa zmazu vsetky quady, ktore uz nemusia existovat
    private void vymazPrazdneQuady(Stack<Quad<T>> cesta)
    {
        Quad<T> dno = cesta.pop();
        if (dno.jeRozdeleny() || dno.getData().size() > 1 || dno.getUrovenQuadu() == 0)
        {
            return;
        }

        while (!cesta.isEmpty())
        {
            Quad<T> vyssi = cesta.pop();

            // Ak je ktorykolvek podQuad rozdeleny, tak nie je mozne mazat
            for (Quad<T> podQuad : vyssi.getPodQuady())
            {
                if (podQuad.jeRozdeleny())
                {
                    return;
                }
            }

            // Pocet elementov, ktore sa nachadzaju v podquadoch
            int pocetElementovPodQuady = 0;
            for (Quad<T> podQuad : vyssi.getPodQuady())
            {
                pocetElementovPodQuady += podQuad.getData().size();
            }

            // Ak je tento pocet vacsi ako 1, tak nie je mozne mazat
            if (pocetElementovPodQuady > 1)
            {
                return;
            }

            // Rovnako nie je mozne mazat ak vyssi quad obsahuje data a zaroven existuje element aj v podquadoch
            if (!vyssi.getData().isEmpty() && pocetElementovPodQuady == 1)
            {
                return;
            }

            // Ak som sa dostal az sem, tak mozem vykonat mazanie
            // V podquadoch sa moze nachadzat prave 1 alebo 0 elementov
            T vytlacenyElement = null;
            for (Quad<T> podQuad : vyssi.getPodQuady())
            {
                if (!podQuad.getData().isEmpty())
                {
                    vytlacenyElement = podQuad.getData().remove(0);
                    break;
                }
            }

            vyssi.vymazPodQuady();
            if (vytlacenyElement != null)
            {
                vyssi.getData().add(vytlacenyElement);
            }
        }
    }

    // Metoda presunie data z quadov, ktore maju uroven hlbsiu
    // ako uroven dana parametrom, do quadov o hlbke parametra
    public void presunPlytsie(int uroven)
    {
        Stack<Quad<T>> zasobnik = new Stack<>();
        zasobnik.push(this.getRootQuad());

        while (!zasobnik.isEmpty())
        {
            Quad<T> curQuad = zasobnik.pop();

            if (!curQuad.jeRozdeleny())
            {
                // Nie je potrebne nic riesit nakolko quad nie je rozdeleny
                continue;
            }

            if (curQuad.getUrovenQuadu() < uroven)
            {
                // V tomto pripade nie je nutne nic robit,
                // iba si vlozim podquady do zasobnika
                for (Quad<T> podQuad : curQuad.getPodQuady())
                {
                    zasobnik.push(podQuad);
                }

                continue;
            }

            // Nasiel som quad, do ktoreho budem presuvat data z nizsich quadov
            Stack<Quad<T>> podQuadyZasobnik = new Stack<>();
            podQuadyZasobnik.push(curQuad);
            ArrayList<Quad<T>> podQuadyZoznam = new ArrayList<>();

            while (!podQuadyZasobnik.isEmpty())
            {
                Quad<T> curPodQuad = podQuadyZasobnik.pop();

                if (curPodQuad.jeRozdeleny())
                {
                    for (Quad<T> podQuad : curPodQuad.getPodQuady())
                    {
                        podQuadyZasobnik.push(podQuad);
                        podQuadyZoznam.add(podQuad);
                    }
                }
            }

            // Presuniem data z podquadov
            for (Quad<T> podQuad : podQuadyZoznam)
            {
                curQuad.getData().addAll(podQuad.getData());
            }

            // V tomto pripade mozem podquady zrusit hoci obsahuju data,
            // nakolko tieto som si uz presunul vyssie
            curQuad.forceVymazPodQuady();
        }
    }

    // Realna nahlbsia uroven v strome
    public int getNajhlbsiaUroven()
    {
        int najhlbsiaUroven = 0;

        for (Quad<T> quad : this)
        {
            if (najhlbsiaUroven < quad.getUrovenQuadu())
            {
                najhlbsiaUroven = quad.getUrovenQuadu();
            }
        }

        return najhlbsiaUroven;
    }

    public Quad<T> getRootQuad()
    {
        return this.rootQuad;
    }

    public int getMaxUroven()
    {
        return this.maxUroven;
    }

    public Iterator<Quad<T>> iterator()
    {
        return new QSIterator();
    }

    // Pre-order iterator
    private class QSIterator implements Iterator<Quad<T>>
    {
        private final Stack<Quad<T>> quady;

        public QSIterator()
        {
            this.quady = new Stack<>();
            this.quady.push(QuadStrom.this.rootQuad);
        }

        @Override
        public boolean hasNext()
        {
            return !this.quady.isEmpty();
        }

        @Override
        public Quad<T> next()
        {
            if (!this.hasNext())
            {
                throw new NoSuchElementException();
            }

            Quad<T> curQuad = this.quady.pop();

            if (curQuad.jeRozdeleny())
            {
                for (Quad<T> podQuad : curQuad.getPodQuady())
                {
                    this.quady.push(podQuad);
                }
            }

            return curQuad;
        }
    }
}
