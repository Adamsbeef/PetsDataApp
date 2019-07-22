package data;

import android.provider.BaseColumns;

public final class PetContractClass {
    private PetContractClass(){}

    public final class PetsEntry implements BaseColumns {

        public final static String TABLE_NAME = "pets";
        public final static String COLUMN_PET_NAME = "name";
        public final static String COLUMN_PET_WEIGHT = "weight";
        public final static String COLUMN_PET_BREED = "breed";
        public final static String COLUMN_PET_GENDER = "gender";

        /*for the various gender options*/

        public final static int GENDER_UNKNOWN = 0;
        public final static int  GENDER_MALE = 1;
        public final static int GENDER_FEMALE = 2;


    }
}
