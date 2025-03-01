package com.rozi.gohits


data class RegisterResponse(
    val status: String,
    val message: String
)
data class MenuHomeResponse(
    val status: String,
    val data: List<MenuHomeItem>
)
data class MenuHomeItem(
    val id_event: String,
    val title: String,
    val upload: String,
    val price: String,
    val organizer: String,
    val type_sport: String
)
data class MenuDasResponse(
    val status: String,
    val data: List<MenuDasItem>
)

data class MenuDasItem(
    val title: String,
    val upload: String,
    val organizer: String,
    val time: String,
    val participant: String,
    val location: String
)
data class UploadResponse(
    val success: Boolean,
    val message: String
)
data class Det(
    val status: String,
    val isi : String,
    val data: List<MenuDet>,
    val perserta: List<Detnam>
)

data class MenuDet(
    val title: String,
    val date_column :String,
    val upload: String,
    val organizer: String,
    val time: String,
    val participant: String,
    val location: String
)
data class Detnam(
    val id_user :String,
    val nama: String
)
