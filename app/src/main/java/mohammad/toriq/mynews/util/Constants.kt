package mohammad.toriq.mynews.util

import mohammad.toriq.mynews.R
/***
 * Created By Mohammad Toriq on 29/11/2023
 */
class Constants {
    companion object{

        val IS_LOG: Boolean = getIsLog()

        val CONNECT_TIMEOUT : Long = 60
        val READ_TIMEOUT: Long = 60
        val WRITE_TIMEOUT: Long = 60
        val IMG_CONNECT_TIMEOUT : Long = 10000
        val IMG_READ_TIMEOUT: Long = 10000
        val IMG_WRITE_TIMEOUT: Long = 10000

        val DATE_OUT_FORMAT_DEF0 = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        val DATE_OUT_FORMAT_DEF1 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        val DATE_OUT_FORMAT_DEF2 = "yyyy-MM-dd"
        val DATE_OUT_FORMAT_DEF3 = "MMM dd, yyyy"

        fun getIsLog(): Boolean {
            return true
//            return  if (!BuildConfig.DEBUG) {
//                false
//            } else {
//                true
//            }
        }
        fun getCategoryIcons() : ArrayList<Int>{
            return arrayListOf(
                R.drawable.category_business_24,
                R.drawable.category_entertaiment_24,
                R.drawable.category_general_24,
                R.drawable.category_health_24,
                R.drawable.categoy_science_24,
                R.drawable.category_sport_24,
                R.drawable.category_technology_24,)
        }

        fun getCategories() : ArrayList<String>{
            return arrayListOf(
                "Business",
                "Entertainment",
                "General",
                "Health",
                "Science",
                "Sports",
                "Technology")
        }
        fun getLanguageCodes() : List<String>{
            return listOf(
                "ar",
                "de",
                "en",
                "es",
                "fr",
                "he",
                "it",
                "nl",
                "no",
                "pt",
                "ru",
                "sv",
                "zh")
        }
        fun getLanguages() : List<String>{
            return listOf(
                "Arabic",
                "German",
                "English",
                "Spanish",
                "French",
                "Hebrew",
                "Italian",
                "Dutch",
                "Norwegian",
                "Portuguese",
                "Russian",
                "Swedish",
                "Chinese")
        }

        fun getCountriesCodes() : List<String>{
            return listOf(
                "ar",
                "au",
                "at",
                "be",
                "br",
                "bg",
                "ca",
                "cn",
                "co",
                "cu",
                "cz",
                "eg",
                "fr",
                "de",
                "gr",
                "hk",
                "hu",
                "in",
                "id",
                "ie",
                "it",
                "jp",
                "lv",
                "lt",
                "my",
                "mx",
                "ma",
                "nl",
                "nz",
                "ng",
                "no",
                "ph",
                "pl",
                "pt",
                "ro",
                "ru",
                "sa",
                "rs",
                "sg",
                "sk",
                "si",
                "za",
                "kr",
                "se",
                "ch",
                "tw",
                "th",
                "tr",
                "ae",
                "ua",
                "gb",
                "us",
                "ve",
            )
        }
        fun getCountries() : List<String>{
            return listOf(
                "Argentina",
                "Australia",
                "Austria",
                "Belgium",
                "Brazil",
                "Bulgaria",
                "Canada",
                "China",
                "Colombia",
                "Cuba",
                "Czech Republic",
                "Egypt",
                "France",
                "Germany",
                "Greece",
                "Hong Kong",
                "Hungary",
                "India",
                "Indonesia",
                "Ireland",
                "Italy",
                "Japan",
                "Latvia",
                "Lithuania",
                "Malaysia",
                "Mexico",
                "Morocco",
                "Netherlands",
                "New Zealand",
                "Nigeria",
                "Norway",
                "Philippines",
                "Poland",
                "Portugal",
                "Romania",
                "Russia",
                "Saudi Arabia",
                "Serbia",
                "Singapore",
                "Slovakia",
                "Slovenia",
                "South Africa",
                "South Korea",
                "Sweden",
                "Switzerland",
                "Taiwan",
                "Thailand",
                "Turkey",
                "UAE",
                "Ukraine",
                "United Kingdom",
                "United States",
                "Venuzuela",
            )
        }
    }
}