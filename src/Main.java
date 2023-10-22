import Aplikacia.Aplikacia;
import Testovanie.TesterVseobecny;
import Testovanie.TesterZakladneOperacie;

public class Main
{
    public static void main(String[] args)
    {
        //Aplikacia aplikacia = new Aplikacia();
        //aplikacia.vykonavaj();

        TesterZakladneOperacie testerZakladneOperacie = new TesterZakladneOperacie();
        testerZakladneOperacie.replikacie(1);
    }
}
