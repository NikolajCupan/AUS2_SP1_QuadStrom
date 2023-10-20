package QuadStrom;

import Objekty.Polygon;
import Objekty.Suradnica;
import Ostatne.IPolygon;
import Ostatne.Konstanty;

import java.util.ArrayList;
import java.util.Stack;

public class QuadStrom<T extends IPolygon>
{
    private static final String TYP_VKLADANIA = "plytko";

    private final int maxHlbka;
    private final Quad<T> quad;

    public QuadStrom(double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY, int maxHlbka)
    {
        this.maxHlbka = maxHlbka;
        Suradnica suradnica1 = new Suradnica(vlavoDoleX, vlavoDoleY);
        Suradnica suradnica2 = new Suradnica(vpravoHoreX, vpravoHoreY);
        this.quad = new Quad<T>(suradnica1, suradnica2, 0);
    }

    public int getPocetElementov()
    {
        int pocet = 0;
        Stack<Quad<T>> zasobnik = new Stack<>();
        zasobnik.push(this.quad);

        while (!zasobnik.empty())
        {
            Quad<T> curQuad = zasobnik.pop();
            pocet += curQuad.getData().size();

            if (curQuad.jeRozdeleny())
            {
                zasobnik.push(curQuad.getPodQuad(Konstanty.SZ));
                zasobnik.push(curQuad.getPodQuad(Konstanty.SV));
                zasobnik.push(curQuad.getPodQuad(Konstanty.JV));
                zasobnik.push(curQuad.getPodQuad(Konstanty.JZ));
            }
        }

        return pocet;
    }

    public void vloz(T pridavany)
    {
        if (TYP_VKLADANIA == "hlboko")
        {
            this.vlozHlboko(pridavany);
        }
        else
        {
            this.vlozPlytko(pridavany);
        }
    }

    public void vlozHlboko(T pridavany)
    {
        Quad<T> curQuad = this.quad;

        while (true)
        {
            if (curQuad.getHlbkaQuadu() >= this.maxHlbka)
            {
                curQuad.getData().add(pridavany);
                break;
            }

            if (!curQuad.jeRozdeleny())
            {
                curQuad.rozdel();
            }

            boolean vPodquade = false;
            for (Quad<T> podquad : curQuad.getPodQuady())
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

    public void vlozPlytko(T pridavany)
    {
        Quad<T> curQuad = this.quad;

        while (true)
        {
            if (curQuad.getHlbkaQuadu() >= this.maxHlbka)
            {
                curQuad.getData().add(pridavany);
                break;
            }

            // Dostal som sa na list, ktory je prazdny
            // Nie je nutne ist hlbsie
            if (!curQuad.jeRozdeleny() && curQuad.getData().isEmpty())
            {
                curQuad.getData().add(pridavany);
                break;
            }

            boolean podquadyPrazdne = true;
            if (curQuad.jeRozdeleny())
            {
                podquadyPrazdne = false;
            }

            // Dostal som sa na list, ktory nie je prazdny
            if (!curQuad.jeRozdeleny())
            {
                curQuad.rozdel();

                // Ak sa v liste nachadza iba 1 element, tak je mozne,
                // ze tento bude mozne vlozit hlbsie
                if (curQuad.getData().size() == 1)
                {
                    // Vytlaceny element hned vlozim
                    podquadyPrazdne = this.vlozVytlaceny(curQuad, curQuad.getData().remove(0));
                }
            }

            boolean novyVPodquade = false;
            for (Quad<T> podquad : curQuad.getPodQuady())
            {
                // Polygon sa moze nachadzat v maximalne 1 podquade
                if (podquad.leziVnutri(pridavany))
                {
                    curQuad = podquad;
                    novyVPodquade = true;
                    break;
                }
            }

            // Ziadny podquad nevyhovuje
            if (!novyVPodquade)
            {
                if (curQuad.leziVnutri(pridavany))
                {
                    // Ak vytlaceny element nebol vlozeny do podquadu, tak nie je nutne, aby tieto existovali
                    if (podquadyPrazdne)
                    {
                        curQuad.vymazPodquady();
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
        Quad<T> curQuad = this.quad;

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
        zasobnik.push(this.quad);

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
                    if (podQuad.prekryva(prehladavanaOblast))
                    {
                        zasobnik.push(podQuad);
                    }
                }
            }
        }

        return najdene;
    }

    // Vrati zmazany element
    // V pripade ak sa nenasiel, tak vrati null
    public T vymaz(double x, double y, double hladanyKluc)
    {
        Quad<T> curQuad = this.quad;
        Stack<Quad<T>> cesta = new Stack<>();

        while (true)
        {
            cesta.push(curQuad);

            for (T element : curQuad.getData())
            {
                if (element.leziVnutri(x, y) && element.getUnikatnyKluc() == hladanyKluc)
                {
                    curQuad.getData().remove(element);
                    this.vymazPrazdneQuady(cesta);
                    return element;
                }
            }

            if (!curQuad.jeRozdeleny())
            {
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
        if (dno.jeRozdeleny() || dno.getData().size() > 1 || dno.getHlbkaQuadu() == 0)
        {
            return;
        }

        while (!cesta.isEmpty())
        {
            Quad<T> vyssi = cesta.pop();

            // Ak je ktorykolvek podquad rozdeleny, tak nie je mozne mazat
            for (Quad<T> podQuad : vyssi.getPodQuady())
            {
                if (podQuad.jeRozdeleny())
                {
                    return;
                }
            }

            // Pocet elementov, ktore sa nachadzaju v podquadoch
            int pocetElementovDolne = 0;
            for (Quad<T> podQuad : vyssi.getPodQuady())
            {
                pocetElementovDolne += podQuad.getData().size();
            }

            // Ak je tento vacsi ako 1, tak nie je mozne mazat
            if (pocetElementovDolne > 1)
            {
                return;
            }

            // Rovnako nie je mozne mazat ak vyssi quad obsahuje data a zaroven existuje element aj v podquadoch
            if (!vyssi.getData().isEmpty() && pocetElementovDolne == 1)
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

            vyssi.vymazPodquady();
            if (vytlacenyElement != null)
            {
                vyssi.getData().add(vytlacenyElement);
            }
        }
    }

    public Quad<T> getRootQuad()
    {
        return this.quad;
    }

    public int getMaxHlbka()
    {
        return this.maxHlbka;
    }
}
