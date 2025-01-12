package com.fr.gsb_medecine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity { // la classe main activity hérite de AppCompaActivity
    private static final String PREF_NAME = "UserPrefs";// attribut de la class MainActivity
    private static final String KEY_USER_STATUS = "UserStatus";
    private EditText editTextDenomination,editTextFormepharmaceutique,editTextTitulaires, editTextDenominationsubstance;
    private Button btnRechercher,btnDeconnexion,btnquitterapp;
    private Spinner spinnerVoixdadministration;
    private ListView listviewResults;
    private DatabaseHelper dbHelper;

    @Override
    // le constructeur :
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // la vue associé à cette classe java c'est activity_main

        //initialisation des composents UI (User Interface)
        editTextDenomination = findViewById(R.id.edit_text_Denomination_du_medicament); //est rattache au champs de saisi denomination de la liste
        editTextFormepharmaceutique = findViewById(R.id.edit_text_Forme_pharmaceutique);
        editTextTitulaires = findViewById(R.id.edit_text_Titulaires);
        editTextDenominationsubstance = findViewById(R.id.edit_text_Denomination_substance);
        spinnerVoixdadministration = findViewById(R.id.spinner_Voix_d_administration);
        btnRechercher = findViewById(R.id.btn_Rechercher);
        btnDeconnexion = findViewById(R.id.btn_Deconnexion);
        btnquitterapp = findViewById(R.id.btn_Quitter_l_application);
        listviewResults = findViewById(R.id.Listview_Results);
        dbHelper = new DatabaseHelper(this); //initialisation BDD

        // Set up the spinner with Voies_dadministration data // fonction qu'on apl qui permet de remplire les valeurs du spinner qui est la liste deroulante des voies admin
        setupVoiesAdminSpinner();
        // Set up the click listener for the search button
        btnRechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform the search and update the ListView
                performSearch();
                cacherClavier();
            }
        });
        listviewResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Get the selected item
                Medicament selectedMedicament = (Medicament) adapterView.getItemAtPosition(position);
                // Show composition of the selected medicament
                //afficherCompositionMedicament(selectedMedicament);
            }
        }
        );
    }


    //méthode
    private void performSearch() { // une méthode
        String denomination = editTextDenomination.getText().toString().trim();// recup, changer en chaine de caracteres et enlever les espaces
        String formepharmaceutique = editTextFormepharmaceutique.getText().toString().trim();
        String titulaires = editTextTitulaires.getText().toString().trim();
        String denominationsubstance = editTextDenominationsubstance.getText().toString().trim();
        String voixadmin = spinnerVoixdadministration.getSelectedItem().toString(); //recupe l'item delectionne
        List<Medicament> searchResults = dbHelper.searchMedicament(denomination, formepharmaceutique, titulaires, denominationsubstance, voixadmin);
        MedicamentAdapter adapter = new MedicamentAdapter(this, searchResults);
        listviewResults.setAdapter(adapter);
        // Initialize the database helper
    }

    private void setupVoiesAdminSpinner() {
        // Fetch distinct Voies_dadministration data from the database and populate the spinner
        List<String> voiesAdminList = dbHelper.getDistinctVoiesAdmin();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, voiesAdminList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVoixdadministration.setAdapter(spinnerAdapter);
    }


    private boolean isUserAuthenticated() { // une methode
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String Userstatus = preferences.getString(KEY_USER_STATUS,"");
        return "Authentification=ok".equals(Userstatus);
    }
    private void cacherClavier() {
        // Obtenez le gestionnaire de fenetre
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        // Obtenez la vue actuellement focalisée, qui devrait être la vue avec le clavier
        View vueCourante = getCurrentFocus();

        // Vérifiez si la vue est non nulle pour éviter les erreurs
        if (vueCourante != null) {
            // Masquez le clavier
            imm.hideSoftInputFromWindow(vueCourante.getWindowToken(), 0);
        }
    }
}