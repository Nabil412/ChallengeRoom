package com.example.challengeroom.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class tbsiswa (
    @PrimaryKey
    val nis: Int,
    val nama: String,
    val kelas: String,
    val alamat: String,
        )