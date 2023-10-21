package QuadStrom;

import Objekty.Polygon;
import Objekty.Suradnica;
import Ostatne.IPolygon;

import java.util.ArrayList;
import java.util.Stack;

public class QuadStrom<T extends IPolygon>
{
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
                for (Quad<T> podQuad : curQuad.getPodQuady())
                {
                    zasobnik.push(podQuad);
                }
            }
        }

        return pocet;
    }

    public void vloz(T pridavany)
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

    // Metoda presunie data z quadov, ktore maju hlbku
    // vyssiu ako parameter do quadov o hlbke parametra
    public void presunData(int hlbka)
    {
        Stack<Quad<T>> zasobnik = new Stack<>();
        zasobnik.push(this.getRootQuad());

        while (!zasobnik.isEmpty())
        {
            Quad<T> curQuad = zasobnik.pop();

            // Nie je potrebne nic riesit nakolko nie je rozdeleny
            if (!curQuad.jeRozdeleny())
            {
                continue;
            }

            if (curQuad.getHlbkaQuadu() < hlbka)
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
            curQuad.forceVymazPodquady();
        }
    }

    // Realna maximalna hlbka v strome
    public int getCurHlbka()
    {
        int maxHlbka = 0;
        Stack<Quad<T>> zasobnik = new Stack<>();
        zasobnik.push(this.getRootQuad());

        while (!zasobnik.isEmpty())
        {
            Quad<T> curQuad = zasobnik.pop();

            if (curQuad.jeRozdeleny())
            {
                for (Quad<T> podQuad : curQuad.getPodQuady())
                {
                    zasobnik.push(podQuad);
                }
            }

            if (maxHlbka < curQuad.getHlbkaQuadu())
            {
                maxHlbka = curQuad.getHlbkaQuadu();
            }
        }

        return maxHlbka;
    }

    // Zdravie zavisi od toho, ako su elementy v strome rozlozene
    // Zdravie je z intervalu <0; 100>, pricom 0 = najlepsie, 100 najhorsie
    // Pre kazdu uroven:
    // ZDRAVIE_UROVEN_I = (POCET_UROVEN_I / POCET_SPOLU * 100) / (1 / 4^I)
    // ZDREVIE_CELKOM = SUMA(ZDRAVIE_UROVEN_I PRE VSETKY UROVNE)
    // Kazda uroven ma nizsiu vahu nakolko sa tam nachadcha viac quadov => viac miesta pre data
    public double zdravie()
    {
        int curMaxHlbka = this.getCurHlbka();
        // Pocet elementov, ktore sa nachadzaju na danej urovni
        int[] pocetHlbka = new int[curMaxHlbka + 1];

        Stack<Quad<T>> zasobnik = new Stack<>();
        zasobnik.push(this.getRootQuad());

        while (!zasobnik.isEmpty())
        {
            Quad<T> curQuad = zasobnik.pop();

            if (curQuad.jeRozdeleny())
            {
                for (Quad<T> podQuad : curQuad.getPodQuady())
                {
                    zasobnik.push(podQuad);
                }
            }

            int hlbkaQuadu = curQuad.getHlbkaQuadu();
            pocetHlbka[hlbkaQuadu] += curQuad.getData().size();
        }

        int pocetElementov = this.getPocetElementov();
        System.out.println("Elementov: " + pocetElementov);

        double celkoveZdravie = 0;
        for (int i = 0; i < curMaxHlbka + 1; i++)
        {
            // Kolko percent z celkovych elementov sa nachadza na danej urovni1
            double percentoElementov = (double)pocetHlbka[i] / pocetElementov * 100;

            // Cim je uroven nizsia, tym jej vaha na celkovom zdravi mensia,
            // nizsia uroven => vacsi pocet quadov na tejto urovni
            double vaha = 1 / Math.pow(4, i);
            System.out.println("U: " + i + ", pocet: " + pocetHlbka[i] + ", percento: " + percentoElementov + ", vaha: " + vaha + ", zdravie: " + percentoElementov * vaha);

            celkoveZdravie += percentoElementov * vaha;
        }

        return celkoveZdravie;
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
