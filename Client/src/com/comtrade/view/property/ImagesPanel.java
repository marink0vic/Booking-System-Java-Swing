package com.comtrade.view.property;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;


public class ImagesPanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private JLabel lblAddImagesOf;
	private JLabel lblImage;
	private JButton btnAddImage;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel dtm = new DefaultTableModel(); 
	private List<Icon> images = new ArrayList<>();
	private JButton btnContinue;
	//----
	private JLayeredPane layeredPane;
	private List<File> propertyImageFiles;
	private JPanel PaymentPanel;
	private JLabel lblPropertyImages;
	private JLabel lblPayment;
	

	public ImagesPanel(JLayeredPane layeredPane, List<File> propertyImageFiles, JPanel paymentPanel, JLabel lblPropertyImages, JLabel lblPayment) {
		this.layeredPane = layeredPane;
		this.propertyImageFiles = propertyImageFiles;
		this.PaymentPanel = paymentPanel;
		this.lblPropertyImages = lblPropertyImages;
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
		
		lblImage = new JLabel("Image holder");
		lblImage.setFont(new Font("Dialog", Font.BOLD, 18));
		lblImage.setForeground(new Color(71, 71, 71));
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblImage.setBounds(147, 209, 335, 289);
		this.add(lblImage);
		
		btnAddImage = new JButton("Add image");
		btnAddImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAddImage.setBounds(145, 540, 339, 60);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnAddImage.setBounds(147, 542, 335, 55);
			}
		});
		btnAddImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser file = new JFileChooser();
				file.setCurrentDirectory(new File(System.getProperty("user.home")));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","png");
				file.addChoosableFileFilter(filter);
				int result = file.showSaveDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = file.getSelectedFile();
					propertyImageFiles.add(selectedFile);
					Icon icon = resizeImage(selectedFile);
					images.add(icon);
					updateTable();
				} else if (result == JFileChooser.CANCEL_OPTION) {
					System.out.println("No file select");
				}
			}
		});
		btnAddImage.setForeground(Color.WHITE);
		btnAddImage.setFont(new Font("Dialog", Font.BOLD, 20));
		btnAddImage.setBorder(null);
		btnAddImage.setBackground(new Color(9, 121, 186));
		btnAddImage.setBounds(147, 542, 335, 55);
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
		table.setRowHeight(lblImage.getHeight());
		
		btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel(PaymentPanel);
				updateUI(lblPropertyImages,lblPayment);
			}
		});
		btnContinue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnContinue.setBounds(290, 667, 531, 60);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnContinue.setBounds(292, 669, 527, 55);
			}
		});
		btnContinue.setForeground(Color.WHITE);
		btnContinue.setFont(new Font("Dialog", Font.BOLD, 20));
		btnContinue.setBorder(null);
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

	private Icon resizeImage(File selectedFile) {
		ImageIcon myImage = new ImageIcon(selectedFile.getAbsolutePath());
		Image img = myImage.getImage();
		Image newImg = img.getScaledInstance(scrollPane.getWidth(), scrollPane.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}
	
	private void switchPanel(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}

	private void updateUI(JLabel label1, JLabel label2) {
		label1.setBorder(null);
		label2.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(255, 255, 255)));// TODO Auto-generated method stub
	}
	
	class LabelRenderer implements TableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			return (Component) value;
		}
	}

}
