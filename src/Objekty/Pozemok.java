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

        this.surVlavoDole.setPoziciaDlzky(Math.min(suradnica1.getPoziciaDlzky(), suradnica2.getPoziciaDlzky()));
        this.surVlavoDole.setPoziciaSirky(Math.min(suradnica1.getPoziciaSirky(), suradnica2.getPoziciaSirky()));

        this.surVpravoHore.setPoziciaDlzky(Math.max(suradnica1.getPoziciaDlzky(), suradnica2.getPoziciaDlzky()));
        this.surVpravoHore.setPoziciaSirky(Math.max(suradnica1.getPoziciaSirky(), suradnica2.getPoziciaSirky()));
    }

    protected boolean pozemkySaPrekryvaju(Pozemok pozemok)
    {
        if (this.surVlavoDole.getPoziciaDlzky() > pozemok.getSurVpravoHore().getPoziciaDlzky() ||
            this.surVlavoDole.getPoziciaSirky() > pozemok.getSurVpravoHore().getPoziciaSirky() ||
            pozemok.getSurVlavoDole().getPoziciaDlzky() > this.surVpravoHore.getPoziciaDlzky() ||
            pozemok.getSurVlavoDole().getPoziciaSirky() > this.surVpravoHore.getPoziciaSirky())
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
