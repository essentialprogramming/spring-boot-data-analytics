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

# :nut_and_bolt: Example 1:

Let’s say we have the following problem statement: find the team with most points from each group over the entire dataset.

![Teams_-_random](https://user-images.githubusercontent.com/46305342/200454380-8615373f-2f38-4968-b2db-1d8913babd21.png)

Before jumping into this question, it’s worth examining a few similar questions that we can easily answer without window functions.

 * If our task was to find the team with most points by group that would have been very simple.
 *  Or if we wanted to find the team with most points overall, that would have been trivial.
 *  What makes our original question different, is that it asks us to find out a piece of information separately for specific groups within our data, but also to preserve information from the original dataset. We need the team with most points from each group and we need to calculate that separately.

Moving on — let’s first answer this question conceptually, using the three steps outlined above:

 * Separate data into groups: In this case, we want to split up by group.
 * Perform an aggregation: Once we have our data separated, we can rank by team points.
 * Combine the data back together: When we combine back together, each original record will have a column indicating its rank order compared to all other teams within same group.


![three_key_steps](https://user-images.githubusercontent.com/46305342/200454133-20d0b092-3d27-442b-86e1-27fde6d24f64.png)

 * Each group is a window: 

![Groups_-_each_group_is_a_window](https://user-images.githubusercontent.com/46305342/200454652-ef082e20-55d3-422c-9bd9-24bf663b4a61.png)

 * We rank teams within each window: 


![Teams_-_ranking_per_group](https://user-images.githubusercontent.com/46305342/200454838-8f1dc5f3-27b9-4266-b15e-2f0b5809098a.png)

 * Combine the data back together and select all teams which have rank 1. And we have all group winners:
![Group_Winners](https://user-images.githubusercontent.com/46305342/200455149-f34d1dd2-0338-43e7-8f27-d965c126809d.png)

