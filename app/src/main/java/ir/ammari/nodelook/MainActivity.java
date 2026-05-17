package ir.ammari.nodelook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SiteInfo {
    String name;
    String url;
    String status;

    SiteInfo(String name, String url, String status) {
        this.name = name;
        this.url = url;
        this.status = status;
    }
}

public class MainActivity extends Activity {
    private static final List<SiteInfo> sites = new ArrayList<>() {{
        add(new SiteInfo("GitHub Site", "github.com/xmha97/test/blob/main/test", "thegithubsiteisworkingproperly"));
        add(new SiteInfo("GitHub API", "api.github.com/octocat?s=thegithubapiisworkingproperly", "thegithubapiisworkingproperly"));
        add(new SiteInfo("GitHub Pages", "xmha97.github.io/status", "200"));
        add(new SiteInfo("GitHub Gists", "gist.githubusercontent.com/xmha97/94f6ba425d9874179fdd73fc0e2dc899/raw/e3a7ca5109c651f711000b3a02b4e032bd1d695f/status", "200"));
        add(new SiteInfo("GitHub Repository", "raw.githubusercontent.com/xmha97/test/refs/heads/main/status", "200"));
        add(new SiteInfo("GitHub Releases", "github.com/xmha97/test/releases/download/v1.0.0/status", "200"));
        add(new SiteInfo("GitHub Pages", "xmha97.github.io/status", "200"));
        add(new SiteInfo("Codeberg Repository", "codeberg.org/xmha97/test/raw/branch/main/status", "200"));
        add(new SiteInfo("Codeberg Releases", "codeberg.org/xmha97/test/releases/download/v1.0.0/status", "200"));
        add(new SiteInfo("GitLab", "gitlab.com/xmha97/test/-/raw/master/status", "200"));
        add(new SiteInfo("DropBox", "www.dropbox.com/scl/fi/dovory2z1y9xnj6kxwyq7/status?rlkey=48kb8gpm3fjnx76oglv1bm3u0&st=hy5uz0th&dl=1", "200"));
        add(new SiteInfo("Archive", "archive.org/download/xmha97/status", "200"));
        add(new SiteInfo("Pastebin", "pastebin.com/raw/ER5BRSx7", "200"));
        add(new SiteInfo("Wikipedia (English)", "en.wikipedia.org/w/index.php?title=User:Xmha97/status.js&action=raw&ctype=text/javascript", "200"));
        add(new SiteInfo("Wikipedia (Arabic)", "ar.wikipedia.org/w/index.php?title=User:Xmha97/status.js&action=raw&ctype=text/javascript", "200"));
        add(new SiteInfo("Wikipedia (Persian)", "fa.wikipedia.org/w/index.php?title=User:Xmha97/status.js&action=raw&ctype=text/javascript", "200"));
        add(new SiteInfo("Telegram", "t.me/thetelegramsiteisworkingproperly", "thetelegramsiteisworkingproperly"));
        add(new SiteInfo("JS-org", "pad.js.org/status", "200"));
        add(new SiteInfo("jsDelivr", "cdn.jsdelivr.net/gh/xmha97/test@1.0.0/status", "200"));
        add(new SiteInfo("Postman Echo", "postman-echo.com/status/200", "200"));
        add(new SiteInfo("Google Search", "google.com/search?q=thegooglesearchsiteisworkingproperly", "thegooglesearchsiteisworkingproperly"));
        add(new SiteInfo("Google Translate", "translate.google.com/?text=thegoogletranslatesiteisworkingproperly", "thegoogletranslatesiteisworkingproperly"));
    }};

    private void testURL(Map<String, String> status, @NonNull TextView textView, @NonNull SiteInfo site) {
        new Thread(() -> {
            var result = "Invalid result";
            try (final var inputStream = new URL("https://" + site.url).openStream();
                 final var reader = new BufferedReader(new InputStreamReader(inputStream))) {
                final var expected = site.status;
                final var responseBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
                final var response = responseBuilder.toString();
                final var cleanResponse =
                        response.replaceAll("[\\n\\r\\t .]", "");
                final var cleanExpected =
                        expected.replaceAll("[\\n\\r\\t .]", "");
                if (cleanResponse.contains(cleanExpected)) {
                    result = "success";
                } else {
                    result = "Unexpected response: " + response;
                }
            } catch (IOException e) {
                result = e.getMessage();
                e.printStackTrace();
            }
            final var finalResult = result;
            runOnUiThread(() -> {
                status.put(site.name, finalResult);
                displayResult(status, textView);
            });
        }).start();
    }

    private void displayResult(Map<String, String> status, @NonNull TextView textView) {
        final var text = new SpannableStringBuilder();
        for (final var site : sites) {
            final var key = site.name;
            text.append(key);
            if (status.containsKey(key)) {
                text.append(" - ");
                var result = status.get(key);
                result = result == null ? "" : result;
                final var success = result.equals("success");
                final var color = new ForegroundColorSpan(success ? 0xFF007500 : Color.RED);
                final var spannable = new SpannableString(result);
                spannable.setSpan(color, 0, result.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                text.append(spannable);
            } else {
                text.append("…");
            }
            text.append("\n");
        }
        textView.setText(text);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        final var scrollView = new ScrollView(this);
        scrollView.addView(textView);
        final var scrollViewParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0
        );
        scrollViewParams.weight = 1f;
        scrollView.setLayoutParams(scrollViewParams);
        final var root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.addView(scrollView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            root.setFitsSystemWindows(true);
        }
        final var buttonLayoutParams = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        buttonLayoutParams.weight = 1f;
        final var buttonBar = new LinearLayout(this);
        final var testButton = new Button(this);
        testButton.setText(R.string.test);
        testButton.setOnClickListener((v) -> testAll(textView));
        testButton.setLayoutParams(buttonLayoutParams);
        buttonBar.addView(testButton);
        final var pingButton = new Button(this);
        pingButton.setText(R.string.ping);
        pingButton.setOnClickListener((v) -> ping(textView));
        pingButton.setLayoutParams(buttonLayoutParams);
        buttonBar.addView(pingButton);
        root.addView(buttonBar);
        setContentView(root);
    }

    @SuppressLint("SetTextI18n")
    private void ping(TextView textView) {
        textView.setText("");
        new Thread(() -> {
            final var runtime = Runtime.getRuntime();
            try (final var inputStream = runtime.exec("ping -c 4 google.com").getInputStream()) {
                final var br = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = br.readLine()) != null) {
                    final var finalLine = line;
                    runOnUiThread(() -> textView.setText(textView.getText() + "\n" + finalLine));
                }
                br.close();
            } catch (IOException e) {
                textView.setText(e.getMessage());
            }
        }).start();
    }

    private void testAll(TextView textView) {
        final var status = new HashMap<String, String>();
    
        displayResult(status, textView);
    
        for (final var site : sites) {
            testURL(status, textView, site);
        }
    }
}
