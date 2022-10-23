# Example Implementation 1

Clone the BiDiServer repository to your local system. In package `test.testSeverAndClient`, you can find a test implementation for the server and the client.

First, run the server `TestServer`. Upon first start, it will create a folder named *ServerData* and some sub-folders. This is where the server will store its configuration and user data.

Then, while the server is running, run `ClientTester`. Select user  `_ADMIN`. The ClientTester picks the client credential file `_ADMIN_localhost_56084` which was created upon first start of the server, from the ServerData folder. In real server implementations, you as the administrator should immediatedly move the admin credential file from the server to a safe folder on client side.

Now, create one or more users. The ClientTester only offers the user names *User0* to *User9*. Select *Create User (Admin)* and select a user, for example, *User0*. This creates an inactive user on the server and returns the data that the user needs for activation. In real server implementation, you would can this data to the user on a memory stick, in an encrypted e-mail, or by any other (more or less safe) means. 

In the ClientTester, press *ActivateUser* and select the user that you created in the previous step. In real implementation, activation of a user is done by the user, not by the administrator. Activation of the user creates a client configuration which can be written to a file and be reloaded when the client is started.

After the user has been activated successfully, you can launch another instance of the ClientTester. Upon start-up, select the new user, for example, *User0*. As you can see at the bottom of the UI, the client has established a notification socket to the server. This means, that the client is now ready to receive push notifications from the server.

Now create some test data. The ClientTester offers the button *CreateGame* which can create two different data sets *Game0* and *Game1*. Choose either Game0 or Game1. The third option is just a negative test with a data set name that contains invalid characters. The "game" is stored on the server as a data set with a unique ID and the names of the users who are allowed to read, update, or delete the data set. In the ClientTester, *Game0* is assigned to some users, and *Game1* to others. Don't worry that a game is assigned to users who might even not exist at this point in time. It's just this test implementation.

You can now perform other actions like reading, updating, or reading games, or get a list of all users on the server, except the administrator user.

Change back to the ClientTester of the administrator and create more users. Activate them as described before, and open new instances of the ClientTester. You can now write messages from one user to another. When you write a message to a user while that user is currently not connected, the user will receive the message once he or she reconnects to the server.

Also watch the data that is created in folder *ServerData* while you are testing.

[Back to overview](README.md)

