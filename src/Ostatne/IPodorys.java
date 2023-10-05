package Ostatne;

import Objekty.Suradnica;

public interface IPodorys
{
    Suradnica getSurVlavoDole();
    Suradnica getSurVpravoHore();
    boolean jeVnutri(Suradnica suradnica);
}
