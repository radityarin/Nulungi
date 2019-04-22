package com.rai.nulungi;

public class Profil {

    private String userId, namaUser, emailUser, NoKTP, nomorHP;

    public Profil() {
    }

    public Profil(String namaUser, String emailUser, String noKTP, String nomorHP) {
        this.namaUser = namaUser;
        this.emailUser = emailUser;
        NoKTP = noKTP;
        this.nomorHP = nomorHP;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getNoKTP() {
        return NoKTP;
    }

    public void setNoKTP(String noKTP) {
        NoKTP = noKTP;
    }

    public String getNomorHP() {
        return nomorHP;
    }

    public void setNomorHP(String nomorHP) {
        this.nomorHP = nomorHP;
    }

}
