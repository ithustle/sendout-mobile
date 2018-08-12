package com.celiogarcia.SendOut.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.modelo.Campanha;
import com.celiogarcia.SendOut.task.CampanhasTask;
import com.celiogarcia.SendOut.task.PropagaCampanhasTask;

public class CampanhasActivity extends AppCompatActivity {

    private ListView lista_campanha;
    private Campanha campanha;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campanhas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        lista_campanha = (ListView) findViewById(R.id.lista_campanhas);
        new CampanhasTask(this, lista_campanha).execute();

        lista_campanha.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                registerForContextMenu(parent);

                campanha = (Campanha) parent.getItemAtPosition(position);

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contexto_campanha, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_context_campanha_propagar:
                new PropagaCampanhasTask(context, campanha.getCodigo()).execute();
                break;


        }
        return super.onContextItemSelected(item);
    }
}
