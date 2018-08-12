package com.celiogarcia.SendOut.helper;

import android.provider.BaseColumns;

/**
 * Created by 92178 on 08/12/2016.
 */

public final class DatabaseHelper {

    private DatabaseHelper(){}

    public final static class TabelaAgenda implements BaseColumns {
        public final static String TABELA_AGENDA = "agenda";
        public final static String COLUNA_NOME = "nome";
        public final static String COLUNA_NUMERO_AGENDA = "numero";
    }

    public final static class TabelaMensagens implements BaseColumns {
        public final static String TABELA_MENSAGENS = "mensagens";
        public final static String COLUNA_PARA = "para";
        public final static String COLUNA_SMS = "sms";
        public final static String COLUNA_DATASMS = "datasms";
        public final static String COLUNA_ESTADO = "estado";
        public final static String COLUNA_FLAG = "flag";
    }

    public final static class TabelaHistoricoChamada implements BaseColumns {
        public final static String TABELA_HISTORICO_CHAMADAS = "historico_chamadas";
        public final static String COLUNA_NUMERO_HISTORICO = "numero";
        public final static String COLUNA_DATA_CHAMADAS = "data_chamada";
        public final static String COLUNA_TEMPO_DURACAO = "tempo_duracao";
    }
}
