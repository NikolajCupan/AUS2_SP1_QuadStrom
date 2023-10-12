package QuadStrom;

import Objekty.Polygon;
import Objekty.Suradnica;
import Ostatne.IPolygon;
import Ostatne.Konstanty;

import java.util.ArrayList;
import java.util.Stack;

public class QuadStrom<T extends IPolygon>
{
    private Quad<T> quad;

    public QuadStrom(double vlavoDoleX, double vlavoDoleY, double vpravoHoreX, double vpravoHoreY)
    {
        Suradnica suradnica1 = new Suradnica(vlavoDoleX, vlavoDoleY);
        Suradnica suradnica2 = new Suradnica(vpravoHoreX, vpravoHoreY);
        this.quad = new Quad<T>(suradnica1, suradnica2);
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
        Quad<T> curQuad = this.quad;

        while (true)
        {
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
}
