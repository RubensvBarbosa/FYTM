package pt.ismai.fytmproject.view.lobby.csgopg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import pt.ismai.fytmproject.R
import pt.ismai.fytmproject.view.MyAdapter
import pt.ismai.fytmproject.view.User

class LobbyCsgo : AppCompatActivity() {
    //Variaveis
    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<User>
    private lateinit var myAdapter: MyAdapter
    private lateinit var db : FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby_csgo)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()

        myAdapter = MyAdapter(userArrayList)

        recyclerView.adapter = myAdapter

        EventChangeListener()
    }
    //Função que defino qual tipo quero fazer o get
    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("Usuários").whereEqualTo("jogo","CS:GO").
                addSnapshotListener(object : EventListener<QuerySnapshot>{
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {

                        if(error != null){
                            Log.e("Firestore Error", error.message.toString())
                            return
                        }

                        for (dc : DocumentChange in value?.documentChanges!!){
                            if (dc.type == DocumentChange.Type.ADDED){
                                userArrayList.add(dc.document.toObject(User::class.java))
                            }
                        }

                        myAdapter.notifyDataSetChanged()

                    }

                })
    }
}