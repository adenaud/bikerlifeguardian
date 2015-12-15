package com.bikerlifeguardian.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.bikerlifeguardian.R;
import com.bikerlifeguardian.adapter.BloodGroupArrayAdapter;
import com.bikerlifeguardian.dao.UserDataDao;
import com.bikerlifeguardian.event.OnPromptDialogCloseListener;
import com.bikerlifeguardian.model.UserData;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;

public class FormActivity extends RoboActionBarActivity implements View.OnClickListener {

    private UserData userData;

    @Inject
    private UserDataDao userDataDao;

    @InjectView(R.id.text_firstname)
    private EditText textFirstname;

    @InjectView(R.id.text_lastname)
    private EditText textLastname;


    @InjectView(R.id.text_email)
    private EditText textEmail;

    @InjectView(R.id.text_password)
    private EditText textPassword;


    @InjectView(R.id.text_phone)
    private EditText textPhone;

    @InjectView(R.id.text_allergies)
    private EditText textAllergies;

    @InjectView(R.id.text_medicines)
    private EditText textMedicines;

    @InjectView(R.id.text_languages)
    private EditText textLanguages;

    @InjectView(R.id.text_rep_legal_firstname)
    private EditText textRepLegalFirstname;

    @InjectView(R.id.text_rep_legal_lastname)
    private EditText textRepLegalLastName;

    @InjectView(R.id.text_rep_legal_phone)
    private EditText textRepLegalPhone;

    @InjectView(R.id.text_comments)
    private EditText textComment;

    @InjectView(R.id.btn_save)
    private Button btnSave;

    @InjectView(R.id.radioFemale)
    private RadioButton radioFemale;

    @InjectView(R.id.radioMale)
    private RadioButton radioMale;

    @InjectView(R.id.buttonAddLanguage)
    private ImageButton btnAddLanguage;

    @InjectView(R.id.buttonAddMedicine)
    private ImageButton btnAddMedicine;

    @InjectView(R.id.buttonAddAllergie)
    private ImageButton btnAddAllergie;


    private BloodGroupArrayAdapter bloodGroupArrayAdapter;

    @InjectView(R.id.spinner_blood_group)
    private Spinner spinnerBloodGroup;

    private List<String> languages;
    private List<String> allergies;
    private List<String> medicines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        bloodGroupArrayAdapter = new BloodGroupArrayAdapter(this);
        spinnerBloodGroup.setAdapter(bloodGroupArrayAdapter);

        btnSave.setOnClickListener(this);
        btnAddLanguage.setOnClickListener(this);
        btnAddAllergie.setOnClickListener(this);
        btnAddMedicine.setOnClickListener(this);

        userData = userDataDao.read();

        if (userData != null) {

            if (userData.getGender() == 1) {
                radioMale.setChecked(true);
            } else if (userData.getGender() == 0) {
                radioFemale.setChecked(true);
            }
            textFirstname.setText(userData.getFirstname());
            textLastname.setText(userData.getLastname());
            textPhone.setText(userData.getPhone());
            textLanguages.setText(userData.getLanguages());
            textAllergies.setText(userData.getAllergies());
            textMedicines.setText(userData.getMedicines());
            textRepLegalFirstname.setText(userData.getRepLegalFirstname());
            textRepLegalLastName.setText(userData.getRepLegalLastname());
            textRepLegalPhone.setText(userData.getRepLegalPhone());
            textComment.setText(userData.getComments());

            medicines = new ArrayList<>();
            medicines.addAll(deserializeLists(userData.getMedicines()));

            allergies = new ArrayList<>();
            allergies.addAll(deserializeLists(userData.getAllergies()));

            languages = new ArrayList<>();
            languages.addAll(deserializeLists(userData.getLanguages()));
        }
    }

    private List<String> deserializeLists(String string) {
        if (string.length() > 0) {
            return Arrays.asList(string.split(","));
        }
        return new ArrayList<>();
    }

    private String seriralizeLists(List<String> stringList) {
        return TextUtils.join(",", stringList);
    }

    private void save() {

        boolean error = false;

        if (userData == null) {
            userData = new UserData();
        }

        if (radioMale.isChecked()) {
            userData.setGender(1);
        } else if (radioFemale.isChecked()) {
            userData.setGender(0);
        } else {
            error = true;
        }

        userData.setFirstname(textFirstname.getText().toString());
        userData.setLastname(textLastname.getText().toString());
        userData.setPhone(textPhone.getText().toString());
        userData.setRepLegalFirstname(textRepLegalFirstname.getText().toString());
        userData.setRepLegalLastname(textRepLegalLastName.getText().toString());
        userData.setRepLegalPhone(textRepLegalPhone.getText().toString());
        userData.setComments(textComment.getText().toString());
        userData.setAllergies(seriralizeLists(allergies));
        userData.setMedicines(seriralizeLists(medicines));
        userData.setLanguages(seriralizeLists(languages));


        if (!error) {
            userDataDao.save(userData);
            setResult(RESULT_OK, null);
            finish();
        }
    }

    @Override
    public void onClick(final View v) {
        if (v == btnSave) {
            save();
        }


        if (v == btnAddLanguage) {
            openDialog("Langue", new OnPromptDialogCloseListener() {
                @Override
                public void onClose(String input) {
                    languages.add(input);
                    textLanguages.setText(seriralizeLists(languages));
                }
            });
        }
        if (v == btnAddMedicine) {
            openDialog("Médicament", new OnPromptDialogCloseListener() {
                @Override
                public void onClose(String input) {
                    medicines.add(input);
                    textMedicines.setText(seriralizeLists(medicines));
                }
            });
        }
        if (v == btnAddAllergie) {
            openDialog("Allergie", new OnPromptDialogCloseListener() {
                @Override
                public void onClose(String input) {
                    allergies.add(input);
                    textAllergies.setText(seriralizeLists(allergies));
                }
            });
        }
    }

    private void openDialog(String title, final OnPromptDialogCloseListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setTitle(title);
        builder.setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onClose(input.getText().toString());
            }
        });
        builder.show();
    }
}
