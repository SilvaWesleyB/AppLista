package com.main.worklist.bdhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.main.worklist.model.Tarefa;

import java.util.ArrayList;

import static com.main.worklist.bdhelper.BdHelper.TABELA_TAREFAS;

public class DAO implements InterfaceDao {

    static private DAO singleton;

    private SQLiteDatabase BDListas;

    public static DAO getSingleton(Context context){
        if (singleton == null){
            singleton = new DAO();
            singleton.BDListas = new BdHelper(context , "", null, 0).getWritableDatabase();
        }
        return singleton;
    }

    private ArrayList<Tarefa> listaTarefas = new ArrayList<>();

    @Override
    public ArrayList<Tarefa> listar() {

        String[] projection = {
                "id",
                "nomeLista",
                "descLista"
        };

        Cursor cv = BDListas.query(TABELA_TAREFAS , projection, null,
                null, null, null, null);

        ArrayList itens = new ArrayList<Tarefa>();

        while (cv.moveToNext()){
            long id = cv.getLong(cv.getColumnIndexOrThrow("id"));
            String nomedaLista = cv.getString(cv.getColumnIndexOrThrow("nomeLista"));
            String descdaLista = cv.getString(cv.getColumnIndexOrThrow("descLista"));

            Tarefa tarefa = new Tarefa(id, nomedaLista, descdaLista);

            itens.add(tarefa);
        }

        cv.close();
        return itens;
    }


    @Override
    public void salvar(Tarefa tarefa) {
        listaTarefas.add(tarefa);

        ContentValues cv = new ContentValues();
        cv.put("nomeLista", tarefa.getNomeLista());
        cv.put("descLista", tarefa.getDescLista());

        BDListas.insertOrThrow(TABELA_TAREFAS, null, cv);
    }

    @Override
    public void atualizar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nomeLista", tarefa.getNomeLista());
        cv.put("descLista", tarefa.getDescLista());

        String selection = "id = ?";
        String[] args = {String.valueOf(tarefa.getId())};

        int count = BDListas.update(TABELA_TAREFAS, cv, selection, args );
    }

    @Override
    public void deletar(Tarefa tarefa) {
        listaTarefas.remove(tarefa);

        String selection = "id = ?";
        String[] args = {String.valueOf(tarefa.getId())};

        BDListas.delete(TABELA_TAREFAS, selection, args );
    }
}
