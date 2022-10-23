/**
 * A library that provides bi-directional communication between a server and clients.
 * Clients can send synchronous requests to the server. The server can push asynchronous
 * notifications to the clients. If the server pushes a notification to a client while the
 * client is not connected, the notification is stored on the server and pushed to the client
 * as soon as the client reconnects.
 * <p>
 * The server administrator creates users and thereby gives clients access to the server.
 * Clients activate their users and can then store their credentials in a local file. 
 * The credential file contains the public key of the server to encrypt request messages sent
 * to the server and the private key of the user to decrypt the response message received from
 * the server. The private key of the user is also required to decrypt notifications received
 * from the server. 
 * </p>
 * <p>
 * The administration of the server is also performed through the client, using the administrator
 * credentials file which is created at the first start of the server. The administrator can create,
 * update, and delete users, query the server status, download the server log, and shut down
 * the server from remote.
 * </p>
 * <p>
 * Data is stored on the server as data sets. A data set has a unique ID, a list of users who
 * are authorized to read, update, or delete the data set, and the payload of the data set.
 * </p>
 * <p>
 * The server can accept multiple requests in parallel. Every request to the server spawns a
 * thread. Up to 200 parallel threads are allowed, before the server refuses the connection.
 * This library is suitable for scenarios with a limited number of users, typically up to
 * 100 users. With more than 50 users being connected and sending requests at the same time,
 * the server may run out of threads.
 * </p>
 * <p>
 * A typical use case for the library is the client-server communication for a turn-based game,
 * where games are hosted on the server, and the players can submit their moves to the server
 * and get notified when the evaluation of the turn is available.
 * </p>
 *
 * @since 1.0.0
 * @author spielwitz
 * @version 1.0.0
 */
package spielwitz.biDiServer;