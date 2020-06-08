package com.example.trab3bimandroidstudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText edtDescr;
    private EditText edtCaloria;
    private EditText edtUnidade;
    private EditText edtCod;
    private EditText edtRes;
    private double x1, x2;
    private double y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtDescr = (EditText) findViewById(R.id.edtDescr);
        edtCaloria = (EditText) findViewById(R.id.edtCodProd);
        edtUnidade = (EditText) findViewById(R.id.edtQTDE);
        edtCod = (EditText) findViewById(R.id.edtCod);
        edtRes = (EditText) findViewById(R.id.edtRes);
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
                if(y1 - y2>50){
                    Intent inte = new Intent(MainActivity.this,Menu.class);
                    finish();
                    startActivity(inte);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void gravar(View v){
        Produto obj;
        ProdutoDAO objDAO;
        try{
            obj = new Produto();
            obj.setDescr(edtDescr.getText().toString());
            obj.setUnidade(edtUnidade.getText().toString());
            obj.setCaloria(edtCaloria.getText().toString());
            objDAO = new ProdutoDAO();
            objDAO.gravar(getBaseContext(), obj);
            Toast.makeText(this, edtDescr.getText().toString() + " gravado com sucesso" , Toast.LENGTH_SHORT).show();
            edtDescr.setText(" ");
            edtCaloria.setText(" ");
            edtUnidade.setText(" ");
            edtCod.setText(" ");
            edtDescr.requestFocus();
            listar(v);
        }catch (Exception ex){
            Toast.makeText(this, "Erro: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void alterar(View v){
        Produto obj;
        ProdutoDAO objDAO;
        try{
            obj = new Produto();
            obj.setDescr(edtDescr.getText().toString());
            obj.setCaloria(edtCaloria.getText().toString());
            obj.setUnidade(edtUnidade.getText().toString());
            obj.setCodigo(edtCod.getText().toString().trim());
            objDAO = new ProdutoDAO();
            objDAO.alterar(getBaseContext(), obj);
            Toast.makeText(this, edtDescr.getText().toString() + " alterado com sucesso" , Toast.LENGTH_SHORT).show();
            edtDescr.setText(" ");
            edtCaloria.setText(" ");
            edtUnidade.setText(" ");
            edtCod.setText(" ");
            edtDescr.requestFocus();
            listar(v);
        }catch (Exception ex){
            Toast.makeText(this, "Erro: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void remover(View v){
        Produto obj;
        ProdutoDAO objDAO;
        try{
            obj = new Produto();
            obj.setCodigo(edtCod.getText().toString().trim());
            objDAO = new ProdutoDAO();
            objDAO.remover(getBaseContext(), obj);
            Toast.makeText(this, edtCod.getText().toString() + " removido com sucesso" , Toast.LENGTH_SHORT).show();
            edtDescr.setText(" ");
            edtCaloria.setText(" ");
            edtUnidade.setText(" ");
            edtCod.setText(" ");
            edtDescr.requestFocus();
            listar(v);
        }catch (Exception ex){
            Toast.makeText(this, "Erro: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public  void  listar(View v){
        ProdutoDAO objDAO;
        String res = " ";
        Cursor tabela;
        DecimalFormat df = new DecimalFormat("0.00");
        try {
            objDAO = new ProdutoDAO();
            tabela = objDAO.listar(getBaseContext());
            if (tabela != null){
                while (tabela.moveToNext()){
                    res+=tabela.getInt(0) + "     " + tabela.getString(1) + "     " + tabela.getDouble(2) + "g" + "     " + tabela.getDouble(3) +  "     " + "Kcal" + df.format(tabela.getDouble(4)) + "Kcal/g" + "\n \n";
                }
                edtRes.setText(res);
            }
        }catch (Exception ex){
            edtRes.setText(ex.getMessage());
        }
    }

}
