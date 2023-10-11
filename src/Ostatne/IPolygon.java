package Ostatne;

import Objekty.Suradnica;

// Polygon je definovany pomocou dvoch suradnic
// -> lava dolna
// -> prava horna
public interface IPolygon
{
    boolean leziVnutri(double x, double y);
    boolean leziVnutri(IPolygon polygon);
    boolean prekryva(IPolygon polygon);
    double getVlavoDoleX();
    double getVlavoDoleY();
    double getVpravoHoreX();
    double getVpravoHoreY();
}
