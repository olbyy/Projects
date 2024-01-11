
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <pthread.h>
#define MAX_BUFFER_SIZE 1024
#define MAX_NAME_LENGTH 20
#define MAX_ROOM_NAME_LENGTH 20
int sock = 0;
// Overright std out
void str_overwrite_stdout()
{
 printf("%s", "> ");
 fflush(stdout);
}
void *receive_messages(void *arg)
{
 char buffer[MAX_BUFFER_SIZE] = {0};
 ssize_t valread;
 while (1)
 {
 valread = recv(sock, buffer, MAX_BUFFER_SIZE, 0);
 if (valread > 0)
 {
 printf("%s", buffer);
 str_overwrite_stdout();
 }
 else if (valread == 0)
 {
 printf("Server disconnected\n");
 exit(EXIT_SUCCESS);
 }
 else
 {
 perror("recv failed");
 exit(EXIT_FAILURE);
 }
 memset(buffer, 0, sizeof(buffer));
 }
 pthread_exit(NULL);
}
int main(int argc, char *argv[])
{
 if (argc != 2)
 {
 fprintf(stderr, "Usage: <COMMAND>");
 exit(EXIT_FAILURE);
 }
 //int PORT = atoi(argv[2]); //Accept port as arg
 int PORT = 12345; //Set PORT to well know
 char *command_arg = argv[1];
 struct sockaddr_in serv_addr;
 char buffer[MAX_BUFFER_SIZE] = {0};
 char name[MAX_NAME_LENGTH];
 char room[MAX_ROOM_NAME_LENGTH];
 // Create socket
 if ((sock = socket(AF_INET, SOCK_STREAM, 0)) < 0)
 {
 perror("socket creation error");
 return -1;
 }
 serv_addr.sin_family = AF_INET;
 serv_addr.sin_addr.s_addr = inet_addr("127.0.0.1"); //server IP known.
 serv_addr.sin_port = htons(PORT);
 if (strcmp(command_arg, "JOIN") == 0)
 {
 // Connect to server
 if (connect(sock, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0)
 {
 perror("connection failed");
 return -1;
 }
 else
 {
 printf("Welcome to the server\n"); // Debug: Successful connection
 }
 // Get client name and room
 printf("Enter your name: ");
 fgets(name, MAX_NAME_LENGTH, stdin);
 name[strcspn(name, "\n")] = '\0'; // Remove newline characters from name
 // Send client name
 send(sock, name, strlen(name), 0);
 printf("Enter the chat room: ");
 fgets(room, MAX_ROOM_NAME_LENGTH, stdin);
 room[strcspn(room, "\n")] = '\0'; // Remove newline character from the name
 // Send room name
 send(sock, room, strlen(room), 0);
 // Create a thread for receiving messages
 pthread_t receive_thread;
 if (pthread_create(&receive_thread, NULL, receive_messages, NULL) < 0)
 {
 perror("could not create thread");
 return -1;
 }
 // Communication with the server
 while (1)
 {
 str_overwrite_stdout();
 fgets(buffer, MAX_BUFFER_SIZE, stdin);
 send(sock, buffer, strlen(buffer), 0);
 if (strcmp(buffer, "LEAVE\n") == 0)
 {
 printf("You have left the chat.\n");
 // Notify other clients that the user has left
 char exitMessage[MAX_BUFFER_SIZE];
 snprintf(exitMessage, sizeof(exitMessage), "%s has left the chat\n", name);
 send(sock, exitMessage, strlen(exitMessage), 0);
 return 0; // terminate program after leaving
 }
 memset(buffer, 0, sizeof(buffer));
 }
 pthread_join(receive_thread, NULL); // Wait for the receive thread to finish
 }
 else
 {
 printf("'%s' is not a valid argument\n", command_arg);
 }
 close(sock);
 return 0;
}
