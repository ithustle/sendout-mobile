package com.celiogarcia.SendOut.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.celiogarcia.SendOut.modelo.Agenda;
import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.modelo.Contacto;
import com.celiogarcia.SendOut.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by celiogarcia on 18/07/16.
 */
public class ContactosAdapter extends BaseAdapter implements Filterable {

    private List<Agenda> contactos;
    private final Context context;
    private ContactsFilter contactoFilter;
    private List<Agenda> origContactos;
    private Configuracao configuracao;

    public ContactosAdapter(Context context, List<Agenda> contactos) {
        this.contactos = contactos;
        this.context = context;
        this.origContactos = contactos;
        this.configuracao = new Configuracao(context);
    }

    @Override
    public int getCount() {
        return contactos.size();
    }

    @Override
    public Object getItem(int i) {
        return contactos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Agenda contacto = contactos.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;

        if (view == null){
            view = inflater.inflate(R.layout.minha_lista, viewGroup, false);
        }

        TextView campoNome = (TextView) view.findViewById(R.id.lista_nome);
        campoNome.setText(contacto.getNome());

        TextView campoTelefone = (TextView) view.findViewById(R.id.lista_telefone);
        campoTelefone.setText(contacto.addIndicativo(configuracao, contacto.getNumero()));

        return view;

    }

    public void resetData(){
        contactos = origContactos;
    }

    @Override
    public Filter getFilter() {
        if (contactoFilter == null)
            contactoFilter = new ContactsFilter();

        return contactoFilter;
    }

    public class ContactsFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {

                // No filter implemented we return all the list
                results.values = origContactos;
                results.count = origContactos.size();
            }
            else {

                // We perform filtering operation
                List<Agenda> newContact = new ArrayList<>();

                for (Agenda c : contactos) {
                    if (c.getNome().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        newContact.add(c);
                }

                results.values = newContact;
                results.count = newContact.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0){
                notifyDataSetInvalidated();
            }else{
                contactos = (List<Agenda>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}
