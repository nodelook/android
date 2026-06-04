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
        add(new SiteInfo("[VCS] GitHub: Site", "github.com/xmha97/test/blob/main/test", "thegithubsiteisworkingproperly"));
        add(new SiteInfo("[VCS] GitHub: API", "api.github.com/octocat?s=thegithubapiisworkingproperly", "thegithubapiisworkingproperly"));
        add(new SiteInfo("[VCS] GitHub: Pages", "xmha97.github.io/status", "200"));
        add(new SiteInfo("[VCS] GitHub: Gists", "gist.githubusercontent.com/xmha97/94f6ba425d9874179fdd73fc0e2dc899/raw/e3a7ca5109c651f711000b3a02b4e032bd1d695f/status", "200"));
        add(new SiteInfo("[VCS] GitHub: Repository", "raw.githubusercontent.com/xmha97/test/refs/heads/main/status", "200"));
        add(new SiteInfo("[VCS] GitHub: Releases", "github.com/xmha97/test/releases/download/v1.0.0/status", "200"));
        add(new SiteInfo("[VCS] GitLab: Repository", "gitlab.com/xmha97/test/-/raw/master/status", "200"));
        add(new SiteInfo("[VCS] Codeberg: Repository", "codeberg.org/xmha97/test/raw/branch/main/status", "200"));
        add(new SiteInfo("[VCS] Codeberg: Releases", "codeberg.org/xmha97/test/releases/download/v1.0.0/status", "200"));
        add(new SiteInfo("[Wikipedia] English", "en.wikipedia.org/w/index.php?title=User:Xmha97/status.js&action=raw&ctype=text/javascript", "200"));
        add(new SiteInfo("[Wikipedia] Arabic", "ar.wikipedia.org/w/index.php?title=User:Xmha97/status.js&action=raw&ctype=text/javascript", "200"));
        add(new SiteInfo("[Wikipedia] Persian", "fa.wikipedia.org/w/index.php?title=User:Xmha97/status.js&action=raw&ctype=text/javascript", "200"));
        add(new SiteInfo("[Docker Registry] Timeweb Cloud", "dockerhub.timeweb.cloud/v2/jc21/nginx-proxy-manager/manifests/latest", "application/vnd.oci.image.index.v1+json"));
        add(new SiteInfo("[Docker Registry] Pardis Co", "mirrors.pardisco.co/v2/jc21/nginx-proxy-manager/manifests/latest", "application/vnd.oci.image.index.v1+json"));
        add(new SiteInfo("[Docker Registry] Arvan Cloud", "docker.arvancloud.ir/v2/jc21/nginx-proxy-manager/manifests/latest", "application/vnd.oci.image.index.v1+json"));
        add(new SiteInfo("[Docker Registry] Atlantis Cloud", "hub.atlantiscloud.ir/v2/jc21/nginx-proxy-manager/manifests/latest", "application/vnd.oci.image.index.v1+json"));
        add(new SiteInfo("[Ubuntu Repository] Ubuntu Archive", "archive.ubuntu.com/ubuntu/dists/noble/Release", "Ubuntu"));
        add(new SiteInfo("[Ubuntu Repository] ArvanCloud", "mirror.arvancloud.ir/ubuntu/dists/noble/Release", "Ubuntu"));
        add(new SiteInfo("[Ubuntu Repository] Friedrich-Alexander University Erlangen-Nürnberg", "ftp.fau.de/ubuntu/dists/noble/Release", "Ubuntu"));
        add(new SiteInfo("[Ubuntu Repository] NetCologne", "mirror.netcologne.de/ubuntu/dists/noble/Release", "Ubuntu"));
        add(new SiteInfo("[Ubuntu Repository] NLUUG", "mirror.nl.leaseweb.net/ubuntu/dists/noble/Release", "Ubuntu"));
        add(new SiteInfo("[Ubuntu Repository] Leaseweb", "ftp.nluug.nl/os/Linux/distr/ubuntu/dists/noble/Release", "Ubuntu"));
        add(new SiteInfo("[Ubuntu Repository] University of California, Berkeley", "mirrors.ocf.berkeley.edu/ubuntu/dists/noble/Release", "Ubuntu"));
        add(new SiteInfo("[Ubuntu Repository] University of Waterloo Computer Science Club", "mirror.csclub.uwaterloo.ca/ubuntu/dists/noble/Release", "Ubuntu"));
        add(new SiteInfo("[Software Repos] Snapcraft", "api.snapcraft.io/", "snapcraft"));
        add(new SiteInfo("[Software Repos] Flathub", "flathub.org/api/v2/status", "OK"));
        add(new SiteInfo("[Search Engine] Google", "google.com/search?q=thegooglesearchsiteisworkingproperly", "thegooglesearchsiteisworkingproperly"));
        add(new SiteInfo("[Search Engine] Bing", "www.bing.com/search?q=thebingsearchsiteisworkingproperly","thebingsearchsiteisworkingproperly"));
        add(new SiteInfo("[Search Engine] DuckDuckGo", "duckduckgo.com/?q=theduckduckgosearchsiteisworkingproperly","theduckduckgosearchsiteisworkingproperly"));
        add(new SiteInfo("[Social Media] TikTok", "www.tiktok.com/robots.txt", "Disallow"));
        add(new SiteInfo("[Social Media] Telegram", "t.me/thetelegramsiteisworkingproperly", "thetelegramsiteisworkingproperly"));
        add(new SiteInfo("[Social Media] Signal", "updates.signal.org/desktop/latest.yml", "signal"));
        add(new SiteInfo("[Social Media] WhatsApp by Meta", "www.whatsapp.com/robots.txt", "Disallow"));
        add(new SiteInfo("[Social Media] WhatsApp Web by Meta", "web.whatsapp.com/robots.txt", "Disallow"));
        add(new SiteInfo("[Social Media] Facebook by Meta", "www.facebook.com/robots.txt", "Disallow"));
        add(new SiteInfo("[Social Media] Instagram by Meta", "www.instagram.com/robots.txt", "Disallow"));
        add(new SiteInfo("[Social Media] Reddit", "www.reddit.com/robots.txt", "Disallow"));
        add(new SiteInfo("[Social Media] X", "x.com/robots.txt", "Disallow"));
        add(new SiteInfo("[Social Media] Bluesky", "bsky.app/robots.txt", "Allow"));
        add(new SiteInfo("[Social Media] Bale", "bale.ai/robots.txt", "Disallow"));
        add(new SiteInfo("[Social Media] Bale Web", "web.bale.ai/robots.txt", "Disallow"));
        add(new SiteInfo("[Social Media] Bale Web Beta", "beta.bale.ai/robots.txt", "Disallow"));
        add(new SiteInfo("[Music] Spotify", "www.spotify.com/robots.txt", "Disallow"));
        add(new SiteInfo("[Music] Spotify Open", "open.spotify.com/robots.txt", "Disallow"));
        add(new SiteInfo("[Video] Aparat", "www.aparat.com/robots.txt", "Disallow"));
        add(new SiteInfo("[Video] YouTube", "www.youtube.com/robots.txt", "Disallow"));
        add(new SiteInfo("[Video] BiliBili", "api.bilibili.com/x/web-interface/nav", "false"));
        add(new SiteInfo("[AI] Claude", "claude.ai/robots.txt", "Disallow"));
        add(new SiteInfo("[AI] Copilot", "copilot.microsoft.com/robots.txt", "Disallow"));
        add(new SiteInfo("[AI] Grok", "grok.com/robots.txt", "Disallow"));
        add(new SiteInfo("[AI] Gemini", "gemini.google.com/robots.txt", "Disallow"));
        add(new SiteInfo("[AI] ChatGPT", "chatgpt.com/robots.txt", "Disallow"));
        add(new SiteInfo("[XXX] PornHub", "www.pornhub.com/passkey/preflight", "false"));
        add(new SiteInfo("[XXX] Fuq", "www.fuq.com/robots.txt", "Disallow"));
        add(new SiteInfo("[XXX] xHamster", "xhamster.com/robots.txt", "Disallow"));
        add(new SiteInfo("[XXX] XNXX", "www.xnxx.com/robots.txt", "Disallow"));
        add(new SiteInfo("[XXX] RedGifs", "www.redgifs.com/robots.txt", "Disallow"));
        add(new SiteInfo("[Other] DropBox", "www.dropbox.com/scl/fi/dovory2z1y9xnj6kxwyq7/status?rlkey=48kb8gpm3fjnx76oglv1bm3u0&st=hy5uz0th&dl=1", "200"));
        add(new SiteInfo("[Other] JS-org", "pad.js.org/status", "200"));
        add(new SiteInfo("[Other] jsDelivr", "cdn.jsdelivr.net/gh/xmha97/test@1.0.0/status", "200"));
        add(new SiteInfo("[Other] Postman", "postman-echo.com/status/200", "200"));
        add(new SiteInfo("[Other] Firefox", "detectportal.firefox.com/success.txt", "success"));
        add(new SiteInfo("[Other] Translate by Google", "translate.google.com/?text=thegoogletranslatesiteisworkingproperly", "thegoogletranslatesiteisworkingproperly"));
        add(new SiteInfo("[Other] Docker Hub", "hub.docker.com/search?q=thedockerhubsiteisworkingproperly", "thedockerhubsiteisworkingproperly"));
        add(new SiteInfo("[Other] Archive", "archive.org/download/xmha97/status", "200"));
        add(new SiteInfo("[Other] Pastebin", "pastebin.com/raw/ER5BRSx7", "200"));
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
