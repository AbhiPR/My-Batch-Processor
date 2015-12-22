CS 5348 Operating Systems Concepts

Project 1

My Batch Processor

Name: Abhishek Palani Rajan

netid: axp154830

This project can be executed by the following command in command prompt:

java -jar <EXECUTABLE JAR FILE OF THE PROJECT> work\<YOUR XML FILE>

For example:
java -jar myBatchProcessor.jar work\batch1.xml

The scope of this project is to build a tool that parses and executes a batch files containing a number of commands. The batch file consists of XML tags which gives information about the command to be used, the directory and the files on which the command should be executed. Each command executed by our batch processor will be executed as a process and communicate using files or pipes.

This project is designed to execute 4 types of batch files.
(Put all the batch files along with addLine.jar and avgFile.jar in a directory named "work" or create a directory based on the changes made in the "wd" tag in the xml file)

1. batch1.xml - this batch executes DOS dir command and directs the output to a file.
2. batch2.xml - this batch executes "sort" and "/r sort" command on the specified file and directs the output to a file.
3. batch3.xml - this batch finds the sum of the list of numbers in each line of a given text file using "addLines.jar" and finds the average of the numbers obtained after finding the sum using "avgFile.jar".
4. batch4.xml - this batch executes "sort" command on the specified file and directs the output for executing "/r sort" command using pipes. The final output is directed to a file.




