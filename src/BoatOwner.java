import java.io.Serializable;
import java.lang.Override;

public class BoatOwner extends Person implements Serializable{

    // assign member number
    private static int NEXT_MEMBER_NR = 100;
    private int memberNumber;

    // the owners boat, default is null until a boat is assigned
    private BoatList boats = null;

    public BoatOwner(String firstName, String lastName, String address) {
        super(firstName, lastName, address);
        boats = new BoatList();
        memberNumber = NEXT_MEMBER_NR++;
    }

    // get method for member number
    public int getMemberNumber() {
        return memberNumber;
    }

    public BoatList getBoats() {
        return boats;
    }

    // set-method for assigning boat to owner
    public void setBoat( Boat boat ) {
    	boats.add(boat);
    }

    // BoatOwners toString
    public String toString() {
        String output = super.toString() + "\nMember nr: " + memberNumber + "\n";
        return output;
    }
    
    public String printBoats() {
    	String output= "";
        if (boats.first == null){
       	 output += "\n\nNo boats owned!\n";
        }
        else {
       	 output += boats.toString();
        }
        return output;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        BoatOwner boatOwner = (BoatOwner) o;

        if (memberNumber != boatOwner.memberNumber) return false;
        if (boats != null ? !boats.equals(boatOwner.boats) : boatOwner.boats != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + memberNumber;
        result = 31 * result + (boats != null ? boats.hashCode() : 0);
        return result;
    }

}