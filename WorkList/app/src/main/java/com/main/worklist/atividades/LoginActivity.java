package com.main.worklist.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class LoginActivity extends Carregando implements
        View.OnClickListener {

    private static final String TAG = "EmailPassword";

    private EditText mEmail;
    private EditText mSenha;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bind();

        mAuth = FirebaseAuth.getInstance();
    }

    public void bind(){
        mEmail = findViewById(R.id.login_email);
        mSenha = findViewById(R.id.login_senha);

        findViewById(R.id.login_btn_login).setOnClickListener(this);
        findViewById(R.id.login_btn_registrar).setOnClickListener(this);
    }


    private void LogIn(String email, String senha){
        Log.d(TAG, "LogIn: " + email);
        if (!validacaoForm()){
            return;
        }

        showProgressDialog();

        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "LoginComEmail: Sucesso");

                            FirebaseUser user = mAuth.getCurrentUser();
                            updateInfo(user);

                            Intent intent = new Intent(LoginActivity.this, MostraTarefas.class);
                            intent.putExtra("Usuário", user.getUid());

                            Toast.makeText(LoginActivity.this, "Login Realizado com Sucesso!", Toast.LENGTH_SHORT).show();

                            startActivity(intent);
                        } else {
                            Log.v(TAG, "LoginComEmail: Falhou", task.getException());
                             updateInfo(null);
                        }

                        hideProgressDialog();
                    }
                });
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

    private void updateInfo(FirebaseUser user) {
        hideProgressDialog();
        if (user == null) {
            if (!validacaoForm()) {
                return;
            }
            Toast.makeText(LoginActivity.this, "Usuário não Cadastrado ou Senha Incorreta!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if
        (i == R.id.login_btn_login){
            LogIn(mEmail.getText().toString(), mSenha.getText().toString());
        }
        else if
        (i == R.id.login_btn_registrar) {
            Intent intent = new Intent(this, CadastroActivity.class);
            startActivity(intent);
        }
    }
}
