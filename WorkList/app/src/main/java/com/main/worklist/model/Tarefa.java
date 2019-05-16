package com.main.worklist.model;

import java.io.Serializable;

public class Tarefa  implements Serializable {
    private Long id;
    private String nomeLista;
    private String descLista;

    public Tarefa(Long id, String nomeLista, String descLista) {
        this.id = id;
        this.nomeLista = nomeLista;
        this.descLista = descLista;
    }

    public String getDescLista() {
        return descLista;
    }

    public void setDescLista(String descLista) {
        this.descLista = descLista;
    }

    public String getNomeLista() {
        return nomeLista;
    }

    public void setNomeLista(String nomeLista) {
        this.nomeLista = nomeLista;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
