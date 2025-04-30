package application;

public class Station {
    private String numero;
    private String nom;
    private String statut;
    private boolean bornePaiement;
    private int capacite;
    private int velosDisponibles;
    private int bornesDisponibles;
    private String commune;
    private String departement;
    private String arrondissement;

    public Station(String numero, String nom, String statut, boolean bornePaiement,
                   int capacite, int velosDisponibles, int bornesDisponibles,
                   String commune, String departement, String arrondissement) {
        this.numero = numero;
        this.nom = nom;
        this.statut = statut;
        this.bornePaiement = bornePaiement;
        this.capacite = capacite;
        this.velosDisponibles = velosDisponibles;
        this.bornesDisponibles = bornesDisponibles;
        this.commune = commune;
        this.departement = departement;
        this.arrondissement = arrondissement;
    }

    public String getNumero() { return numero; }
    public String getNom() { return nom; }
    public String getStatut() { return statut; }
    public boolean isBornePaiement() { return bornePaiement; }
    public int getCapacite() { return capacite; }
    public int getVelosDisponibles() { return velosDisponibles; }
    public int getBornesDisponibles() { return bornesDisponibles; }
    public String getCommune() { return commune; }
    public String getDepartement() { return departement; }
    public String getArrondissement() { return arrondissement; }
}
