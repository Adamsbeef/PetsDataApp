
package com.example.android.pets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import data.PetContractClass;
import data.PetsDbHelper;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    private PetsDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        mDbHelper = new PetsDbHelper(this);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        displayDatabaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projections = {
                PetContractClass.PetsEntry._ID,
                PetContractClass.PetsEntry.COLUMN_PET_NAME,
                PetContractClass.PetsEntry.COLUMN_PET_BREED,
                PetContractClass.PetsEntry.COLUMN_PET_GENDER,
                PetContractClass.PetsEntry.COLUMN_PET_WEIGHT};

        // to get a Cursor that contains all rows from the pets table.
        Cursor cursor = db.query(PetContractClass.PetsEntry.TABLE_NAME,
                projections,
                null,
                null,
                null,
                null,
                null);

        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).

            //get the index of the columns defined here.
            int idColumnIndex = cursor.getColumnIndex(PetContractClass.PetsEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(PetContractClass.PetsEntry.COLUMN_PET_NAME);
            int breedColumnIndex = cursor.getColumnIndex(PetContractClass.PetsEntry.COLUMN_PET_BREED);
            int genderColumnIndex = cursor.getColumnIndex(PetContractClass.PetsEntry.COLUMN_PET_GENDER);
            int weightColumnIndex = cursor.getColumnIndex(PetContractClass.PetsEntry.COLUMN_PET_WEIGHT);

            TextView displayView = (TextView) findViewById(R.id.text_view_pet);

            displayView.append(PetContractClass.PetsEntry._ID + " | "
                    + PetContractClass.PetsEntry.COLUMN_PET_NAME + " | "
                    + PetContractClass.PetsEntry.COLUMN_PET_BREED + " | "
                    + PetContractClass.PetsEntry.COLUMN_PET_GENDER + " | "
                    + PetContractClass.PetsEntry.COLUMN_PET_WEIGHT);



            while(cursor.moveToNext()){

                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentBreed = cursor.getString(breedColumnIndex);
                int currentGender = cursor.getInt(genderColumnIndex);
                int currentWeight = cursor.getInt(weightColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append((
                        "\n"
                                + currentID + " | "
                                + currentName + " | "
                                + currentBreed + " | "
                                + currentGender + " | "
                                + currentWeight
                ));
            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //write to database.
    private void insertPet() {
        //get a data base object.
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        //create a variable with which to hold the fields we are  passing in
        ContentValues value = new ContentValues();

        value.put(PetContractClass.PetsEntry.COLUMN_PET_NAME,"Toto");
        value.put(PetContractClass.PetsEntry.COLUMN_PET_WEIGHT,7);
        value.put(PetContractClass.PetsEntry.COLUMN_PET_GENDER,PetContractClass.PetsEntry.GENDER_MALE);
        value.put(PetContractClass.PetsEntry.COLUMN_PET_BREED,"Terrier");


        final long rowId = db.insert(PetContractClass.PetsEntry.TABLE_NAME, null, value);

        if (rowId >= 1){
            Toast.makeText(this,"Added Successfully ",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"unsuccessful ",Toast.LENGTH_LONG).show();
        }

    }
}