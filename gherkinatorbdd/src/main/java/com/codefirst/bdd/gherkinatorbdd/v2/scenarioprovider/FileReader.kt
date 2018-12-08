package com.codefirst.bdd.gherkinatorbdd.v2.scenarioprovider

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder

class FileReader {

    fun read(context: Context, fileName: String): StringBuilder {
        val reader = BufferedReader(InputStreamReader(context.assets.open(fileName)))

        val wholeFile = StringBuilder()
        var fileLine: String? = reader.readLine()
        while (fileLine != null) {
            if (fileLine.isNotEmpty()) {
                wholeFile.append("${fileLine.trim()}\n")
            }

            fileLine = reader.readLine()
        }
        reader.close()
    }
}