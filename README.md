# Spring-Boot-Data-Analytics
A Quick, Interactive Approach to Learning Data Analytics with SQL using Spring Data, JOOQ and Window Functions

 # :bookmark_tabs: What you will learn
 
 * Understand what data analytics is and why it is important
 * Experiment with data analytics using basic and advanced queries
 * Interpret data through descriptive statistics and aggregate functions
 * Work with and manipulate data using SQL joins and constraints
 * Speed up your data analysis workflow by optimizing queries
 
 # :bookmark: What are window functions ?
 
 * Nothing to do with Windows OS
 * Standard functionality added to T-SQL
 * Functions that operate on a set or window of rows
 * Always with an OVER clause (but sometimes you will see an OVER clause without a window funtion)
 * Always found in the SELECT and ORDER BY
 * Makes queries easier to write
 * Often better performance
 
 ## :page_with_curl: Introduction to SQL Window Functions
 
 Window functions are an advanced SQL feature offered to improve the execution performance of queries. These functions act on a group of rows related to the targeted row called window frame. Unlike a GROUP BY clause, Window functions do not collapse the rows to a single row, preserving the details of each row instead. This new approach to querying data is invaluable in data analytics and business intelligence.
 
 Window functions follow three key steps:

 * They split data into groups
 * They perform a calculation on each group
 * They combine the results of those calculations back into the original dataset
 ![three_key](https://user-images.githubusercontent.com/46305342/200438573-328304bd-63b7-4e40-a1c0-093a9a519bd1.png)

 ![have_over_works](https://user-images.githubusercontent.com/46305342/200440834-896fbe42-6c46-4a3d-a8c1-183a951c60d4.png)


