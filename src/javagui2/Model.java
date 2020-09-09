
package javagui2;

public class Model {
                private String ime;
                private String prezime;
                private String adresa;
                private String brzina;
                private String protok;
                private String ugovor;
                
                public Model(String ime, String prezime, String adresa, String brzina, String protok, String ugovor) {
        this.ime = ime;
        this.prezime = prezime; 
        this.adresa = adresa;
        this.brzina = brzina;
        this.protok = protok;
        this.ugovor = ugovor;
    }

    public String getIme() {
        return ime;
    }
    
    public String getPrezime() {
        return prezime;
    }


    public String getAdresa() {
        return adresa;
    }

    public String getBrzina() {
        return brzina;
    }

    public String getProtok() {
        return protok;
    }

    public String getUgovor() {
        return ugovor;
    }
}
