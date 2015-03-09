import java.io.Serializable;

public class BoatList implements Serializable{

	public Node first = null;

	public boolean isEmpty(){
		if (first == null){
			return true;
		}
		else {
			return false;
		}
	}
    public boolean add( Boat data ) {
        if( first == null ) {
            first = new Node(data);
            return true;
        }
        else {
            Node temp = new Node(data);
            Node current = first;
            while (current.getNext() != null) current = current.getNext();
            current.setNext(temp);
            return true;
        }

    }
	
	//finds a specific node
	public Node getNode(String regnr) {
		Node temp = first;
		while (temp.getNext() != null){
		if(temp.getData().getRegistrationNumber() == regnr){
			return temp;	
		}
		temp = temp.getNext();
		}
		return null;
	}
	
	//removes a specific node
    public boolean Remove(Boat data){
        if( first == null ) return false;

        if( first.getData().equals(data) ) {
            first = first.next;
            return true;
        }
        else {
            Node current = first;
            Node temp = null;
            boolean success = false;

            while (current != null) {
                if (current.getData().equals(data)) {

                    if (current == first) {
                        first = first.getNext();
                    } else {
                        temp.next = current.getNext();
                    }
                    success = true;
                } else {
                    temp = current;
                }
                current = current.getNext();
            }
            return success;
        }
    }
	
	//returns a specific boat
	public Boat getBoat(String regnr){
		Node temp = first;
		while (temp != null){
		if (temp.getData().getRegistrationNumber().equals(regnr)){
			return temp.getData();
		}
		temp = temp.getNext();
	}
		return null;
	}
	
	//prints out the entire list of boats
	public String toString() {
		Node temp = first;
		String output;
        if( temp == null ){
        	output = "No boats";
        	return output;
        }
        output = "\n\nBoat(s):";
		while (temp != null){
			output += temp.getData().toString() + "\n";
			temp = temp.getNext();
		}

		return output;
		
	}
	private class Node  {
		public Node next;
		Boat data;
		
		public Node (Boat data) {
			next = null;
			this.data = data;
		}

		// Sets the current node's data
		public void setData( Boat data) {
			this.data = data;
		}
		
		// Gets the node's content
		public Boat getData() {
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
		public String toString(Boat data) {
			return data.toString();
			
		}
	}
}