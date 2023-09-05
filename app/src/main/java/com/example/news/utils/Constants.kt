package com.example.news.utils

import android.annotation.SuppressLint
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*

object Constants {

    const val MIN_SCALE = 0.75f

    const val BASE_URL = "https://newsapi.org/v2/"
    const val BASE_URL_COVID = "https://akashraj.tech/"
    const val BASE_URL_WEATHER = "https://api.openweathermap.org/data/2.5/forecast/"
    const val BASE_URL_IMAGE = "https://openweathermap.org/img/w/"
    const val BASE_URL_VIDEO = "https://api.news.zing.vn"


    const val Q_HOME = "dịch covid"
    const val Q_LATEST = "mới nhất"
    const val Q_VIETNAM = "việt nam"
    const val Q_WORLD = "thế giới"
    const val Q_ENTERTAINMENT = "giải trí"
    const val Q_SPORT = "thể thao"
    const val Q_BUSINESS = "kinh doanh"
    const val Q_SCIENCE = "khoa học"
    const val Q_LAW = "pháp luật"
    const val Q_EDUCATION = "giáo dục"
    const val Q_HEALTH = "sức khoẻ"
    const val Q_LIFE = "đời sống"
    const val Q_TRAVEL = "du lịch"
    const val Q_DIGITIZATION = "số hoá"
    const val Q_IDEA = "ý kiến"
    const val Q_TALK = "tâm sự"

    const val SORT_BY = "publishedAt"
    const val UNITS = "metric"
    const val TIMES = 18
//    const val API_KEY = "eb4ad6db55664fed859956dd12c3e9df"
    const val API_KEY = "059e7aee88c84da2adfea256156b7afe"
    const val API_KEY_WEATHER = "648a3aac37935e5b45e09727df728ac2"
    const val CNT = 7
    const val LANG = "vi"

    //video
    val cateidVideo = 472
    val pVideo = 1
    val cVideo = 50
    val appversionVideo = 202105011
    val platformVideo = "android"
    val uidVideo = "2002.SSZ-wu8DGTLrXQddcmj2d26MgUN20Ww4Aj2xe8mE3ueWtBlgXGq8n2RRlkwB3Wx4Dm.1"
    val keyVideo = "4Q450KNqJq3T3kRCXDBrIg=="


    const val LATEST_NEWS = "Mới nhất"
    const val VIETNAM = "Kinh Doanh"
    const val WORLD = "Thế Giới"
    const val ENTERTAINMENT = "Giải trí"
    const val SPORT = "Thể thao"
    const val SCIENCE = "Khoa học"
    const val LAW = "Pháp luật"
    const val EDUCATION = "Giáo dục"
    const val HEALTH = "Sức khoẻ"
    const val LIFE = "Đời sống"
    const val TRAVEL = "Du lịch"
    const val IDEA = "Ý kiến"
    const val TALK = "Tâm sự"

    const val TOTAL_PAGE_NEWS = 13
    const val PAGE_LATEST_NEWS = 0
    const val PAGE_VIETNAM_NEWS = 1
    const val PAGE_WORLD_NEWS = 2
    const val PAGE_ENTERTAINMENT_NEWS = 3
    const val PAGE_SPORT_NEWS = 4
    const val PAGE_SCIENCE_NEWS = 5
    const val PAGE_LAW_NEWS = 6
    const val PAGE_EDUCATION_NEWS = 7
    const val PAGE_HEALTH_NEWS = 8
    const val PAGE_LIFE_NEWS = 9
    const val PAGE_TRAVEL_NEWS = 10
    const val PAGE_IDEA_NEWS = 11
    const val PAGE_TALK_NEWS = 12

    const val NEWS_TABLE = "news_table"
    const val DATABASE_NAME = "news_database"
    const val ID_COLUMN = "id"
    const val NAME_COLUMN = "name"
    const val TITLE_COLUMN = "title"
    const val IMAGE_COLUMN = "image"
    const val DATE_COLUMN = "date"
    const val URL_COLUMN = "url"

    const val FAVORITES_ARTICLES_TABLE = "favorites_articles_table"

    const val NOTIFY_DIALOG = "Xóa thành công"
    const val NOTIFY = "Đã lưu tin vào mục yêu thích"
    const val NOTIFY_LOGIN = "Bạn chưa nhập đủ thông tin"
    const val TITLE_FRAGMENT_HOME = "Tóm tắt thông tin cho "
    const val NOTIFY_LOCATION = "Cho phép xác định vị trí của bạn"
    const val NOTIFY_LOCATION_NOT_GRANTED = "Quyền vị trí chưa được cấp"

    //service
    const val BROADCAST_STRING_FOR_ACTION = "check_internet"

    private var newDate = ""


    val listProvince = arrayListOf<String>(
        "An Giang",
        "Bà Rịa – Vũng Tàu",
        "Bắc Giang",
        "Bắc Kạn",
        "Bạc Liêu",
        "Bắc Ninh",
        "Bến Tre",
        "Bình Định",
        "Bình Dương",
        "Bình Phước",
        "Bình Thuận",
        "Cà Mau",
        "Cần Thơ",
        "Cao Bằng",
        "Đà Nẵng",
        "Đắk Lắk",
        "Đắk Nông",
        "Điện Biên",
        "Đồng Nai",
        "Đồng Tháp",
        "Gia Lai",
        "Hà Giang",
        "Hà Nam",
        "Hà Nội",
        "Hà Tĩnh",
        "Hải Dương",
        "Hải Phòng",
        "Hậu Giang",
        "Hòa Bình",
        "Hưng Yên",
        "Khánh Hòa",
        "Kiên Giang",
        "Kon Tum",
        "Lai Châu",
        "Lâm Đồng",
        "Lạng Sơn",
        "Lào Cai",
        "Long An",
        "Nam Định",
        "Nghệ An",
        "Ninh Bình",
        "Ninh Thuận",
        "Phú Thọ",
        "Phú Yên",
        "Quảng Bình",
        "Quảng Nam",
        "Quảng Ngãi",
        "Quảng Ninh",
        "Quảng Trị",
        "Sóc Trăng",
        "Sơn La",
        "Tây Ninh",
        "Thái Bình",
        "Thái Nguyên",
        "Thanh Hóa",
        "Thừa Thiên Huế",
        "Tiền Giang",
        "Thành Phố Hồ Chí Minh",
        "Trà Vinh",
        "Tuyên Quang",
        "Vĩnh Long",
        "Vĩnh Phúc",
        "Yên Bái",
    )

    @SuppressLint("SimpleDateFormat")
    fun dateFormat(stringDate: String): String {
        val sdf = SimpleDateFormat("E, d MMM yyy", Locale(getCountry()))
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(stringDate)

        if (date != null) {
            newDate = sdf.format(date)
        }
        return newDate
    }

    fun dateToTimeFormat(oldStringDate: String): String {
        val p = PrettyTime(Locale(getCountry()))
        val sdf = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss'Z'",
            Locale.ENGLISH
        )
        val date = sdf.parse(oldStringDate)
        return "• ${p.format(date)}"
    }

    private fun getCountry(): String {
        val locale = Locale.getDefault()
        val country = locale.country.toString()
        return country.lowercase(Locale.getDefault())
    }

    @SuppressLint("SimpleDateFormat")
    fun convertStringDay(dt: Int): String {
        val time = dt.toString()

        val l: Long = (time.toLong())

        val date = Date(l * 1000L)

        val simpleDateFormat = SimpleDateFormat("EE\nHH-mm")
        val days = simpleDateFormat.format(date)
        return days.toString()
    }
}