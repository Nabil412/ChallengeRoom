package com.example.challengeroom.room

import androidx.room.*

@Dao
interface tbsiswaDao {
    @Insert
    suspend fun addtbsiswa (tbsis: tbsiswa)

    @Update
    suspend fun updatetbsiswa (tbsis: tbsiswa)

    @Delete
    suspend fun deletetbsiswa (tbsis: tbsiswa)

    @Query("SELECT * FROM tbsiswa")
    suspend fun tampilsemua() : List<tbsiswa>

    @Query("SELECT * FROM tbsiswa WHERE nis=:siswa_nis")
    fun tampilid (siswa_nis: Int) : List<tbsiswa>

}