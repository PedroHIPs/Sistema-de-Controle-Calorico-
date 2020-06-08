package com.example.trab3bimandroidstudio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Relatorio extends AppCompatActivity {

    private Spinner spin;
    private EditText edtYear;
    private ArrayList<String> ListSM;
    private ArrayList<String> ListRes;
    private ArrayList<String> ListC;
    private ArrayList<Integer> ListDias;
    private ListView lstRela;
    private double x1, x2, y1 ,y2, total;
    private int mes, ano;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);

        spin = (Spinner) findViewById(R.id.spMeses);
        edtYear = (EditText) findViewById(R.id.edtYear);
        lstRela = (ListView) findViewById(R.id.lstRelatorio);
        ListSM = new ArrayList<String>();
        ListRes = new ArrayList<>();
        ListC = new ArrayList<>();
        ListDias = new ArrayList<Integer>();

        ListSM.add("Janeiro");
        ListSM.add("Fevereiro");
        ListSM.add("Março");
        ListSM.add("Abril");
        ListSM.add("Maio");
        ListSM.add("Junho");
        ListSM.add("Junlho");
        ListSM.add("Agosto");
        ListSM.add("Setembro");
        ListSM.add("Outubro");
        ListSM.add("Novembro");
        ListSM.add("Dezembro");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ListSM);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(dataAdapter);

        ArrayAdapter<String> dataAdapterLr = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, ListRes);
        lstRela.setAdapter(dataAdapterLr);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int pos;
                try{
                    pos = i;
                    mes = pos + 1;
                    Toast.makeText(getBaseContext(),ListSM.get(pos) + ", mês: " + mes,Toast.LENGTH_LONG).show();
                }catch (Exception ex){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        lstRela.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int pos, dia;
                double total = 0;
                String r = "";
                ConsumoDAO dao;
                Cursor tabela;
                pos = i;
                dia = ListDias.get(pos);
                try{
                    dao = new ConsumoDAO();
                    tabela=dao.listarDiaMesAno(getBaseContext(), dia, mes, Integer.parseInt(edtYear.getText().toString().trim()));
                    if (tabela != null){
                        while (tabela.moveToNext()){
                            r+="Produto: " + tabela.getString(5) + "     " + tabela.getInt(2) + "/" + tabela.getInt(3) + "/" + tabela.getInt(4) + "     " + tabela.getInt(0) + ":" + tabela.getInt(1) + "     Quantidade:" +tabela.getDouble(6) + "     Calorias:" +tabela.getDouble(7) +"\n \n";
                            total += tabela.getDouble(7);
                        }
                        r += "\n" + "Total: " + String.valueOf(total);
                        MessageBox(r,"Consumiu: ");
                    }
                }catch (Exception ex){
                    MessageBox(ex.getMessage(),"Erro: ");
                }
            }
        });
    }

    public void MessageBox(String texto, String titulo){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle(titulo);
        dialogo.setMessage(texto);
        dialogo.setNeutralButton("OK", null);
        dialogo.show();
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1=ev.getX();
                y1=ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2=ev.getX();
                y2=ev.getY();
                if(x1 - x2<-500){
                    Intent inte = new Intent(Relatorio.this,Menu.class);
                    finish();
                    startActivity(inte);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void listar(View v){
        ConsumoDAO dao;
        Cursor tabela;
        try {
            dao = new ConsumoDAO();
            tabela=dao.listarMesAno(getBaseContext(), mes, Integer.parseInt(edtYear.getText().toString().trim()));
            if(tabela!=null) {
                ListC.clear();
                ListDias.clear();
                while(tabela.moveToNext()) {
                    ListC.add("Dia:" + tabela.getString(0) + "/" + tabela.getString(1) + "/" + tabela.getString(2) + "     Calorias: " + tabela.getString(3));
                    ListDias.add(tabela.getInt(0));
                }
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,ListC);
                lstRela.setAdapter(adapter);
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Erro ao listar: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
