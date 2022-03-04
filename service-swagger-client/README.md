# swagger-java-client

Setlist Organizer API
- API version: 1.0.0
  - Build date: 2022-02-28T21:26:17.069758+03:00[Europe/Minsk]

'Setlist Organizer' is a web application for organizing repertoires of musical bands.


*Automatically generated by the [Swagger Codegen](https://github.com/swagger-api/swagger-codegen)*


## Requirements

Building the API client library requires:
1. Java 1.7+
2. Maven/Gradle

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn clean install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn clean deploy
```

Refer to the [OSSRH Guide](http://central.sonatype.org/pages/ossrh-guide.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>io.swagger</groupId>
  <artifactId>swagger-java-client</artifactId>
  <version>1.0.0</version>
  <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "io.swagger:swagger-java-client:1.0.0"
```

### Others

At first generate the JAR by executing:

```shell
mvn clean package
```

Then manually install the following JARs:

* `target/swagger-java-client-1.0.0.jar`
* `target/lib/*.jar`

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java
import com.epam.brest.*;
import com.epam.brest.auth.*;

import io.swagger.client.api.BandApi;

import java.io.File;
import java.util.*;

public class BandApiExample {

    public static void main(String[] args) {
        
        BandApi apiInstance = new BandApi();
        try {
            List<Band> result = apiInstance.bands();
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling BandApi#bands");
            e.printStackTrace();
        }
    }
}
import com.epam.brest.*;
import com.epam.brest.auth.*;

import io.swagger.client.api.BandApi;

import java.io.File;
import java.util.*;

public class BandApiExample {

    public static void main(String[] args) {
        
        BandApi apiInstance = new BandApi();
        Band body = new Band(); // Band | 
        try {
            Integer result = apiInstance.createBand(body);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling BandApi#createBand");
            e.printStackTrace();
        }
    }
}
import com.epam.brest.*;
import com.epam.brest.auth.*;

import io.swagger.client.api.BandApi;

import java.io.File;
import java.util.*;

public class BandApiExample {

    public static void main(String[] args) {
        
        BandApi apiInstance = new BandApi();
        Integer id = 56; // Integer | 
        try {
            Integer result = apiInstance.deleteBand(id);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling BandApi#deleteBand");
            e.printStackTrace();
        }
    }
}
import com.epam.brest.*;
import com.epam.brest.auth.*;

import io.swagger.client.api.BandApi;

import java.io.File;
import java.util.*;

public class BandApiExample {

    public static void main(String[] args) {
        
        BandApi apiInstance = new BandApi();
        Integer id = 56; // Integer | 
        try {
            Band result = apiInstance.getBandById(id);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling BandApi#getBandById");
            e.printStackTrace();
        }
    }
}
import com.epam.brest.*;
import com.epam.brest.auth.*;

import io.swagger.client.api.BandApi;

import java.io.File;
import java.util.*;

public class BandApiExample {

    public static void main(String[] args) {
        
        BandApi apiInstance = new BandApi();
        Band body = new Band(); // Band | 
        try {
            Integer result = apiInstance.updateBand(body);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling BandApi#updateBand");
            e.printStackTrace();
        }
    }
}
```

## Documentation for API Endpoints

All URIs are relative to *http://localhost:8088*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*BandApi* | [**bands**](docs/BandApi.md#bands) | **GET** /bands | Get information for all bands based on their IDs
*BandApi* | [**createBand**](docs/BandApi.md#createBand) | **POST** /bands | Create a new band
*BandApi* | [**deleteBand**](docs/BandApi.md#deleteBand) | **DELETE** /bands/{id} | Delete a band
*BandApi* | [**getBandById**](docs/BandApi.md#getBandById) | **GET** /bands/{id} | Get information for a single band identified by its unique ID
*BandApi* | [**updateBand**](docs/BandApi.md#updateBand) | **PUT** /bands | Update a band
*BandsApi* | [**bandsDto**](docs/BandsApi.md#bandsDto) | **GET** /bands_dto | Get information for all bands with their repertoire duration and track count
*TrackApi* | [**createTrack**](docs/TrackApi.md#createTrack) | **POST** /repertoire | Create a new track
*TrackApi* | [**deleteTrack**](docs/TrackApi.md#deleteTrack) | **DELETE** /repertoire/{id} | Delete a track
*TrackApi* | [**getTrackById**](docs/TrackApi.md#getTrackById) | **GET** /repertoire/{id} | Get information for a single track identified by its unique ID
*TrackApi* | [**tracks**](docs/TrackApi.md#tracks) | **GET** /repertoire | Get information for all tracks based on their IDs
*TrackApi* | [**updateTrack**](docs/TrackApi.md#updateTrack) | **PUT** /repertoire | Update a track
*TracksApi* | [**findAllTracksWithBandName**](docs/TracksApi.md#findAllTracksWithBandName) | **GET** /tracks_dto | Get information for all tracks with their band names
*TracksApi* | [**findAllTracksWithBandNameByBandId**](docs/TracksApi.md#findAllTracksWithBandNameByBandId) | **GET** /repertoire/filter/band/{bandId} | Get information about band&#x27;s tracks
*TracksApi* | [**findAllTracksWithReleaseDateFilter**](docs/TracksApi.md#findAllTracksWithReleaseDateFilter) | **GET** /repertoire/filter | Get information for tracks with their release dates between {fromDate} and {toDate}
*VersionApi* | [**version**](docs/VersionApi.md#version) | **GET** /version | Get information for the API version

## Documentation for Models


## Documentation for Authorization

All endpoints do not require authorization.
Authentication schemes defined for the API:

## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author

