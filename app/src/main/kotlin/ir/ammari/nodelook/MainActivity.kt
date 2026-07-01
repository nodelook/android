package ir.ammari.nodelook

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
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
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import kotlin.math.PI
import kotlin.math.roundToInt
import kotlin.math.sin

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
                if (status.keys.size == category.items.size) playBeep(1200.0, 100)
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

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = createStatusTextView()
        textView.text = "Welcome to NodeLook\n\nhttps://github.com/nodelook/android"
        val root = LinearLayout(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            root.fitsSystemWindows = true
        }
        val scrollView = ScrollView(this)
        scrollView.addView(textView)
        run {
            val buttonsBar = LinearLayout(this)
            buttonsBar.addView(
                createButton(getString(R.string.ping), 0xFFEA4335.toInt()) {
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

    fun playBeep(freq: Double, durationMs: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return
        val sampleRate = 44100
        val samples = sampleRate * durationMs / 1000

        val buffer = ShortArray(samples)

        for (i in buffer.indices) {
            val angle = 2.0 * PI * i * freq / sampleRate
            buffer[i] = (sin(angle) * Short.MAX_VALUE).toInt().toShort()
        }

        val audioTrack = AudioTrack(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build(),
            AudioFormat.Builder()
                .setSampleRate(sampleRate)
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                .build(),
            buffer.size * 2,
            AudioTrack.MODE_STATIC,
            AudioManager.AUDIO_SESSION_ID_GENERATE
        )

        audioTrack.write(buffer, 0, buffer.size)
        audioTrack.play()
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
        ipv6CheckBox.text = "IPv6"
        root.addView(ipv6CheckBox)
        root.setPadding(padding, 0, padding, 0)
        AlertDialog.Builder(this).setTitle(
            R.string.ping_dialog_title
        ).setView(root).setPositiveButton(R.string.ping) { _, _ ->
            textView.text = ""
            val domain = editText.getText().toString()
            Thread {
                runCatching {
                    val ping = if (ipv6CheckBox.isChecked) "ping6" else "ping"
                    Runtime.getRuntime().exec(
                        arrayOf(ping, "-c4", domain)
                    ).inputStream.use { inputStream ->
                        InputStreamReader(inputStream).use { inputStreamReader ->
                            BufferedReader(inputStreamReader).use { bufferedReader ->
                                generateSequence { bufferedReader.readLine() }.forEach { line ->
                                    runOnUiThread {
                                        textView.text = textView.getText().let {
                                            if (it.isNotEmpty()) "$it\n$line" else line
                                        }
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
