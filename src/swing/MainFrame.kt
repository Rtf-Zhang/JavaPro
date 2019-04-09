package swing

import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.JPanel

class MainFrame : JFrame() {

    init {
        initWindow()
    }

    fun initWindow() {
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(800, 1000)
        title = "RxJava"
        isVisible = true
    }

    fun addPannel() {
        val mainPanel = JPanel()
    }
}