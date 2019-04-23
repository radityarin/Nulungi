package com.rai.nulungi;

public class Tempat {
    private String alamat,kategori,nama,notelepon,urlfoto;

    public Tempat(String alamat, String kategori, String nama, String notelepon, String urlfoto) {
        this.alamat = alamat;
        this.kategori = kategori;
        this.nama = nama;
        this.notelepon = notelepon;
        this.urlfoto = urlfoto;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getKategori() {
        return kategori;
    }

    public String getNama() {
        return nama;
    }

    public String getNotelepon() {
        return notelepon;
    }

    public String getUrlfoto() {
        return urlfoto;
    }
}
