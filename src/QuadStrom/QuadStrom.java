package QuadStrom;

import Objekty.Suradnica;
import Ostatne.IPolygon;

public class QuadStrom<T extends IPolygon>
{
    public Quad<T> quad;
    public QuadStrom(Suradnica surVlavoDole, Suradnica surVpavoHore)
    {
        this.quad = new Quad<T>(surVlavoDole, surVpavoHore);
    }
}
