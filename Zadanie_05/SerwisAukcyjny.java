import java.util.*;

public class SerwisAukcyjny implements Aukcja{
    private Map<String,Uzytkownik>uzytkownicy = new HashMap<>();
    private Map<Integer, Przedmiot>przedmioty = new HashMap<>(); // mapa z przedmiotami i Id;

    private List<Oferta>oferty = new ArrayList<>();
    private Map<Integer,KoncoweOferty>zakonczoneAukcje = new HashMap<>();

    public class KoncoweOferty {
       private int id;
       private int najwyszaOferta;
       private String wygranaOsoba;

       KoncoweOferty(int id_, int najwyszaOferta_,String wygrany ){
           this.id = id_;
           this.najwyszaOferta = najwyszaOferta_;
           this.wygranaOsoba = wygrany;
       }
        public int getId() {
            return id;
        }

        public int getNajwyszaOferta() {
            return najwyszaOferta;
        }

        public String getWygranaOsoba() {
            return wygranaOsoba;
        }
    }


    public class Oferta{
        Uzytkownik uzytkownik;
        int oferowanaKwota;
        int identifikatorPrzedmiotu;

        Oferta(Uzytkownik u, int oferowanaKwota_, int identifikatorPrzedmiotu_){
            this.uzytkownik = u;
            this.oferowanaKwota = oferowanaKwota_;
            this.identifikatorPrzedmiotu = identifikatorPrzedmiotu_;
        }

        public Uzytkownik getUzytkownik() {
            return uzytkownik;
        }

        public int getOferowanaKwota() {
            return oferowanaKwota;
        }
        public void aktualizujOferte(int oferta){this.oferowanaKwota=oferta;}
    }

    public class Przedmiot implements PrzedmiotAukcji {
        private int id;
        private boolean czyKoniec = false;
        private String nazwa;
        private int ofeta;
        private int cena;
        private List<Uzytkownik>obserwujacy = new ArrayList<>();

        public void dodajSubskrybenta(Uzytkownik u) {
            obserwujacy.add(u);
        }

        public void usunSubskrybenta(Uzytkownik u){
            obserwujacy.remove(u);
        }

        public void zaaktualizujOferte(int oferta_){
            this.ofeta = oferta_;
        }

        public void zaaktualizujCene(){
            if(this.cena<this.ofeta){
                this.cena = this.ofeta;
            }
        }

        public void powiadomSubskrybentow(){
            for(Uzytkownik u:obserwujacy){
                // sprawdz jego aktualna oferte
                for(Oferta o : oferty) {
                        if(o.oferowanaKwota<this.ofeta) {
                            u.kontakt.przebitoTwojąOfertę(this);
                    }
                }
            }
        }

        public void koniecAukcji(){
            this.czyKoniec = true;
        }

        public boolean getCzyKoniec() {
            return this.czyKoniec;
        }
        Przedmiot(int id_, String nazwa_, int oferta_, int cena_){
            this.id = id_;
            this.nazwa = nazwa_;
            this.ofeta = oferta_;
            this.cena = cena_;
        }
        @Override
        public int identyfikator() {
            return this.id;
        }

        @Override
        public String nazwaPrzedmiotu() {
            return this.nazwa;
        }

        @Override
        public int aktualnaOferta() {
            return this.ofeta;
        }

        @Override
        public int aktualnaCena() {
            return this.cena;
        }
    }


    public class Uzytkownik{
        String name;
        Powiadomienie kontakt;

        Uzytkownik(String name_, Powiadomienie kontakt_){
            this.name = name_;
            this.kontakt = kontakt_;
        }

        public String getName() {
            return name;
        }

        public Powiadomienie getKontakt() {
            return kontakt;
        }
    }
    @Override
    public void dodajUżytkownika(String username, Powiadomienie kontakt) {
        uzytkownicy.put(username, new Uzytkownik(username, kontakt));
    }

