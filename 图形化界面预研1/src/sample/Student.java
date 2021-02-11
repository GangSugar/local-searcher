package sample;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

public class Student {
    public int id;
    public String name;
    public int age;
    public String gender;//性别

    public Student(int id, String name, int age, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public ObservableValue<String> getIdValue() {
        String s = String.valueOf(id);
        return new SimpleStringProperty(s);
    }
    public String getId(){
        return String.valueOf(id);
    }
    public ObservableValue<String> getNameValue() {
        return new SimpleStringProperty(name);
    }
    public String getName(){
        return name;
    }
    public ObservableValue<String> getAgeValue() {
        String s = String.valueOf(age);
        return new SimpleStringProperty(s);
    }
    public String getAge(){
        return String.valueOf(age);
    }
    public ObservableValue<String> getGenderValue() {
        return new SimpleStringProperty(gender);
    }
    public String getGender(){
        return gender;
    }
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}
