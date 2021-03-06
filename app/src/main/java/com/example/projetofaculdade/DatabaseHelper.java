package com.example.projetofaculdade;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import androidx.annotation.Nullable;

import java.nio.DoubleBuffer;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 14;
    private static final String DATABASE_NAME = "categoria.db";

    // Tables names
    private static final String TABLE_CATEGORIA = "tb_categoria";
    private static final String TABLE_QUESTAO = "tb_questao";
    private static final String TABLE_OPCAO = "tb_opcao";
    private static final String TABLE_QUESTAO_OPCAO = "questao_opcao";

    // Table CATEGORIA columns names
    private static final String CATEGORIA_ID = "id";
    private static final String CATEGORIA_NOME = "nome";

    // Table QUESTAO columns names
    private static final String QUESTAO_ID = "id";
    private static final String QUESTAO_NOME = "nome";
    private static final String QUESTAO_CATEGORIA_ID = "CATEGORIA_ID";

    // Table OPCAO columns names
    private static final String OPCAO_ID = "id";
    private static final String OPCAO_NOME = "nome";

    // Table QUESTAO_OPCAO columns names
    private static final String QUESTAO_OPCAO_QUESTAO_ID = "questao_id";
    private static final String QUESTAO_OPCAO_OPCAO_ID = "opcao_id";
    private static final String QUESTAO_OPCAO_NUMERO = "numero";
    private static final String QUESTAO_OPCAO_CORRETA = "ic_correta";

    // Create CATEGORIA table statement
    private static final String CREATE_CATEGORIA_TABLE = "CREATE TABLE " +
            TABLE_CATEGORIA + " (" + CATEGORIA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CATEGORIA_NOME + " TEXT" + ")";

    // CREATE QUESTAO TABLE statement
    private static final String CREATE_QUESTAO_TABLE = "CREATE TABLE " +
            TABLE_QUESTAO + " ("  + QUESTAO_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, " +
            QUESTAO_NOME + " TEXT, " +
            QUESTAO_CATEGORIA_ID + " INTEGER, " +
            "FOREIGN KEY(" + QUESTAO_CATEGORIA_ID + ") REFERENCES " + TABLE_CATEGORIA + " (" + CATEGORIA_ID + "))";

    // CREATE OPCAO TABLE statement
    private static final String CREATE_OPCAO_TABLE = "CREATE TABLE " +
            TABLE_OPCAO + " (" + OPCAO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            OPCAO_NOME + " TEXT" + ")";

    // CREATE QUESTAO_OPCAO TABLE statement
    private static final String CREATE_QUESTAO_OPCAO_TABLE = "CREATE TABLE " +
            TABLE_QUESTAO_OPCAO + " (" + QUESTAO_OPCAO_QUESTAO_ID + " INTEGER, " +
            QUESTAO_OPCAO_OPCAO_ID + " INTEGER, " +
            QUESTAO_OPCAO_NUMERO + " INTEGER, " +
            QUESTAO_OPCAO_CORRETA +  " INTEGER, " +
            "FOREIGN KEY (" + QUESTAO_OPCAO_QUESTAO_ID  + ") REFERENCES " +
            TABLE_QUESTAO + " (" + QUESTAO_ID + "), " +
             "FOREIGN KEY (" +  QUESTAO_OPCAO_OPCAO_ID + ") REFERENCES " +
            TABLE_OPCAO + " (" + OPCAO_ID + "))";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CATEGORIA_TABLE);
        db.execSQL(CREATE_QUESTAO_TABLE);
        db.execSQL(CREATE_OPCAO_TABLE);
        db.execSQL(CREATE_QUESTAO_OPCAO_TABLE);


        long categoriaDesenvolvimentoid = this.insertCategoria("Desenvolvimento", db);
        long categoriaRedesId = this.insertCategoria("Redes", db);
        long categoriaSegurancaId = this.insertCategoria("Seguranca da Informação", db);

        this.insertQuestoes(categoriaDesenvolvimentoid, this.getDesenvolvimentoQuestoes(), db);
        this.insertQuestoes(categoriaRedesId, this.getRedesQuestoes(), db);
        this.insertQuestoes(categoriaSegurancaId, this.getSegurancaQuestoes(), db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTAO_OPCAO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTAO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPCAO);

        this.onCreate(db);
    }

    private List<Questao> getSegurancaQuestoes() {
        List<Questao> questoes = new ArrayList<Questao>();
        List<QuestaoOpcao> opcoes = new ArrayList<QuestaoOpcao>();
        List<QuestaoOpcao> opcoes2 = new ArrayList<QuestaoOpcao>();
        List<QuestaoOpcao> opcoes3 = new ArrayList<QuestaoOpcao>();
        List<QuestaoOpcao> opcoes4 = new ArrayList<QuestaoOpcao>();
        List<QuestaoOpcao> opcoes5 = new ArrayList<QuestaoOpcao>();

        opcoes.add(new QuestaoOpcao(1, 1, 1,"A) autenticidade.", true));
        opcoes.add(new QuestaoOpcao( 1, 1, 2, "B) integridade.", false));
        opcoes.add(new QuestaoOpcao(1, 1, 3, "C) confidencialidade.", true));
        opcoes.add(new QuestaoOpcao(1, 1, 4, "D) disponibilidade.", false));
        questoes.add(new Questao(1, "Para o estabelecimento de padrões de segurança, um dos princípios críticos é a necessidade de se verificar a legitimidade de uma comunicação, de uma transação ou de um acesso a algum serviço. Esse princípio refere-se à", opcoes));

        opcoes2.add(new QuestaoOpcao(1, 1, 1, "A) backdoor.", false));
        opcoes2.add(new QuestaoOpcao(1, 1, 2, "B) spyware", true));
        opcoes2.add(new QuestaoOpcao(1, 1, 3, "C) phishing.", false));
        opcoes2.add(new QuestaoOpcao(1, 1, 4, "D) rootkit.", false));
        questoes.add(new Questao(2, "Os técnicos precisam ter consciência sobre softwares que têm objetivos de monitorar atividades de uma Instituição e de enviar as informações coletadas para terceiros de forma dissimulada e não autorizada. Estes se enquadram na categoria de software denominada", opcoes2));

        opcoes3.add(new QuestaoOpcao(1, 1, 1, "A) possibilitar a conexão com a Internet.", false));
        opcoes3.add(new QuestaoOpcao(1, 1, 2, "B) configurar uma rede privada.", false));
        opcoes3.add(new QuestaoOpcao(1, 1, 3, "C) visualizar diversos tipos de arquivos.", false));
        opcoes3.add(new QuestaoOpcao(1, 1, 4, "D) realizar a segurança de redes privadas.", true));
        questoes.add(new Questao(3, "O objetivo do firewall é: ", opcoes3));

        opcoes4.add(new QuestaoOpcao(1, 1, 1, "A) spyware", false));
        opcoes4.add(new QuestaoOpcao(1, 1, 2, "B) trojan", false));
        opcoes4.add(new QuestaoOpcao(1, 1, 3, "C) vírus", false));
        opcoes4.add(new QuestaoOpcao(1, 1, 4, "D) tripod", true));
        questoes.add(new Questao(4, "Quanto à Segurança da Informação identifique a única alternativa que NÃO é considerada tecnicamente como um malware:", opcoes4));

        opcoes5.add(new QuestaoOpcao(1, 1, 1, "A) IPV4.", true));
        opcoes5.add(new QuestaoOpcao(1, 1, 2, "B) IPV6.", false));
        opcoes5.add(new QuestaoOpcao(1, 1, 3, "C) IPV8.", false));
        opcoes5.add(new QuestaoOpcao(1, 1, 4, "D) IP/NES.", false));
        questoes.add(new Questao(4, "O esquema de endereçamento de rede mais comum, que consiste de endereços de 32 bits divididos em 4 octetos é chamado", opcoes5));

        return questoes;
    }

    private List<Questao> getDesenvolvimentoQuestoes() {
        List<Questao> questoes = new ArrayList<Questao>();
        List<QuestaoOpcao> opcoes = new ArrayList<QuestaoOpcao>();
        List<QuestaoOpcao> opcoes2 = new ArrayList<QuestaoOpcao>();
        List<QuestaoOpcao> opcoes3 = new ArrayList<QuestaoOpcao>();
        List<QuestaoOpcao> opcoes4 = new ArrayList<QuestaoOpcao>();
        List<QuestaoOpcao> opcoes5 = new ArrayList<QuestaoOpcao>();

        opcoes.add(new QuestaoOpcao(1, 1, 1,"A) uma classe e tem o mesmo nome da classe.", false));
        opcoes.add(new QuestaoOpcao( 1, 1, 2, "B) um objeto e tem o mesmo nome do objeto.", false));
        opcoes.add(new QuestaoOpcao(1, 1, 3, "C) um objeto e tem o mesmo nome da classe a qual pertence.", true));
        opcoes.add(new QuestaoOpcao(1, 1, 4, "D) uma classe e tem o nome diferente do nome da classe.", false));
        questoes.add(new Questao(1, "Construtores Java são métodos especiais chamados pelo sistema no momento da criação de:", opcoes));


        opcoes2.add(new QuestaoOpcao(1, 1, 1, "A) generic.", false));
        opcoes2.add(new QuestaoOpcao(1, 1, 2, "B) void.", false));
        opcoes2.add(new QuestaoOpcao(1, 1, 3, "C) initial.", false));
        opcoes2.add(new QuestaoOpcao(1, 1, 4, "D) abstract.", true));
        questoes.add(new Questao(2, "Na linguagem Java, um método que é apenas declarado como membro de uma classe, mas não provê uma implementação, deve ser declarado como:", opcoes2));

        opcoes3.add(new QuestaoOpcao(1, 1, 1, "A) System.println(nomeA.compare(nomeB));", true));
        opcoes3.add(new QuestaoOpcao(1, 1, 2, "B) System.out.println(nomeA.equals(nomeB));", false));
        opcoes3.add(new QuestaoOpcao(1, 1, 3, "C) System.out(equals(nomeA, nomeB));", false));
        opcoes3.add(new QuestaoOpcao(1, 1, 4, "D) System.out.print(equals(nomeA==nomeB));", false));
        questoes.add(new Questao(3, "O comando da linguagem Java que exibe se a string nomeA é igual à string nomeB é:", opcoes3));

        opcoes4.add(new QuestaoOpcao(1, 1, 1, "A) String x = (String) (b > c) ? \"true\" : \"false\"", false));
        opcoes4.add(new QuestaoOpcao(1, 1, 2, "B) public static void main (String ... args){}", true));
        opcoes4.add(new QuestaoOpcao(1, 1, 3, "C) final enum letra {A, B, C}", false));
        opcoes4.add(new QuestaoOpcao(1, 1, 4, "D) Boolean bool = new Boolean()", false));
        questoes.add(new Questao(4, "Dos trechos de códigos abaixo, extraídos de um arquivo fonte escrito para a versão 8 da linguagem Java, o único que compila corretamente é:", opcoes4));

        opcoes5.add(new QuestaoOpcao(1, 1, 1, "A) List.", false));
        opcoes5.add(new QuestaoOpcao(1, 1, 2, "B) Set.", false));
        opcoes5.add(new QuestaoOpcao(1, 1, 3, "C) ArrayList.", false));
        opcoes5.add(new QuestaoOpcao(1, 1, 4, "D) HashMap.", true));
        questoes.add(new Questao(4, "No Java, é uma interface que não permite elementos duplicados e modela a abstração matemática de conjunto. Contém apenas métodos herdados da interface Collection e adiciona a restrição de que elementos duplicados são proibidos. A interface citada é:", opcoes5));

        return questoes;
    }

    private List<Questao> getRedesQuestoes() {
        List<Questao> questoes = new ArrayList<Questao>();
        List<QuestaoOpcao> opcoes = new ArrayList<QuestaoOpcao>();
        List<QuestaoOpcao> opcoes2 = new ArrayList<QuestaoOpcao>();
        List<QuestaoOpcao> opcoes3 = new ArrayList<QuestaoOpcao>();
        List<QuestaoOpcao> opcoes4 = new ArrayList<QuestaoOpcao>();
        List<QuestaoOpcao> opcoes5 = new ArrayList<QuestaoOpcao>();

        opcoes.add(new QuestaoOpcao(1, 1, 1,"A) endereço de FTP válido para esse domínio.", false));
        opcoes.add(new QuestaoOpcao( 1, 1, 2, "B) endereço MAC de rede registrado na máquina cliente.", false));
        opcoes.add(new QuestaoOpcao(1, 1, 3, "C) porta válida para a intranet desse domínio..", false));
        opcoes.add(new QuestaoOpcao(1, 1, 4, "D) conta cadastrada e autorizada nesse domínio.", true));
        questoes.add(new Questao(1, "Para conectar sua estação de trabalho a uma rede local de computadores controlada por um servidor de domínios, o usuário dessa rede deve informar uma senha e um[a]:", opcoes));


        opcoes2.add(new QuestaoOpcao(1, 1, 1, "A) 7 camadas.", true));
        opcoes2.add(new QuestaoOpcao(1, 1, 2, "B) 3 camadas.", false));
        opcoes2.add(new QuestaoOpcao(1, 1, 3, "C) 12 camadas.", false));
        opcoes2.add(new QuestaoOpcao(1, 1, 4, "D) 6 camadas.", false));
        questoes.add(new Questao(2, "Em quantas camadas se divide o modelo de referência OSI?", opcoes2));

        opcoes3.add(new QuestaoOpcao(1, 1, 1, "A) Enquadramento e Empacotamento", true));
        opcoes3.add(new QuestaoOpcao(1, 1, 2, "B) Encaminhamento e roteamento", false));
        opcoes3.add(new QuestaoOpcao(1, 1, 3, "C) Segmentação e empacotamento", false));
        opcoes3.add(new QuestaoOpcao(1, 1, 4, "D) Roteamento e enquadramento", false));
        questoes.add(new Questao(3, "Quais são as principais funções da camada de rede?", opcoes3));

        opcoes4.add(new QuestaoOpcao(1, 1, 1, "A) Apresentação", false));
        opcoes4.add(new QuestaoOpcao(1, 1, 2, "B) Sessão", false));
        opcoes4.add(new QuestaoOpcao(1, 1, 3, "C) Enlace", true));
        opcoes4.add(new QuestaoOpcao(1, 1, 4, "D) Física", false));
        questoes.add(new Questao(4, "Qual camada do Modelo OSI é responsável pela correção de erros e fluxo de dados de modo básico?", opcoes4));

        opcoes5.add(new QuestaoOpcao(1, 1, 1, "A) IP E TCP.", false));
        opcoes5.add(new QuestaoOpcao(1, 1, 2, "B) TCP E UDP.", true));
        opcoes5.add(new QuestaoOpcao(1, 1, 3, "C) HTTP E SMTP.", false));
        opcoes5.add(new QuestaoOpcao(1, 1, 4, "D) UDP E POP.", false));
        questoes.add(new Questao(4, "Quais são os principais protocolos da camada de Transporte?", opcoes5));

        return questoes;
    }

    private void insertQuestoes(long categoriaId, List<Questao> questoes, SQLiteDatabase database) {
        for (int index = 0; index < questoes.size(); index++) {
            ContentValues questaoValues = new ContentValues();
            Questao questaoAtual = questoes.get(index);

            long questaoId = this.insertQuestao(questaoAtual.nome, categoriaId, database);

            for (int opcaoIndex = 0; opcaoIndex < questaoAtual.opcoes.size(); opcaoIndex++) {
                QuestaoOpcao questaoOpcaoAtual = questaoAtual.opcoes.get(opcaoIndex);

                long opcaoId = this.insertOpcao(questaoOpcaoAtual.nome, database);
                this.insertQuestaoOpcao(questaoOpcaoAtual, questaoId, opcaoId, database);
            }
        }
    }


    private long insertCategoria(String nome, SQLiteDatabase database) {
        ContentValues values = new ContentValues();

        values.put(CATEGORIA_NOME, nome);
        long id = database.insert(TABLE_CATEGORIA, null, values);

        return id;
    }

    private long insertQuestao(String nome, long categoriaId, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(QUESTAO_NOME, nome);
        values.put(QUESTAO_CATEGORIA_ID, categoriaId);
        long id = database.insert(TABLE_QUESTAO, null, values);

        return id;
    }

    private long insertOpcao(String nome, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(OPCAO_NOME, nome);
        long id = database.insert(TABLE_OPCAO, null, values);

        return id;
    }

    private void insertQuestaoOpcao(QuestaoOpcao questaoOpcao, long questaoId, long opcaoId, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(QUESTAO_OPCAO_QUESTAO_ID, questaoId);
        values.put(QUESTAO_OPCAO_OPCAO_ID, opcaoId);
        values.put(QUESTAO_OPCAO_CORRETA, questaoOpcao.isCorreta ? 1 : 0);
        values.put(QUESTAO_OPCAO_NUMERO, questaoOpcao.numero);
        database.insert(TABLE_QUESTAO_OPCAO, null, values);
    }

    public List<Categoria> getCategorias() {
        String query  = "SELECT * FROM " + TABLE_CATEGORIA;
        SQLiteDatabase database  = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        List<Categoria> categorias = new ArrayList<Categoria>();

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(CATEGORIA_ID));
                String nome = cursor.getString(cursor.getColumnIndex(CATEGORIA_NOME));
                categorias.add(new Categoria(id, nome));
            } while (cursor.moveToNext());
        }

        return categorias;
    }

    public List<Questao> getQuestoes(long categoriaId) {
        String query = "SELECT * FROM " + TABLE_QUESTAO +
                " WHERE " + QUESTAO_CATEGORIA_ID + " = " + categoriaId;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        List<Questao> questoes = new ArrayList<Questao>();

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(QUESTAO_ID));
                String nome = cursor.getString(cursor.getColumnIndex(QUESTAO_NOME));
                List<QuestaoOpcao> questoesOpcoes = this.getQuestaoOpcoes(id);

                Questao questao = new Questao(id, nome, questoesOpcoes);
                questoes.add(questao);
            } while (cursor.moveToNext());
        }

        return questoes;
    }

    public Opcao getOpcao(long id) {
        String query = "SELECT * FROM " + TABLE_OPCAO +
                " WHERE " + OPCAO_ID + " = " + id;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        String nome = cursor.getString(cursor.getColumnIndex(OPCAO_NOME));
        Opcao opcao = new Opcao(id, nome);

        return opcao;
    }

    public List<QuestaoOpcao> getQuestaoOpcoes(long questaoId) {
        String query = "SELECT * FROM " + TABLE_QUESTAO_OPCAO +
                " WHERE " +  QUESTAO_OPCAO_QUESTAO_ID + " = " + questaoId;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        List<QuestaoOpcao> questaoOpcoes = new ArrayList<QuestaoOpcao>();

        if (cursor.moveToFirst()) {
            do {
                long opcaoId = cursor.getLong(cursor.getColumnIndex(QUESTAO_OPCAO_OPCAO_ID));
                boolean isCorreta = cursor.getInt(cursor.getColumnIndex(QUESTAO_OPCAO_CORRETA)) == 1 ? true : false;
                int numero = cursor.getInt(cursor.getColumnIndex(QUESTAO_OPCAO_NUMERO));
                String nome = this.getOpcao(opcaoId).nome;

                QuestaoOpcao questaoOpcao = new QuestaoOpcao(questaoId, opcaoId, numero, nome, isCorreta);
                questaoOpcoes.add(questaoOpcao);
            } while (cursor.moveToNext());
        }

        return questaoOpcoes;
    }

    public Categoria getCategoriaByNome(String nome) {
        String selectQuery  = "SELECT * FROM " + TABLE_CATEGORIA +
                " WHERE " + CATEGORIA_NOME + " = " + nome;
        SQLiteDatabase database  = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        int id = cursor.getInt(cursor.getColumnIndex(CATEGORIA_ID));
        Categoria categoria = new Categoria(id, nome);


        return categoria;
    }
}
