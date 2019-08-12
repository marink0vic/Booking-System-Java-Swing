package com.comtrade.view.user.host.frames;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.comtrade.constants.DomainType;
import com.comtrade.constants.Operations;
import com.comtrade.controller.ControllerUI;
import com.comtrade.domain.PropertyImage;
import com.comtrade.domain.User;
import com.comtrade.domain.behavior.GeneralDomain;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.generics.GenericMap;
import com.comtrade.transfer.TransferClass;
import com.comtrade.util.ImageResize;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

public class PropertyImagesFrame extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblImage;
	private List<PropertyImage> propertyImages;
	private List<PropertyImage> imagesForDeletion;
	private int propertyId;
	private int index;
	private HomePanel homePanel;
	private PropertyWrapper propertyWrapper;
	private File imageFile;
	private Icon imageIcon;
	
	public PropertyImagesFrame(HomePanel homePanel, PropertyWrapper propertyWrapper) {
		this.homePanel = homePanel;
		this.propertyWrapper = propertyWrapper;
		this.propertyImages = propertyWrapper.getImages();
		this.propertyId = propertyWrapper.getProperty().getIdProperty();
		imagesForDeletion = new ArrayList<>();
		initializeComponents();
		
	}

	private void initializeComponents() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setBounds(100, 100, 952, 677);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblImage = new JLabel("");
		lblImage.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblImage.setBackground(new Color(255, 255, 255));
		lblImage.setBounds(120, 93, 699, 383);
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
					image.setIdProperty(propertyId);
					
					propertyImages.add(image);
					
					index = propertyImages.size() - 1;
					imageFile = new File(propertyImages.get(index).getImage());
					imageIcon = ImageResize.resizeImage(imageFile, lblImage.getWidth(), lblImage.getHeight());
					lblImage.setIcon(imageIcon);
					
				} else if (result == JFileChooser.CANCEL_OPTION) {
					System.out.println("No file select");
				}
			}
		});
		btnAddNewImage.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAddNewImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnAddNewImage.setBounds(118, 520, 327, 60);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btnAddNewImage.setBounds(120, 522, 323, 55);
			}
		});
		btnAddNewImage.setForeground(Color.WHITE);
		btnAddNewImage.setFont(new Font("Dialog", Font.BOLD, 20));
		btnAddNewImage.setBorder(null);
		btnAddNewImage.setBackground(new Color(9, 121, 186));
		btnAddNewImage.setBounds(120, 522, 323, 55);
		contentPane.add(btnAddNewImage);
		
		JButton btnDeleteThisImage = new JButton("Delete this image");
		btnDeleteThisImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (propertyImages.size() == 1) {
					JOptionPane.showMessageDialog(null, "You cannot delete all images from gallery");
				} else {
					PropertyImage propertyImage = propertyImages.get(index);
					if (propertyImage.getIdImage() != 0) {
						imagesForDeletion.add(propertyImage);
					}
					propertyImages.remove(index);
					homePanel.removeIconFromPanel(index);
					if (index == 0) homePanel.addSmallTopIcon();
					if (index != 0) index--;
					imageFile = new File(propertyImages.get(index).getImage());
					imageIcon = ImageResize.resizeImage(imageFile, lblImage.getWidth(), lblImage.getHeight());
					lblImage.setIcon(imageIcon);
				}
			}
		});
		btnDeleteThisImage.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDeleteThisImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnDeleteThisImage.setBounds(490, 520, 327, 60);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnDeleteThisImage.setBounds(492, 522, 323, 55);
			}
		});
		btnDeleteThisImage.setForeground(Color.WHITE);
		btnDeleteThisImage.setFont(new Font("Dialog", Font.BOLD, 20));
		btnDeleteThisImage.setBorder(null);
		btnDeleteThisImage.setBackground(new Color(255, 88, 93));
		btnDeleteThisImage.setBounds(492, 522, 323, 55);
		contentPane.add(btnDeleteThisImage);
		
		JButton btnLeft = new JButton("");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				moveSlider('L');
			}
		});
		btnLeft.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLeft.setBackground(new Color(230, 230, 230));
		btnLeft.setBounds(35, 258, 63, 55);
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
		btnRight.setBounds(837, 258, 63, 55);
		btnRight.setBorder(null);
		btnRight.setIcon(returnIcon(new File("./resources/icons/arrow-right.png")));
		contentPane.add(btnRight);
		
		JButton btnBack = new JButton("Back to home");
		btnBack.setForeground(new Color(255, 255, 255));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (imagesForDeletion.size() > 0) {
					TransferClass transferClass = new TransferClass();
					transferClass.setClientRequest(imagesForDeletion);
					transferClass.setDomainType(DomainType.IMAGES);
					transferClass.setOperation(Operations.DELETE);
					ControllerUI.getController().sendToServer(transferClass);
				}
				
				List<PropertyImage> newImagesForDatabase = newImagesForDatabase();
				if (newImagesForDatabase.size() > 0) {
					PropertyWrapper wrapper = new PropertyWrapper();
					wrapper.setImages(newImagesForDatabase);
					wrapper.setUser(propertyWrapper.getUser());
					
					TransferClass transferClass = new TransferClass();
					transferClass.setClientRequest(wrapper);
					transferClass.setDomainType(DomainType.IMAGES);
					transferClass.setOperation(Operations.SAVE);
					ControllerUI.getController().sendToServer(transferClass);
					
					wrapper = ControllerUI.getController().getPropertyWrapper();
					propertyWrapper.setImages(wrapper.getImages());
					propertyImages = propertyWrapper.getImages();
				}
				
				dispose();
			}
		});
		btnBack.setFont(new Font("Dialog", Font.PLAIN, 13));
		btnBack.setBounds(693, 41, 125, 25);
		btnBack.setBackground(new Color(9, 121, 186));
		btnBack.setBorder(null);
		contentPane.add(btnBack);
		imageFile = new File(propertyImages.get(0).getImage());
		imageIcon = ImageResize.resizeImage(imageFile, lblImage.getWidth(), lblImage.getHeight());
		lblImage.setIcon(imageIcon);
	}

	protected List<PropertyImage> newImagesForDatabase() {
		List<PropertyImage> images = new ArrayList<>();
		for (int i = propertyImages.size() - 1; i >= 0; i--) {
			if (propertyImages.get(i).getIdImage() != 0) break;
			images.add(propertyImages.get(i));
		}
		return images;
	}
	
	private void moveSlider(char command) {
		if (command == 'L') {
			if (index == 0) {
				index = propertyImages.size() - 1;
			} else {
				index--;
			}
			imageFile = new File(propertyImages.get(index).getImage());
			imageIcon = ImageResize.resizeImage(imageFile, lblImage.getWidth(), lblImage.getHeight());
			lblImage.setIcon(imageIcon);
		} else if (command == 'R') {
			if (index == propertyImages.size() - 1) {
				index = 0;
			} else {
				index++;
			}
			imageFile = new File(propertyImages.get(index).getImage());
			imageIcon = ImageResize.resizeImage(imageFile, lblImage.getWidth(), lblImage.getHeight());
			lblImage.setIcon(imageIcon);
		}
	}
	
	private Icon returnIcon(File selectedFile) {
		ImageIcon myImage = new ImageIcon(selectedFile.getAbsolutePath());
		return myImage;
	}
}
