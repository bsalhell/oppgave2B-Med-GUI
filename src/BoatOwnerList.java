import java.io.Serializable;

/* Start class List */
public class BoatOwnerList implements Serializable {
	/**
	 * 
	 */
	public Node first;

    public BoatOwnerList() {
        first = null;
    }

// Creates a new node and adds it to a list
	public void add( BoatOwner data ) {
        if( first == null ) {
            first = new Node(data);
        }
        else {
            Node temp = new Node(data);
            Node current = first;
            while (current.getNext() != null) current = current.getNext();
            current.setNext(temp);
        }
	}
	
	//finds specific boat owner
	public BoatOwner findBoatOwner(int memberNumber) {
		Node temp = first;
		while (temp != null) {
			if (temp.getData().getMemberNumber() == memberNumber) {
				return temp.getData();
			}
			temp = temp.getNext();
		}
		return null;
		
	}
	// Finds a boat's owner based on it's registration number
	public BoatOwner findOwnerOfBoat(String regnr) {
		Node temp = first;
		while (temp != null) {
			if (temp.getData().getBoats().getBoat(regnr) != null) {
				return temp.getData();
			}
			temp = temp.getNext();
		}
		return null;
		
	}
	
	// Adds a boat to a boat owner
	public boolean addBoat(int memberNumber, Boat boat){
		BoatOwner temp = findBoatOwner(memberNumber);
		if (temp != null){
			temp.setBoat(boat);
			return true;
		}
		return false;
	}
	
	// Adds a boat and then adds it to an owner
	public boolean addBoat(int memberNumber, String rnr, String b, int m, int l, int HP, String c) {
		BoatOwner temp = findOwnerOfBoat(rnr);
		if (temp != null){
			return false;
		}
			Boat boat = new Boat(rnr, b, m, l, HP, c );
		if (addBoat(memberNumber, boat)) {
		return true;
		}
			return false;
	}

	// Changes ownership of a boat
	public String changeOwner( BoatOwner previous_owner, BoatOwner new_owner, Boat boat){

		if( previous_owner == null || new_owner == null ) return "Owner not found";

		if (boat != null) {
            previous_owner.getBoats().Remove(boat);
            new_owner.getBoats().add(boat);
            return "Ownership of the boat " + boat.getRegistrationNumber() + " has successfully changed. \n";
        }
		else {
			return "Boat not found.";
		}

	}

	// Removes a boat owner from a list.
    public boolean removeBoatOwner(BoatOwner data){
        BoatList list = data.getBoats();
        Node current = first;
        Node temp = first.getNext();

        	while(current.getNext() != null){
        	if (list.isEmpty() == true){
        	if (current.getData().equals(data)){
        	first.setNext(temp);
        	return true;
        }
        	current = current.getNext();
        		temp = temp.getNext();
        	}
        	//also checks if the last node is the user that's going to be deleted.
        	if (current.getNext() == null){
        		if (list.isEmpty() == true){
                	if (current.getData().equals(data)){
                	first.setNext(temp);
                	return true;
                }
        	}
        }
        	}
    return false;	
    }

    
    // print list content
    public String toString() {
        Node current = first;

        String output = "List content: \n";
        while( current != null ) {
            output += current.getData().toString() + "\n";
            current = current.getNext();
        }

        return output;
    }

    
    
    
/* Start class Node */
private class Node implements Serializable{
	public Node next;
	BoatOwner data;
	
	public Node (BoatOwner data) {
		next = null;
		this.data = data;
	}

	// Sets the current node's data
	public void setData( BoatOwner data) {
		this.data = data;
	}
	
	// Gets the node's content
	public BoatOwner getData() {
		return this.data;
	}
	
	// Sets the next node's content
	public void setNext (Node next) {
		this.next = next;
	}
	
	// Gets the next node
	public Node getNext() {
		return this.next;
	}
	public String toString(String regnr) { 
		BoatOwner temp = findOwnerOfBoat(regnr);
		return temp.getBoats().toString();
		
	}
}
}

