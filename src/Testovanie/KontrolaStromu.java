package Testovanie;

import Ostatne.IPolygon;
import QuadStrom.QuadStrom;
import QuadStrom.Quad;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class KontrolaStromu
{
    // Kontroluje, ci by elementy v strome nemohli lezat hlbsie
    public static <T extends IPolygon> void prilisPlytko(QuadStrom<T> strom)
    {
        for (Quad<T> quad : strom)
        {
            // Element nemoze lezal hlbsie, nakolko je obmedzeny maximalnou hlbkou
            if (quad.getUrovenQuadu() >= strom.getMaxUroven())
            {
                continue;
            }

            // Jediny element v liste, v tomto pripade nie je nutne davat ho hlbsie
            if (!quad.jeRozdeleny() && quad.getData().size() <= 1)
            {
                continue;
            }

            boolean rozdelenie = false;
            if (!quad.jeRozdeleny())
            {
                rozdelenie = true;
                quad.rozdel();
            }

            // Skusim zistit, ci mozem element vlozit hlbsie
            for (T element : quad.getData())
            {
                for (Quad<T> podQuad : quad.getPodQuady())
                {
                    if (podQuad.leziVnutri(element))
                    {
                        throw new RuntimeException("Polygon by mal lezal hlbsie!");
                    }
                }
            }

            if (rozdelenie)
            {
                quad.vymazPodQuady();
            }
        }
    }

    // Kontroluje, ci by elementy v strome nemali lezat plytsie
    public static <T extends IPolygon> void prilisHlboko(QuadStrom<T> strom)
    {
        for (Quad<T> quad : strom)
        {
            if (!quad.jeRozdeleny())
            {
                continue;
            }

            boolean rozdelenyPodQuad = false;
            for (Quad<T> podQuad : quad.getPodQuady())
            {
                if (podQuad.jeRozdeleny())
                {
                    rozdelenyPodQuad = true;
                    break;
                }
            }

            if (rozdelenyPodQuad)
            {
                continue;
            }

            int pocetQuad = quad.getData().size();

            int pocetPodQuady = 0;
            for (Quad<T> podQuad : quad.getPodQuady())
            {
                pocetPodQuady += podQuad.getData().size();
            }

            if (pocetQuad == 0 && pocetPodQuady <= 1)
            {
                throw new RuntimeException("Existuje element, ktory by mal lezat plytsie!");
            }
        }
    }

    public static <T extends IPolygon> void prazdnePodStromy(QuadStrom<T> strom)
    {
        for (Quad<T> quad : strom)
        {
            if (quad.jeRozdeleny())
            {
                boolean prazdnyPodstrom = true;
                for (Quad<T> podQuad : quad.getPodQuady())
                {
                    // Staci, aby vyhovoval aspon jeden podQuad
                    // Problem nastava ak vsetky podquady su prazdne a nie su rozdelene
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

    // Kontrola, ci elementy v quade nie su prilis velke,
    // spolu s vypisom struktury do suboru
    public static <T extends IPolygon> void mimoQuad(QuadStrom<T> strom, String nazovSuboru)
    {
        try
        {
            PrintWriter printWriter = new PrintWriter(nazovSuboru, StandardCharsets.UTF_8);

            int spracovaneElementy = 0;
            int najhlbsiaUroven = -1;

            for (Quad<T> quad : strom)
            {
                int urovenQuadu = quad.getUrovenQuadu();
                if (urovenQuadu > najhlbsiaUroven)
                {
                    najhlbsiaUroven = urovenQuadu;
                }

                String urovenString = String.format("%3d", urovenQuadu);
                KontrolaStromu.vypis(printWriter, urovenQuadu, urovenString + ". Quad {" + quad.getVlavoDoleX() + ", " + quad.getVlavoDoleY() + "}," +
                        " {" + quad.getVpravoHoreX() + ", " + quad.getVpravoHoreY() + "}");

                if (quad.getData().isEmpty())
                {
                    KontrolaStromu.vypis(printWriter, urovenQuadu, "     Quad neobsahuje ziadne data");
                }

                for (T element : quad.getData())
                {
                    spracovaneElementy++;

                    if (quad.leziVnutri(element))
                    {
                        KontrolaStromu.vypis(printWriter, urovenQuadu, "     {" + element.getVlavoDoleX() + ", " + element.getVlavoDoleY() + "}," +
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

            printWriter.println("Najhlbsia uroven v strome: " + najhlbsiaUroven);

            printWriter.close();
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    private static void vypis(PrintWriter printWriter, int hlbka, String text)
    {
        for (int i = 0; i < hlbka * 6; i++)
        {
            printWriter.print(' ');
        }

        printWriter.println(text);
    }
}
