package pt.ismai.fytmproject.view.formlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import pt.ismai.fytmproject.R
import pt.ismai.fytmproject.databinding.ActivityFormLoginBinding
import pt.ismai.fytmproject.view.formcadastro.FormCadastro
import pt.ismai.fytmproject.view.telaprincipal.TelaPrincipal

class FormLogin : AppCompatActivity() {
    //Variaveis
    private lateinit var binding : ActivityFormLoginBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Botão para mudar de pagina para criar registro
        binding.textcadastro.setOnClickListener{
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }

        binding.btEntrar.setOnClickListener{
            //Variaveis
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            if(email.isEmpty() || senha.isEmpty()){
                Toast.makeText(this,"Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }else{
                //Verificação de autenticação do usuario ao fazer login
                auth.signInWithEmailAndPassword(email,senha).addOnCompleteListener { autenticacao ->
                    if(autenticacao.isSuccessful){
                        navegarTelaPrincipal()
                    }
                }.addOnFailureListener {exception ->
                    val mensagemErro = when(exception){
                        is FirebaseAuthWeakPasswordException -> "Digite uma senha com no mínimo 6 caracteres!"
                        is FirebaseAuthInvalidCredentialsException -> "Digite um email válido!"
                        is FirebaseNetworkException -> "Sem conexão com a internet!"
                        else -> "Falha ao fazer login!"
                    }
                    Toast.makeText(this,mensagemErro, Toast.LENGTH_SHORT).show()

                }
            }
        }


    }
    //Função para trocar de pagina para tela principal
    private fun navegarTelaPrincipal(){
        val intent = Intent(this, TelaPrincipal::class.java)
        startActivity(intent)
    }
    //Override do onStart para verificar se o usuario esta com o login feito para não precisar fazer o login mais de uma vez
    override fun onStart(){
        super.onStart()

        val usuarioAtual = FirebaseAuth.getInstance().currentUser //Função para a verificação se está logado

        if(usuarioAtual != null){
           navegarTelaPrincipal()
        }
    }
}