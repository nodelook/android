package ir.ammari.nodelook

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.media.AudioManager
import android.media.ToneGenerator
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
import android.widget.CheckBox
import android.widget.EditText
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import java.net.URL
import kotlin.math.PI
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.system.exitProcess

class MainActivity : Activity() {
    private fun testURL(
        status: MutableMap<SiteInfo, String>,
        textView: TextView,
        site: SiteInfo,
        category: Category,
    ) {
        Thread {
            val result = runCatching {
                if (site.shouldContain in URL(site.url).readText()) "SUCCESS" else "FAILED"
            }.onFailure { Log.e("NodeLook", site.toString(), it) }.getOrElse { "FAILED" }
            runOnUiThread {
                if (category != currentCategory) return@runOnUiThread
                status[site] = result
                displayResult(status, textView, category)
                if (status.keys.size == category.items.size) playSuccessBeep()
            }
        }.start()
    }

    private var currentCategory: Category? = null

    private fun displayResult(
        status: Map<SiteInfo, String>,
        textView: TextView,
        category: Category,
    ) {
        val text = SpannableStringBuilder()
        text.append(category.description).append("\n")
        category.items.forEach { site ->
            text.append("\n")
            text.append(site.name)
            if (status.containsKey(site)) {
                text.append(" - ")
                val result = status[site] ?: ""
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

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = createStatusTextView()
        textView.setText(R.string.welcome_message)
        val root = LinearLayout(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            root.fitsSystemWindows = true
        }
        val scrollView = ScrollView(this)
        scrollView.addView(textView)
        run {
            val buttonsBar = LinearLayout(this)
            buttonsBar.addView( 
                createButton(getString(R.string.stop), 0xFFEA4335.toInt()) {
                    System.exit(0)
                },
            )
            buttonsBar.addView(
                createButton(getString(R.string.ping), 0xFFFBBC05.toInt()) {
                    ping(textView)
                },
            )
            categories.map { category ->
                createButton(category.title, category.color) { testAll(textView, category) }
            }.forEach(buttonsBar::addView)
            val padding = 4.dp
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                scrollView.setPadding(padding, 0, padding, 0)
                buttonsBar.setPadding(padding, 0, padding, 0)
                root.orientation = LinearLayout.HORIZONTAL
                buttonsBar.orientation = LinearLayout.VERTICAL
                val buttonBarScrollable = ScrollView(this)
                buttonBarScrollable.addView(buttonsBar)
                root.addView(buttonBarScrollable)
            } else {
                buttonsBar.setPadding(0, 0, 0, padding)
                root.orientation = LinearLayout.VERTICAL
                buttonsBar.orientation = LinearLayout.HORIZONTAL
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                    val buttonBarScrollable = HorizontalScrollView(this)
                    buttonBarScrollable.addView(buttonsBar)
                    root.addView(buttonBarScrollable)
                } else root.addView(buttonsBar)
            }
        }
        scrollView.setLayoutParams(
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
        )
        root.addView(scrollView)
        setContentView(root)
    }

    fun playSuccessBeep() {
        val tg = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100)
        tg.startTone(ToneGenerator.TONE_PROP_ACK, 150)

        Thread {
            Thread.sleep(200)
            tg.release()
        }.start()
    }

    fun playErrorBeep() {
        val tg = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100)
        tg.startTone(ToneGenerator.TONE_PROP_NACK, 200)

        Thread {
            Thread.sleep(250)
            tg.release()
        }.start()
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

    private val Int.dp: Int get() = (this * resources.displayMetrics.density).roundToInt()

    private fun createButton(
        title: String,
        color: Int,
        action: View.OnClickListener,
    ): View {
        val button = Button(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            button.setBackgroundTintList(ColorStateList.valueOf(color))
        } else {
            button.setBackgroundColor(color)
        }
        button.text = title
        button.setOnClickListener(action)
        return button
    }

    @SuppressLint("SetTextI18n")
    private fun ping(textView: TextView) {
        currentCategory = null
        val editText = EditText(this)
        editText.setText("google.com")
        editText.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
        val root = LinearLayout(this)
        root.addView(editText)
        root.orientation = LinearLayout.VERTICAL
        val padding = 20.dp
        val ipv6CheckBox = CheckBox(this)
        ipv6CheckBox.setOnCheckedChangeListener { _, value ->
            if (editText.text.toString() == "google.com" && value) {
                editText.setText("ipv6.google.com")
            } else if (editText.text.toString() == "ipv6.google.com" && !value) {
                editText.setText("google.com")
            }
        }
        ipv6CheckBox.text = getString(R.string.version6)
        root.addView(ipv6CheckBox)
        root.setPadding(padding, 0, padding, 0)
        AlertDialog.Builder(this).setTitle(
            R.string.ping_dialog_title
        ).setView(root).setPositiveButton(R.string.ping) { _, _ ->
            textView.text = ""
            val domain = editText.getText().toString()
            Thread {
                while (!Thread.currentThread().isInterrupted) {
                    runCatching {
                        val ping = if (ipv6CheckBox.isChecked) "ping6" else "ping"

                        val process = ProcessBuilder(
                            ping,
                            "-c", "1",
                            domain
                        )
                            .redirectErrorStream(true)
                            .start()

                        val output = process.inputStream.bufferedReader().readText()

                        val success = process.waitFor() == 0

                        if (currentCategory != null) return@Thread
                        runOnUiThread {
                            if (success) {
                                textView.append(getString(R.string.ping) + ": " + getString(R.string.success) + "\n")
                                playSuccessBeep()
                            } else {
                                textView.append(getString(R.string.ping) + ": " + getString(R.string.failure) + "\n")
                                playErrorBeep()
                            }
                        }

                        Thread.sleep(1000)
                    }.onFailure {
                        runOnUiThread {
                            Log.e("NodeLook", it.message, it)
                        }
                        break
                    }
                }
            }.start()
        }.show()
    }

    private fun testAll(textView: TextView, category: Category) {
        currentCategory = category
        val status = HashMap<SiteInfo, String>()
        displayResult(status, textView, category)
        category.items.forEach { site -> testURL(status, textView, site, category) }
    }
}
