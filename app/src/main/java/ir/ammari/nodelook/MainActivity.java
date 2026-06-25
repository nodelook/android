package ir.ammari.nodelook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
    private void testURL(@NonNull Map<String, String> status, @NonNull TextView textView, @NonNull SiteInfo site, @NonNull Category category) {
        new Thread(() -> {
            String result;
            try (final var inputStream = new URL(site.url()).openStream(); //
                 final var inputReader = new InputStreamReader(inputStream); //
                 final var reader = new BufferedReader(inputReader) //
            ) {
                final var responseBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
                final var response = responseBuilder.toString();
                result = response.contains(site.shouldContain()) ? "SUCCESS" : "FAILED";
            } catch (IOException e) {
                result = "ERROR";
                Log.e("NodeLook", site.toString(), e);
            }
            final var finalResult = result;
            runOnUiThread(() -> {
                status.put(site.name(), finalResult);
                displayResult(status, textView, category);
            });
        }).start();
    }

    private void displayResult(@NonNull Map<String, String> status, @NonNull TextView textView, @NonNull Category category) {
        final var text = new SpannableStringBuilder();
        text.append(category.description()).append("\n\n");
        for (final var site : category.items()) {
            text.append("\n");
            final var key = site.name();
            text.append(key);
            if (status.containsKey(key)) {
                text.append(" - ");
                var result = status.get(key);
                result = result == null ? "" : result;
                final var success = result.equals("SUCCESS");
                final var color = new ForegroundColorSpan(success ? 0xFF34A853 : 0xFFEA4335);
                final var spannable = new SpannableString(result);
                spannable.setSpan(color, 0, result.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                text.append(spannable);
            } else {
                text.append("…");
            }
            text.append("\n");
            {
                final var url = site.url();
                final var color = new ForegroundColorSpan(0xFF4285F4);
                final var spannable = new SpannableString(url);
                spannable.setSpan(color, 0, url.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                text.append(spannable);
            }
            text.append("\n");
        }
        textView.setText(text);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final var textView = createStatusTextView();
        final var scrollView = new ScrollView(this);
        scrollView.addView(textView);
        final var scrollViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        scrollViewParams.weight = 1f;
        scrollView.setLayoutParams(scrollViewParams);
        final var root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.addView(scrollView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            root.setFitsSystemWindows(true);
        }
        final var buttonBar = createButtonsBar(textView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            final var buttonBarScrollable = new HorizontalScrollView(this);
            buttonBarScrollable.addView(buttonBar);
            root.addView(buttonBarScrollable);
        } else root.addView(buttonBar);
        setContentView(root);
    }

    @NonNull
    private TextView createStatusTextView() {
        final var textView = new TextView(this);
        textView.setId(R.id.result);
        textView.setFreezesText(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            textView.setTextIsSelectable(true);
        }
        textView.setHorizontallyScrolling(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        return textView;
    }

    @NonNull
    private LinearLayout createButtonsBar(@NonNull TextView textView) {
        final var result = new LinearLayout(this);
        result.setPadding(0, 0, 0, (int) (getResources().getDisplayMetrics().density * 4));
        {
            final var pingButton = new Button(this);
            final var color = 0xFFEA4335;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                pingButton.setBackgroundTintList(ColorStateList.valueOf(color));
            } else {
                pingButton.setBackgroundColor(color);
            }
            pingButton.setText(R.string.ping);
            pingButton.setOnClickListener((v) -> ping(textView));
            result.addView(pingButton);
        }
        for (final var category : Data.categories) {
            final var button = new Button(this);
            final var color = category.color();
            if (color != 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    button.setBackgroundTintList(ColorStateList.valueOf(category.color()));
                } else {
                    button.setBackgroundColor(category.color());
                }
            }
            button.setText(category.title());
            button.setOnClickListener((v) -> testAll(textView, category));
            result.addView(button);
        }
        result.setOrientation(LinearLayout.HORIZONTAL);
        return result;
    }

    @SuppressLint("SetTextI18n")
    private void ping(@NonNull TextView textView) {
        textView.setText("");
        new Thread(() -> {
            final var runtime = Runtime.getRuntime();
            try (final var inputStream = runtime.exec("ping google.com").getInputStream()) {
                final var br = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = br.readLine()) != null) {
                    final var finalLine = line;
                    runOnUiThread(() -> textView.setText(textView.getText() + "\n" + finalLine));
                }
                br.close();
            } catch (IOException e) {
                runOnUiThread(() -> textView.setText(e.getMessage()));
            }
        }).start();
    }

    private void testAll(@NonNull TextView textView, @NonNull Category category) {
        final var status = new HashMap<String, String>();
        displayResult(status, textView, category);
        for (final var site : category.items()) testURL(status, textView, site, category);
    }
}
