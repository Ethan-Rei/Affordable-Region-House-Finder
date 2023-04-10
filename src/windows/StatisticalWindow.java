package windows;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.commons.math3.exception.NumberIsTooSmallException;

import analysis.Analysis;

import javax.swing.JComboBox;
import javax.swing.JButton;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;

public class StatisticalWindow extends InternalFrame {
	private final String title = "Statistical Test";
	private final JLabel lblNewLabel = new JLabel("Statistical Test");
	private final JLabel loclabel = new JLabel("Location");
	private final JLabel startlabel = new JLabel("Start Date");
	private final JLabel endlabel = new JLabel("End Date");
	private final JLabel plabel = new JLabel("P - Value");
	private final JComboBox<String> pBox = new JComboBox<String>();
	private final JComboBox<String> locBox1 = new JComboBox<String>();
	private final JComboBox<String> locBox2 = new JComboBox<String>();
	private final JComboBox<String> startBox1 = new JComboBox<String>();
	private final JComboBox<String> startBox2 = new JComboBox<String>();
	private final JComboBox<String> endBox1 = new JComboBox<String>();
	private final JComboBox<String> endBox2 = new JComboBox<String>();
	private final JButton btnCompare = new JButton("Compare");

	private final String errorLocation = "You must select both locations. Please try again.";
	private final String errorDate = "The selected dates are invalid. Please try again.";
	private final String errorDateLength = "The timespan of the two locations must be the same. Please try again.";
	private final String errorSize = "There must be more than 1 month selected in the dates to do a statistical test. Please try again.";
	private final String reject = "We can reject the null hypothesis.";
	private final String cantReject = "We cannot reject the null hypothesis.";
	
	private final HashMap<String, HashMap<Date, Double>> loadedData;
	
	public StatisticalWindow(HashMap<String, HashMap<Date, Double>> data) {
		this.loadedData = data;
		setInternalWindowSettings(title, 500, 380);
		createFrame();
		frame.setVisible(true);
	}
	
	public void createFrame() {
		populatePValues();
		populateLocBox(locBox1, loadedData);
		populateLocBox(locBox2, loadedData);
		setGUIBounds();
		setGUIListeners();
		addToInternalFrame();
	}
	
	private void setGUIBounds() {
		lblNewLabel.setBounds(200, 20, 93, 23);
		loclabel.setBounds(30, 70, 61, 16);
		locBox1.setBounds(30, 110, 101, 27);
		locBox2.setBounds(30, 160, 101, 27);
		startlabel.setBounds(180, 70, 61, 16);
		startBox1.setBounds(180, 110, 101, 27);
		startBox2.setBounds(180, 160, 101, 27);
		endlabel.setBounds(330, 70, 61, 16);
		endBox1.setBounds(330, 110, 101, 27);
		endBox2.setBounds(330, 160, 101, 27);
		plabel.setBounds(180, 204, 117, 29);
		pBox.setBounds(180, 244, 117, 29);
		btnCompare.setBounds(180, 294, 117, 29);
	}
	
	private void setGUIListeners() {
		locBox1.addActionListener(e -> populateDateBoxes(locBox1, startBox1, endBox1, loadedData));
		locBox2.addActionListener(e -> populateDateBoxes(locBox2, startBox2, endBox2, loadedData));
		startBox1.addActionListener(e -> populateEndDate(locBox1, startBox1, endBox1, loadedData));
		startBox2.addActionListener(e -> populateEndDate(locBox2, startBox2, endBox2, loadedData));
		btnCompare.addActionListener(e -> compare());
	}
	
	private void addToInternalFrame() {
		frame.getContentPane().add(lblNewLabel);
		frame.getContentPane().add(loclabel);
		frame.getContentPane().add(locBox1);
		frame.getContentPane().add(locBox2);
		frame.getContentPane().add(startlabel);
		frame.getContentPane().add(startBox1);
		frame.getContentPane().add(startBox2);
		frame.getContentPane().add(endlabel);
		frame.getContentPane().add(endBox1);
		frame.getContentPane().add(endBox2);
		frame.getContentPane().add(plabel);
		frame.getContentPane().add(pBox);
		frame.getContentPane().add(btnCompare);
	}
	
	public void close() {
		MainWindow.getInstance().getBtnCompare().setEnabled(true);
		MainWindow.getInstance().frame.remove(frame);
	}
	
	private void populatePValues() {
		double val = 0.01;
		for (int i = 0; i < 10; i++) {
			pBox.addItem(String.format("%.2f", val));
			val += 0.01;
		}
		pBox.setSelectedIndex(4);
	}

	private void compare() {
		if (!checkValidLocation()) {
			JOptionPane.showMessageDialog(null, errorLocation, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (!checkValidDates()) {
			JOptionPane.showMessageDialog(null, errorDate, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (!checkSameDateLength()) {
			JOptionPane.showMessageDialog(null, errorDateLength, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// get the double array values for both time series
		try {
			Date startLoc1 = dateFormat.parse(startBox1.getSelectedItem().toString());
			Date startLoc2 = dateFormat.parse(startBox2.getSelectedItem().toString());
			Date endLoc1 = dateFormat.parse(endBox1.getSelectedItem().toString());
			Date endLoc2 = dateFormat.parse(endBox2.getSelectedItem().toString());
			
			double[] loc1Values = getNHPIInRange(locBox1.getSelectedItem().toString(), startLoc1, endLoc1, loadedData);
			double[] loc2Values = getNHPIInRange(locBox2.getSelectedItem().toString(), startLoc2, endLoc2, loadedData);
			
			double pValue = Analysis.getInstance().tTest(loc1Values, loc2Values);
			if (pValue <= Double.parseDouble(pBox.getSelectedItem().toString()))
				JOptionPane.showMessageDialog(null, String.format("P Value: %.2f", pValue) + "\n" + reject, "Result", JOptionPane.DEFAULT_OPTION);
			else
				JOptionPane.showMessageDialog(null, String.format("P Value: %.2f", pValue) + "\n" + cantReject, "Result", JOptionPane.DEFAULT_OPTION);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NumberIsTooSmallException en) {
			JOptionPane.showMessageDialog(null, errorSize, "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private boolean checkValidDates() {
		if (startBox1.getSelectedItem() == null || startBox2.getSelectedItem() == null)
			return false;
		if (startBox1.getSelectedItem().toString().compareTo(endBox1.getSelectedItem().toString()) > 0 || startBox2.getSelectedItem().toString().compareTo(endBox2.getSelectedItem().toString()) > 0)
			return false;
		return true;
	}
	
	private boolean checkSameDateLength() {
		String startLoc1 = startBox1.getSelectedItem().toString() + "-01";
		String endLoc1 = endBox1.getSelectedItem().toString() + "-01";
		String startLoc2 = startBox2.getSelectedItem().toString() + "-01";
		String endLoc2 = endBox2.getSelectedItem().toString() + "-01";
		long firstLoc = ChronoUnit.MONTHS.between(LocalDate.parse(startLoc1), LocalDate.parse(endLoc1));
		long secondLoc = ChronoUnit.MONTHS.between(LocalDate.parse(startLoc2), LocalDate.parse(endLoc2));
		
		if (firstLoc != secondLoc)
			return false;
		
		return true;
	}
	
	private boolean checkValidLocation() {
		if (locBox1.getSelectedItem() == null || locBox2.getSelectedItem() == null)
			return false;
		return true;
	}
}
