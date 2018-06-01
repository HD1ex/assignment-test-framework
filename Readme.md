# Kit assignment test framework

This repository provides classes to simplify testing KIT Programming 1 assignments.

For further information take a look at the [wiki](link).

Also have a look at our [Discord-Server](https://discord.gg/RVxBMVa).

## Describtion
This project serves a solid base for creating code for assignments.

It especially encourages beginners to start test-driven-development, use git and the powerful features of an modern IDE.

## Quick start
I really recommend to use [IntelliJ]() as IDE. The Ultimate version is free for students and provides many features you don't want to miss.

### Git
The first thing you have to check is that [git](https://git-scm.com/download) is installed on your system. You can check this by opening a command prompt and by typing 'git'.
+ **On windows** git isn't [installed](https://git-scm.com/download) by default
+ **On linux and mac** git is mostly installed by default. If it isn't, just install the **git** package on your system.

### Download and import the files in a project
First you have to go to the start window of IntelliJ (close any opened project).

There you can click on **Check out from Version Control** and select **git**.

In there you can paste in the git link of this repo: https://git.scc.kit.edu/hdlex/assignment-test-framework.git

If you edit the **Parent Directory** here you may have to edit it later when creating the project.

Click **clone**.

IntelliJ now asks you to create a new Project. Click **yes**.

You can continue clicking next until you have to select the Project SDK. There you have to add your [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) by telling IntelliJ where to find it.

After that click **next** and then **finish**. Your project will now be created and opened.

### Setup the new project
Before you can start building, you have to add **JUnit 5.0** to the class-path of your project. 
We can let IntelliJ do this work for us.
To do so you have to search for an junit-import. So just hit Ctrl-Shift-F (search in all files), type junit and hit Enter.
Now your cursor should be at a red marked junit, so just hit ALT+ENTER (hotfix) and select **JUnit 5.0**.

The project should compile now. You can verify this by pressing **CTRL+F9** (Build Project).

### Start programming
You now can start programming just like any other project.
You probably want to add your `.java` source files into the `edu.kit.informatik` package.

### Writing a test
You now can write your first test. To switch files fast, just hit Ctrl-Shift+N.

First go to DummyMain.java in test\java\utility and replace the present Main class with your one.

Now go to SimpleTests.java in test\java\tests.

Here you can see a simple test function.

The easiest way to create a test is by specifying an input-output-file (io-file).

Io-files are resources loaded by tests. In a java project resource files should be separated from code files.
Therefore there is a resource folder in the test folder (src/test/resources).

SimpleTests has already a folder containing the io-file for the firstSimpleTest.

You can **navigate to the io file** by selecting firstSimpleTest and hitting Ctrl+Shift+N.

#### IO Files
The syntax of io files should be straight forward. You may recognize this syntax from your assignment sheets.

The following rules apply:
+ A line starting with '**>** ' models an input
+ The following lines after an input line is the expected output for that input.
+ If an expected output ends with '**...**', the test expects the output to start with the specified text
+ Error messages can be expected with the technique above or by specifying an example error message. 
Therefore a line starting with 'Error, ' is treated like a generic error message.
+ The first lines before the first input line are ignored and therefore can be used for commenting.
+ A **quit is automatically added** by the test.

### Run tests
Before you can run a test, you have to tell it what to test. This can be done by editing the **extended** class of the provided **DummyMain** in the according directory of the test.
So hit Ctrl+Shift+N to search for your dedicated DummyMain (probably you want to edit the file in **src/test/java/utility**)

To run all tests in a folder or class right-click on it and select '**Run Tests ...**'.