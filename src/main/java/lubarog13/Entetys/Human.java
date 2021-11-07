package lubarog13.Entetys;

public class Human {
    private int id;
    private String fio;
    private int yearOfBirth;
    private char gender;
    private double rating;

    public Human(int id, String fio, int yearOfBirth, char gender, double rating) {
        this.id = id;
        this.fio = fio;
        this.yearOfBirth = yearOfBirth;
        this.gender = gender;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Human{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", gender=" + gender +
                ", rating=" + rating +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
