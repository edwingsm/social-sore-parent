# Social Score

To complete the assignment 3 maven modules are used.

- Core
- Service
- Client

## CORE

It holds some POJO classes and enum used in both service and client 

```diff
it's not mandatory that client or service should user core for it's development. 
I used it based on reusability of some pojos 
but in a micro service world it's better to have less coupling in all means.
``` 

## Service

Expose REST end points for client to access 

## Client 

A Cli client application that communicates with service via HTTP protocol.

### Why chose this approach

I followed this approach based on following PROS

- Service can be scaled , modified & maintained independently 
- Since the contract is defined in service , different types of client can be developed using consuming the service
- Client application can be customized based on it's need without affeting the api.
  

- Can use  command-line/console libraries for client eg:picocli
