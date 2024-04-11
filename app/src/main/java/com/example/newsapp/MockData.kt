package com.example.newsapp

import android.os.Build
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*


object MockData {
    val topNewsList = listOf<NewsData>(
        NewsData(
            id=1,
            image= R.drawable.clinton,
            author="Not Available",
            title="Clinton lost elections",
            description = "Clinton lost elections to Donald Trump",
            publishedAt = "2022-06-23T04:23:37:00Z"
        ),
        NewsData(
            id=2,
            image= R.drawable.guilty,
            author="Claudia",
            title="Found Guilty",
            description = "The accuser was found guilty. The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.The accuser was found guilty." +
                    "The accuser was found guilty. The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.The accuser was found guilty." +
                    "The accuser was found guilty. The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.The accuser was found guilty." +
                    "Very bad" +
                    "" +
                    "The accuser was found guilty. The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.The accuser was found guilty.",
            publishedAt = "2022-06-23T05:23:37:00Z"
        ),
        NewsData(
            id=3,
            image= R.drawable.gunman,
            author="Claudia",
            title="Gunman shots people",
            description = "A gunman shots at a lot of children in a school",
            publishedAt = "2022-06-23T06:23:37:00Z"
        ),
        NewsData(
            id=4,
            image= R.drawable.payday,
            author="Héctor",
            title="Today was a payday",
            description = "Many people gains a lot of money today",
            publishedAt = "2022-06-23T08:23:37:00Z"
        ),
        NewsData(
            id=5,
            image= R.drawable.trainer,
            author="Héctor",
            title="Trainer abused athletes",
            description = "The famous trainer abused of the gymnastics girls",
            publishedAt = "2022-06-23T09:23:37:00Z"
        ),
        NewsData(
            id=6,
            image= R.drawable.vaccine,
            author="Manuel",
            title="Pfizer develop a vaccine for Covid",
            description = "Pfizer lab has a promising vaccine for COVID-19",
            publishedAt = "2022-06-23T10:23:37:00Z"
        ),
        NewsData(
            id=7,
            image= R.drawable.wallstreet,
            author="Héctor",
            title="Great day on Wallstreet",
            description = "Many people happy on Wallstreet",
            publishedAt = "2022-06-23T12:23:37:00Z"
        )
    )

    fun getNews(newsId:Int?):NewsData{
        return topNewsList.first {it.id==newsId}
    }

    fun Date.getTimeAgo():String {
        val calendar = Calendar.getInstance( )
        calendar.time =this
        Log.d(this.toString(),"hola")
        val year = calendar.get(Calendar.YEAR)
        val month=calendar.get(Calendar.MONTH)
        val day=calendar.get(Calendar.DAY_OF_MONTH)
        val hour=calendar.get(Calendar.HOUR_OF_DAY)
        val minute=calendar.get(Calendar.MINUTE)

        val currentCalendar =Calendar.getInstance()
        val currentYear = currentCalendar.get(Calendar.YEAR)
        val currentMonth=currentCalendar.get(Calendar.MONTH)
        val currentDay=currentCalendar.get(Calendar.DAY_OF_MONTH)
        val currentHour=currentCalendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute=currentCalendar.get(Calendar.MINUTE)

        return if (year<currentYear){
            val interval=currentYear-year
            if (interval==1) "1 year ago" else "$interval years ago"
        }else if (month<currentMonth){
            val interval=currentMonth-month
            if (interval==1) "1 month ago" else "$interval months ago"
        }else if (day<currentDay) {
            val interval = currentDay - day
            if (interval == 1) "1 day ago" else "$interval days ago"
        }else if (hour<currentHour) {
            val interval = currentHour - hour
            if (interval == 1) "1 hour ago" else "$interval hours ago"
        }else if (minute<currentMinute) {
            val interval = currentMinute - minute
            if (interval == 1) "1 minute ago" else "$interval minutes ago"
        }else {
            "A moment ago"
        }
    }

    fun stringToDate(publishedAt:String):Date{
        val date=
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(publishedAt)
            }else {
                java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(publishedAt)
            }
        Log.d("published","$date")
        return date
    }
}