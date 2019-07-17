package com.comtrade.view.user.host.frames;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.comtrade.domain.PropertyImage;
import com.comtrade.view.user.host.HomePanel;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PropertyImagesFrame extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblImage;
	private List<PropertyImage> propertyImages;
	private List<PropertyImage> imagesToAdd;
	private List<PropertyImage> imagesToDelete;
	private int propertyId;
	private int index;
	private HomePanel homePanel;
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					PropertyImagesFrame frame = new PropertyImagesFrame(null, 2);
//					frame.setLocationRelativeTo(null);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 * @param homePanel 
	 * @param i 
	 * @param propertyImages 
	 */
	public PropertyImagesFrame(HomePanel homePanel, List<PropertyImage> propertyImages, int propertyId) {
		this.homePanel = homePanel;
		this.propertyImages = propertyImages;
		this.propertyId = propertyId;
		imagesToAdd = new ArrayList<>();
		imagesToDelete = new ArrayList<>();
		initializeComponents();
		
	}

	private void initializeComponents() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 952, 650);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblImage = new JLabel("");
		lblImage.setBorder(null);
		lblImage.setBackground(new Color(255, 255, 255));
		lblImage.setBounds(120, 66, 699, 383);
		contentPane.add(lblImage);
		
		JButton btnAddNewImage = new JButton("Add new image");
		btnAddNewImage.addActionListener(new ActionListener() {
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
					homePanel.addIconToPanel(selectedFile);
					image.setImage(path);
					
					propertyImages.add(image);
					imagesToAdd.add(image);
					
					index = propertyImages.size() - 1;
					setImage(index);
					
				} else if (result == JFileChooser.CANCEL_OPTION) {
					System.out.println("No file select");
				}
			}
		});
		btnAddNewImage.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAddNewImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnAddNewImage.setBounds(118, 490, 327, 60);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnAddNewImage.setBounds(120, 492, 323, 55);
			}
		});
		btnAddNewImage.setForeground(Color.WHITE);
		btnAddNewImage.setFont(new Font("Dialog", Font.BOLD, 20));
		btnAddNewImage.setBorder(null);
		btnAddNewImage.setBackground(new Color(9, 121, 186));
		btnAddNewImage.setBounds(120, 492, 323, 55);
		contentPane.add(btnAddNewImage);
		
		JButton btnDeleteThisImage = new JButton("Delete this image");
		btnDeleteThisImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (propertyImages.size() == 1) {
					JOptionPane.showMessageDialog(null, "You cannot delete all images from gallery");
				} else {
					imagesToDelete.add(propertyImages.get(index));
					propertyImages.remove(index);
					homePanel.removeIconFromPanel(index);
					if (index == 0) homePanel.addSmallTopIcon();
					if (index != 0) index--;
					setImage(index);
				}
			}
		});
		btnDeleteThisImage.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDeleteThisImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnDeleteThisImage.setBounds(490, 490, 327, 60);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnDeleteThisImage.setBounds(492, 492, 323, 55);
			}
		});
		btnDeleteThisImage.setForeground(Color.WHITE);
		btnDeleteThisImage.setFont(new Font("Dialog", Font.BOLD, 20));
		btnDeleteThisImage.setBorder(null);
		btnDeleteThisImage.setBackground(new Color(255, 88, 93));
		btnDeleteThisImage.setBounds(492, 492, 323, 55);
		contentPane.add(btnDeleteThisImage);
		
		JButton btnLeft = new JButton("");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				moveSlider('L');
			}
		});
		btnLeft.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLeft.setBackground(new Color(230, 230, 230));
		btnLeft.setBounds(35, 231, 63, 55);
		btnLeft.setBorder(null);
		btnLeft.setIcon(returnIcon(new File("./resources/icons/arrow-left.png")));
		contentPane.add(btnLeft);
		
		JButton btnRight = new JButton("");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveSlider('R');
			}
		});
		btnRight.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRight.setBackground(new Color(230, 230, 230));
		btnRight.setBounds(837, 231, 63, 55);
		btnRight.setBorder(null);
		btnRight.setIcon(returnIcon(new File("./resources/icons/arrow-right.png")));
		contentPane.add(btnRight);
		
		setImage(0);
	}
	
	private void setImage(int position) {
		File file = new File(propertyImages.get(position).getImage());
		ImageIcon imgIcon = new ImageIcon(file.getPath());
		Image img = imgIcon.getImage();
		Image newImg = img.getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		lblImage.setIcon(image);
	}
	
	private void moveSlider(char command) {
		if (command == 'L') {
			if (index == 0) {
				index = propertyImages.size() - 1;
			} else {
				index--;
			}
			setImage(index);
		} else if (command == 'R') {
			if (index == propertyImages.size() - 1) {
				index = 0;
			} else {
				index++;
			}
			setImage(index);
		}
	}
	
	private Icon returnIcon(File selectedFile) {
		ImageIcon myImage = new ImageIcon(selectedFile.getAbsolutePath());
		return myImage;
	}
}
