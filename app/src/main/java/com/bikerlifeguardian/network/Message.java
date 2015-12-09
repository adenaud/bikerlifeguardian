package com.bikerlifeguardian.network;

import java.util.Date;
import java.util.List;

public class Message {
    private static String DATE_FORMAT = "dd/MM/yyyy";

    private String nom;
    private String prenom;
    private Date dateDeNaissance;
    private int sexe;
    private String groupeSanguin;
    private String numeroTelephone;
    private List<String> languesParler;
    private List<String> allergies;
    private List<String> medicamentsPris;
    private String problemeSanteConnu;
    private String representantLegalNom;
    private String representantLegalPrenom;
    private String representantLegalNumeroTelephone;
    private String commentaires;
    private String coordonneesGPS;


    public Message(String nom, String prenom, Date dateDeNaissance, int sexe, String groupeSanguin, String numeroTelephone, List<String> languesParler, List<String> allergies,
                   List<String> medicamentsPris, String problemeSanteConnu, String representantLegalNom, String representantLegalPrenom, String representantLegalNumeroTelephone, String commentaires) {
        super();
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateDeNaissance;
        this.sexe = sexe;
        this.groupeSanguin = groupeSanguin;
        this.numeroTelephone = numeroTelephone;
        this.languesParler = languesParler;
        this.allergies = allergies;
        this.medicamentsPris = medicamentsPris;
        this.problemeSanteConnu = problemeSanteConnu;
        this.representantLegalNom = representantLegalNom;
        this.representantLegalPrenom = representantLegalPrenom;
        this.representantLegalNumeroTelephone = representantLegalNumeroTelephone;
        this.commentaires = commentaires;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public int getSexe() {
        return sexe;
    }

    public void setSexe(int sexe) {
        this.sexe = sexe;
    }

    public String getGroupeSanguin() {
        return groupeSanguin;
    }

    public void setGroupeSanguin(String groupeSanguin) {
        this.groupeSanguin = groupeSanguin;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public List<String> getLanguesParler() {
        return languesParler;
    }

    public void setLanguesParler(List<String> languesParler) {
        this.languesParler = languesParler;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public List<String> getMedicamentsPris() {
        return medicamentsPris;
    }

    public void setMedicamentsPris(List<String> medicamentsPris) {
        this.medicamentsPris = medicamentsPris;
    }

    public String getProblemeSanteConnu() {
        return problemeSanteConnu;
    }

    public void setProblemeSanteConnu(String problemeSanteConnu) {
        this.problemeSanteConnu = problemeSanteConnu;
    }

    public String getRepresentantLegalNom() {
        return representantLegalNom;
    }

    public void setRepresentantLegalNom(String representantLegalNom) {
        this.representantLegalNom = representantLegalNom;
    }

    public String getRepresentantLegalPrenom() {
        return representantLegalPrenom;
    }

    public void setRepresentantLegalPrenom(String representantLegalPrenom) {
        this.representantLegalPrenom = representantLegalPrenom;
    }

    public String getRepresentantLegalNumeroTelephone() {
        return representantLegalNumeroTelephone;
    }

    public void setRepresentantLegalNumeroTelephone(String representantLegalNumeroTelephone) {
        this.representantLegalNumeroTelephone = representantLegalNumeroTelephone;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public String getCoordonneesGPS() {
        return coordonneesGPS;
    }

    public void setCoordonneesGPS(String coordonneesGPS) {
        this.coordonneesGPS = coordonneesGPS;
    }



}
