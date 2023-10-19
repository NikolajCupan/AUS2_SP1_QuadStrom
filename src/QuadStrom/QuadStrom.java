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
                    this.vymazPrazdnePodquady(cesta);
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

    // Po mazani sa zmazu vsetky quady, ktore uz nemusia existovat
    private void vymazPrazdnePodquady(Stack<Quad<T>> cesta)
    {
        Quad<T> spodny = cesta.pop();
        // Existuju urcite situacie, kedy urcite nepojde rusit podquady
        if (spodny.jeRozdeleny() || spodny.getData().size() > 1 || spodny.getHlbkaQuadu() == 0)
        {
            return;
        }

        T vytlaceny = null;
        while (true)
        {
            Quad<T> vyssi = cesta.pop();

            int pocetPodstrom = 0;
            // Vytlaceny element musim pocitat tiez
            if (vytlaceny != null)
            {
                pocetPodstrom++;
            }

            for (Quad<T> podQuad : vyssi.getPodQuady())
            {
                if (podQuad.jeRozdeleny())
                {
                    // Niektory z podquadov je rozdeleny, urcite nebude mozne rusit dalsie podquady
                    if (vytlaceny != null)
                    {
                        this.vloz(vytlaceny);
                    }

                    return;
                }

                pocetPodstrom += podQuad.getData().size();
            }

            if (pocetPodstrom > 1)
            {
                if (vytlaceny != null)
                {
                    this.vloz(vytlaceny);
                }

                break;
            }

            // Zistim, ci mozem vytlacit element
            boolean moznoVytlacit = true;
            if (!vyssi.getData().isEmpty())
            {
                moznoVytlacit = false;
            }

            if (moznoVytlacit)
            {
                // Ak som sa dostal az sem tak plati, ze podquady maju dokopy bud 1 alebo ziadny element
                // Tento element, ak existuje, vytlacim
                for (Quad<T> podQuad : vyssi.getPodQuady())
                {
                    if (podQuad.getData().size() == 1)
                    {
                        vytlaceny = podQuad.getData().remove(0);
                        break;
                    }
                }
            }

            if (pocetPodstrom == 0 && vytlaceny == null)
            {
                vyssi.vymazPodquady();
            }
            else if (pocetPodstrom == 1 && vytlaceny != null)
            {
                vyssi.vymazPodquady();
            }

            if (vyssi.getHlbkaQuadu() == 0)
            {
                if (vytlaceny != null)
                {
                    this.vloz(vytlaceny);
                }

                return;
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
