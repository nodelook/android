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
import java.util.HashMap;
import java.util.LinkedHashMap;
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
        add(new SiteInfo("Archive (Tor)", "archivep75mbjunhxc6x4j5mwjmomyxb573v42baldlqu56ruil2oiad.onion/download/xmha97/status", "200"));
        add(new SiteInfo("Pastebin", "pastebin.com/raw/ER5BRSx7", "200"));
        add(new SiteInfo("Wikipedia (English)", "en.wikipedia.org/w/index.php?title=User:Xmha97/status.js&action=raw&ctype=text/javascript", "200"));
        add(new SiteInfo("Wikipedia (Persian)", "fa.wikipedia.org/w/index.php?title=User:Xmha97/status.js&action=raw&ctype=text/javascript", "200"));
        add(new SiteInfo("Wikipedia (Arabic)", "ar.wikipedia.org/w/index.php?title=User:Xmha97/status.js&action=raw&ctype=text/javascript", "200"));
        add(new SiteInfo("HamGit (GitLab)", "hamgit.ir/xmha97/test/-/raw/main/status", "200"));
        add(new SiteInfo("AranServer (GitLab)", "gitlab.aranserver.com/xmha97/test/-/raw/main/status", "200"));
        add(new SiteInfo("Rokhsara (Gitea)", "git.rokhsara.com/xmha97/test/raw/branch/main/status", "200"));
        add(new SiteInfo("Fandak (Gitea)", "gitea.fandak.lol/xmha97/test/raw/branch/main/status", "200"));
        add(new SiteInfo("Mred (Gitea)", "git.mred.ir/xmha97/test/raw/branch/main/status", "200"));
        add(new SiteInfo("Paragraph (Forgejo)", "git.prgph.fun/xmha97/test/raw/branch/main/status", "200"));
        add(new SiteInfo("AbreHamrahi", "abrehamrahi.ir/o/public/vlwU5cXR", "200"));
        add(new SiteInfo("AbreHamrahi (ShortenerLink)", "abhm.ir/kpWgPj", "200"));
        add(new SiteInfo("JS-org", "pad.js.org/status", "200"));
        add(new SiteInfo("Uupload (ShortenerLink)", "my.uupload.ir/dl/NdQ4K1BD", "200"));
        add(new SiteInfo("Uupload", "s15.uupload.ir/files/xmha97/status.txt", "200"));
        add(new SiteInfo("jsDelivr", "cdn.jsdelivr.net/gh/xmha97/test@1.0.0/status", "200"));
        add(new SiteInfo("TEROZA", "trzw.ir/up/loads/status.txt", "200"));
        add(new SiteInfo("Liara", "obd.storage.c2.liara.space/status?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=6b96162b-d379-44a7-ae3f-e3cd178bbf19%2F20260429%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20260429T040002Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=064a1ef82adc6efe0918f74c909742770da989d9b90a348ec345d52fd0871e77", "200"));
        add(new SiteInfo("ArvanStorage (Tehran-Standard DotOne)", "test-hive.s3.ir-thr-at1.arvanstorage.ir/status", "200"));
        add(new SiteInfo("ArvanStorage (Tehran-Standard)", "xmh.s3.ir-thr-at1.arvanstorage.ir/status", "200"));
        add(new SiteInfo("ArvanStorage (Tehran-Standard WebSite)", "xmh.s3-website.ir-thr-at1.arvanstorage.ir/status", "200"));
        add(new SiteInfo("ArvanStorage (Tehran-HiOps)", "xmh.hot.ir-central1.arvanstorage.ir/status", "200"));
        add(new SiteInfo("ArvanStorage (Tehran-HiOps WebSite)", "xmh.hot-website.ir-central1.arvanstorage.ir/status", "200"));
        add(new SiteInfo("ArvanStorage (Tabriz)", "xmh.s3.ir-tbz-sh1.arvanstorage.ir/status", "200"));
        add(new SiteInfo("ArvanStorage (Tabriz WebSite)", "xmh.s3-website.ir-tbz-sh1.arvanstorage.ir/status", "200"));
        add(new SiteInfo("GitHub API", "api.github.com/octocat?s=200", "\n               MMM.           .MMM\n               MMMMMMMMMMMMMMMMMMM\n               MMMMMMMMMMMMMMMMMMM      _____\n              MMMMMMMMMMMMMMMMMMMMM    |     |\n             MMMMMMMMMMMMMMMMMMMMMMM   | 200 |\n            MMMMMMMMMMMMMMMMMMMMMMMM   |_   _|\n            MMMM::- -:::::::- -::MMMM    |/\n             MM~:~ 00~:::::~ 00~:~MM\n        .. MMMMM::.00:::+:::.00::MMMMM ..\n              .MM::::: ._. :::::MM.\n                 MMMM;:::::;MMMM\n          -MM        MMMMMMM\n          ^  M+     MMMMMMMMM\n              MMMMMMM MM MM MM\n                   MM MM MM MM\n                   MM MM MM MM\n                .~~MM~MM~MM~MM~~.\n             ~~~~MM:~MM~~~MM~:MM~~~~\n            ~~~~~~==~==~~~==~==~~~~~~\n             ~~~~~~==~==~==~==~~~~~~\n                 :~==~==~==~==~~\n"));
        add(new SiteInfo("Postman Echo", "postman-echo.com/status/200", "{\"status\":200}"));
    }};

    private void testURL(Map<String, String> status, @NonNull TextView textView, @NonNull String name, @NonNull URL url) {
        new Thread(() -> {
            var result = "Invalid result";
            try (final var inputStream = url.openStream()) {
                if (inputStream.read() == '2' && inputStream.read() == '0' && inputStream.read() == '0')
                    result = "success";
            } catch (IOException e) {
                result = e.getMessage();
                e.printStackTrace();
            }
            final var finalResult = result;
            runOnUiThread(() -> {
                status.put(name, finalResult);
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
            try {
                testURL(
                        status,
                        textView,
                        site.name,
                        new URL("https://" + site.url)
                );
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
