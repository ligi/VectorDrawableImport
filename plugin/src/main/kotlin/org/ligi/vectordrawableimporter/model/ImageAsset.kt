package org.ligi.vectordrawableimporter.model

import java.io.File

class ImageAsset(val file: File) {

    override fun toString(): String {
        return file.name.replace(".png","")
    }
}
