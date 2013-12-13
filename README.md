ScrudJavaServer
===============

Scrud is a JSON based messaging format designed to be used over web sockets.  In addition to normal CRUD operation is supports subscriptions.

The ScrudJavaServer is a framework which facilitates using Scrud to create a J2EE server application.

Scrud Messaging format
----------------------

### Subscriptions ###

#### Initiating a subscription ####

Subscriptions are initiated by the client with a subscribe with the following properties:

<table>
  <tr>
    <th>Name</th>
    <th>Type</th>
    <th>Description or <em>Content</em></th>
  </tr>
  <tr>
    <td>message-type</td>
    <td>String</td>
    <td><em>subscribe</em></td>
  </tr>
  <tr>
    <td>client-id</td>
    <td>String</td>
    <td>A string which will be included in all messages relating to the resultant subscription</td>
  </tr>
  <tr>
    <td>resource-type</td>
    <td>String</td>
    <td>The type of resource for the subscription</td>
  </tr>
  <tr>
    <td>filter</td>
    <td>unknown</td>
    <td>The filter which will be evaluated to determine if resources match the subscription</td>
  </tr>
</table>

Note that filters are not currently supported.

A reply will be sent for a successful subscription with the following properties:

<table>
  <tr>
    <th>Name</th>
    <th>Type</th>
    <th>Description or <em>Content</em></th>
  </tr>
  <tr>
    <td>message-type</td>
    <td>String</td>
    <td><em>subscription-success</em></td>
  </tr>
  <tr>
    <td>client-id</td>
    <td>String</td>
    <td>The client-id provided by the client in the subscribe message</td>
  </tr>
  <tr>
    <td>resources</td>
    <td>JSON object containing resources mapped by their id</td>
    <td>The current resources matching this subscription</td>
  </tr>
</table>


An unsuccessful subscription will result in a message with the following properties:

TODO

#### Messages from the server relating to a subscription ####

Once a subscription is active the server can send a message indicating a new matching resource has been created:

<table>
  <tr>
    <th>Name</th>
    <th>Type</th>
    <th>Description or <em>Content</em></th>
  </tr>
  <tr>
    <td>message-type</td>
    <td>String</td>
    <td><em>created</em></td>
  </tr>
  <tr>
    <td>client-id</td>
    <td>String</td>
    <td>The client-id provided by the client in the subscribe message</td>
  </tr>
  <tr>
    <td>resource-id</td>
    <td>String</td>
    <td>The server side id of the newly created resource</td>
  </tr>
  <tr>
    <td>resource</td>
    <td>Object</td>
    <td>The newly created resource</td>
  </tr>
</table>

Or if a resource changes:

<table>
  <tr>
    <th>Name</th>
    <th>Type</th>
    <th>Description or <em>Content</em></th>
  </tr>
  <tr>
    <td>message-type</td>
    <td>String</td>
    <td><em>updated</em></td>
  </tr>
  <tr>
    <td>client-id</td>
    <td>String</td>
    <td>The client-id provided by the client in the subscribe message</td>
  </tr>
  <tr>
    <td>resource-id</td>
    <td>String</td>
    <td>The server side id of the modified resource</td>
  </tr>
  <tr>
    <td>resource</td>
    <td>Object</td>
    <td>The modified resource</td>
  </tr>
</table>

When a resource is deleted:

<table>
  <tr>
    <th>Name</th>
    <th>Type</th>
    <th>Description or <em>Content</em></th>
  </tr>
  <tr>
    <td>message-type</td>
    <td>String</td>
    <td><em>updated</em></td>
  </tr>
  <tr>
    <td>client-id</td>
    <td>String</td>
    <td>The client-id provided by the client in the subscribe message</td>
  </tr>
  <tr>
    <td>resource-id</td>
    <td>Object</td>
    <td>The modified resource</td>
  </tr>
</table>

Since a resource may be relevant to multiple subscriptions the server must send a message per matching subscription when a resource is created, updated or deleted.

When a resource no longer matches the filter for a subscription the following is sent:

<table>
  <tr>
    <th>Name</th>
    <th>Type</th>
    <th>Description or <em>Content</em></th>
  </tr>
  <tr>
    <td>message-type</td>
    <td>String</td>
    <td><em>no-longer-matches</em></td>
  </tr>
  <tr>
    <td>client-id</td>
    <td>String</td>
    <td>The client-id provided by the client in the subscribe message</td>
  </tr>
  <tr>
    <td>resource-id</td>
    <td>String</td>
    <td>The server side id of the modified resource</td>
  </tr>
  <tr>
    <td>resource</td>
    <td>Object</td>
    <td>The modified resource</td>
  </tr>
</table>

#### Messages from the client relating to a subscription ####

When a subscription is active a client may choose to end that subscription:

<table>
  <tr>
    <th>Name</th>
    <th>Type</th>
    <th>Description or <em>Content</em></th>
  </tr>
  <tr>
    <td>message-type</td>
    <td>String</td>
    <td><em>unsubscribe</em></td>
  </tr>
  <tr>
    <td>client-id</td>
    <td>String</td>
    <td>The client-id provided by the client in the subscribe message</td>
  </tr>
</table>

No response is sent from the server, whilst it is being processed additional messages relating to the subscription could be received but can be ignored.

### Create ###

To create a resource the client sends a message with the following properties:

<table>
  <tr>
    <th>Name</th>
    <th>Type</th>
    <th>Description or <em>Content</em></th>
  </tr>
  <tr>
    <td>message-type</td>
    <td>String</td>
    <td><em>create</em></td>
  </tr>
  <tr>
    <td>client-id</td>
    <td>String</td>
    <td>A string which will be included in the server's response</td>
  </tr>
  <tr>
    <td>resource-type</td>
    <td>String</td>
    <td>The type of resource for the subscription</td>
  </tr>
  <tr>
    <td>resource</td>
    <td>Object</td>
    <td>The resource to create</td>
  </tr>
</table>

The response for a successful creation will have the following properties:

<table>
  <tr>
    <th>Name</th>
    <th>Type</th>
    <th>Description or <em>Content</em></th>
  </tr>
  <tr>
    <td>message-type</td>
    <td>String</td>
    <td><em>create-success</em></td>
  </tr>
  <tr>
    <td>client-id</td>
    <td>String</td>
    <td>The client-id provided by the client in the create message</td>
  </tr>
  <tr>
    <td>resource-id</td>
    <td>String</td>
    <td>The server side id of the newly created resource</td>
  </tr>
  <tr>
    <td>resource</td>
    <td>Object</td>
    <td>The newly created resource</td>
  </tr>
</table>

The response if the create failed will contain the following:

TODO

### Read ###

TODO

### Update ###

TODO

### Delete ###

TODO

## Restrictions ##

+ If a client has a subscription to a resource type and then creates a resource of that type the server will need to send two messages to the client, _created_ and _create-success_.  The _created_ message must be sent by the server first, this allows the client to avoid creating duplicate objects to represent a resource.