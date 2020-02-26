package com.rgxcp.jakfood;

public class FoodList {

    // Deklarasi variable global
    private Double bintang;
    private String alamat_singkat, id_restoran, jenis_makanan, nama_restoran, thumbnail_restoran;

    // Constructor kosong
    FoodList() {
        // null
    }

    // Constructor dengan parameter
    public FoodList(Double bintang, String alamat_singkat, String id_restoran, String jenis_makanan, String nama_restoran, String thumbnail_restoran) {
        this.bintang = bintang;
        this.alamat_singkat = alamat_singkat;
        this.id_restoran = id_restoran;
        this.jenis_makanan = jenis_makanan;
        this.nama_restoran = nama_restoran;
        this.thumbnail_restoran = thumbnail_restoran;
    }

    // Getter
    Double getBintang() {
        return bintang;
    }

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

    // Setter
    void setBintang(Double bintang) {
        this.bintang = bintang;
    }

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
}
