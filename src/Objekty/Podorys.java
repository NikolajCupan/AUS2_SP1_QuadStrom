package Objekty;

import Ostatne.IPodorys;

public class Podorys implements IPodorys
{
    protected String popis;
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

    protected boolean podorysySaPrekryvaju(Podorys podorys)
    {
        if (this.surVlavoDole.getX() > podorys.getSurVpravoHore().getX() ||
            this.surVlavoDole.getY() > podorys.getSurVpravoHore().getY() ||
            podorys.getSurVlavoDole().getX() > this.surVpravoHore.getX() ||
            podorys.getSurVlavoDole().getY() > this.surVpravoHore.getY())
        {
            return false;
        }

        return true;
    }

    // Metoda vrati true ak sa dana suradnica nachadza v oblasti podorysu
    @Override
    public boolean jeVnutri(Suradnica suradnica)
    {
        return suradnica.getX() >= this.surVlavoDole.getX() && suradnica.getY() >= this.surVlavoDole.getY() &&
                suradnica.getX() <= this.surVpravoHore.getX() && suradnica.getY() <= this.surVpravoHore.getY();
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
