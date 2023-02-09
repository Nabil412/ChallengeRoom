package com.example.challengeroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengeroom.room.Constant
import com.example.challengeroom.room.dbsmksa
import com.example.challengeroom.room.tbsiswa
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class MainActivity : AppCompatActivity() {

    lateinit var siswaAdapter: SiswaAdapter
    val db by lazy { dbsmksa(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        halEdit()
        setupRecyclerView()
    }

    override fun onStart(){
        super.onStart()
        loadData()
    }

    fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            val siswa = db.tbsisDao().tampilsemua()
            Log.d("MainActivity", "dbResponse:$siswa")
            withContext(Dispatchers.Main){
                siswaAdapter.setData(siswa)
            }
        }
    }

    private fun halEdit(){
        btnInput.setOnClickListener{
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(tbsisnis:Int, intentType:Int){
        startActivity(
            Intent(applicationContext,EditActivity::class.java)
                .putExtra("intent_nis",tbsisnis)
                .putExtra("intent_type",intentType)
        )
    }

    fun setupRecyclerView(){
        siswaAdapter = SiswaAdapter(arrayListOf(),object : SiswaAdapter.OnAdapterListener{
            override fun onClick(tbsis: tbsiswa) {
                intentEdit(tbsis.nis,Constant.TYPE_READ)
            }

            override fun onUpdate(tbsis: tbsiswa) {
                intentEdit(tbsis.nis,Constant.TYPE_UPDATE)
            }
            override fun onDelete(tbsis: tbsiswa) {
                deleteDialog(tbsis)

            }
        })

        //idRecyclerView
        listdatasiswa.apply{
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = siswaAdapter
        }
    }
    private fun deleteDialog(tbsiswa: tbsiswa) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin Hapus ${tbsiswa.nis}?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.tbsisDao().deletetbsiswa(tbsiswa)
                    loadData()
                }
            }
        }
        alertDialog.show()
    }
}
