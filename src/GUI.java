import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame implements ActionListener {

	JPanel north, south, findPnl, newBoatPnl, newOwnerPnl, ownerChangePnl;
	JTextArea info;
	JLabel firstNameLbl, lastNameLbl, addressLbl, boatRegNoLbl, ownerIdLbl, ownerIdLbl2, registrationNumberLbl, brandLbl, modelLbl, lengthLbl, horsePowerLbl, colorLbl, oldOwnerIdLbl;
	JTextField findFld, firstNameFld, lastNameFld, addressFld, newOwnerIdFld, ownerIdFld2, boatRegNoFld, registrationNumberFld, brandFld, modelFld, lengthFld, horsePowerFld, colorFld, oldOwnerIdFld;
	JButton findBtn, clearBtn, newOwnerBtn, changeOwnerBtn, newBoatBtn;
	JRadioButton boatBtn, ownerBtn;
	ButtonGroup bg;
	BoatOwnerList thelist = null;

	GUI() {
		super("Båt Registeret!");
		this.setIconImage(new ImageIcon(getClass().getResource("boat.png")).getImage());
		
		////NORTH PANEL
		setLayout(new BorderLayout());

		north = new JPanel();
		north.setLayout(new BorderLayout());

		JTabbedPane tabbedPane = new JTabbedPane();

		setupSearchTab();
		setupNewOwnerTab();  
		setupNewBoatTab();
		setupOwnerChangeTab();

		tabbedPane.addTab("Finn", findPnl);
		tabbedPane.addTab("Ny Båt", newBoatPnl);
		tabbedPane.addTab("Ny Eier", newOwnerPnl);
		tabbedPane.addTab("Eierbytte", ownerChangePnl);

		north.add(tabbedPane);
		add(north);

		////SOUTH PANEL
		south = new JPanel();
		south.setLayout(new BorderLayout());
		info = new JTextArea(25,0);
		info.setForeground(Color.WHITE);
		info.setBackground(Color.BLACK);
		info.setMargin(new Insets(10,10,10,10));
		Font font = new Font("Lucida Console", Font.PLAIN, 12);


		info.setFont(font);
		info.setText("> Hello World!! =)");
		info.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(info); 

		south.add(scrollPane,BorderLayout.CENTER);
		add(south,BorderLayout.SOUTH);


		load();
		pack();
		setSize(550,600);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	public void actionPerformed(ActionEvent a) {
		if (a.getSource() == findBtn) {
			if(ownerBtn.isSelected()) {
				findOwner();
			}
			if(boatBtn.isSelected() ) {
				findOwnerOfBoat();
			}
		}
		if(a.getSource() == newOwnerBtn) {
			newOwner();
		}
		if(a.getSource() == newBoatBtn) {
			newBoat();
		}
		if(a.getSource() == changeOwnerBtn) {
			changeOwner();
		}

	}

	private void changeOwner() {
		int oldId = 0;
		int newId = 0;
		try{
			oldId = Integer.parseInt(oldOwnerIdFld.getText());
			newId = Integer.parseInt(newOwnerIdFld.getText());
		}
		catch (NumberFormatException e) {
			info.setText("ERROR!! 'Eier ID' må være tall");
			return;
		}
		if(thelist.findBoatOwner(oldId)==null || thelist.findBoatOwner(newId)==null) {
			info.setText("Id eksisterer ikke!");
			return;
		}
		if(thelist.findOwnerOfBoat(boatRegNoFld.getText())==null) {
			info.setText("Båt registreringsnummer eksisterer ikke");
			return;
		}
		thelist.changeOwner(thelist.findBoatOwner(oldId), thelist.findBoatOwner(newId), thelist.findBoatOwner(oldId).getBoats().getBoat(boatRegNoFld.getText()));
		info.setText("Owner has been changed.");
		
		oldOwnerIdFld.setText("");
		newOwnerIdFld.setText("");
		boatRegNoFld.setText("");
	}

	public void newBoat() {
		String registrationNumber = registrationNumberFld.getText();
		if(thelist.findOwnerOfBoat(registrationNumber)!=null) {
			info.setText("ERROR!! Boat exists!");
			return;
		}

		int id = 0;
		try{
			id = Integer.parseInt(ownerIdFld2.getText());
		}
		catch (NumberFormatException e) {
			info.setText("ERROR!! 'Eier ID' må være tall");
			return;
		}

		if(thelist.findBoatOwner(id)==null) {
			info.setText("Eier ID eksisterer ikke!");
			return;
		}

		String brand = brandFld.getText();

		String color = colorFld.getText();
		int length=0;
		int horsePower=0;
		int model=0;
		try {
			model = Integer.parseInt(modelFld.getText());
			length = Integer.parseInt(lengthFld.getText());
			horsePower = Integer.parseInt(horsePowerFld.getText());
		}
		catch (NumberFormatException e) {
			info.setText("Feil i input data; model, lengde og hestekrefter skal være tall!");
			return;
		}

		thelist.addBoat(id, registrationNumber, brand, model, length, horsePower, color);
		info.setText("New boat added successfully!");

	}

	public void newOwner() {
		String firstName = firstNameFld.getText();
		String lastName = lastNameFld.getText();
		String address = addressFld.getText();

		if(firstName.length()!=0&&lastName.length()!=0&&address.length()!=0) {
			thelist.add(new BoatOwner(firstName, lastName, address));
			info.setText("New owner has been registered succesfully!");
		}
		else {
			info.setText("Missing information, fill out the fields above");
		}
	}

	public void findOwnerOfBoat() {
		String regnr = findFld.getText();
		BoatOwner found = thelist.findOwnerOfBoat(regnr);
		if(found!=null) {
			info.setText("Båt '"+ regnr +"' er funnet!\nRegistrert på:\n\n"+ found.toString()+"\nDetaljer:"+found.getBoats().getBoat(regnr));
		}
		else {
			info.setText("Søkte etter '" + regnr +"', fant ingenting!");
		}
	}

	public void findOwner() {
		int accountNumber=0;
		try {
			accountNumber = Integer.parseInt(findFld.getText());
		}
		catch (NumberFormatException e) {
			info.setText("Ugyldig data! \nTast inn medlemsnummeret du vil finne.");
		}
		if(accountNumber>0) {
			BoatOwner found = thelist.findBoatOwner(accountNumber);
			if(found!=null){
				info.setText(found.toString()+found.printBoats());
			} else {
				info.setText("Ingen medlem funnet!");
			}	
		}
	}

	public void setupSearchTab() {
		////TAB 1 Config

		findPnl = new JPanel();
		findPnl.setLayout(new GridBagLayout());
		findPnl.setBorder(BorderFactory.createTitledBorder("Finn"));
		GridBagConstraints gc = new GridBagConstraints();

		////SEARCH FIELD CONFIG
		gc.gridwidth=3;
		gc.gridx=0;
		findFld = new JTextField(20);
		findFld.setBorder(new EmptyBorder(5, 5, 5, 5) );

		////FIND BUTTON CONFIG
		findPnl.add(findFld, gc);
		gc.gridwidth=1;
		gc.gridx=4;
		gc.gridy=0;
		findBtn = new JButton("Finn");
		findBtn.addActionListener(this);
		findPnl.add(findBtn, gc);

		////RADIO BUTTONS CONFIG
		boatBtn = new JRadioButton("Båt Reg.Nr");
		ownerBtn = new JRadioButton("Eier ID");
		bg = new ButtonGroup();
		bg.add(boatBtn);
		bg.add(ownerBtn);

		gc.gridx=0;
		gc.gridy=1;
		findPnl.add(boatBtn,gc);

		gc.gridx=1;
		gc.gridy=1;
		findPnl.add(ownerBtn,gc);
	}

	public void setupNewBoatTab() {
		newBoatPnl = new JPanel();
		newBoatPnl.setLayout(new GridLayout(8,2,4,5));
		newBoatPnl.setBorder(BorderFactory.createEmptyBorder(20,20, 20, 20));

		ownerIdFld2 = new JTextField(20);
		registrationNumberFld = new JTextField(20);
		brandFld = new JTextField(20);
		modelFld = new JTextField(20);
		lengthFld = new JTextField(20);
		horsePowerFld = new JTextField(20);
		colorFld = new JTextField(20);

		ownerIdLbl2 = new JLabel("Eier ID", SwingConstants.RIGHT);
		registrationNumberLbl = new JLabel("Reg.Nr", SwingConstants.RIGHT);
		brandLbl =new JLabel("Brand", SwingConstants.RIGHT);
		modelLbl  = new JLabel("Model", SwingConstants.RIGHT);
		lengthLbl = new JLabel("Lengh", SwingConstants.RIGHT);
		horsePowerLbl = new JLabel("Horse Power", SwingConstants.RIGHT);
		colorLbl = new JLabel("Color", SwingConstants.RIGHT);


		newBoatBtn = new JButton("Registrer");
		newBoatBtn.addActionListener(this);

		newBoatPnl.add(ownerIdLbl2);
		newBoatPnl.add(ownerIdFld2);

		newBoatPnl.add(registrationNumberLbl);
		newBoatPnl.add(registrationNumberFld);

		newBoatPnl.add(brandLbl);
		newBoatPnl.add(brandFld);

		newBoatPnl.add(modelLbl);
		newBoatPnl.add(modelFld);

		newBoatPnl.add(lengthLbl);
		newBoatPnl.add(lengthFld);

		newBoatPnl.add(horsePowerLbl);
		newBoatPnl.add(horsePowerFld);

		newBoatPnl.add(colorLbl);
		newBoatPnl.add(colorFld);

		newBoatPnl.add(new JPanel());
		newBoatPnl.add(newBoatBtn);


	}

	public void setupNewOwnerTab() {

		newOwnerPnl = new JPanel();
		newOwnerPnl.setLayout(new GridLayout(7,2,4,5));
		newOwnerPnl.setBorder(BorderFactory.createEmptyBorder(20,20, 20, 20));
		firstNameLbl = new JLabel("Fornavn ", SwingConstants.RIGHT);
		lastNameLbl = new JLabel("Etternavn ", SwingConstants.RIGHT);
		addressLbl = new JLabel("Adresse ", SwingConstants.RIGHT);

		newOwnerBtn = new JButton("Registrer");
		newOwnerBtn.addActionListener(this);

		firstNameFld = new JTextField();
		lastNameFld = new JTextField();
		addressFld = new JTextField();

		newOwnerPnl.add(firstNameLbl);
		newOwnerPnl.add(firstNameFld);

		newOwnerPnl.add(lastNameLbl);
		newOwnerPnl.add(lastNameFld);

		newOwnerPnl.add(addressLbl);
		newOwnerPnl.add(addressFld);
		newOwnerPnl.add(new JLabel());
		newOwnerPnl.add(newOwnerBtn);

	}


	public void setupOwnerChangeTab() {

		ownerChangePnl = new JPanel();
		ownerChangePnl.setLayout(new GridLayout(6,2,4,5));
		ownerChangePnl.setBorder(BorderFactory.createEmptyBorder(20,20, 20, 20));

		oldOwnerIdLbl = new JLabel("ID (Gamle Eier)", SwingConstants.RIGHT);
		ownerIdLbl = new JLabel("ID (Ny Eier)", SwingConstants.RIGHT);
		boatRegNoLbl = new JLabel("Båt Reg.Nr", SwingConstants.RIGHT);
		oldOwnerIdFld =new JTextField(20);
		newOwnerIdFld = new JTextField(20);
		boatRegNoFld = new JTextField(20);


		changeOwnerBtn = new JButton("Bytt Eier");
		changeOwnerBtn.addActionListener(this);

		ownerChangePnl.add(oldOwnerIdLbl);
		ownerChangePnl.add(oldOwnerIdFld);

		ownerChangePnl.add(ownerIdLbl);
		ownerChangePnl.add(newOwnerIdFld);

		ownerChangePnl.add(boatRegNoLbl);
		ownerChangePnl.add(boatRegNoFld);

		ownerChangePnl.add(new JLabel());
		ownerChangePnl.add(changeOwnerBtn);
	}


	public void load() {
		ObjectInputStream ois= null;
		try {
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(new File("data.dat")));
			thelist = (BoatOwnerList) input.readObject();
			input.close();
			info.setText("File loaded successfully!");
			info.append("\n\n"+thelist.toString());

		} catch (IOException e) {
			thelist = new BoatOwnerList();
			info.setText("ERROR!! loading file failed. Empty list initiated.");
		}
		catch(ClassNotFoundException e) {
			System.out.println("Failed to load file2.");
		}
	}

	public void save() {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream("data.dat"));
			oos.writeObject(thelist);
			oos.close();
		}
		catch(IOException e) {
			info.setText("SUP!?");
		}

	}

}