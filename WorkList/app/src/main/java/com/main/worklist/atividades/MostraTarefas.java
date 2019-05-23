package com.main.worklist.atividades;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.main.worklist.R;
import com.main.worklist.adapter.AdapterLista;
import com.main.worklist.bdhelper.DAO;
import com.main.worklist.model.Tarefa;



public class MostraTarefas extends Carregando implements AdapterView.OnItemClickListener,
        AdapterLista.onContactItemListner {

    private ListView lista;
    private AdapterLista mAdapter;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        lista = findViewById(R.id.minhasListas);
        lista.setOnItemClickListener(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdicionaTarefa.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarListaTarefas();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sair, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void carregarListaTarefas(){
        DAO listaDAO = DAO.getSingleton(this);
        mAdapter = new AdapterLista(this, 0, listaDAO.listar(), this);
        lista.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onTrashClick(final Tarefa tarefaToRemove) {
        new AlertDialog.Builder(this)
                .setTitle("Atenção!")
                .setMessage("Deseja Realmente Apagar esta Tarefa?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DAO listaDAO =  DAO.getSingleton(getApplicationContext());
                        listaDAO.deletar(tarefaToRemove);
                        mAdapter.remove(tarefaToRemove);
                        mAdapter.notifyDataSetChanged();

                        Toast.makeText(MostraTarefas.this, "Tarefa Excluida com Sucesso!", Toast.LENGTH_SHORT).show();
                    }
        }).setNegativeButton("Não", null).show();
    }

    @Override
    public void editaLista(Tarefa tarefa, int position) {
        Intent intent = new Intent(this, EditaTarefa.class);
        intent.putExtra("Tarefa", mAdapter.getItem(position));
        startActivity(intent);
    }

    private void signOut() {
        mAuth.signOut();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.btn_sair) {
            new AlertDialog.Builder(this)
                .setTitle("Atenção!")
                .setMessage("Deseja Realmente Sair?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        signOut();

                        Toast.makeText(MostraTarefas.this, "Você Saiu! Att Breve", Toast.LENGTH_SHORT).show();

                        finish();
                    }
                }).setNegativeButton("Não", null).show();

        }
        return super.onOptionsItemSelected(item);
    }

}
