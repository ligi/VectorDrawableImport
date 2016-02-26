package org.ligi.vectordrawableimporter

import com.intellij.ui.ListCellRendererWrapper
import org.ligi.vectordrawableimporter.model.ImageAsset
import javax.swing.ImageIcon
import javax.swing.JList

class AssetSpinnerRenderer : ListCellRendererWrapper<ImageAsset>() {

    override fun customize(list: JList<Any>, imageAsset: ImageAsset, index: Int, selected: Boolean, hasFocus: Boolean) {
        setIcon(ImageIcon(imageAsset.file.absolutePath))
    }
}