    @Override
    public void dodajPrzedmiotAukcji(PrzedmiotAukcji przedmiot) {
        Przedmiot newPrzedmiot = new Przedmiot(przedmiot.identyfikator(), przedmiot.nazwaPrzedmiotu(), przedmiot.aktualnaOferta(), przedmiot.aktualnaCena());
        przedmioty.put(newPrzedmiot.identyfikator(), newPrzedmiot);
    }

    @Override
    public void subskrypcjaPowiadomień(String username, int identyfikatorPrzedmiotuAukcji) {
        przedmioty.get(identyfikatorPrzedmiotuAukcji).dodajSubskrybenta(uzytkownicy.get(username));
    }

    @Override
    public void rezygnacjaZPowiadomień(String username, int identyfikatorPrzedmiotuAukcji) {
        przedmioty.get(identyfikatorPrzedmiotuAukcji).usunSubskrybenta(uzytkownicy.get(username));
    }

    public boolean aukcjaTrwa(int identfikatorPrzedmiotuAukcji){
        if(zakonczoneAukcje.get(identfikatorPrzedmiotuAukcji)!=null) {
            return false;
        }
        return true;
    }

public boolean czyUzytkownikZlozylOferte(String username){
        for(Oferta o : oferty){
            if(o.uzytkownik.name == username){
                return true;
            }
        }
        return false;
}

Uzytkownik getUzytkownikByName(String username){
        for(Oferta o : oferty){
            if(o.uzytkownik.name == username){
                return o.uzytkownik;
            }
        }
        return null;
}

    @Override
    public void oferta(String username, int identyfikatorPrzedmiotuAukcji, int oferowanaKwota) {
        //dodaj ofete
        //TESTOWO:
        if(aukcjaTrwa(identyfikatorPrzedmiotuAukcji)) {
            if(czyUzytkownikZlozylOferte(username)){
                for(Oferta o:oferty){
                    if(o.uzytkownik.name==username && o.identifikatorPrzedmiotu==identyfikatorPrzedmiotuAukcji){
                        o.aktualizujOferte(oferowanaKwota);
                    }
                }
            }else {
                //KONIEC TESTOWO
                Oferta nowaOferta = new Oferta(uzytkownicy.get(username), oferowanaKwota, identyfikatorPrzedmiotuAukcji);
                oferty.add(nowaOferta); // dodaje oferte
            }

            //zaaktualizuj cene w Przedmiotach
            przedmioty.get(identyfikatorPrzedmiotuAukcji).zaaktualizujOferte(oferowanaKwota); //aktualizuje oferte w przedmiocie
            przedmioty.get(identyfikatorPrzedmiotuAukcji).zaaktualizujCene(); // aktualizuje cene

            //powiadom subskrybentow
            przedmioty.get(identyfikatorPrzedmiotuAukcji).powiadomSubskrybentow();
        }
    }

    @Override
    public void koniecAukcji(int identyfikatorPrzedmiotuAukcji) {
        int najwyzszaOferta = najwyższaOferta(identyfikatorPrzedmiotuAukcji);
        String winner ="";
        for(Oferta o : oferty){
            if(o.oferowanaKwota == najwyzszaOferta){
                winner = o.uzytkownik.name;
            }
        }
        KoncoweOferty nowaKoncowaOferta = new KoncoweOferty(identyfikatorPrzedmiotuAukcji,najwyzszaOferta,winner);
        zakonczoneAukcje.put(identyfikatorPrzedmiotuAukcji, nowaKoncowaOferta);
    }

    @Override
    public String ktoWygrywa(int identyfikatorPrzedmiotuAukcji) {
        return zakonczoneAukcje.get(identyfikatorPrzedmiotuAukcji).getWygranaOsoba();
    }

    @Override
    public int najwyższaOferta(int identyfikatorPrzedmiotuAukcji) {
        return przedmioty.get(identyfikatorPrzedmiotuAukcji).cena;
    }
}