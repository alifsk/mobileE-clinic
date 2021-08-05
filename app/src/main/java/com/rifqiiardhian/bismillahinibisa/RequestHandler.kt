package com.rifqiiardhian.bismillahinibisa

import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import javax.net.ssl.HttpsURLConnection

class RequestHandler {
    // Passing parameter
    companion object {
        @Throws(Exception::class)
        private fun encodeParams(params: JSONObject): String? {
            val result = StringBuilder()
            var first = true
            val itr = params.keys()
            while (itr.hasNext()) {
                val key = itr.next()
                val value = params[key]
                if (first) first = false else result.append("&")
                result.append(URLEncoder.encode(key, "UTF-8"))
                result.append("=")
                result.append(URLEncoder.encode(value.toString(), "UTF-8"))
            }
            return result.toString()
        }

        @Throws(java.lang.Exception::class)
        fun sendPost(r_url: String?, postDataParams: JSONObject): String? {
            val url = URL(r_url)
            val conn = url.openConnection() as HttpURLConnection
            conn.readTimeout = 20000
            conn.connectTimeout = 20000
            conn.requestMethod = "POST"
            conn.doInput = true
            conn.doOutput = true
            val os = conn.outputStream
            val writer = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
            writer.write(encodeParams(postDataParams))
            writer.flush()
            writer.close()
            os.close()
            val responseCode = conn.responseCode // To Check for 200
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                val `in` =
                    BufferedReader(InputStreamReader(conn.inputStream))
                val sb = StringBuffer("")
                var line: String? = ""
                while (`in`.readLine().also { line = it } != null) {
                    sb.append(line)
                    break
                }
                `in`.close()
                return sb.toString()
            }
            return null
        }

        @Throws(IOException::class)
        fun sendGet(url: String?): String? {
            val obj = URL(url)
            val con = obj.openConnection() as HttpURLConnection
            con.requestMethod = "GET"
            val responseCode = con.responseCode
            println("Response Code :: $responseCode")
            return if (responseCode == HttpURLConnection.HTTP_OK) { // connection ok
                val `in` = BufferedReader(InputStreamReader(con.inputStream))
                var inputLine: String?
                val response = StringBuffer()
                while (`in`.readLine().also { inputLine = it } != null) {
                    response.append(inputLine)
                }
                `in`.close()
                response.toString()
            } else {
                ""
            }
        }
    }
}