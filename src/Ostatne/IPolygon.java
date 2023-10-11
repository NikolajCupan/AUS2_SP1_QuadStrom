package Ostatne;

import Objekty.Polygon;
import Objekty.Suradnica;

// Polygon je definovany pomocou dvoch suradnic
// -> lava dolna
// -> prava horna
public interface IPolygon
{
    boolean leziVnutri(Suradnica suradnica);
    boolean leziVnutri(Polygon polygon);
    boolean prekryva(Polygon polygon);
    Suradnica getSurVlavoDole();
    Suradnica getSurVpravoHore();
}
