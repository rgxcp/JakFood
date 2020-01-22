package com.rgxcp.jakfood;

public class FoodList {

    // Deklarasi variable global
    private Double bintang;
    private String alamat_singkat, id_restoran, jenis_makanan, nama_restoran, thumbnail_restoran;

    // Constructor kosong
    public FoodList() {
        // null
    }

    // Constructor dengan parameter
    public FoodList(Double mBintang, String mAlamat_singkat, String mId_restoran, String mJenis_makanan, String mNama_restoran, String mThumbnail_restoran) {
        bintang = mBintang;
        alamat_singkat = mAlamat_singkat;
        id_restoran = mId_restoran;
        jenis_makanan = mJenis_makanan;
        nama_restoran = mNama_restoran;
        thumbnail_restoran = mThumbnail_restoran;
    }

    // Getter
    public Double getBintang() {
        return bintang;
    }

    public String getAlamat_singkat() {
        return alamat_singkat;
    }

    public String getId_restoran() {
        return id_restoran;
    }

    public String getJenis_makanan() {
        return jenis_makanan;
    }

    public String getNama_restoran() {
        return nama_restoran;
    }

    public String getThumbnail_restoran() {
        return thumbnail_restoran;
    }
}
