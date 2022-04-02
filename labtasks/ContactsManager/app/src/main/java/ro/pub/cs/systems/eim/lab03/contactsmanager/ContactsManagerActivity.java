package ro.pub.cs.systems.eim.lab03.contactsmanager;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import android.os.Bundle;

public class ContactsManagerActivity extends AppCompatActivity {

    private AddtionalButtonListener addtionalButtonListener = new AddtionalButtonListener();
    private SaveButtonListener saveButtonListener = new SaveButtonListener();

    public class AddtionalButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            EditText job = (EditText)findViewById(R.id.job);
            job.setVisibility(View.VISIBLE);

            EditText company = (EditText) findViewById(R.id.company);
            company.setVisibility(View.VISIBLE);

            EditText webs = (EditText) findViewById(R.id.website);
            webs.setVisibility(View.VISIBLE);

            EditText mes = (EditText) findViewById(R.id.message);
            mes.setVisibility(View.VISIBLE);
        }
    }

    public class SaveButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
            intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

            String name = ((EditText)findViewById(R.id.name)).getText().toString();
            String email = ((EditText)findViewById(R.id.el_address)).getText().toString();
            String phone = ((EditText)findViewById(R.id.phone)).getText().toString();
            String address = ((EditText)findViewById(R.id.post_address)).getText().toString();
            String jobTitle = ((EditText)findViewById(R.id.job)).getText().toString();
            String company = ((EditText)findViewById(R.id.company)).getText().toString();
            String website = ((EditText)findViewById(R.id.website)).getText().toString();
            String im = ((EditText)findViewById(R.id.message)).getText().toString();

            if (name != null) {
                intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
            }
            if (phone != null) {
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
            }
            if (email != null) {
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
            }
            if (address != null) {
                intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
            }
            if (jobTitle != null) {
                intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
            }
            if (company != null) {
                intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
            }

            ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
            if (website != null) {
                ContentValues websiteRow = new ContentValues();
                websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                contactData.add(websiteRow);
            }
            if (im != null) {
                ContentValues imRow = new ContentValues();
                imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                contactData.add(imRow);
            }
            intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
//            startActivity(intent);

            startActivityForResult(intent, 200);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);
        Button save = (Button)findViewById(R.id.save);
        Button cancel = (Button)findViewById(R.id.cancel);
        Button additional = (Button)findViewById(R.id.additional);

        save.setOnClickListener(saveButtonListener);
        additional.setOnClickListener(addtionalButtonListener);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED, new Intent());

                finish();
            }
        });

        EditText phoneEditText = (EditText) findViewById(R.id.phone);
        Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phone != null) {
                phoneEditText.setText(phone);
            } else {
                Toast.makeText(this, getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            setResult(resultCode, new Intent());
            finish();
        }
    }
}