package com.main.worklist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.main.worklist.R;
import com.main.worklist.model.Tarefa;

import java.util.List;


public class AdapterLista extends ArrayAdapter<Tarefa> {

    private final onContactItemListner callback;

    public interface onContactItemListner{
        void onTrashClick(Tarefa tarefaToRemove);

        void editaLista(Tarefa tarefa, int position);
    }


    public AdapterLista(@NonNull Context context, int resource, @NonNull List<Tarefa> objects, onContactItemListner callback) {
        super(context, resource, objects);
        this.callback = callback;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflator = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewItem = inflator.inflate(R.layout.modelo_lista, null);
        Tarefa tarefa = getItem(position);
        TextView tarefaNome = viewItem.findViewById(R.id.tarefa_nome);
        tarefaNome.setText(tarefa.getNomeLista());
        TextView tarefaDesc = viewItem.findViewById(R.id.tarefa_desc);
        tarefaDesc.setText(tarefa.getDescLista());
        ImageView removeButton = viewItem.findViewById(R.id.btn_delete);
        removeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Tarefa tarefa = getItem(position);
                AdapterLista.this.callback.onTrashClick(tarefa);
            }
        });

        ImageView editButton = viewItem.findViewById(R.id.btn_edit);
        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Tarefa tarefa = getItem(position);
                AdapterLista.this.callback.editaLista(tarefa, position);
            }
        });

        return viewItem;
    }


}
