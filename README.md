# My Personal Project: Finance Tracker

## Project Overview
### What the Application Does ###
I propose to design a **finance tracker** to help keep track of someone's spending per month. The application 
should have multiple sections such as total spending per day, goal budget, income, expenditures, and savings. 
The application should be able to keep track of all money spent for each day along with a description box. In the 
description box users can note what type of expense it was, for example, personal or for school, which card they used,
what they spent the money on, for example, a donut or a book. The application should also be able to 
differentiate whether the user used a debit or credit card and calculate the total amount of money available to spend 
and to ensure they have enough to pay of the bill. Finally, it will also have keep track of upcoming bills or expenses 
needed to be made, for example rent or taxes.

### Target Users ###
Target users are anyone who is managing their personal money. This can be both working or non-working adults, students,
small businesses. Note: not large businesses as this is something for individual use. 

### Why I want to do this project ###
I want to start developing habits that will be useful for when I am older such as managing my finances and by building 
a finance tracker, I am able to start keeping a record of my finances and develop these necessary skills. As a 
university student who has never had to keep track of money and spending, I find it difficult to save money and keep
track of the money between my accounts. I want to build this project because I will be able to personalize it to
my specific needs and add other aspects if needed.

## User Stories
- As a user, I want to be able to add a finance
- As a user, I want to be able to remove a finance
- As a user, I want to be able to differentiate which card I am using
- As a user, I want to be able to keep track of dates and specific things I spent money on
- As a user, I want to be able to set spending and saving goals
- As a user, I want to be able to keep track of receipts
- As a user, I want to be able to add finances to a specific day
- As a user, I want to be able to view all the purchases I have made in a day
- As a user, I want to be able to save my list of finances to file
- As a user, I want to be able to load my finance list from file

## Phase 4: Task 2
1. Initialization of the program
   - The application starts and GUI is initialized with headers, backgrounds, and buttons
2. Load Prompt 
   - The user is prompted to load the previous entry they had made
   - If user chooses yes, JSON reader reads the data from the file, the list of finances is updated with the loaded
     data and the net amount is updated
3. User Interaction events
   - Allows the user to choose to add or remove entries
   - Also allows the user to set budgeting and saving goals 
4. Saving on Exit
   - Prompts user to save their new entries added
   - If yes is selected, JSON writer writes the FinanceList data to the file.
5. Display of Events Logged
   - Events like adding finance and removing finance are logged in the event log
   - Event log is printed to the console upon exiting the application


## Phase 4: Task 3
I would separate some classes to ensure each only had one responsbility. Currently, some of my classes do multiple 
tasks, for example, I may make one class responsible for saving goals and make it more detailed with more functionality
and another class focused on only adding and removing. This is so if I were to add more functionality to the program,
it will be easily implementable and there would be less room for errors to occur. Another thing I would do is look 
for duplicated code or code that is unused. Since this project was done in multiple phases, I feel I have some
duplicated code as well as code that is not being used since it was written at the start. Finally, for better
understanding and readbility, changing names and naming convetions to ensure each method is well understood for its
function. 