package model;

import java.util.ArrayList;
import java.util.List;
import com.example.senoir.newpmatry1.R;


/**
 * Created by my131 on 10/5/2559.
 */
public class PersonModel {

    public String name;
    public String age;
    public int photoId;

    public PersonModel(){
    }
    public PersonModel(String name, String age, int photoId) {
        this.name = name;
        this.age = age;
        this.photoId = photoId;
    }


    public List<PersonModel> persons;

    // This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.
    public void initializeData(){
        persons = new ArrayList<>();
        persons.add(new PersonModel("PASAKORN PROMSUBAN", "5588161", R.drawable.pasakron_pro));
        persons.add(new PersonModel("NAPAPOL WORATHANARAT", "5588283", R.drawable.napapol_pro));
        persons.add(new PersonModel("TERAWAT JANGKRAJANG", "5588062", R.drawable.teerawat_pro));
    }
}