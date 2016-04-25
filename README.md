# Weather Forecast Application
![2016-04-25 17 02 28](https://github.com/droidizer/weather-forecast-app/blob/master/app/src/main/res/raw/Screenshot_2016-04-25-18-13-48.png)
## Introduction

## Table of contents

* [Introduction](#introduction)

* [How to install](#how-to-install)
	* [Gradle](#how-to-install#gradle)
	* [Adb](#how-to-install#adb)
* [How to run](#how-to-install)
	* [Gradle](#how-to-run#gradle)
	* [Adb](#how-to-run#adb)
* [How to build](#how-to-build)
	* [Gradle](#how-to-build#gradle)
	* [Adb](#how-to-build#adb)
* [Backend](#backend)
* [Contact](#contact)

## Known Issues

I recommend taking a look at missing features:

1. User entered city.

2. Current weather.

3. Selection of weather forecast for 5, 7, 16 days.

4. Saving city for pre-populating weather next time user starts the app.

5. Additional case like connectivity is handled.

## How to install

### HockeyApp

[Download build here:](https://rink.hockeyapp.net/apps/18f6915099a04d97a8da19d9a43e32a2/app_versions/1)


### Gradle

	gradle installDebug

## How to build
    
    gradle clean build 
	
## Backend

[Open weather API](http://openweathermap.org/api)

## Contributor

* [Sowmya Guru](mailto:sowmyasguru@gmail.com)

# Additional Installation Notes

1. Install Homebrew

        ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
     
2. Install Java with cask

        brew tap caskroom/versions
        brew cask install java7      
       
3. (Optional) use set jdk script in [˜/.bash_profile](https://gist.github.com/kibotu/bee00e5876a3bc134f43)                

4. Install android sdk
    
        brew install android-sdk

5. Set android home

        export ANDROID_HOME="/usr/local/opt/android-sdk"
         
6. Install android api level and extras

        android sdk 
    
7. (Bonus) updates in the future:
        
        brew update
        brew upgrade
        
8. Setup recommended [˜/gradle.properties](https://gist.github.com/kibotu/2e9601e92fac05cff72b)