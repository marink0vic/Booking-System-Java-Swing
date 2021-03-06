package com.comtrade.view.property;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import com.comtrade.domain.PropertyImage;
import com.comtrade.util.ImageResize;


public class ImagesPanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private JLabel lblAddImagesOf;
	private JButton btnAddImage;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel dtm = new DefaultTableModel(); 
	private List<Icon> images = new ArrayList<>();
	private JButton btnContinue;
	private JLayeredPane layeredPane;
	private List<PropertyImage> propertyImageFiles;
	private PaymentPanel paymentPanel;
	private JLabel lblPayment;
	

	public ImagesPanel(JLayeredPane layeredPane, List<PropertyImage> propertyImageFiles, PaymentPanel paymentPanel, JLabel lblPayment) {
		this.layeredPane = layeredPane;
		this.propertyImageFiles = propertyImageFiles;
		this.paymentPanel = paymentPanel;
		this.lblPayment = lblPayment;
		initializeComponents();
	}



	private void initializeComponents() {
		this.setBounds(0, 120, 1282, 783);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);
		
		lblAddImagesOf = new JLabel("Add images of your property");
		lblAddImagesOf.setForeground(new Color(71, 71, 71));
		lblAddImagesOf.setFont(new Font("Dialog", Font.PLAIN, 23));
		lblAddImagesOf.setBounds(423, 92, 300, 80);
		this.add(lblAddImagesOf);
		
		btnAddImage = new JButton("Add image");
		btnAddImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser file = new JFileChooser();
				file.setCurrentDirectory(new File("C:\\Users\\marko\\Desktop\\hotel_images"));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","png");
				file.addChoosableFileFilter(filter);
				int result = file.showSaveDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					
					File selectedFile = file.getSelectedFile();
					PropertyImage image = new PropertyImage();
					String path = selectedFile.getAbsolutePath();
					image.setImage(path);
					propertyImageFiles.add(image);
					
					Icon icon = ImageResize.resizeImage(selectedFile, scrollPane.getWidth(), scrollPane.getHeight());
					images.add(icon);
					updateTable();
					
				} else if (result == JFileChooser.CANCEL_OPTION) {
					System.out.println("No file select");
				}
			}
		});
		btnAddImage.setForeground(Color.WHITE);
		btnAddImage.setFont(new Font("Dialog", Font.BOLD, 20));
		btnAddImage.setBackground(new Color(9, 121, 186));
		btnAddImage.setBounds(141, 372, 335, 55);
		this.add(btnAddImage);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(561, 209, 443, 388);
		this.add(scrollPane);
		
		table = new JTable(dtm);
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Dialog", Font.BOLD, 20));
		scrollPane.setViewportView(table);
		Object[] o = {"Image"};
		dtm.addColumn(o[0]);
		table.setRowHeight(300);
		
		btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (propertyImageFiles.size() == 0) {
					JOptionPane.showMessageDialog(null, "You have to select at least one image");
				} else {
					switchPanel(paymentPanel);
					updateUI(lblPayment);
				}
			}
		});
		btnContinue.setForeground(Color.WHITE);
		btnContinue.setFont(new Font("Dialog", Font.BOLD, 20));
		btnContinue.setBackground(new Color(255, 88, 93));
		btnContinue.setBounds(292, 669, 527, 55);
		this.add(btnContinue);
	}
	
	private void updateTable() {
		dtm.setRowCount(0);
		for (int i = images.size() - 1; i >= 0; i--) {
			JLabel label = new JLabel();
			label.setIcon(images.get(i));
			table.getColumn("Image").setCellRenderer(new LabelRenderer());
			dtm.addRow(new Object[] {label});
		}
	}
	
	private void switchPanel(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}

	private void updateUI(JLabel label) {
		label.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(255, 255, 255)));
	}
	
	class LabelRenderer implements TableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			return (Component) value;
		}
	}

}
