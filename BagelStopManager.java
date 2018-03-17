/* Project 		: Project5KHang
 * Class 		: BagelStopManager
 * Date			: 04/17/2017
 * Programmer	: Kathleen Hang
 * Description	: This application is used to maintain the current list of available 
 * 				  bagel and cream cheese products. The user will be able to add and delete products.  
 */
import javax.swing.*;
import java.awt.event.*;

public class BagelStopManager extends JFrame implements ActionListener, ItemListener
{

	String bagelString [] = {"[[ Bagel Menu ]]", "Plain", "Egg", "Rye", "Salt", "Blueberry", "Garlic", "Onion", "Sesame", "Poppy Seed", "Works"};
	String creamString [] = {"[[ Cream Cheese Menu ]]", "Plain", "Herb", "Garlic"};
	
	JRadioButton bagelRadioButton = new JRadioButton("Bagel");
	JRadioButton creamRadioButton = new JRadioButton("Cream Cheese");
	JRadioButton invisibleRadioButton = new JRadioButton("");
	ButtonGroup userButtonGroup = new ButtonGroup();
	
	JTextField addTextField = new JTextField(15);
	JLabel addLabel = new JLabel("Add bagel/cream cheese");
	
	JComboBox bagelComboBox = new JComboBox(bagelString);
	JComboBox creamComboBox = new JComboBox(creamString);
	JButton addButton = new JButton("Add");
	JButton deleteButton = new JButton("Delete");
	JButton summaryButton = new JButton("# of Bagels/Cream Cheese");
	
	JPanel mainPanel = new JPanel();
	
	// instance variables
	final int WIDTH_INTEGER = 240, HEIGHT_INTEGER = 300;
	
	public static void main(String[] args)
	{
		BagelStopManager myApp = new BagelStopManager();
		myApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public BagelStopManager()
	{
		// call methods to add components to main panel
		addListeners();
		designClass();
		add(mainPanel);
		setSize(WIDTH_INTEGER,HEIGHT_INTEGER);
		setVisible(true);

	}
	
	public void designClass()
	{
		addButton.setEnabled(false);
		deleteButton.setEnabled(false);
		
		userButtonGroup.add(bagelRadioButton);
		userButtonGroup.add(creamRadioButton);
		userButtonGroup.add(invisibleRadioButton);
		
		mainPanel.add(bagelComboBox);
		mainPanel.add(creamComboBox);
		
		mainPanel.add(bagelRadioButton);
		mainPanel.add(creamRadioButton);
		
		mainPanel.add(addLabel);
		mainPanel.add(addTextField);
		
		mainPanel.add(addButton);
		mainPanel.add(deleteButton);
		mainPanel.add(summaryButton);
			
	}
	
	public void addListeners()
	{
		addButton.addActionListener(this);
		deleteButton.addActionListener(this);
		summaryButton.addActionListener(this);
		
		bagelRadioButton.addItemListener(this);
		creamRadioButton.addItemListener(this);
		
		bagelComboBox.addActionListener(this);
		creamComboBox.addActionListener(this);
		addTextField.addActionListener(this);
		
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		Object sourceObject = evt.getSource();
		
		if(sourceObject == summaryButton)
		{
			summary();
		}
		else if(sourceObject == addButton)
		{
			addItem();
		}
		else if(sourceObject == deleteButton)
		{
			deleteItem();
		}
	}
	
	public void itemStateChanged(ItemEvent evt)
	{
		Object sourceObject = evt.getSource();
		
		if(sourceObject == bagelRadioButton||sourceObject == creamRadioButton)
		{
			if(bagelRadioButton.isSelected()||creamRadioButton.isSelected())
			{ 
				addButton.setEnabled(true);
				deleteButton.setEnabled(true);
				
			}
		}
	}
	
	public void addItem()
	{
		String inputString;
		boolean foundBoolean;
		
		inputString = addTextField.getText();
		
		if((!(inputString.equals(""))))
		{
			
			foundBoolean = checkInput(inputString);
			displayFound(foundBoolean, inputString);
			
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Enter a bagel to add in the text field");
			addTextField.requestFocus();
		}
	}
	
	public void deleteItem()
	{
		int indexInteger, resultInteger;
		indexInteger = bagelComboBox.getSelectedIndex();
		String deleteString, outputString;
		
		if(indexInteger != -1 && indexInteger != 0)
		{
			deleteString = (String) bagelComboBox.getItemAt(indexInteger);
			outputString = "Do you wish to delete " + deleteString + "?";
			resultInteger = JOptionPane.showConfirmDialog(null, outputString,
					"Confirmation",JOptionPane.YES_NO_OPTION);
			if(resultInteger == JOptionPane.YES_OPTION)
			{
				bagelComboBox.removeItemAt(indexInteger);
				clearAll();
				JOptionPane.showMessageDialog(null, deleteString + " was successfully deleted");
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Select an item to remove from the drop down menu");
			bagelComboBox.requestFocus();
		}
	}
	
	public boolean checkInput(String userInputString)
	{
		boolean foundBoolean = false;
		int indexInteger = 0;
		String bagelCreamString = new String();
		
		while(!foundBoolean && indexInteger < bagelComboBox.getItemCount())
		{
			bagelCreamString = (String) bagelComboBox.getItemAt(indexInteger);
			if(userInputString.equalsIgnoreCase(bagelCreamString))
			{
				foundBoolean = true;
			}
			else
			{
				indexInteger++;
			}
		}
		
		return foundBoolean;
	}
	
	public void displayFound(boolean bagelBoolean, String inputString)
	{
		if(bagelBoolean)
		{
			JOptionPane.showMessageDialog(null, "This item is already in the list");
			addTextField.selectAll();
			addTextField.requestFocus();
		}
		else
		{
			bagelComboBox.addItem(inputString);
			JOptionPane.showMessageDialog(null, "This item has been added to the list");
			clearAll();
		}
	}
	
	
	public boolean validation()
	{
		boolean validateComponentsBoolean = false;
		int indexInteger;
		indexInteger = bagelComboBox.getSelectedIndex();
		
		if(bagelRadioButton.isSelected()||creamRadioButton.isSelected())
		{
			
				if(!(addTextField.getText().equals("")))
				{
					validateComponentsBoolean = true;
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please enter bagel or cream cheese type");
					addTextField.requestFocus();
				}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Please select either bagel or cream button");
		}
		
		return validateComponentsBoolean;
	}
	
	public void summary()
	{
		int bagelCountInteger, creamCountInteger, bagelSummaryInteger, creamSummaryInteger;
		
		bagelCountInteger = bagelComboBox.getItemCount();
		creamCountInteger = creamComboBox.getItemCount();
		
		bagelSummaryInteger = bagelCountInteger - 1;
		creamSummaryInteger = creamCountInteger - 1;
		
		JOptionPane.showMessageDialog(null, "# of Bagel types: " + bagelSummaryInteger + "\n" +
									   "# of Cream cheese types: " + creamSummaryInteger + "\n");
		clearAll();
	}
	
	public void clearAll()
	{
		invisibleRadioButton.setSelected(true);
		addTextField.setText("");
		bagelComboBox.setSelectedIndex(0);
		creamComboBox.setSelectedIndex(0);
	
	}

	
}
