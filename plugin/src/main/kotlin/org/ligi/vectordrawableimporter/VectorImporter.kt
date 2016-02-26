package org.ligi.vectordrawableimporter

import com.intellij.ide.BrowserUtil
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.MessageType
import com.intellij.openapi.util.SystemInfo
import com.intellij.ui.ComboboxSpeedSearch
import org.ligi.vectordrawableimporter.model.ImageAsset

import javax.swing.*
import java.awt.event.FocusAdapter
import java.awt.event.FocusEvent
import java.io.File
import java.net.URISyntaxException

class VectorImporter internal constructor(private val project: Project?, module: Module) : DialogWrapper(project) {

    private val comboBoxSpeedSearch: ComboboxSpeedSearch

    lateinit var iconSearchComboBox: JComboBox<Any>
    lateinit var container: JPanel
    lateinit var resDirComboBox: JComboBox<Any>

    fun getBundledResource(file: String): File? {
        val resource = this.javaClass.getResource("/" + file)!!;
        try {
            return File(resource.toURI());
        } catch (e: URISyntaxException) {
            return File(resource.path);
        }
    }


    init {
        init()

        title = "Android Vector-Drawable import"
        for (file in getBundledResource("data/png/")!!.listFiles()) {
            iconSearchComboBox.addItem(ImageAsset(file))
        }

        iconSearchComboBox.renderer = AssetSpinnerRenderer()
        comboBoxSpeedSearch = object : ComboboxSpeedSearch(iconSearchComboBox) {
            override fun getElementText(element: Any): String? {
                return if (element is ImageAsset) element.file.name else ""
            }
        }

        val currentFacet = AndroidHelper.getCurrentFacet(project!!, module)

        val allResourceDirectories = currentFacet!!.allResourceDirectories

        for (allResourceDirectory in allResourceDirectories) {
            resDirComboBox.addItem(allResourceDirectory.toString().replace("file://", "")+"/drawable-anydpi/")
        }

        iconSearchComboBox.addFocusListener(object : FocusAdapter() {
            override fun focusGained(e: FocusEvent?) {
                comboBoxSpeedSearch.showPopup()
            }
        })
    }


    override fun doHelpAction() {
        BrowserUtil.browse("http://github.com/ligi/VectorDrawableImport")
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return iconSearchComboBox
    }

    override fun createCenterPanel(): JComponent? {
        return container
    }

    override fun doOKAction() {
        super.doOKAction()

        val imageAsset = iconSearchComboBox.selectedItem  as ImageAsset
        val selectedTo = File(resDirComboBox.selectedItem as String)
        selectedTo.mkdir()
        val toFile = File(selectedTo, "ic_" + imageAsset.file.name.replace(".png", ".xml"))

        val fromFile = File(imageAsset.file.absolutePath.replace(".png", ".xml").replace("/png/", "/xml/"))

        if (toFile.exists()) {
            MessageHelper.message(project!!, "File exists already - NOP", MessageType.WARNING)
        } else {
            fromFile.copyTo(toFile)

            MessageHelper.message(project!!, "Drawable imported", MessageType.INFO)
        }
    }



    public override fun createActions(): Array<Action> {
        return if (SystemInfo.isMac)
            arrayOf(this.helpAction, this.cancelAction, this.okAction)
        else
            arrayOf(this.okAction, this.cancelAction, this.helpAction)
    }

}
