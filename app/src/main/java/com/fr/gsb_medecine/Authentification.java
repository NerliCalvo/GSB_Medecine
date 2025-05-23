package com.fr.gsb_medecine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.security.SecureRandom;

public class Authentification extends AppCompatActivity {
//déclaration des attributes en privée
    private EditText codeV;
    private EditText cleS;
    private static final String SECURETOKEN = "BethSepher5";
    String myRandomKey;
    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_USER_STATUS = "UserStatus";

    LinearLayout layoutKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_authentification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        codeV = findViewById(R.id.edit_text_codeVisiteur);
        cleS = findViewById(R.id.edit_text_cleSecrete);
        layoutKey = findViewById(R.id.layoutCle);
        layoutKey.setVisibility(View.INVISIBLE);



    }

    //Ajout des methodes
    public void afficherLayout(View v){ //fonction qui affiche le Layout
        layoutKey.setVisibility(View.VISIBLE);
        myRandomKey = genererChaineAleatoire(5);
        String codeVisiteur = codeV.getText().toString().trim();
        SendKeyTask sendKeyTask = new SendKeyTask(getApplicationContext());
        sendKeyTask.execute(codeVisiteur,myRandomKey,SECURETOKEN);

    }
    private String genererChaineAleatoire(int longueur) {
        String caracteresPermis = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder chaineAleatoire = new StringBuilder();

        SecureRandom random = new SecureRandom();

        for (int i = 0; i < longueur; i++) {
            int index = random.nextInt(caracteresPermis.length());
            char caractereAleatoire = caracteresPermis.charAt(index);
            chaineAleatoire.append(caractereAleatoire);
        }

        return chaineAleatoire.toString();
    }
    public void comparerCle(View v){ //fonction qui compare la clé saisi avec la clé généré
        String CleSecrete = cleS.getText().toString().trim();
        if(CleSecrete.equals(myRandomKey)){
            Toast.makeText(this, "Succès", Toast.LENGTH_LONG).show();
            setUserStatus("authentification=OK");
            Intent authIntent = new Intent(this, MainActivity.class);
            startActivity(authIntent);
        } else {
            Toast.makeText(this, "Echèc", Toast.LENGTH_LONG).show();
            setUserStatus("authentification=KO");
        }
    }



    private void setUserStatus(String status) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_STATUS, status);
        editor.apply();
    }
}