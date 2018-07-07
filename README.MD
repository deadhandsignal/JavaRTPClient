#  Cork Campus Radio Java/RTP Client

‘Cork Campus Radio RTP Client’(CCR RTP Client), allows the user to listen to an RTP audio stream that is broadcast over a network, (the internet, college intranet). User can also view an up to date schedule of the station’s programmes for the any day of the week, while listening to the station’s programmes. 

User can also listen to any other RTP stream. The application implements an ‘RTP Session History’ list which keeps track of any steams which have been listened to in a particular session. I have also incorporated an audio file player into the ‘Radio Client”. This player allows the user to listen to an audio file(s) of the ‘MP3’ and ‘WAV’ file formats.

The ‘Cork Campus Radio RTP Admin’ (CCR RTP Admin), allows the radio station to update the stations programme schedule through an intuitive Graphical User Interface (GUI) and view the schedule for any day of the week. The radio station administrator can also record a live stream from their live RTP transmission, allowing them to easily save programmes in compressed (‘MP3)’ or uncompressed (‘WAV’) audio file formats. 

Full Dissertation can be read here:

[Thomas Kilroy - Cork Campus Radio JAVA/RTP Client - M.Sc. Multimedia Technology](https://docs.google.com/document/d/1ttyFScsTflYy3plaUvlpgWsAOaWQzgErXUCZEsxXGuI/edit?usp=sharing)

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
