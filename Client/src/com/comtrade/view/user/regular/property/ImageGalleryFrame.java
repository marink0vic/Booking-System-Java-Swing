package com.comtrade.view.user.regular.property;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.io.File;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.comtrade.domain.PropertyImage;
import com.comtrade.util.ImageResize;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ImageGalleryFrame extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Icon[] icons;
	private JLabel label;
	private ImageGallery head;
	private ImageGallery tail;
	private ImageGallery current;

	public ImageGalleryFrame(List<PropertyImage> propertyImages) {
		icons = new Icon[propertyImages.size()];
		initializeComponents(propertyImages);
		
	}


	private void initializeComponents(List<PropertyImage> propertyImages) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JButton rightButton = new JButton("");
		rightButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				current = current.next;
				Icon i = current.value;
				label.setIcon(i);
			}
		});
		rightButton.setBorder(null);
		rightButton.setBackground(new Color(230, 230, 230));
		rightButton.setBounds(883, 301, 63, 55);
		rightButton.setIcon(setIcon(new File("./resources/icons/arrow-right.png")));
		contentPane.add(rightButton);
		
		JButton leftButton = new JButton("");
		leftButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				current = current.previous;
				Icon i = current.value;
				label.setIcon(i);
			}
		});
		leftButton.setBorder(null);
		leftButton.setBackground(new Color(230, 230, 230));
		leftButton.setBounds(22, 301, 63, 55);
		leftButton.setIcon(setIcon(new File("./resources/icons/arrow-left.png")));
		contentPane.add(leftButton);
		
		label = new JLabel("");
		label.setBackground(Color.WHITE);
		label.setBounds(111, 87, 746, 492);
		contentPane.add(label);
		
		addIcons(propertyImages);
		fillGalleryList();
		label.setIcon(current.value);
	}


	private void addIcons(List<PropertyImage> property_images) {
		int index = 0;
		for (PropertyImage image : property_images) {
			File f = new File(image.getImage());
			Icon i = ImageResize.resizeImage(f, label.getWidth(), label.getHeight());
			icons[index++] = i;
		}
		
	}

	private Icon setIcon(File file) {
		ImageIcon myImage = new ImageIcon(file.getAbsolutePath());
		return myImage;
	}
	
	private void fillGalleryList() {
		for (Icon i : icons) {
			if (head == null) {
				head = new ImageGallery();
				tail = head;
				head.value = i;
			} else {
				ImageGallery temp = new ImageGallery();
				temp.value = i;
				tail.next = temp;
				temp.previous = tail;
				tail = temp;
			}
		}
		
		head.previous = tail;
		tail.next = head;
		current = head;
	}
	
	private class ImageGallery {
		private Icon value;
		private ImageGallery next;
		private ImageGallery previous;
	};
}
