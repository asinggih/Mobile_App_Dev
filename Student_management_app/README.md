
# Assignment 1 - Student Management App


## Assignment Details

For this assignment, you are required to create a simple Student Management on an Android
App. The app includes a home screen that includes one or more decorative images and options
(e.g. button, menu items, etc.) The App has four main functions aside from the home screen:

1. Allow user to view and manipulate students
2. Allow user to view and manipulate to-do-tasks
3. Allow user to view and manipulate exams associated to a student
4. Allow user to view and manipulate images that are associated with students

In particular, the App should

#### Pass level ( up to 64%)

- Provide the main interface and corresponding actions where user can select an option from
buttons including “add new student record”, “edit student record” and delete student
records”.

	- Add a new student record: the user can enter a student information, including student Id (unique for each student), first name, last name, gender, course study, age and address.
	
	- Edit a student record: the user can select a student from the list. The program will
display all information of the student in the corresponding fields where the information
can be edited.

	- Delete student records: user can select one or more friends from the list. When the
delete button is pressed, all selected records (checked items) will be removed.
	
- Provide the main interface and implemented actions where the user can select an option from
the buttons/menus for manipulating the to-do-tasks including
	
	- Add a new to-do-task: user can add a task in the list. The to-do-task record includes
task name, location and status (completed and not completed). The status is initially
default as not completed. 

	- Edit a to-do-task: the user can select a to-do-task from the list. The program will
display full detail information in the corresponding fields where they can be changed.
The user can switch the to-do-task to either “complete” or “not complete”.

	- View all the to-do-tasks that are grouped into two categories: “complete” and “not
complete”.

- Be able to return to the home page or the previous page

- Provide a good quality user interface - Are the screens easy to use? Are they laid out neatly?
Do they look good?

#### Credit level ( up to 64%)

Include all functions of the pass level, plus

- Store data in a local SQLLite database, and implement ```INSERT```, ```UPDATE```, ```SELECT``` and
```DELETE``` on the database

- Provide an interface and corresponding actions where user can select an image and assign
the image to a student. The images can be manually stored within the app itself. The app
should be able to display the assigned image when showing the student information.
	
#### Distinction level ( up to 84%):

Include all functions of the credit level, plus

- Provide the main interface and implemented actions to view and manipulate exams
associated with a student. When the user selects a student, in addition to the edit and the
delete options, the app also includes an option for the exam manipulation. The exam
manipulation can be shown on the following page, including

	- Add a new exam: the user can add an exam to the list. The exam record includes exam
name, date and time, location.

	- Edit or remove an exam record: the user can select an exam from the list for a
particular student. The program will display full detail information in the
corresponding fields where they can be edited or removed.

	- View all the exam record, including current exams and past exams. Note: the program
should check the current date/time to flag the exams.

	- Have highly readable coding styles with Object Oriented structure and comments.
	
	- Screen displays should be of high quality.
	
#### High Distinction level (up to 98%):
Include all functions of the distinction level, plus 2 of the following features (7% for each)

- Provide a function to get one or more photos from the camera photo gallery, and add them to
the photo gallery in the app. The user can also remove one or more photos from the photo
gallery.

- Provide a function to show a student’s address on the map. Note: the address is included with
the student record.

*Any figures beyond the requirements are welcomed and they might be considered as extra
contributions.*


## Deliverables
You are only allowed to use Android Java to code your solution. Your program must be
executable. It is an advice that you need to keep multiple versions in case of unforeseen
problems. You are allowed to demonstrate your program on your laptop. You might modify the
code from related source(s) with a proper citation(s) and you must be able to explain clearly your
understanding. The external code should contribute less than 30% of the total program. No part
of the code can be written by any other persons.
	
	
	
	
	
	
	
	
