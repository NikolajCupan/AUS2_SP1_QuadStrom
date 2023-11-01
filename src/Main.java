import GUI.*;
import Testovanie.Tester;

public class Main
{
    // G => GUI
    // T => Testovanie
    public static final char REZIM = 'G';

    public static void main(String[] args)
    {
        if (REZIM == 'G')
        {
            new GUI();
        }
        else if (REZIM == 'T')
        {
            Tester tester = new Tester();
            tester.replikacie(100);
        }
    }
}
