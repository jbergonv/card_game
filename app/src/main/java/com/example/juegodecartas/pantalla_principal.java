package com.example.juegodecartas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Arrays;

public class pantalla_principal extends AppCompatActivity {

    static TextView totalCartas;
    static LinearLayout layout;
    static ImageButton[] arrayBotonesMas;
    static ImageButton[] arrayBotonesMenos;
    static TextView[] arrayCuentaCartas;
    static TextView[] arrayEtiquetas;
    static int cuentaCartasTotales,total,totalCartasAux;
    static Button reset,salir;
    static SeekBar barra;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        layout = findViewById(R.id.layout2);
        reset = findViewById(R.id.button5);
        salir = findViewById(R.id.button6);
        barra = findViewById(R.id.seekBar6);
        barra.setProgress(20);

        Intent intentoDeFuera = getIntent();
        Bundle extras = intentoDeFuera.getExtras();

        int cuentaJugadores;

        totalCartas = findViewById(R.id.textoCartasTotal);

        String numeroCartasFuera = extras.getString("cartas");
        String numeroJugadoresFuera = extras.getString("jugadores");


        cuentaJugadores = Integer.parseInt(numeroJugadoresFuera);
        total=cuentaJugadores;
        cuentaCartasTotales = Integer.parseInt(numeroCartasFuera);
        totalCartasAux=cuentaCartasTotales;

        Log.i("cartas v2",numeroCartasFuera);
        Log.i("jugadores v2",numeroJugadoresFuera);
        totalCartas.setText(totalCartas.getText()+" "+cuentaCartasTotales);


        crearBotones(cuentaJugadores,layout);

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pantalla_principal.this.finish();

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reiniciarTodo(arrayCuentaCartas);

            }
        });


        barra.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                cambiarTamañoTexto(arrayCuentaCartas,barra,arrayEtiquetas);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }

    public void crearBotones(int n, LinearLayout linear){

        arrayBotonesMas = new ImageButton[n];
        arrayBotonesMenos = new ImageButton[n];
        arrayCuentaCartas = new TextView[n];
        arrayEtiquetas = new TextView[n];


        for(int i=0;i<n;i++){


            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout layout2 = new LinearLayout(this);
            layout2.setLayoutParams(params1);
            layout2.setPadding(0,0,0,20);
            layout2.setOrientation(LinearLayout.HORIZONTAL);

            //Etiquetas Jugador X

            TextView tx = new TextView(this);
            tx.setId(View.generateViewId());
            arrayEtiquetas[i]=tx;
            String auxCad = getResources().getString(R.string.jugador);
            auxCad=auxCad+Integer.toString(i+1);
            tx.setText(auxCad);
            tx.setTextSize(20f);
            tx.setPadding(0,0,60,0);
            tx.setLayoutParams(params1);
            layout2.addView(tx);


            //Botones de sumar cartas

            ImageButton b = new ImageButton(this);
            arrayBotonesMas[i]=b;
            b.setId(View.generateViewId());
            b.setImageResource(R.drawable.flecha_abajo);
            b.setLayoutParams(params1);
            layout2.addView(b);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(comprobarCartasDisponibles(cuentaCartasTotales,arrayCuentaCartas)){

                        int aux = Arrays.asList(arrayBotonesMas).indexOf(b);

                        int aux2 = Integer.parseInt(arrayCuentaCartas[aux].getText().toString());

                        aux2= aux2+1;

                        arrayCuentaCartas[aux].setText(Integer.toString(aux2));

                        cuentaCartasTotales--;

                        totalCartas.setText("Cartas totales: "+Integer.toString(cuentaCartasTotales));

                    }
                }
            });



            //Texview Cuenta de cartas

            TextView t = new TextView(this);
            arrayCuentaCartas[i]=t;
            arrayCuentaCartas[i].setText("0");
            t.setId(View.generateViewId());
            t.setTextSize(20f);
            t.setLayoutParams(params1);
            t.setPadding(20,0,20,0);
            layout2.addView(t);


            //Botones de restar cartas

            ImageButton c = new ImageButton(this);
            arrayBotonesMenos[i]=c;
            c.setId(View.generateViewId());
            c.setImageResource(R.drawable.flecha_arriba);
            c.setLayoutParams(params1);
            layout2.addView(c);

            c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(cuentaCartasTotales!=totalCartasAux){

                        if(comprobarCartasDisponibles(cuentaCartasTotales,arrayCuentaCartas)){

                            int aux3 = Arrays.asList(arrayBotonesMas).indexOf(b);

                            int aux4 = Integer.parseInt(arrayCuentaCartas[aux3].getText().toString());

                            if(aux4!=0){

                                aux4= aux4-1;

                                arrayCuentaCartas[aux3].setText(Integer.toString(aux4));

                                cuentaCartasTotales++;

                                totalCartas.setText("Cartas totales: "+Integer.toString(cuentaCartasTotales));

                            }

                        }

                    }

                }
            });

            linear.addView(layout2);

        }

    }

    public Boolean comprobarCartasDisponibles(int n, TextView[] v){

        int aux=0;
        int pos=0;

        if(n>0){
            return true;
        }
        else{

            for(int i=0;i<total;i++){

                if(Integer.parseInt(v[i].getText().toString())>aux){

                    aux=Integer.parseInt(v[i].getText().toString());
                    pos=i+1;

                }


            }


            String cadenaAux = getResources().getString(R.string.ganador_juego);

            cadenaAux = cadenaAux + Integer.toString(pos);


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.fin_juego);
            builder.setMessage(cadenaAux);
            builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    pantalla_principal.this.finish();

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();


        }

        return false;

    }

    public void reiniciarTodo(TextView[] v){

        for(int i=0;i<total;i++){

                v[i].setText("0");


            }


        totalCartas.setText("Cartas totales: "+Integer.toString(totalCartasAux));

        cuentaCartasTotales = totalCartasAux;

    }

    public void cambiarTamañoTexto(TextView[] v, SeekBar barra,TextView[] eti){

        int aux = barra.getProgress();

        for(int i=0;i<total;i++){

            v[i].setTextSize(aux);
            eti[i].setTextSize(aux);

        }

      totalCartas.setTextSize(aux);

    }

}