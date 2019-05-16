package com.main.worklist.bdhelper;

import com.main.worklist.model.Tarefa;

import java.util.ArrayList;

interface InterfaceTarefaDao {
    public void salvar(Tarefa tarefa);
    public void atualizar(Tarefa tarefa);
    public void deletar(Tarefa tarefa);
    public ArrayList listar();
}