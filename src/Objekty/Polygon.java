package Objekty;

import Ostatne.IPolygon;

public class Polygon implements IPolygon
{
    protected Suradnica surVlavoDole;
    protected Suradnica surVpravoHore;

    protected void nastavSuradnice(Suradnica suradnica1, Suradnica suradnica2)
    {
        this.surVlavoDole = new Suradnica();
        this.surVpravoHore = new Suradnica();

        this.surVlavoDole.setX(Math.min(suradnica1.getX(), suradnica2.getX()));
        this.surVlavoDole.setY(Math.min(suradnica1.getY(), suradnica2.getY()));

        this.surVpravoHore.setX(Math.max(suradnica1.getX(), suradnica2.getX()));
        this.surVpravoHore.setY(Math.max(suradnica1.getY(), suradnica2.getY()));
    }

    // Metoda vrati true ak sa dana suradnica lezi vo vnutri polygonu
    @Override
    public boolean leziVnutri(Suradnica suradnica)
    {
        return suradnica.getX() >= this.surVlavoDole.getX() &&
               suradnica.getY() >= this.surVlavoDole.getY() &&
               suradnica.getX() <= this.surVpravoHore.getX() &&
               suradnica.getY() <= this.surVpravoHore.getY();
    }

    // Cely obsah polygonu musi lezat vo vnutri
    @Override
    public boolean leziVnutri(IPolygon vnutorny)
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

    @Override
    public boolean prekryva(IPolygon polygon)
    {
        if (this.surVlavoDole.getX() > polygon.getSurVpravoHore().getX() ||
            this.surVlavoDole.getY() > polygon.getSurVpravoHore().getY() ||
            polygon.getSurVlavoDole().getX() > this.surVpravoHore.getX() ||
            polygon.getSurVlavoDole().getY() > this.surVpravoHore.getY())
        {
            return false;
        }

        return true;
    }

    @Override
    public Suradnica getSurVlavoDole()
    {
        return this.surVlavoDole;
    }

    @Override
    public Suradnica getSurVpravoHore()
    {
        return this.surVpravoHore;
    }
}
