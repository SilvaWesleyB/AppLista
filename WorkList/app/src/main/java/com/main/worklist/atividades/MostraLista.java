package com.main.worklist.atividades;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.main.worklist.R;
import com.main.worklist.adapter.TarefaAdapter;
import com.main.worklist.bdhelper.TarefaDAO;
import com.main.worklist.model.Tarefa;



public class MostraLista extends Carregando implements AdapterView.OnItemClickListener,
        TarefaAdapter.onContactItemListner {

    private ListView lista;
    private TarefaAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lista = findViewById(R.id.minhasListas);
        lista.setOnItemClickListener(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdicionaLista.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarListaTarefas();
    }

    public void carregarListaTarefas(){
        TarefaDAO tarefaDAO = TarefaDAO.getSingleton(this);
        mAdapter = new TarefaAdapter(this, 0,tarefaDAO.listar(), this);
        lista.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onTrashClick(final Tarefa tarefaToRemove) {
        new AlertDialog.Builder(this)
                .setTitle("Atenção!")
                .setMessage("Deseja Realmente Apagar esta Lista?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TarefaDAO tarefaDAO =  TarefaDAO.getSingleton(getApplicationContext());
                        tarefaDAO.deletar(tarefaToRemove);
                        mAdapter.remove(tarefaToRemove);
                        mAdapter.notifyDataSetChanged();

                        Toast.makeText(MostraLista.this, "Lista Excluida com Sucesso!", Toast.LENGTH_SHORT).show();
                    }
        }).setNegativeButton("Não", null).show();
    }

    @Override
    public void editaLista(Tarefa tarefa, int position) {
        Intent intent = new Intent(this, EditaLista.class);
        intent.putExtra("Lista", mAdapter.getItem(position));
        startActivity(intent);
    }
}
