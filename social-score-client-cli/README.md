
## Testing

Could find a way to test the console part, Since Scanner is final class clould find way to test it

# Social Score Client

A spring Boot CLI  application.



## How to run

Application can run in two modes

- Single Command execution mode
- Multi Command execution mode 

This is determined by config property `app

single.exec=true or false` , default is true

in Multi command momde type `EXIT` to close application

## logging
Currently logs are sparyed to to the console while application run


## Testing

Could find a way to test the console part, Since Scanner is final class clould find way to test it

## Good To have

- Logs to seprate file
- Testing using spring cloud contract or wire mock. 



## Note

 The client is desinged to keeping the spcae to expand the functionlities mentioned in API interface from core 

  As i mentioned in another README form micro service point of view client doesn't need to implement those APi interfaces from core

  I implemented those interface just to demonstarate how the client can be expanded. based on requirement

