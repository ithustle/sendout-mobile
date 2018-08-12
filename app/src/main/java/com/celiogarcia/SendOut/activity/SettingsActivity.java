package com.celiogarcia.SendOut.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.adapter.SettingsAdapter;
import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.modelo.Menus;

import java.io.File;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private static final int CODIGO_CAMERA = 2310;
    private static final int CODIGO_GALERIA = 1023;
    private ListView lvListaSettings;
    private SettingsAdapter adapter;
    private List<Integer> list;
    private TextView nomePerfil;
    public Configuracao configuracao;
    private String caminhoFoto;
    private ImageView foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Menus menus = new Menus();
        configuracao = new Configuracao(this);

        list = menus.getSettings();

        lvListaSettings = (ListView) findViewById(R.id.lista_settings);
        nomePerfil = (TextView) findViewById(R.id.settings_nome_perfil);
        nomePerfil.setText(configuracao.pegaRemetente());

        foto = (ImageView) findViewById(R.id.settings_foto_perfil);
        carregaImagem(configuracao.verFotoDePerfil());

        lvListaSettings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(SettingsActivity.this, IntroductionRegisteringCallerIdActivity.class));
                        break;
                    case 1:
                        Toast.makeText(SettingsActivity.this, getString(R.string.toast_saldo) +": "+ configuracao.creditoSms() + " / " + configuracao.minutosDeVoz(), Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(SettingsActivity.this, configuracao.pegaRemetente() + " " + getString(R.string.toast_meu_numero) + " " +  configuracao.pegaNumeroDoRemetente(), Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        startActivity(new Intent(SettingsActivity.this, SuporteActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(SettingsActivity.this, SobreActivity.class));
                        break;
                }
            }
        });

        adapter = new SettingsAdapter(this, list);
        lvListaSettings.setAdapter(adapter);

        Button botaoFoto = (Button) findViewById(R.id.editar_foto_perfil);
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {getString(R.string.dialogo_camera_foto), getString(R.string.dialogo_galeria_foto), getString(R.string.dialogo_cancela_foto)};

                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle(getString(R.string.dialogo_titulo_foto));
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equals(getString(R.string.dialogo_camera_foto))){

                            Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                            File foto = new File(caminhoFoto);
                            intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto));
                            startActivityForResult(intentCamera, CODIGO_CAMERA);

                        }else if (items[which].equals(getString(R.string.dialogo_galeria_foto))){

                            Intent intentPickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(Intent.createChooser(intentPickPhoto, "Select file"), CODIGO_GALERIA);

                        }else if (items[which].equals(getString(R.string.dialogo_cancela_foto))){
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CODIGO_CAMERA) {
                carregaImagem(caminhoFoto);
            }else if (requestCode == CODIGO_GALERIA){
                Uri selectedImageUri = data.getData();
                String tempPath = getPath(selectedImageUri, SettingsActivity.this);
                carregaImagem(tempPath);
            }
        }
    }


    public void carregaImagem(String caminhoFoto){
        if (caminhoFoto != null){
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            configuracao.guardaFotoDePerfil(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 280, true);
            foto.setImageBitmap(bitmapReduzido);
            foto.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    public String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = activity
                .managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
