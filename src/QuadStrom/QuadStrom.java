package QuadStrom;

import Objekty.Polygon;
import Objekty.Suradnica;
import Ostatne.IPolygon;
import Ostatne.Konstanty;

import java.util.ArrayList;
import java.util.Stack;

public class QuadStrom<T extends IPolygon>
{
    private static final int MAX_HLBKA = 10;
    private Quad<T> quad;

    public QuadStrom(double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY)
    {
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
        this.vlozPlytko(pridavany);
        //this.vlozOld(pridavany);
    }

    public void vlozOld(T pridavany)
    {
        Quad<T> curQuad = this.quad;

        while (true)
        {
            if (curQuad.getHlbkaQuady() >= MAX_HLBKA)
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

    private void vlozPlytko(T pridavany)
    {
        Quad<T> curQuad = this.quad;

        while (true)
        {
            if (curQuad.getHlbkaQuady() >= MAX_HLBKA)
            {
                curQuad.getData().add(pridavany);
                break;
            }

            if (!curQuad.jeRozdeleny() && curQuad.getData().isEmpty())
            {
                // Nie je nutne ist nizsie
                curQuad.getData().add(pridavany);
                break;
            }

            boolean podquadyPrazdne = false;
            if (!curQuad.jeRozdeleny())
            {
                curQuad.rozdel();

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
                    // Ak nebol vlozeny do podquadu, tak nie je nutne, aby tieto existovali
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
    public <U extends IPolygon> ArrayList<U> vyhladaj(double x, double y, Class<U> typ)
    {
        ArrayList<T> najdene = new ArrayList<>();
        Quad<T> curQuad = this.quad;

        while (true)
        {
            for (T element : curQuad.getData())
            {
                if (typ.isInstance(element) && element.leziVnutri(x, y))
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

        return (ArrayList<U>)najdene;
    }

    // Vyhladavenie podla polygonu
    public <U extends IPolygon> ArrayList<U> vyhladaj(double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY, Class<U> typ)
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
                if (typ.isInstance(element) && element.prekryva(prehladavanaOblast))
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

        return (ArrayList<U>)najdene;
    }

    public <U extends IPolygon> U vymaz(double x, double y, double hladanyKluc, Class<U> typ)
    {
        Quad<T> curQuad = this.quad;

        while (true)
        {
            ArrayList<T> quadData = curQuad.getData();

            for (T element : quadData)
            {
                if (typ.isInstance(element) && element.leziVnutri(x, y) && element.getUnikatnyKluc() == hladanyKluc)
                {
                    quadData.remove(element);
                    return (U)element;
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

    public Quad<T> getQuad()
    {
        return this.quad;
    }
}
