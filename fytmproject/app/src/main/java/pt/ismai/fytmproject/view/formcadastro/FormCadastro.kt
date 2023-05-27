package pt.ismai.fytmproject.view.formcadastro

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import pt.ismai.fytmproject.R
import pt.ismai.fytmproject.databinding.ActivityFormCadastroBinding
import pt.ismai.fytmproject.view.formlogin.FormLogin
import pt.ismai.fytmproject.view.telaprincipal.TelaPrincipal

class FormCadastro : AppCompatActivity() {

    //Variaveis
    private lateinit var binding: ActivityFormCadastroBinding
    lateinit var radiogame:RadioButton
    lateinit var radioperiodo: RadioButton

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Função para pegar o valor do RadioGroup selecionado de jogos
        val radio_groupgame = binding.radiogroupgame

        radio_groupgame.setOnCheckedChangeListener { group, checkedId ->
            radiogame = findViewById(checkedId)
        }

        //Função para pegar o valor do RadioGroup selecionado de periodo
        val radio_groupperiodo = binding.radiogroupperiodo

        radio_groupperiodo.setOnCheckedChangeListener { group, checkedId ->
            radioperiodo = findViewById(checkedId)
        }

        //função botao
        binding.btCadastrar.setOnClickListener {
            //incio as variaveis
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()
            val nickname = binding.editNickname.text.toString()
            val idade = binding.editIdade.text.toString()
            val game = radiogame.text.toString()
            val periodo = radioperiodo.text.toString()

            //Hash map para criação do user
            val usuariosMap = hashMapOf(
                "nickName" to nickname,
                "email" to email,
                "senha" to senha,
                "idade" to idade,
                "jogo" to game,
                "periodo" to periodo
            )


            if (email.isNotEmpty() && senha.isNotEmpty()){
                //condiçoes de registar autenticação
                auth.createUserWithEmailAndPassword(email , senha).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this,"Sucesso ao cadastrar", Toast.LENGTH_SHORT).show()
                        binding.editSenha.setText("")
                        binding.editEmail.setText("")
                        binding.editIdade.setText("")
                        binding.editNickname.setText("")
                        binding.radiogroupgame.clearCheck()
                        binding.radiogroupperiodo.clearCheck()
                    }else{
                        Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {exception ->
                    val mensagemErro = when(exception){
                        is FirebaseAuthWeakPasswordException -> "Digite uma senha com no mínimo 6 caracteres!"
                        is FirebaseAuthInvalidCredentialsException -> "Digite um email válido!"
                        is FirebaseAuthUserCollisionException -> "Esta conta já foi cadastrada!"
                        is FirebaseNetworkException -> "Sem conexão com a internet!"
                        else -> "Erro ao cadastrar usuário!"
                    }
                    Toast.makeText(this,mensagemErro, Toast.LENGTH_SHORT).show()

                }
                //função para a criação do usuario
                db.collection("Usuários").document(nickname)
                    .set(usuariosMap).addOnCompleteListener {
                        Toast.makeText(this,"DATABASE OK", Toast.LENGTH_SHORT).show()
                        navegarTelaLogin()
                    }.addOnFailureListener {

                    }
            }else{
                Toast.makeText(this,"Tem que preencher todos os campos  ", Toast.LENGTH_SHORT).show()
            }
        }
    }
    //Função para trocar de tela
    private fun navegarTelaLogin(){
        val intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
    }
}