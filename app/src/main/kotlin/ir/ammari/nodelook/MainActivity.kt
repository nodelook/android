package ir.ammari.nodelook

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class MainActivity : Activity() {
    private fun testURL(
        status: MutableMap<String, String>,
        textView: TextView,
        site: SiteInfo,
        category: Category,
    ) {
        Thread {
            val result = runCatching {
                if (site.shouldContain in URL(site.url).readText()) "SUCCESS" else "FAILED"
            }.onFailure { Log.e("NodeLook", site.toString(), it) }.getOrElse { "ERROR" }
            runOnUiThread {
                status[site.name] = result
                displayResult(status, textView, category)
            }
        }.start()
    }

    private fun displayResult(
        status: Map<String, String>,
        textView: TextView,
        category: Category,
    ) {
        val text = SpannableStringBuilder()
        text.append(category.description).append("\n")
        category.items.forEach { site ->
            text.append("\n")
            val key = site.name
            text.append(key)
            if (status.containsKey(key)) {
                text.append(" - ")
                val result = status[key] ?: ""
                val success = result == "SUCCESS"
                val color = ForegroundColorSpan((if (success) 0xFF34A853 else 0xFFEA4335).toInt())
                val spannable = SpannableString(result)
                spannable.setSpan(color, 0, result.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                text.append(spannable)
            } else {
                text.append("…")
            }
            text.append("\n")
            run {
                val url = site.url
                val color = ForegroundColorSpan(0xFF4285F4.toInt())
                val spannable = SpannableString(url)
                spannable.setSpan(color, 0, url.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                text.append(spannable)
            }
            text.append("\n")
        }
        textView.text = text
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = createStatusTextView()
        val scrollView = ScrollView(this)
        scrollView.addView(textView)
        val scrollViewParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
        scrollViewParams.weight = 1f
        scrollView.setLayoutParams(scrollViewParams)
        val root = LinearLayout(this)
        root.orientation = LinearLayout.VERTICAL
        root.addView(scrollView)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            root.fitsSystemWindows = true
        }
        val buttonBar = createButtonsBar(textView)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            val buttonBarScrollable = HorizontalScrollView(this)
            buttonBarScrollable.addView(buttonBar)
            root.addView(buttonBarScrollable)
        } else root.addView(buttonBar)
        setContentView(root)
    }

    private fun createStatusTextView(): TextView {
        val textView = TextView(this)
        textView.setId(R.id.result)
        textView.freezesText = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            textView.setTextIsSelectable(true)
        }
        textView.setHorizontallyScrolling(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textView.layoutDirection = View.LAYOUT_DIRECTION_LTR
        }
        return textView
    }

    private val dp: Float get() = resources.displayMetrics.density

    private fun createButtonsBar(textView: TextView): LinearLayout {
        val buttonsBar = LinearLayout(this)
        buttonsBar.setPadding(0, 0, 0, (dp * 4).toInt())
        buttonsBar.addView(
            createFooterButton(getString(R.string.ping), 0xFFEA4335.toInt()) {
                ping(textView)
            },
        )
        categories.map { category ->
            createFooterButton(category.title, category.color) { testAll(textView, category) }
        }.forEach(buttonsBar::addView)
        buttonsBar.orientation = LinearLayout.HORIZONTAL
        return buttonsBar
    }

    private fun createFooterButton(
        title: String,
        color: Int,
        action: View.OnClickListener,
    ): View {
        val button = Button(this)
        if (color != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                button.setBackgroundTintList(ColorStateList.valueOf(color))
            } else {
                button.setBackgroundColor(color)
            }
        }
        button.text = title
        button.setOnClickListener(action)
        return button
    }

    @SuppressLint("SetTextI18n")
    private fun ping(textView: TextView) {
        val editText = EditText(this)
        editText.setText("google.com")
        val frame = FrameLayout(this)
        frame.addView(editText)
        val padding = (20 * dp).toInt()
        frame.setPadding(padding, 0, padding, 0)
        AlertDialog.Builder(this).setTitle("Enter a domain").setView(frame).setPositiveButton(
            "Ping"
        ) { _, _ ->
            textView.text = ""
            val domain = editText.getText()
            Thread {
                runCatching {
                    Runtime.getRuntime().exec("ping -c 4 $domain").inputStream.use { inputStream ->
                        InputStreamReader(inputStream).use { inputStreamReader ->
                            BufferedReader(inputStreamReader).use { bufferedReader ->
                                var line: String
                                while ((bufferedReader.readLine().also { line = it }) != null) {
                                    val finalLine = line
                                    runOnUiThread {
                                        textView.text =
                                            textView.getText().toString() + "\n" + finalLine
                                    }
                                }
                            }
                        }
                    }
                }.onFailure { runOnUiThread { textView.text = it.message } }
            }.start()
        }.show()
    }

    private fun testAll(textView: TextView, category: Category) {
        val status = HashMap<String, String>()
        displayResult(status, textView, category)
        category.items.forEach { site -> testURL(status, textView, site, category) }
    }
}
