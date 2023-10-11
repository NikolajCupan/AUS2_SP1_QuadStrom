package QuadStrom;

import Objekty.Suradnica;
import Ostatne.IPolygon;
import Ostatne.Konstanty;

import java.util.ArrayList;
import java.util.Stack;

import static Ostatne.Konstanty.SZ;

public class QuadStrom<T extends IPolygon>
{
    public Quad<T> quad;

    public QuadStrom(Suradnica surVlavoDole, Suradnica surVpavoHore)
    {
        this.quad = new Quad<T>(surVlavoDole, surVpavoHore);
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

    public ArrayList<T> vyhladaj(Suradnica suradnica)
    {
        ArrayList<T> polygony = new ArrayList<>();
        Quad<T> curQuad = this.quad;

        while (true)
        {
            for (T element : curQuad.getData())
            {
                if (element.leziVnutri(suradnica))
                {
                    polygony.add(element);
                }
            }

            // Quad nemusi byt rozdeleny
            if (curQuad.jeRozdeleny())
            {
                // Suradnica moze lezat maximalne v 1 podquade
                for (Quad<T> podQuad : curQuad.getPodQuady())
                {
                    if (podQuad.leziVnutri(suradnica))
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

        return polygony;
    }
}
