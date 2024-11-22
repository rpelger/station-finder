# Station Finder Application

A full-stack Java web-application for finding charging stations for electric vehicles in Germany.

## Local Setup

To run locally you require:
* Java 21
* connection to Internet (for downloading current list of stations)

To start the application locally just type
```
$ ./gradlew run
```

## Design Principles

The application is designed according to the _Ports & Adapters_ pattern, 
also known as Hexagonal Architecture.

As an entry point to the application you should just look at [Main.java](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2FMain.java),
where the application is _configured_ by providing the required _Driven Adapters_
to the [ChargingStationsApp.java](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fapp%2FChargingStationsApp.java) and by starting the web-application (see: [JavalinWebApp.java](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fadapters%2Fweb%2FJavalinWebApp.java))
and thus exposing the _Driven Ports_ via a web interface. The ChargingStationApp implements
all _Driving Ports_ as a simple _facade_ by delegating functionality to disstinct _UseCases_.

### Users / Roles

(see: [User](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fapp%2Fmodel%2FUser.java))

The application defines 3 different User roles:

* SYSTEM_USER (for running automated jobs)
* ADMIN_USER (for maintaining application data, i.e. stations and users)
* CONSUMER_USER (for using the application, i.e. this is the end-user)
* _anonymous_ i.e. un-authenticated access is possible for registration and authentication

### Ports

These users can access the various use-cases implemented by the application via its respective driving port.
The use-cases require access to external systems, specified by _Driven Ports_. So there is always a relation of `Driving Port -> Use-Case -> Driven Port`

#### Driving 
(_see [package](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fapp%2Fports%2Fdriving)_)

The following list shows the _Driving Ports_ and the use-cases each port exposes.

* Port [ForAccessingPlatform](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fapp%2Fports%2Fdriving%2FForAccessingPlatform.java)
  * UseCase [RegisterUser](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fapp%2Fusecases%2FRegisterUser.java) (_anonymous user_)
  * UseCase [AuthenticateUser](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fapp%2Fusecases%2FAuthenticateUser.java) (_anonymous user_)
* Port [ForFindingStations](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fapp%2Fports%2Fdriving%2FForFindingStations.java)
  * UseCase [FindNearestStations](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fapp%2Fusecases%2FFindNearestStations.java) (CONSUMER)
  * UseCase [ViewStationDetails](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fapp%2Fusecases%2FViewStationDetails.java) (CONSUMER)
* Port [ForReviewingStations](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fapp%2Fports%2Fdriving%2FForReviewingStations.java) *)
  * UseCase [ListStationReviews](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fapp%2Fusecases%2FListStationReviews.java) (CONSUMER)
  * UseCase [AddStationReview](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fapp%2Fusecases%2FAddStationReview.java) (CONSUMER) 
* Port [ForMaintainingStations](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fapp%2Fports%2Fdriving%2FForMaintainingStations.java)
  * UseCase [UpdateStationOperator](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fapp%2Fusecases%2FUpdateStationOperator.java) (ADMIN)
  * UseCase [ImportChargingStations](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fapp%2Fusecases%2FImportChargingStations.java) (ADMIN, SYSTEM)

_*) This port may be merged with `ForFindingStations` in the future, as both expose CONSUMER use-cases._ 

#### Driven 
(see [package](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fapp%2Fports%2Fdriven))

The following list shows the _Driven Ports_ which are required by the use-cases (please see code for wiring: package [usecases](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fapp%2Fusecases))

* [ForObtainingStations](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fapp%2Fports%2Fdriven%2FForObtainingStations.java)
* [ForStoringStations](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fapp%2Fports%2Fdriven%2FForStoringStations.java)
* [ForStoringUsers](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fapp%2Fports%2Fdriven%2FForStoringUsers.java)

### Adapters
(see [package](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fadapters))

As Ports are just interfaces, defining the entry points (Driving Port) to the application and the interaction points with external systems (Driven Port), the application requires concrete _Adapters_
that implement the Ports and make them usable in a concrete way. 

The Driving Ports are implemented mainly via a web-adapter (see: [web](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fadapters%2Fweb)), but also via _Tests_. 

The Driven Adapters are implemented based on the external systems they interact with. Currently these are (InMemory-)DB-access (see: [db](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fadapters%2Fdb)) and a REST-client (see: [restclient](src%2Fmain%2Fjava%2Fcom%2Fcomsystoreply%2Flabs%2Fchargingstations%2Fadapters%2Frestclient))