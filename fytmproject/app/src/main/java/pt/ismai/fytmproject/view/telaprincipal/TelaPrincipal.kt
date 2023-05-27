package pt.ismai.fytmproject.view.telaprincipal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import pt.ismai.fytmproject.R
import pt.ismai.fytmproject.databinding.ActivityFormLoginBinding
import pt.ismai.fytmproject.databinding.ActivityTelaPrincipalBinding
import pt.ismai.fytmproject.view.formlogin.FormLogin
import pt.ismai.fytmproject.view.lobby.csgopg.LobbyCsgo
import pt.ismai.fytmproject.view.lobby.lolpg.LobbyLol
import pt.ismai.fytmproject.view.lobby.pubgpg.LobbyPubg

class TelaPrincipal : AppCompatActivity() {

    private lateinit var binding : ActivityTelaPrincipalBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btDeslogar.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val voltarTelaLogin = Intent(this, FormLogin::class.java)
            startActivity(voltarTelaLogin)
            finish()
        }

        binding.btCsgo.setOnClickListener {
            val telaCsgo = Intent(this, LobbyCsgo::class.java)
            startActivity(telaCsgo)
        }

        binding.btLol.setOnClickListener {
            val telaLol = Intent(this, LobbyLol::class.java)
            startActivity(telaLol)
        }

        binding.btPubg.setOnClickListener {
            val telaPubg = Intent(this, LobbyPubg::class.java)
            startActivity(telaPubg)
        }
    }

}