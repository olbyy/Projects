
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <pthread.h>
#define PORT 12345
#define MAX_CLIENTS 5
#define MAX_NAME_LENGTH 20
#define MAX_BUFFER_SIZE 1024
#define MAX_ROOM_NAME_LENGTH 20
struct Client {
 int socket;
 pthread_t thread;
 char name[MAX_NAME_LENGTH];
 char room[MAX_ROOM_NAME_LENGTH];
};
struct Room { 
 char name[MAX_ROOM_NAME_LENGTH];
 int client_count;
 struct Client clients[MAX_CLIENTS];
};
struct Room rooms[MAX_CLIENTS];
int room_count = 0;
pthread_mutex_t room_mutex = PTHREAD_MUTEX_INITIALIZER;
struct Client clients[MAX_CLIENTS];
int client_count = 0;
pthread_mutex_t client_mutex = PTHREAD_MUTEX_INITIALIZER;
void *handle_client(void *client_ptr) {
 struct Client *client = (struct Client *)client_ptr;
 int client_socket = client->socket;
 char buffer[MAX_BUFFER_SIZE] = {0};
 ssize_t valread;
 while ((valread = read(client_socket, buffer, MAX_BUFFER_SIZE)) > 0) {
 buffer[valread] = '\0'; // Ensure null-termination
 
 //Handle multi messaging
 // Broadcast the message to all clients in the same room with sender's name
 pthread_mutex_lock(&room_mutex);
 for (int i = 0; i < room_count; i++) {
 if (strcmp(rooms[i].name, client->room) == 0) {
 for (int j = 0; j < rooms[i].client_count; j++) {
 if (rooms[i].clients[j].socket != client_socket) {
 char message[MAX_BUFFER_SIZE + MAX_NAME_LENGTH + 3]; // +3 for ": " and null terminator
 snprintf(message, sizeof(message), "%s: %s", client->name, buffer);
 send(rooms[i].clients[j].socket, message, strlen(message), 0);
 }
 }
 break;
 }
 }
 pthread_mutex_unlock(&room_mutex);
 memset(buffer, 0, sizeof(buffer));
 }
 // Client disconnected
 pthread_mutex_lock(&room_mutex);
 for (int i = 0; i < room_count; ++i) {
 if (strcmp(rooms[i].name, client->room) == 0) {
 for (int j = 0; j < rooms[i].client_count; ++j) {
 if (rooms[i].clients[j].socket == client_socket) {
 // Remove the disconnected client
 close(client_socket);
 printf("User left '%s'\n", rooms[i].name);
 for (int k = j; k < rooms[i].client_count - 1; ++k) {
 rooms[i].clients[k] = rooms[i].clients[k + 1];
 }
 rooms[i].client_count--;
 break;
 }
 }
 // If the room is empty, remove the room
 if (rooms[i].client_count == 0) {
 for (int k = i; k < room_count - 1; ++k) {
 rooms[k] = rooms[k + 1];
 }
 printf("Room: '%s' removed due to no clients.\n", rooms[i].name);
 room_count--;
 
 }
 break;
 }
 }
 pthread_mutex_unlock(&client_mutex);
 free(client_ptr);
 pthread_exit(NULL);
}
int find_room_index(const char *room_name) {
 for (int i = 0; i < room_count; ++i) {
 if (strcmp(rooms[i].name, room_name) == 0) {
 return i;
 }
 }
 return -1;
}
int main() {
 int server_fd, new_socket;
 struct sockaddr_in address;
 int opt = 1;
 int addrlen = sizeof(address);
 // Create socket
 if ((server_fd = socket(AF_INET, SOCK_STREAM, 0)) == 0) {
 perror("socket failed");
 exit(EXIT_FAILURE);
 }
 // Set socket options
 if (setsockopt(server_fd, SOL_SOCKET, SO_REUSEADDR, &opt, sizeof(opt))) {
 perror("setsockopt");
 exit(EXIT_FAILURE);
 }
 address.sin_family = AF_INET;
 address.sin_addr.s_addr = INADDR_ANY;
 address.sin_port = htons(PORT);
 // Bind the socket
 if (bind(server_fd, (struct sockaddr *)&address, sizeof(address)) < 0) {
 perror("bind failed");
 exit(EXIT_FAILURE);
 }
 // Listen for incoming connections
 if (listen(server_fd, MAX_CLIENTS) < 0) {
 perror("listen");
 exit(EXIT_FAILURE);
 }
 printf("Server listening on port %d...\n", PORT);
 while (1) {
 // Accept a new connection
 if ((new_socket = accept(server_fd, (struct sockaddr *)&address, (socklen_t *)&addrlen)) < 0) {
 perror("accept");
 exit(EXIT_FAILURE);
 }
 printf("New connection accepted!\n");
 // Receive client name and room
 char name[MAX_NAME_LENGTH];
 char room[MAX_ROOM_NAME_LENGTH];
 recv(new_socket, name, MAX_NAME_LENGTH, 0);
 recv(new_socket, room, MAX_ROOM_NAME_LENGTH, 0);
 // Check if the room already exists
 int room_index = find_room_index(room);
 if (room_index == -1) {
 // printf("Room: %s does not exist\n", room); // Debug
 // Create a new room
 if (room_count < MAX_CLIENTS) {
 struct Room *new_room = &rooms[room_count];
 strncpy(new_room->name, room, MAX_ROOM_NAME_LENGTH);
 new_room->client_count = 0;
 room_count++;
 room_index = room_count - 1;
 printf("Room: %s created!\n", room);
 } else {
 // Server at maximum capacity, reject the client
 printf("Server at maximum capacity. Connection rejected.\n");
 close(new_socket);
 pthread_mutex_unlock(&room_mutex);
 continue;
 } 
 //
 }
 printf("User: %s added to '%s'\n", name, room);
 // Create a new thread for the client
 struct Client *client = malloc(sizeof(struct Client));
 client->socket = new_socket;
 strncpy(client->name, name, MAX_NAME_LENGTH);
 strncpy(client->room, room, MAX_ROOM_NAME_LENGTH);
 memset(name, 0, sizeof(name)); // BUG FIX: Clear contents of name
 memset(room, 0, sizeof(room)); // BUG FIX: Clear contents of room
 if (rooms[room_index].client_count < MAX_CLIENTS) {
 rooms[room_index].clients[rooms[room_index].client_count] = *client;
 rooms[room_index].client_count++;
 pthread_create(&(client->thread), NULL, handle_client, (void *)client);
 pthread_detach(client->thread); // Detach the thread to avoid memory leaks
 } else {
 // Room at maximum capacity, reject the client
 printf("Room '%s' at maximum capacity. Connection rejected.\n", room);
 close(new_socket);
 free(client);
 }
 pthread_mutex_unlock(&room_mutex);
 }
 close(server_fd);
 return 0;
}
