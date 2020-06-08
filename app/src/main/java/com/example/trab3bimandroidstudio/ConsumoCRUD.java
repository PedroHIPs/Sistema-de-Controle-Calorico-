package com.example.trab3bimandroidstudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class ConsumoCRUD extends AppCompatActivity {

    private EditText edtCodProduto;
    private EditText edtQTDE;
    private EditText edtRes;
    private EditText edtCod;
    private TextView txtProd;
    private TextView txtCal;
    private double x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumo_crud);

        edtCodProduto = (EditText) findViewById(R.id.edtCodProd);
        edtQTDE = (EditText) findViewById(R.id.edtQTDE);
        edtRes = (EditText) findViewById(R.id.edtRes);
        edtCod = (EditText) findViewById(R.id.edtCod);
        txtProd = (TextView) findViewById(R.id.txtProduto);
        txtCal = (TextView) findViewById(R.id.txtCalorias);

        edtCodProduto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
                public void onFocusChange(View view, boolean b) {
                    if(edtCodProduto.getText().length() >= 1) {
                        int cod = Integer.parseInt(edtCodProduto.getText().toString().trim());
                        ProdutoDAO objDAO;
                        Produto obj;
                        try {
                            objDAO = new ProdutoDAO();
                            obj = (Produto) objDAO.preencher(getBaseContext(), cod);
                            txtProd.setText("Produto: " + obj.getDescr());
                            txtCal.setText("Calorias: " + obj.getCaloria() + "cal");
                        } catch (Exception ex) {
                            edtRes.setText(ex.getMessage());
                        }
                    }
                }
        });
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
                    Intent inte = new Intent(ConsumoCRUD.this,Menu.class);
                    finish();
                    startActivity(inte);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void gravar(View v){
        Consumo obj;
        ConsumoDAO objDAO;
        try{
            obj = new Consumo();
            obj.setCodproduto(edtCodProduto.getText().toString().trim());
            obj.setQtde(edtQTDE.getText().toString());
            objDAO = new ConsumoDAO();
            objDAO.gravar(getBaseContext(), obj);
            Toast.makeText(this, edtCod.getText().toString() + " gravado com sucesso" , Toast.LENGTH_SHORT).show();
            edtQTDE.setText(" ");
            edtCod.setText(" ");
            edtCodProduto.setText(" ");
            edtCodProduto.requestFocus();
            listar(v);
        }catch (Exception ex){
            Toast.makeText(this, "Erro: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void alterar(View v){
        Consumo obj;
        ConsumoDAO objDAO;
        try{
            obj = new Consumo();
            obj.setCodproduto(edtCodProduto.getText().toString().trim());
            obj.setQtde(edtQTDE.getText().toString());
            obj.setCodigo(edtCod.getText().toString().trim());
            objDAO = new ConsumoDAO();
            objDAO.alterar(getBaseContext(), obj);
            Toast.makeText(this, edtCod.getText().toString() + " alterado com sucesso" , Toast.LENGTH_SHORT).show();
            edtQTDE.setText(" ");
            edtCod.setText(" ");
            edtCodProduto.setText(" ");
            edtCodProduto.requestFocus();
            listar(v);
        }catch (Exception ex){
            Toast.makeText(this, "Erro: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void remover(View v){
        Consumo obj;
        ConsumoDAO objDAO;
        try{
            obj = new Consumo();
            obj.setCodigo(edtCod.getText().toString().trim());
            objDAO = new ConsumoDAO();
            objDAO.remover(getBaseContext(), obj);
            Toast.makeText(this, edtCod.getText().toString() + " removido com sucesso" , Toast.LENGTH_SHORT).show();
            edtQTDE.setText(" ");
            edtCod.setText(" ");
            edtCodProduto.setText(" ");
            edtCodProduto.requestFocus();
            listar(v);
        }catch (Exception ex){
            Toast.makeText(this, "Erro: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public  void  listar(View v){
        ConsumoDAO objDAO;
        String res = " ";
        Cursor tabela;
        try {
            objDAO = new ConsumoDAO();
            tabela = objDAO.listar(getBaseContext());
            if (tabela != null){
                while (tabela.moveToNext()){
                    res+=tabela.getInt(0)  + "     " + tabela.getString(1) + "     " + "Produto:" + tabela.getInt(2) + "     " + tabela.getInt(3) + "/" + tabela.getInt(4) + "/" + tabela.getInt(5) + "     " + tabela.getInt(6) + ":" + tabela.getInt(7) + "     " +tabela.getDouble(8) +"\n \n";
                }
                edtRes.setText(res);
            }
        }catch (Exception ex){
            edtRes.setText(ex.getMessage());
        }
    }
}
