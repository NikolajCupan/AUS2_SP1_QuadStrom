package Aplikacia;

import Objekty.Nehnutelnost;
import Objekty.Parcela;
import Objekty.Polygon;
import Objekty.Suradnica;
import Ostatne.Konstanty;
import QuadStrom.QuadStrom;

public class Aplikacia
{
    private final QuadStrom<Polygon> strom;

    public Aplikacia()
    {
        this.strom = new QuadStrom<Polygon>(Konstanty.X_MIN, Konstanty.Y_MIN, Konstanty.X_MAX, Konstanty.Y_MAX);
    }

    public void vykonavaj()
    {
        Operacie.pridajNehnutelnost(this.strom, new Nehnutelnost(61, "Parcela", new Suradnica(-179, 49), new Suradnica(145, -87)));
        Operacie.pridajParcelu(this.strom, new Parcela(28, "Nehnutelnost", new Suradnica(2, 22), new Suradnica(-134, -75)));
        Operacie.pridajParcelu(this.strom, new Parcela(3, "Parcela", new Suradnica(127, -3), new Suradnica(-170, -11)));
        Operacie.pridajNehnutelnost(this.strom, new Nehnutelnost(95, "Parcela", new Suradnica(-66, 38), new Suradnica(-121, -48)));
        Operacie.pridajParcelu(this.strom, new Parcela(72, "Nehnutelnost", new Suradnica(-149, -61), new Suradnica(93, 33)));
        Operacie.pridajParcelu(this.strom, new Parcela(39, "Nehnutelnost", new Suradnica(20, -5), new Suradnica(102, 4)));
        Operacie.pridajNehnutelnost(this.strom, new Nehnutelnost(58, "Nehnutelnost", new Suradnica(133, 59), new Suradnica(73, -39)));
        Operacie.pridajParcelu(this.strom, new Parcela(62, "Parcela", new Suradnica(-104, -56), new Suradnica(-128, -80)));
        Operacie.pridajParcelu(this.strom, new Parcela(46, "Parcela", new Suradnica(146, -18), new Suradnica(-97, -1)));
        Operacie.pridajNehnutelnost(this.strom, new Nehnutelnost(84, "Nehnutelnost", new Suradnica(-128, -69), new Suradnica(107, 35)));
        Operacie.pridajParcelu(this.strom, new Parcela(53, "Parcela", new Suradnica(162, 76), new Suradnica(-80, 68)));
        Operacie.pridajNehnutelnost(this.strom, new Nehnutelnost(14, "Nehnutelnost", new Suradnica(46, -16), new Suradnica(22, -29)));
        Operacie.pridajParcelu(this.strom, new Parcela(10, "Parcela", new Suradnica(-165, 50), new Suradnica(163, 54)));
        Operacie.pridajParcelu(this.strom, new Parcela(77, "Nehnutelnost", new Suradnica(122, -66), new Suradnica(-31, 61)));
        Operacie.pridajNehnutelnost(this.strom, new Nehnutelnost(42, "Nehnutelnost", new Suradnica(52, -12), new Suradnica(-30, -65)));
        Operacie.pridajParcelu(this.strom, new Parcela(59, "Parcela", new Suradnica(-118, -84), new Suradnica(-3, -69)));
        Operacie.pridajNehnutelnost(this.strom, new Nehnutelnost(90, "Nehnutelnost", new Suradnica(107, -5), new Suradnica(-27, -47)));
        Operacie.pridajParcelu(this.strom, new Parcela(49, "Parcela", new Suradnica(71, -56), new Suradnica(-4, 67)));
        Operacie.pridajParcelu(this.strom, new Parcela(37, "Nehnutelnost", new Suradnica(-148, 75), new Suradnica(49, -5)));
        Operacie.pridajNehnutelnost(this.strom, new Nehnutelnost(57, "Parcela", new Suradnica(88, -48), new Suradnica(3, 85)));
        Operacie.pridajParcelu(this.strom, new Parcela(13, "Nehnutelnost", new Suradnica(97, -19), new Suradnica(100, -16)));
        Operacie.pridajNehnutelnost(this.strom, new Nehnutelnost(41, "Parcela", new Suradnica(-129, -64), new Suradnica(-83, 56)));
        Operacie.pridajNehnutelnost(this.strom, new Nehnutelnost(9, "Nehnutelnost", new Suradnica(8, 11), new Suradnica(-31, -18)));
        Operacie.pridajParcelu(this.strom, new Parcela(89, "Parcela", new Suradnica(39, -7), new Suradnica(-155, -86)));
        Operacie.pridajNehnutelnost(this.strom, new Nehnutelnost(20, "Nehnutelnost", new Suradnica(160, 77), new Suradnica(54, 25)));
        Operacie.pridajParcelu(this.strom, new Parcela(68, "Parcela", new Suradnica(131, -71), new Suradnica(60, -33)));
        Operacie.pridajNehnutelnost(this.strom, new Nehnutelnost(6, "Nehnutelnost", new Suradnica(169, 47), new Suradnica(27, -82)));
        Operacie.pridajParcelu(this.strom, new Parcela(30, "Parcela", new Suradnica(-78, -40), new Suradnica(-106, 3)));
        Operacie.pridajParcelu(this.strom, new Parcela(65, "Nehnutelnost", new Suradnica(45, 1), new Suradnica(-132, -83)));
        Operacie.pridajNehnutelnost(this.strom, new Nehnutelnost(2, "Parcela", new Suradnica(-135, 68), new Suradnica(-13, 89)));
        Operacie.pridajParcelu(this.strom, new Parcela(70, "Nehnutelnost", new Suradnica(47, -5), new Suradnica(-45, -45)));
        Operacie.pridajParcelu(this.strom, new Parcela(99, "Parcela", new Suradnica(-73, 47), new Suradnica(35, -23)));
        Operacie.pridajNehnutelnost(this.strom, new Nehnutelnost(31, "Nehnutelnost", new Suradnica(-76, 28), new Suradnica(143, 82)));
        Operacie.pridajParcelu(this.strom, new Parcela(54, "Parcela", new Suradnica(12, 11), new Suradnica(53, 69)));
        Operacie.pridajParcelu(this.strom, new Parcela(88, "Nehnutelnost", new Suradnica(-96, -39), new Suradnica(11, -73)));
        Operacie.pridajNehnutelnost(this.strom, new Nehnutelnost(45, "Parcela", new Suradnica(167, 6), new Suradnica(82, 42)));
        Operacie.pridajNehnutelnost(this.strom, new Nehnutelnost(71, "Nehnutelnost", new Suradnica(85, -29), new Suradnica(13, 40)));
        Operacie.pridajParcelu(this.strom, new Parcela(22, "Parcela", new Suradnica(-102, -88), new Suradnica(-140, -80)));
        Operacie.pridajNehnutelnost(this.strom, new Nehnutelnost(23, "Nehnutelnost", new Suradnica(35, -71), new Suradnica(-49, -56)));
        Operacie.pridajParcelu(this.strom, new Parcela(25, "Parcela", new Suradnica(120, -9), new Suradnica(46, -21)));
        Operacie.pridajParcelu(this.strom, new Parcela(18, "Nehnutelnost", new Suradnica(67, -1), new Suradnica(-23, 66)));
        Operacie.pridajNehnutelnost(this.strom, new Nehnutelnost(86, "Parcela", new Suradnica(2, -33), new Suradnica(-52, 4)));

        int pocet = this.strom.getPocetElementov();
        int x = 100;
    }
}
