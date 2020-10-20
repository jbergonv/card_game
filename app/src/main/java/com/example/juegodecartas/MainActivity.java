package com.example.juegodecartas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText numeroJugadores;
    EditText numeroCartas;
    Button botonJugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numeroCartas = findViewById(R.id.numeroCartas);
        numeroJugadores = findViewById(R.id.numeroJugadores);
        botonJugar = findViewById(R.id.botonJugar);

        botonJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intento = new Intent(MainActivity.this,pantalla_principal.class);

                if(comprobarCampos(numeroJugadores,numeroCartas)){

                    intento.putExtra("cartas",numeroCartas.getText().toString());
                    intento.putExtra("jugadores",numeroJugadores.getText().toString());

                    int numCartas = Integer.parseInt(numeroCartas.getText().toString());
                    int numJugadores = Integer.parseInt(numeroJugadores.getText().toString());

                    if(comprobar(numJugadores,numCartas)){

                        startActivity(intento);

                    }


                }



            }
        });

    }

    public Boolean comprobar(int jugadores, int cartas){





        if(jugadores>20){

            Toast.makeText(MainActivity.this, R.string.maximo, Toast.LENGTH_SHORT).show();
            numeroJugadores.setTextColor(Color.RED);


        }
        else{

            if(jugadores>cartas){

                Toast.makeText(MainActivity.this, R.string.cartas, Toast.LENGTH_SHORT).show();
                numeroCartas.setTextColor(Color.RED);

            }
            else{
                return true;
            }

        }

        return false;

    }

    public Boolean comprobarCampos(EditText jugadores, EditText cartas){

        if(TextUtils.isEmpty(jugadores.getText().toString())||TextUtils.isEmpty((cartas.getText().toString()))){

            jugadores.setHintTextColor(Color.RED);
            cartas.setHintTextColor(Color.RED);
            return false;

        }
        else{
            return true;
        }

    }


}