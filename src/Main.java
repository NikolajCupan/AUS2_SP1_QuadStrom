import Aplikacia.Aplikacia;
import Testovanie.Tester;

public class Main
{
    public static void main(String[] args)
    {
        //Aplikacia aplikacia = new Aplikacia();
        //aplikacia.vykonavaj();

        Tester tester = new Tester(200);
        tester.testuj(100000);
    }
}
