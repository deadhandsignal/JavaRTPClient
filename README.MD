#  Cork Campus Radio JAVA/RTP Client


There are two parts to elements to this application. 

The first element of my project, and the main body of work, is designed specifically for listeners of the college radio station (especially UCC staff and students), ‘Cork Campus Radio’ using RTP (Real Transport Protocol, see Glossary). This element of my project, which I will refer to in this report as the ‘Cork Campus Radio RTP Client’ (CCR RTP Client), allows the user to listen to an RTP audio stream that is broadcast over a network, (the internet, college intranet). Although any RTP can be received and listened to with this application, my radio client was created, more specifically, to allow users to easily listen to the University college radio station. While listening to the college radio station, my application also enables the user to view an up to date schedule of the station’s programmes for the any day of the week, while listening to the station’s programmes. 
Should the user wish to listen to an RTP steam other than that of the ‘Cork Campus Radio’, my application will provide a window where they can enter their own parameters and listen to a user specified RTP stream. The application implements an ‘RTP Session History’ list which keeps track of any steams which have been listened to in a particular session, so the user does not have to re enter details of any session already listened to.  I have also incorporated an audio file player into the ‘Radio Client”. This player allows the user to listen to an audio file(s) of the ‘MP3’ and ‘WAV’ file formats.

The second element of my project is essentially an administrator’s version of the ‘Cork Campus Radio RTP Client’. This element of my project is to be used by the members/employees of the college radio station itself. Incorporating all the elements of ‘CCR RTP Client’ outlined above, this version of the application, which I will refer to as the ‘Cork Campus Radio RTP Admin’ (CCR RTP Admin), allows the radio station to update the stations programme schedule through an intuitive Graphical User Interface (GUI) and view the schedule for any day of the week. The radio station administrator can also record a live stream from their live RTP transmission, allowing them to easily save programmes in compressed (‘MP3)’ or uncompressed (‘WAV’) audio file formats. 
I feel I achieved all the objectives I set out above. Certainly, there are elements of my work I would have wished to improve and I certainly would have preferred to implement my project in a shorter length of time. 


## Getting Started

To run locally download this repository to your machine. 

### Prerequisites

1. Install the Java SDK 

http://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.html

2. Install the Java Media Framework 

http://www.oracle.com/technetwork/java/javase/download-142937.html

3. Add the Java Media Framework to your CLASSPATH:

WINDOWS:
 

```sh
    set CLASSPATH=%JMFDIR%\lib\jmf.jar;%JMFDIR%\lib\sound. jar;.;%CLASSPATH%
```

LINUX/OSX:


```sh
     export CLASSPATH=/Library/Java/Extensions/jmf.jar
```

Or you can add to the existing classpath like this:

```sh
export CLASSPATH=$CLASSPATH:/path/to/some.jar:/path/to/some/other.jar
```

## To compile and run

##### For Admin version:

Compile:
```sh
javac CcrRtpAdmin.java
```

Run:
```sh
java CcrRtpAdmin
```

##### For Client version:

Compile:
```sh
javac CcrRtpClient.java
```

Run:
```sh
java CcrRtpClient
```


## Built With

* [Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html) - It's Java what did it. 


## Authors

* **Thomas Kilroy** - *Initial work* - [deadhandsignal](https://github.com/deadhandsignal)


## Acknowledgments

* A huge debt of gratitude to my lecturer Dr. Sabin Tabirca of University College Cork. 


## Meta
Thomas Kilroy – [@tpkilroy](https://twitter.com/tpkilroy) – thomas.kilroy@city.ac.uk

[https://github.com/deadhandsignal](https://github.com/deadhandsignal/)

## Contributing

1. Fork it (<https://github.com/deadhandsignal/kilroyreact/fork>)
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -am 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request

<!-- Markdown link & img dfn's -->
[dh-image]: https://i0.wp.com/www.deadhandsignal.com/wp-content/uploads/2017/12/cropped-Current2-1.png?w=1596
[dh-url]: https://github.com/deadhandsignal
[wiki]: https://github.com/deadhandsignal/kilroyreact/wiki
