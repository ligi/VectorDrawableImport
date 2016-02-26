package gatherer

import java.io.File

fun main(args: Array<String>) {

    val fromDirectory = File("/home/ligi/git/3rd/material-design-icons/")
    val toDirectory = File("/home/ligi/git/VectorDrawableImport/plugin/src/main/resources/data")

    val pngOutputDir = File(toDirectory, "png")
    pngOutputDir.mkdirs()

    val xmlOutputDir = File(toDirectory, "xml")
    xmlOutputDir.mkdir()

        fromDirectory.walk().forEach {
        if (it.toString().contains("anydpi") && it.toString().endsWith("xml")) {
            val asReversed = it.toString().split("/").asReversed()
            val pngFile = it.toString().replace(".xml", ".png").replace("anydpi-v21", "hdpi")

            if (!File(pngFile).exists())
                println(it)

            val topic = asReversed[2]
            val cleanName = topic + "_" +asReversed[0].replace("ic_","").replace("_black_24dp","")

            val toPngFile=File(pngOutputDir,cleanName.replace(".xml", ".png"))
            File(pngFile).copyTo(toPngFile)

            val toXMLFile=File(xmlOutputDir,cleanName)
            it.copyTo(toXMLFile)

        }
    }

}