package com.fr.gsb_medecine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MedicamentAdapter extends ArrayAdapter<Medicament> { // va etre en lien avec medoc et va permettre d'affecter a la vue les resultats elle est lié à la vue Medicament
    public MedicamentAdapter(Context context, List<Medicament> medicaments) {
        super(context, 0, medicaments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Medicament medicament = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_medicament, parent, false);
        }

        TextView tvCodeCIS = convertView.findViewById(R.id.tvCode_CIS);
        TextView tvDenomination = convertView.findViewById(R.id.tvDenomination);
        TextView tvForme_pharmaceutique = convertView.findViewById(R.id.tvForme_pharmaceutique);
        TextView tvVoies_dadministration = convertView.findViewById(R.id.tvVoies_dadministartion);
        TextView tvTitulaires = convertView.findViewById(R.id.tvTitulaires);
        TextView tvStatut_Administratif = convertView.findViewById(R.id.tvStatut_Administratif);
        TextView tvNombre_Molecules = convertView.findViewById(R.id.tvNombre_Molecules);

        tvCodeCIS.setText(String.valueOf(medicament.getCodeCIS()));
        tvDenomination.setText(String.valueOf(medicament.getDenomination()));
        tvForme_pharmaceutique.setText(String.valueOf(medicament.getFormePharmaceutique()));
        tvVoies_dadministration.setText(String.valueOf(medicament.getVoiesAdmin()));
        tvTitulaires.setText(String.valueOf(medicament.getTitulaires()));
        tvStatut_Administratif.setText(String.valueOf(medicament.getStatutAdministratif()));
        tvNombre_Molecules.setText(String.valueOf(medicament.getnbMolecules()));




        return convertView;

    }
}

