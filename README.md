# BiDiServer

BiDiServer is a library that provides bi-directional communication between a server and multiple clients. You can use this library as a basis for your own client-server implementation. A typical use case is a turn-based game, where games are hosted on the server, and the players can submit their moves to the server and get notified when the evaluation of the turn is available.

## Overview

Clients can send synchronous requests to the server and wait for a response. The server can push asynchronous notifications to the clients. If the server pushes a notification to a client while the client is not connected, the notification is stored on the server and pushed to the client as soon as the client reconnects.

Data is stored on the server as data sets. A data set has a unique ID, and a list of users who are authorized to read, update, or delete the data set, and the payload of the data set. And it contains the use case-specific payload, for example, the game data.

The abstract client and server provide a set of pre-defined services for user administration, server administration, and data set handling. Concrete implementations must define their use case-specific services based on the API for custom-defined requests and notifications.

The server administrator creates users and thereby gives clients access to the server. Clients activate their users and can then store their credentials in a local file. The credential file contains the public key of the server to encrypt request messages sent to the server and the private key of the user to decrypt the response message received from the server. The private key of the user is also required to decrypt notifications received from the server.

The administration of the server is also performed through the client, using the administrator credentials file which is created at the first start of the server. The administrator can create, update, and delete users, query the server status, download the server log, and shut down the server from remote.

The server can accept multiple requests in parallel. Every request to the server spawns a thread. Up to 200 parallel threads are allowed, before the server refuses the connection. This library is suitable for scenarios with a limited number of users, typically up to 100 users. With more than 50 users being connected and sending requests at the same time, the server may run out of threads.

## Prerequisites

* Java 1.8 or later
* [Gson 2.9.1, or later](https://github.com/google/gson)

## Implementation Guide

* [Server](ImplServer.md)
* [Client](ImplClient.md)
* [Custom Requests](ImplCustomRequests.md)
* [Custom Notifications](ImplCustomNotifications.md)
* [Example Implementation 1](ImplExample1.md)
* [Example Implementation 2](ImplExample2.md)

## License

[GNU Affero General Public License v3.0](LICENSE)

