import java.io.Serializable;
import java.lang.Override;

/**
 * Created by root on 27.02.15.
 */
public class Boat implements Serializable{
    // information about the boat
    private String registrationNumber;
    private String brand;
    private int model; // year
    private int length; // feet
    private int horsePower;
    private String color;

    public Boat( String registrationNumber, String brand, int model, int length, int horsePower, String color) {
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.model = model;
        this.length = length;
        this.horsePower = horsePower;
        this.color = color;
    }

    // function that return registration number
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    // function that return model number
    public int getModel() {
        return model;
    }

    // Boat's toString function
    public String toString() {
        return "\nRegistration number: " + registrationNumber
                + "\nBrand: " + brand
                + "\nModell: " + model
                + "\nLength(feet): " + length
                + "\nHorse Power: " + horsePower
                + "\nColor: " + color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Boat boat = (Boat) o;

        if (horsePower != boat.horsePower) return false;
        if (length != boat.length) return false;
        if (model != boat.model) return false;
        if (brand != null ? !brand.equals(boat.brand) : boat.brand != null) return false;
        if (color != null ? !color.equals(boat.color) : boat.color != null) return false;
        if (registrationNumber != null ? !registrationNumber.equals(boat.registrationNumber) : boat.registrationNumber != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = registrationNumber != null ? registrationNumber.hashCode() : 0;
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + model;
        result = 31 * result + length;
        result = 31 * result + horsePower;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }
}