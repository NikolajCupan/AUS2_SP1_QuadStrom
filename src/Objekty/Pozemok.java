package Objekty;

public class Pozemok
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

    protected boolean pozemkySaPrekryvaju(Pozemok pozemok)
    {
        if (this.surVlavoDole.getX() > pozemok.getSurVpravoHore().getX() ||
            this.surVlavoDole.getY() > pozemok.getSurVpravoHore().getY() ||
            pozemok.getSurVlavoDole().getX() > this.surVpravoHore.getX() ||
            pozemok.getSurVlavoDole().getY() > this.surVpravoHore.getY())
        {
            return false;
        }

        return true;
    }

    protected Suradnica getSurVlavoDole()
    {
        return this.surVlavoDole;
    }

    protected Suradnica getSurVpravoHore()
    {
        return this.surVpravoHore;
    }
}
