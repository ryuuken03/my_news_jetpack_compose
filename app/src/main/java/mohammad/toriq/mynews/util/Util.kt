package mohammad.toriq.mynews.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
class Util {
    companion object{
        fun convertDate(
            dateInput: String?,
            inFormat: String?,
            outFormat: String?,
            isLocal: Boolean = false
        ): String {
            var tmp = ""

            val locale = Locale("in")
            val origin =
                SimpleDateFormat(inFormat, Locale.getDefault())
            origin.timeZone = TimeZone.getTimeZone("UTC") //default
            if (isLocal) {
                origin.timeZone = TimeZone.getDefault()
            }
            val result = SimpleDateFormat(outFormat, locale)
            result.timeZone = TimeZone.getDefault()

            try {
                tmp = result.format(origin.parse(dateInput))
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return tmp
        }
    }
}