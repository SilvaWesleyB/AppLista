package com.main.worklist.atividades;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.main.worklist.R;
import com.main.worklist.bdhelper.DAO;
import com.main.worklist.model.Tarefa;

public class AdicionaTarefa extends Carregando {
    private TextInputEditText nome_Tarefa;
    private TextInputEditText desc_Tarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);
        bind();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemSalvar) {

            Tarefa lista = new Tarefa(0L, nome_Tarefa.getText().toString(),desc_Tarefa.getText().toString());
            DAO listaDAO = DAO.getSingleton(getApplicationContext());

            listaDAO.salvar(lista);

            Toast.makeText(this, "Tarefa Criada com Sucesso!", Toast.LENGTH_SHORT).show();

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void bind(){

        nome_Tarefa = findViewById(R.id.tela_nome);
        desc_Tarefa = findViewById(R.id.tela_desc);

    }
}
