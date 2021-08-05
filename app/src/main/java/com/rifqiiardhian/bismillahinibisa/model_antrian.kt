package com.rifqiiardhian.bismillahinibisa

class model_antrian(idAntrian: String, tanggal: String, status: String, namaDokter: String, gelarDepan: String, gelarBelakang: String, poli: String, hari: String, jam: String) {
    var id_antrian: String? = idAntrian
    var tanggal_antrian: String? = tanggal
    var status_antrian: String? = status
    var nama_dokter: String? = namaDokter
    var gelar_depan: String? = gelarDepan
    var gelar_belakang: String? = gelarBelakang
    var poli_dokter: String? = poli
    var hari_praktik: String? = hari
    var jam_praktik: String? = jam
}