package com.bikerlifeguardian.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
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
import com.bikerlifeguardian.event.OnPromptDialogCloseListener;
import com.bikerlifeguardian.model.UserData;
import com.bikerlifeguardian.service.UserService;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;

public class FormActivity extends RoboActionBarActivity implements View.OnClickListener {

    private UserData currentUserData;

    @Inject
    private UserService userService;

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
    private String uuid;

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

        uuid = getIntent().getStringExtra("uuid");

        new ReadUserDataTask().execute((Void) null);
    }

    private void fillForm() {
        if (currentUserData != null) {

            if (currentUserData.getGender() == 1) {
                radioMale.setChecked(true);
            } else if (currentUserData.getGender() == 0) {
                radioFemale.setChecked(true);
            }
            textFirstname.setText(currentUserData.getFirstname());
            textLastname.setText(currentUserData.getLastname());
            textEmail.setText(currentUserData.getEmail());
            textPhone.setText(currentUserData.getPhone());
            textLanguages.setText(currentUserData.getLanguages());
            textAllergies.setText(currentUserData.getAllergies());
            textMedicines.setText(currentUserData.getMedicines());
            textRepLegalFirstname.setText(currentUserData.getRepLegalFirstname());
            textRepLegalLastName.setText(currentUserData.getRepLegalLastname());
            textRepLegalPhone.setText(currentUserData.getRepLegalPhone());
            textComment.setText(currentUserData.getComments());

            medicines = new ArrayList<>();
            medicines.addAll(deserializeLists(currentUserData.getMedicines()));

            allergies = new ArrayList<>();
            allergies.addAll(deserializeLists(currentUserData.getAllergies()));

            languages = new ArrayList<>();
            languages.addAll(deserializeLists(currentUserData.getLanguages()));
        }
    }

    private List<String> deserializeLists(String string) {
        if (string.length() > 0) {
            return Arrays.asList(string.split(","));
        }
        return new ArrayList<>();
    }

    private String serializeLists(List<String> stringList) {
        return TextUtils.join(",", stringList);
    }

    private void save() {

        boolean error = false;

        if (currentUserData == null) {
            currentUserData = new UserData();
        }

        if (radioMale.isChecked()) {
            currentUserData.setGender(1);
        } else if (radioFemale.isChecked()) {
            currentUserData.setGender(0);
        } else {
            error = true;
        }

        if(textPassword.getText().toString().length() > 0){
            currentUserData.setPassword(textPassword.getText().toString());
        }else{
            currentUserData.setPassword(null);
        }

        currentUserData.setFirstname(textFirstname.getText().toString());
        currentUserData.setLastname(textLastname.getText().toString());
        currentUserData.setPhone(textPhone.getText().toString());
        currentUserData.setRepLegalFirstname(textRepLegalFirstname.getText().toString());
        currentUserData.setRepLegalLastname(textRepLegalLastName.getText().toString());
        currentUserData.setRepLegalPhone(textRepLegalPhone.getText().toString());
        currentUserData.setComments(textComment.getText().toString());
        currentUserData.setAllergies(serializeLists(allergies));
        currentUserData.setMedicines(serializeLists(medicines));
        currentUserData.setLanguages(serializeLists(languages));


        if (!error) {
            new SaveUserdataTask().execute((Void) null);
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
                    textLanguages.setText(serializeLists(languages));
                }
            });
        }
        if (v == btnAddMedicine) {
            openDialog("Médicament", new OnPromptDialogCloseListener() {
                @Override
                public void onClose(String input) {
                    medicines.add(input);
                    textMedicines.setText(serializeLists(medicines));
                }
            });
        }
        if (v == btnAddAllergie) {
            openDialog("Allergie", new OnPromptDialogCloseListener() {
                @Override
                public void onClose(String input) {
                    allergies.add(input);
                    textAllergies.setText(serializeLists(allergies));
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

    public class ReadUserDataTask extends AsyncTask<Void, Void, UserData>{

        @Override
        protected UserData doInBackground(Void... params) {
            return userService.getProfile(uuid);
        }

        @Override
        protected void onPostExecute(final UserData userData) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    currentUserData = userData;
                    fillForm();
                }
            });
        }
    }

    public class SaveUserdataTask extends AsyncTask<Void, Void, Integer>{

        @Override
        protected Integer doInBackground(Void... params) {
            return userService.saveProfile(currentUserData);
        }

        @Override
        protected void onPostExecute(final Integer result) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(result == 0){
                        setResult(RESULT_OK, null);
                        finish();
                    }
                }
            });
        }
    }
}
