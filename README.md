# Booking-System-Java-Swing
Desktop application written in java that simulates booking.com website

Booking.com clone is built with core Java and MySql, and contains three level projects (client - common - server)

The application contains all the main features of the booking site:
  -Users can choose a vacation time, hotels, free rooms, make a reservation, add review, chat with property owner...
  -Owners can check reservations,reviews,booked rooms, monthly earnings...
  -Admin can see data from both users and owners

In order to activate application on your computer follow the instructions below

1- Download app data from this link: 	https://drive.google.com/file/d/1UPjGPm8OAL4vea_MWCZhSEwR8ZLivlyY/view?usp=sharing

2 - Import booking_2019.sql to your mysql database management system

3 - add missing jars to lib folder

4 - add images folder to resources folder on server project

- Open project in your editor - 

5 - In all three projects(client, common, server) right click on project - properties - java Buld Path -
	remove libraries with bad path, then click to Add External Jars and add correct jar from project lib folder

6 - in application.properties in both server and client project change port and urls

7 - change server_resources_path in Common project in constants folder

8 - When you log in, passwords are the same as the usernames(check database for username info).
	In column `status` you can check who is regular user(USER), property/hotel owner(SUPER_USER) , admin(ADMIN)
