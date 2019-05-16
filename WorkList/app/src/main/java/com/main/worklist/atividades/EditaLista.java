package com.main.worklist.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.main.worklist.R;
import com.main.worklist.bdhelper.TarefaDAO;
import com.main.worklist.model.Tarefa;

public class EditaLista extends Carregando {

    private Tarefa tarefa;
    private TextView newDescLista;
    private EditText newTitleLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        bind();
        if(intent.hasExtra("Lista")){
            tarefa = (Tarefa) intent.getExtras().getSerializable("Lista");;
            setTela(tarefa);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Tarefa tarefaAtual = new Tarefa(0L, newDescLista.getText().toString(), newTitleLista.getText().toString());
            TarefaDAO repositorio = TarefaDAO.getSingleton(getApplicationContext());

            tarefaAtual.setId(tarefa.getId());
            repositorio.atualizar(tarefaAtual);
                Toast.makeText(EditaLista.this, "Lista Atualizada com suucesso!", Toast.LENGTH_SHORT).show();
            finish();
        }
        });
    }

    public void bind(){
        newDescLista = findViewById(R.id.edita_desc);
        newTitleLista = findViewById(R.id.edit_title);
    }

    public void setTela(Tarefa tarefaAtual){
        newTitleLista.setText(tarefaAtual.getNomeLista());
        newDescLista.setText(tarefaAtual.getDescLista());
    }
}
