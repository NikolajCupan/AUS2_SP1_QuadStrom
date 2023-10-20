package Testovanie;

import Ostatne.IPolygon;
import QuadStrom.QuadStrom;
import QuadStrom.Quad;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Stack;

public class KontrolaStromu
{
    public static <T extends IPolygon> void prilisPlytko(QuadStrom<T> strom)
    {
        Stack<Quad<T>> zasobnik = new Stack<>();
        zasobnik.push(strom.getRootQuad());

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

            if (curQuad.getHlbkaQuadu() >= strom.getMaxHlbka())
            {
                continue;
            }

            if (!curQuad.jeRozdeleny() && curQuad.getData().size() <= 1)
            {
                continue;
            }

            boolean rozdelenie = false;
            if (!curQuad.jeRozdeleny())
            {
                rozdelenie = true;
                curQuad.rozdel();
            }

            for (T element : curQuad.getData())
            {
                for (Quad<T> podQuad : curQuad.getPodQuady())
                {
                    if (podQuad.leziVnutri(element))
                    {
                        throw new RuntimeException("Polygon by mal lezal hlbsie!");
                    }
                }

            }

            if (rozdelenie)
            {
                curQuad.vymazPodquady();
            }
        }
    }

    public static <T extends IPolygon> void prazdnePodstromy(QuadStrom<T> strom)
    {
        Stack<Quad<T>> zasobnik = new Stack<>();
        zasobnik.push(strom.getRootQuad());

        while (!zasobnik.isEmpty())
        {
            Quad<T> curQuad = zasobnik.pop();

            if (curQuad.jeRozdeleny())
            {
                for (Quad<T> podQuad : curQuad.getPodQuady())
                {
                    zasobnik.push(podQuad);
                }

                boolean prazdnyPodstrom = true;
                for (Quad<T> podQuad : curQuad.getPodQuady())
                {
                    if (!podQuad.getData().isEmpty() || podQuad.jeRozdeleny())
                    {
                        prazdnyPodstrom = false;
                        break;
                    }
                }

                if (prazdnyPodstrom)
                {
                    throw new RuntimeException("Existuje prazdny podstrom!");
                }
            }
        }
    }

    public static <T extends IPolygon> void kontrolaStromu(QuadStrom<T> strom, String nazovSuboru)
    {
        try
        {
            PrintWriter printWriter = new PrintWriter(nazovSuboru, StandardCharsets.UTF_8);

            Stack<Quad<T>> zasobnik = new Stack<>();
            zasobnik.push(strom.getRootQuad());

            int spracovaneElementy = 0;
            int maxHlbka = -1;

            while (!zasobnik.isEmpty())
            {
                Quad<T> curQuad = zasobnik.pop();

                int curHlbka = curQuad.getHlbkaQuadu();
                if (curHlbka > maxHlbka)
                {
                    maxHlbka = curHlbka;
                }

                if (curQuad.jeRozdeleny())
                {
                    for (Quad<T> podquad : curQuad.getPodQuady())
                    {
                        zasobnik.push(podquad);
                    }
                }

                String hlbkaVypis = String.format("%3d", curHlbka);
                KontrolaStromu.vypis(printWriter, true, curHlbka, hlbkaVypis + ". Quad {" + curQuad.getVlavoDoleX() + ", " + curQuad.getVlavoDoleY() + "}," +
                        " {" + curQuad.getVpravoHoreX() + ", " + curQuad.getVpravoHoreY() + "}");
                if (curQuad.getData().isEmpty())
                {
                    KontrolaStromu.vypis(printWriter, true, curHlbka, "     Quad neobsahuje ziadne data");
                }

                for (T element : curQuad.getData())
                {
                    spracovaneElementy++;

                    if (curQuad.leziVnutri(element))
                    {
                        KontrolaStromu.vypis(printWriter, true, curHlbka, "     {" + element.getVlavoDoleX() + ", " + element.getVlavoDoleY() + "}," +
                                " {" + element.getVpravoHoreX() + ", " + element.getVpravoHoreY() + "}");
                    }
                    else
                    {
                        throw new RuntimeException("Element v quade je prilis velky!");
                    }
                }
            }

            printWriter.println("\n\nStatistika:");

            int pocetElementov = strom.getPocetElementov();
            if (pocetElementov == spracovaneElementy)
            {
                printWriter.println("Pocet elementov sa zhoduje s realitou: " + pocetElementov + " = " + spracovaneElementy);
            }
            else
            {
                throw new RuntimeException("Pocet elementov sa nezhoduje s realitou!");
            }

            printWriter.println("Maximalna hlbka v strome: " + maxHlbka);

            printWriter.close();
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public static void vypis(PrintWriter printWriter, boolean vypis, int hlbka, String text)
    {
        if (!vypis)
        {
            return;
        }

        for (int i = 0; i < hlbka * 6; i++)
        {
            printWriter.print(' ');
        }

        printWriter.println(text);
    }
}
