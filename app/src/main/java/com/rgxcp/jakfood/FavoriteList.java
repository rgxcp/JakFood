package com.rgxcp.jakfood;

public class FavoriteList {

    // Deklarasi variable global
    private String alamat_singkat, id_restoran, jenis_makanan, nama_restoran, thumbnail_restoran, waktu_akses;

    // Constructor kosong
    FavoriteList() {
        // null
    }

    // Constructor dengan parameter
    public FavoriteList(String alamat_singkat, String id_restoran, String jenis_makanan, String nama_restoran, String thumbnail_restoran, String waktu_akses) {
        this.alamat_singkat = alamat_singkat;
        this.id_restoran = id_restoran;
        this.jenis_makanan = jenis_makanan;
        this.nama_restoran = nama_restoran;
        this.thumbnail_restoran = thumbnail_restoran;
        this.waktu_akses = waktu_akses;
    }

    // Getter
    String getAlamat_singkat() {
        return alamat_singkat;
    }

    String getId_restoran() {
        return id_restoran;
    }

    String getJenis_makanan() {
        return jenis_makanan;
    }

    String getNama_restoran() {
        return nama_restoran;
    }

    String getThumbnail_restoran() {
        return thumbnail_restoran;
    }

    String getWaktu_akses() {
        return waktu_akses;
    }

    // Setter
    void setAlamat_singkat(String alamat_singkat) {
        this.alamat_singkat = alamat_singkat;
    }

    void setId_restoran(String id_restoran) {
        this.id_restoran = id_restoran;
    }

    void setJenis_makanan(String jenis_makanan) {
        this.jenis_makanan = jenis_makanan;
    }

    void setNama_restoran(String nama_restoran) {
        this.nama_restoran = nama_restoran;
    }

    void setThumbnail_restoran(String thumbnail_restoran) {
        this.thumbnail_restoran = thumbnail_restoran;
    }

    void setWaktu_akses(String waktu_akses) {
        this.waktu_akses = waktu_akses;
    }
}
