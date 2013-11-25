ScrudJavaServer
===============

Scrud is a JSON based messaging format designed to be used over web sockets.  The ScrudJavaServer is a framework which facilitates using Scrud to create a J2EE server application.

Scrud Messaging format
----------------------

### Subscriptions ###

Subscriptions are initiated by the client with a subscribe with the following properties:

<table>
  <tr>
    <th>Name</th>
    <th>Type</th>
    <th>Description or <b>Content</b></th>
  </tr>
  <tr>
    <td>message-type</td>
    <td>String</td>
    <td><b>subscribe</b></td>
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
</table>

A reply will be sent for a successful subscription with the following properties:

<table>
  <tr>
    <th>Name</th>
    <th>Type</th>
    <th>Description or <b>Content</b></th>
  </tr>
  <tr>
    <td>message-type</td>
    <td>String</td>
    <td><b>subscription-success</b></td>
  </tr>
  <tr>
    <td>client-id</td>
    <td>String</td>
    <td>The client-id provided by the client in the subscribe message</td>
  </tr>
  <tr>
    <td>resources</td>
    <td>Array</td>
    <td>The current resources matching this subscription</td>
  </tr>
  <tr>
    <td>filter</td>
    <td>unknown</td>
    <td>The filter which will be evaluated to determine if resources match the subscription</td>
  </tr>
</table>

Note that filters are not currently supported.

An unsuccessful subscription will result in a message with the following properties:

TODO




