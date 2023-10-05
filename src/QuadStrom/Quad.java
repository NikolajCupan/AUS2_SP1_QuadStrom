package QuadStrom;

import Objekty.Suradnica;
import Ostatne.IUzol;
import Ostatne.Konstanty;

import java.util.ArrayList;

public class Quad<T extends IUzol>
{
    private Suradnica surVlavoDole;
    private Suradnica surVpravoHore;

    private ArrayList<T> data;

    private Quad<T> SZ;
    private Quad<T> SV;
    private Quad<T> JV;
    private Quad<T> JZ;

    public Quad()
    {
        this.surVlavoDole = new Suradnica(Konstanty.X_MIN, Konstanty.Y_MIN);
        this.surVpravoHore = new Suradnica(Konstanty.X_MAX, Konstanty.Y_MAX);

        this.data = new ArrayList<>();
    }

    public Quad(Suradnica surVlavoDole, Suradnica surVpravoHore)
    {
        this.surVlavoDole = surVlavoDole;
        this.surVpravoHore = surVpravoHore;

        this.data = new ArrayList<>();

        this.SZ = null;
        this.SV = null;
        this.JV = null;
        this.JZ = null;
    }

    public void vloz(T pridavany)
    {
        Quad<T> curOblast = this;

        while (true)
        {
            if (!curOblast.jeRozdelena())
            {
                curOblast.rozdel();
            }

            if (curOblast.SZ.jeVOblasti(pridavany))
            {
                curOblast = curOblast.SZ;
            }
            else if (curOblast.SV.jeVOblasti(pridavany))
            {
                curOblast = curOblast.SV;
            }
            else if (curOblast.JV.jeVOblasti(pridavany))
            {
                curOblast = curOblast.JV;
            }
            else if (curOblast.JZ.jeVOblasti(pridavany))
            {
                curOblast = curOblast.JZ;
            }
            else if (curOblast.jeVOblasti(pridavany))
            {
                curOblast.vlozDoOblasti(pridavany);
                break;
            }
            else
            {
                throw new RuntimeException("Neplatny vkladany element!");
            }
        }
    }

    private void vlozDoOblasti(T pridavany)
    {
        this.data.add(pridavany);
    }

    private boolean jeVOblasti(T vnutorny)
    {
        if (vnutorny.getSurVlavoDole().getX() >= this.surVlavoDole.getX() &&
            vnutorny.getSurVlavoDole().getY() >= this.surVlavoDole.getY() &&
            vnutorny.getSurVpravoHore().getX() <= this.surVpravoHore.getX() &&
            vnutorny.getSurVpravoHore().getY() <= this.surVpravoHore.getY())
        {
            return true;
        }

        return false;
    }

    // Metoda rozdeli dany usek na 4 rovnako velke oblasti
    // Priklad:
    // -> zaklad: {-180; -90}, {180; 90}
    //    -> SZ:  {-180;   0}, {  0; 90}
    //    -> SV:  {   0;   0}, {180; 90}
    //    -> JV:  {   0; -90}, {180;  0}
    //    -> JZ:  {-180; -90}, {  0;  0}
    private void rozdel()
    {
        if (this.jeRozdelena())
        {
            throw new RuntimeException("Oblast je uz rozdelena!");
        }

        double stredX = (this.surVlavoDole.getX() + this.surVpravoHore.getX()) / 2;
        double stredY = (this.surVlavoDole.getY() + this.surVpravoHore.getY()) / 2;

        Suradnica SZvlavoDole = new Suradnica(this.surVlavoDole.getX(), stredY);
        Suradnica SZvpravoHore = new Suradnica(stredX, this.surVpravoHore.getY());
        this.SZ = new Quad<>(SZvlavoDole, SZvpravoHore);

        Suradnica SVvlavoDole = new Suradnica(stredX, stredY);
        Suradnica SVvpravoHore = new Suradnica(this.surVpravoHore.getX(), this.surVpravoHore.getY());
        this.SV = new Quad<>(SVvlavoDole, SVvpravoHore);

        Suradnica JVvlavoDole = new Suradnica(stredX, this.surVlavoDole.getY());
        Suradnica JVvpravoHore = new Suradnica(this.surVpravoHore.getX(), stredY);
        this.JV = new Quad<>(JVvlavoDole, JVvpravoHore);

        Suradnica JZvlavoDole = new Suradnica(this.surVlavoDole.getX(), this.surVlavoDole.getY());
        Suradnica JZvpravoHore = new Suradnica(stredX, stredY);
        this.JZ = new Quad<>(JZvlavoDole, JZvpravoHore);
    }

    private boolean jeRozdelena()
    {
        if (this.SZ == null && this.SV == null && this.JV == null && this.JZ == null)
        {
            return false;
        }

        return true;
    }

    public Suradnica getSurVlavoDole()
    {
        return this.surVlavoDole;
    }

    public Suradnica getSurVpravoHore()
    {
        return this.surVpravoHore;
    }
}
