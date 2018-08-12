package com.celiogarcia.SendOut.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.celiogarcia.SendOut.R;
import com.celiogarcia.SendOut.modelo.Configuracao;
import com.celiogarcia.SendOut.modelo.Menus;

import java.util.List;

/**
 * Created by 92178 on 15/11/2016.
 */

public class MenuLateralAdapter extends BaseAdapter {

    private final List<Integer> conteudoMenuLateral;
    private Context context;

    public MenuLateralAdapter(Context context, List<Integer> osArray){
        this.conteudoMenuLateral = osArray;
        this.context = context;
    }

    @Override
    public int getCount() {
        return conteudoMenuLateral.size();
    }

    @Override
    public Object getItem(int position) {
        return conteudoMenuLateral.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Menus menus = new Menus();

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;

        if (view == null){
            view = inflater.inflate(R.layout.menu_lateral_lista, parent, false);
        }

        // Menus e ícones
        ImageView imageView = (ImageView) view.findViewById(R.id.img_icon);
        imageView.setImageResource(menus.getIconMenuPath().get(position));

        TextView textView = (TextView) view.findViewById(R.id.txt_menu);
        textView.setText(menus.getMenus().get(position));

        return view;

    }

    public class MenuLateralPerfilAdapter extends BaseAdapter{

        private ImageView foto_perfil;
        private TextView nome;
        private TextView numeroDeTelefone;

        //private List<String> list;
        private Configuracao conf;

        public MenuLateralPerfilAdapter(Configuracao configuracao){
            this.conf = configuracao;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = convertView;

            if (view == null){
                view = inflater.inflate(R.layout.menu_lateral_perfil, parent, false);
            }

            // Pega a foto do utilizador
            foto_perfil = (ImageView) view.findViewById(R.id.img_foto);
            carregaFotoPerfil(conf.verFotoDePerfil());

            // Pega o nome do User
            nome = (TextView) view.findViewById(R.id.txt_nome);
            nome.setText(conf.pegaRemetente());

            // Pega o número de telefone
            numeroDeTelefone = (TextView) view.findViewById(R.id.txt_numero);
            numeroDeTelefone.setText(conf.pegaNumeroDoRemetente());

            return view;
        }

        private void carregaFotoPerfil(String caminhoFoto){
            if (caminhoFoto != null){
                Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
                conf.guardaFotoDePerfil(caminhoFoto);
                Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 76, 72, true);
                foto_perfil.setImageBitmap(bitmapReduzido);
                foto_perfil.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        }
    }
}