
COSC 4333 - ChatRoom Project
Due Date: 11/27/2023
Group Members:
Matthew Munsinger
Ayobami Olusegun
Dillon Olbrich
This program is written in C language using Linux/Ubuntu.
This program is implements a multi-threaded chat room service.
The system has one chat server that is multi-threaded and can host several chat rooms and multiple chat clients. It uses socket interfaces to 
implement the network communications. 
Features:
-Multithreading: The server supports multiple clients concurrently using threads.
-Chat Rooms: Users can join different chat rooms to communicate with others.
-Real-time Messaging: Messages are relayed in real-time within the same chat room.
Chat Server
There is only one chat server, whose IP address is known. It listens on a well-known port to accept JOIN request from the client. The JOIN 
request contains the chat room name the client wants to join. If the chat room first does not exist, the chat server creates a new thread to 
serve for this chat room will have a new port. It will receive the chat messages for chat room first, and forward the messages to all chat 
clients who are already in first. If the chat room first already exists, the chat server can make the client join the chat room directly. When 
a client wants to leave a chat room, the client sends a LEAVE request. When there is no client in a chat room anymore, the corresponding chat 
room should be deleted.
Chat Client
Different clients can run on different virtual machines. 
When a client wants to join a specific chat room, they send a JOIN request to the well-known port of the chat server. When a client wants to 
leave a specific chat group, they can send a LEAVE request.
You will need:
A working Ubuntu/Linux system or Virtual Box running a virtual machine with Ubuntu.
Requirements
-C compiler (e.g., gcc)
-POSIX threads library (pthread)
Setting up Virtual Box w/ Ubuntu:
If you have Virtual Box with Ubuntu you can skip this step.
You will need virtual box and an Ubuntu ISO file
Download the appropriate platform package for your system 
https://www.virtualbox.org/wiki/Downloads
Download the appropriate Ubuntu for your system
https://ubuntu.com/download
Ubuntu 22.04.3 LTS is the most up to date, you can download this for your VM or Ubuntu 23.10
NOTE: Make sure to remember where this file is located, you will need to set the ISO file path of your VM with this.
Once virtual box is installed create a new VM via the "New" button.
Give the name of the machine. 
Make sure the type is Linux and Version Ubuntu 64-bit or 32-bit depending on your system.
Also select your Machine Folder to the folder of your choice. This is where your VM will be saved.
Adjust your memory size, this depends on the memory size of your computer and how many VM's you decide to run. Do not set this too large.
You can leave your hard disk option as default to create a virtual hard disk.
You can leave storage on physical hard disk as dynamically allocated.
Adjust the file location and size depending on your computer.
Click create. 
Before starting the new VM, you will need to provide the ISO file path for your VM.
Select the VM you created. 
Click Settings -> Storage
Under Controller: IDE
Click the Empty disk
Under Attributes click the disk to the side of Optical Drive.
Now this is where you need to find the ISO Ubuntu file we downloaded and select it.
Once you have this selected you can click Ok.
You can now select your VM and start.
Follow the installation process. Once you are finished with the Installation process.
Open the terminal and enter the following commands:
sudo apt update
sudo apt install -y build-essential linux-headers-$(uname -r)
Once these are finished you can install the virtual box guest additions
Go to devices
Insert Guest Additions CD image..
You should now see the VBox_GAs_*
If it did not autorun click the disk with this CD icon on the side and open the location.
Now right click and open in terminal
Enter the command ./autorun.sh and enter your password
Now restart your VM and you should be good to go.
Once you have your system installed with Virtual Box and Ubuntu you can proceed to installing GCC.
Installing GCC:
If you have GCC installed and updated you can skip this step.
GCC comes intalled with Ubuntu systems
Check version by entering in the termial window "gcc --version"
If you do not have gcc installed please install the build-essential by the following command
sudo apt install build-essential
This will install gcc and important packages to compile and run the program.
Once this is installed or updated, download the source files and move them into a directory of your choice.
Compiling and Running:
We now need to traverse the Linux file system in order to compile and run.
Traverse the file system by moving foward by the command "cd [destination]" ex: cd Desktop
If you know the direct file path enter the command "cd [file path]"
If you would like to traverse back a folder you can enter the command "cd.." this will move you back one directory.
Once you are in the location of the ChatroomClient.c and ChatroomServer.c
You can now compile the C files
With the commands:
gcc ChatroomClient.c -o client
gcc ChatroomServer.c -o server
This will compile the C files into an executable
In order to execute the Server and Client
First run the Server executable with the command:
./server
Followed by the Client executable with the command:
./client JOIN
Enter a username when prompted
Enter a chatroom name when prompted. If chatroom doesnt exist, it will be created.
Start chatting with other users in the same chat room.
Use 'LEAVE' to exit chat and close connection.
