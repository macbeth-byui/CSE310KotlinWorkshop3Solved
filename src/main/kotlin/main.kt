/*
CSE310 Kotlin Workshop - Example 3 - Solution

This example will demonstrate conversion from Java to Kotlin.  This
will the obtain the current and forecast weather for a city
obtained from the Open Weather API.

Documentation:
https://openweathermap.org/api/one-call-api
*/

import com.google.gson.Gson
import java.util.Scanner
import java.net.URL
import java.io.IOException

/*  JSON Structure from Open Weather API:
    {
        current {
            temp
            wind_speed
            weather [
                {
                    description
                }
            ]
        }
        daily [
            {
                dt
                temp {
                    min
                    max
                }
                wind_speed
                weather [
                    {
                        description
                    }
                ]
            }
        ]
    }
 */

// Data classes only have a primary constructor with public member data

data class WeatherData(val current: WeatherCurrent, val daily: List<WeatherDaily>)

data class WeatherCurrent(val temp: Double, val wind_speed: Double, val weather: List<WeatherCondition>)

data class WeatherDaily(val dt: Int, val temp: WeatherTemperature, val wind_speed: Double, val weather: List<WeatherCondition>)

data class WeatherCondition(val description: String)

data class WeatherTemperature(val min: Double, val max: Double)

class WeatherManager(private val latitude: String, private val longitude: String, private val key: String) {

    fun getCurrent() {
        try {
            // The URL object in Kotlin has a readText function to read the data from the site
            val jsonText = URL("https://api.openweathermap.org/data/2.5/onecall?lat="+latitude+"&lon="+longitude+"&units=imperial&appid="+key).readText()

            // Use GSON to convert to a WeatherData object
            val gson = Gson()
            val data = gson.fromJson(jsonText, WeatherData::class.java)

            // Display the result
            if (data != null) println(data.current)
        } catch (ioe : IOException) {
            println(ioe)
        }
    }

    fun getForecast() {
        try {
            // The URL object in Kotlin has a readText function to read the data from the site
            val jsonText = URL("https://api.openweathermap.org/data/2.5/onecall?lat="+latitude+"&lon="+longitude+"&units=imperial&appid="+key).readText()

            // Use GSON to convert to a WeatherData object
            val gson = Gson()
            val data = gson.fromJson(jsonText, WeatherData::class.java)

            // Display the result
            if (data != null) {
                for (day in data.daily) {
                    println(day)
                }
            }
        } catch (ioe : IOException) {
            println(ioe)
        }
    }

}


/* The main function will test out the WeatherManager class. */
fun main(args: Array<String>) {
    val lat = "43.82602" // Rexburg
    val lon = "-111.78969"

    print("Enter Open Weather API Key: ")
    val scanner = Scanner(System.`in`)
    val key = scanner.nextLine();

    val mgr = WeatherManager(lat, lon, key)

    println("Current:")
    mgr.getCurrent()
    println("\nForecast:")
    mgr.getForecast()

}