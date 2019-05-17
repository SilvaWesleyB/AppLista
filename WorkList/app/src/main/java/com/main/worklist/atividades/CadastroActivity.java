package com.main.worklist.atividades;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.main.worklist.R;

public class CadastroActivity extends Carregando implements
        View.OnClickListener{

    private FirebaseAuth mAuth;

    private static final String TAG = "EmailPassword";

    private EditText mSenha;
    private EditText mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        mAuth = FirebaseAuth.getInstance();
        bind();
    }

    private void Cadastrar(String email, String senha) {
        Log.d(TAG, "LogIn: " + email);
        if (!validacaoForm()) {
            return;
        }

        showProgressDialog();

        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "CriarUsuário: Sucesso");

                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            updateInfo(currentUser);

                            Toast.makeText(CadastroActivity.this, "Usuário cadastrado com Sucesso!", Toast.LENGTH_SHORT).show();

                            finish();

                        }else {

                            Log.v(TAG, "CadastroComEmail: Falhou", task.getException());
                            updateInfo(null);

                        }

                        hideProgressDialog();
                    }
                });
    }

    private void updateInfo(FirebaseUser currentUser) {
        hideProgressDialog();
        if (currentUser == null){

            if (!validacaoForm()) {
                return;
            }

            Toast.makeText(this, "Algum campo vazio ou Email ja Cadastrado!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validacaoForm() {
        boolean valid = true;

        String email = mEmail.getText().toString();
        if (TextUtils.isEmpty(email)){
            mEmail.setError("Preencher.");
            valid = false;
        }else {
            mEmail.setError(null);
        }
        String senha = mSenha.getText().toString();
        if (TextUtils.isEmpty(senha)){
            mSenha.setError("Preencher.");
            valid = false;
        }else {
            mSenha.setError(null);
        }

        return valid;
    }

    public void bind() {
        mEmail = findViewById(R.id.cadastro_email);
        mSenha = findViewById(R.id.cadastro_senha);

        findViewById(R.id.cadastro_btn_registrar).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.cadastro_btn_registrar){
            Cadastrar(mEmail.getText().toString(), mSenha.getText().toString());
        }
    }
}
