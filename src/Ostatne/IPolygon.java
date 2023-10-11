package Ostatne;

import Objekty.Suradnica;

// Polygon je definovany pomocou dvoch suradnic
// -> lava dolna
// -> prava horna
public interface IPolygon
{
    boolean leziVnutri(Suradnica suradnica);
    boolean leziVnutri(IPolygon polygon);
    boolean prekryva(IPolygon polygon);
    Suradnica getSurVlavoDole();
    Suradnica getSurVpravoHore();
}
