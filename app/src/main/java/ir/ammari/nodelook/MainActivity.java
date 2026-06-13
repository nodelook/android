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
import android.util.Log;
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
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
//    private static final List<SiteInfo> sites = new ArrayList<>() {{
//        add(new SiteInfo("[VCS] GitHub: Site", "github.com/xmha97/test/blob/main/test", "thegithubsiteisworkingproperly"));
//        add(new SiteInfo("[VCS] GitHub: API", "api.github.com/octocat?s=thegithubapiisworkingproperly", "thegithubapiisworkingproperly"));
//        add(new SiteInfo("[VCS] GitHub: Pages", "xmha97.github.io/status", "200"));
//        add(new SiteInfo("[VCS] GitHub: Gists", "gist.githubusercontent.com/xmha97/94f6ba425d9874179fdd73fc0e2dc899/raw/e3a7ca5109c651f711000b3a02b4e032bd1d695f/status", "200"));
//        add(new SiteInfo("[VCS] GitHub: Repository", "raw.githubusercontent.com/xmha97/test/refs/heads/main/status", "200"));
//        add(new SiteInfo("[VCS] GitHub: Releases", "github.com/xmha97/test/releases/download/v1.0.0/status", "200"));
//        add(new SiteInfo("[VCS] GitLab: Repository", "gitlab.com/xmha97/test/-/raw/master/status", "200"));
//        add(new SiteInfo("[VCS] Codeberg: Repository", "codeberg.org/xmha97/test/raw/branch/main/status", "200"));
//        add(new SiteInfo("[VCS] Codeberg: Releases", "codeberg.org/xmha97/test/releases/download/v1.0.0/status", "200"));
//        add(new SiteInfo("[Encyclopedia] Wikipedia: English", "en.wikipedia.org/w/index.php?title=User:Xmha97/status.js&action=raw&ctype=text/javascript", "200"));
//        add(new SiteInfo("[Encyclopedia] Wikipedia: Arabic", "ar.wikipedia.org/w/index.php?title=User:Xmha97/status.js&action=raw&ctype=text/javascript", "200"));
//        add(new SiteInfo("[Encyclopedia] Wikipedia: Persian", "fa.wikipedia.org/w/index.php?title=User:Xmha97/status.js&action=raw&ctype=text/javascript", "200"));
//        add(new SiteInfo("[Encyclopedia] Fandom: Site", "www.fandom.com/robots.txt", "200"));
//        add(new SiteInfo("[Docker Registry] Russia: Timeweb Cloud", "dockerhub.timeweb.cloud/v2/jc21/nginx-proxy-manager/manifests/latest", "application/vnd.oci.image.index.v1+json"));
//        add(new SiteInfo("[Docker Registry] Russia: Git Verse", "dh-mirror.gitverse.ru/v2/jc21/nginx-proxy-manager/manifests/latest", "application/vnd.oci.image.index.v1+json"));
//        add(new SiteInfo("[Docker Registry] Iran: Pardis Co", "mirrors.pardisco.co/v2/jc21/nginx-proxy-manager/manifests/latest", "application/vnd.oci.image.index.v1+json"));
//        add(new SiteInfo("[Docker Registry] Iran: Arvan Cloud", "docker.arvancloud.ir/v2/jc21/nginx-proxy-manager/manifests/latest", "application/vnd.oci.image.index.v1+json"));
//        add(new SiteInfo("[Docker Registry] Iran: Atlantis Cloud", "hub.atlantiscloud.ir/v2/jc21/nginx-proxy-manager/manifests/latest", "application/vnd.oci.image.index.v1+json"));
//        add(new SiteInfo("[Docker Registry] Iran: Docker", "registry.docker.ir/v2/jc21/nginx-proxy-manager/manifests/latest", "application/vnd.oci.image.index.v1+json"));
//        add(new SiteInfo("[Docker Registry] Iran: Runflare", "mirror-docker.runflare.com/v2/jc21/nginx-proxy-manager/manifests/latest", "application/vnd.oci.image.index.v1+json"));
//        add(new SiteInfo("[Docker Registry] Iran: Abrha", "docker.abrha.net/v2/jc21/nginx-proxy-manager/manifests/latest", "application/vnd.oci.image.index.v1+json"));
//        add(new SiteInfo("[Ubuntu Repository] Ubuntu Archive", "archive.ubuntu.com/ubuntu/dists/noble/Release", "Ubuntu"));
//        add(new SiteInfo("[Ubuntu Repository] ArvanCloud", "mirror.arvancloud.ir/ubuntu/dists/noble/Release", "Ubuntu"));
//        add(new SiteInfo("[Ubuntu Repository] Germany: University of Erlangen-Nuremberg", "ftp.fau.de/ubuntu/dists/noble/Release", "Ubuntu"));
//        add(new SiteInfo("[Ubuntu Repository] Germany: NetCologne", "mirror.netcologne.de/ubuntu/dists/noble/Release", "Ubuntu"));
//        add(new SiteInfo("[Ubuntu Repository] NLUUG", "mirror.nl.leaseweb.net/ubuntu/dists/noble/Release", "Ubuntu"));
//        add(new SiteInfo("[Ubuntu Repository] Leaseweb", "ftp.nluug.nl/os/Linux/distr/ubuntu/dists/noble/Release", "Ubuntu"));
//        add(new SiteInfo("[Ubuntu Repository] University of Berkeley", "mirrors.ocf.berkeley.edu/ubuntu/dists/noble/Release", "Ubuntu"));
//        add(new SiteInfo("[Ubuntu Repository] University of Waterloo", "mirror.csclub.uwaterloo.ca/ubuntu/dists/noble/Release", "Ubuntu"));
//        add(new SiteInfo("[Alpine Repository] 001", "dl-cdn.alpinelinux.org/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] Russia: 002", "mirror.yandex.ru/mirrors/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 003", "mirrors.gigenet.com/alpinelinux/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] Germany: 004", "mirror1.hs-esslingen.de/pub/Mirrors/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 005", "mirror.leaseweb.com/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 008", "alpine.mirror.wearetriple.com/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 009", "mirror.clarkson.edu/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 010", "mirror.aarnet.edu.au/pub/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 011", "mirrors.dotsrc.org/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] Germany: 012", "ftp.halifax.rwth-aachen.de/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] China: 013", "mirrors.tuna.tsinghua.edu.cn/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] China: 014", "mirrors.ustc.edu.cn/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] China: 015", "mirrors.nju.edu.cn/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] China: 016", "mirror.lzu.edu.cn/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 017", "mirror.accum.se/mirror/alpinelinux.org/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 018", "mirror.xtom.com.hk/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 019", "mirror.csclub.uwaterloo.ca/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 020", "pkg.adfinis.com/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 021", "mirror.ps.kz/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 022", "mirrors.ircam.fr/pub/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 025", "ftp.icm.edu.pl/pub/Linux/distributions/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 026", "mirror.ungleich.ch/mirror/packages/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 027", "mirrors.edge.kernel.org/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 029", "eu.edge.kernel.org/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 030", "mirror.reenigne.net/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 031", "tux.rainside.sk/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 032", "alpine.cs.nycu.edu.tw/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 033", "mirror.ihost.md/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 035", "mirror.lagoon.nc/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 036", "alpinelinux.c3sl.ufpr.br/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 040", "mirror.kumi.systems/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 041", "mirror.sabay.com.kh/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 042", "mirrors.ocf.berkeley.edu/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 045", "mirror.alwyzon.net/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 046", "repo.iut.ac.ir/repo/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 047", "mirror.fcix.net/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 048", "alpine.sakamoto.pl/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 049", "mirror.2degrees.nz/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 050", "mirror.kku.ac.th/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 051", "mirror.uepg.br/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 053", "ftp.udx.icscoe.jp/Linux/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 054", "alpinelinux.mirror.garr.it/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 055", "mirrors.hostico.ro/alpinelinux/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 057", "mirror.mangohost.net/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 058", "mirror.bahnhof.net/pub/alpinelinux/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 059", "mirror.vinehost.net/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 060", "mirror.5i.fi/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] China: 062", "mirror.nyist.edu.cn/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 065", "mirror.marwan.ma/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 067", "alpine.koyanet.lv/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 068", "mirrors.hosterion.ro/alpinelinux/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 070", "mirrors.lax.silicloud.com/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 072", "mirrors.neterra.net/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 073", "mirror.twds.com.tw/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 074", "mirror.raiolanetworks.com/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 075", "alpinelinux.mirrors.ovh.net/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 076", "alpine.ethz.ch/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 077", "pkg.adfinis-on-exoscale.ch/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] China: 078", "mirrors.hust.edu.cn/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 079", "ftp2.crifo.org/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 081", "mirror.cs.odu.edu/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 082", "mirror.telepoint.bg/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 083", "mirror.mobinhost.com/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] China: 084", "mirrors.sdu.edu.cn/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 085", "us.mirror.ionos.com/linux/distributions/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 086", "eu.mirror.ionos.com/linux/distributions/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 090", "mirrors.hoobly.com/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 091", "mirror.gofoss.xyz/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 093", "ftp.yz.yamagata-u.ac.jp/pub/linux/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 095", "mirror.freedif.org/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 096", "mirror.techlabs.co.kr/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 097", "mirror.sajattack.xyz/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 099", "qontinuum.space/mirror/alpinelinux/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 102", "mirror.torchbyte.com/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Alpine Repository] 103", "mirror.ourhost.az/alpine/latest-stable/releases/x86_64/latest-releases.yaml", "alpine"));
//        add(new SiteInfo("[Software Repos] Snapcraft", "api.snapcraft.io/", "snapcraft"));
//        add(new SiteInfo("[Software Repos] Flathub", "flathub.org/api/v2/status", "OK"));
//        add(new SiteInfo("[Search Engine] Google", "google.com/search?q=thegooglesearchsiteisworkingproperly", "thegooglesearchsiteisworkingproperly"));
//        add(new SiteInfo("[Search Engine] Bing", "www.bing.com/search?q=thebingsearchsiteisworkingproperly","thebingsearchsiteisworkingproperly"));
//        add(new SiteInfo("[Search Engine] DuckDuckGo", "duckduckgo.com/?q=theduckduckgosearchsiteisworkingproperly","theduckduckgosearchsiteisworkingproperly"));
//        add(new SiteInfo("[Social Media] TikTok", "www.tiktok.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Social Media] Telegram", "t.me/thetelegramsiteisworkingproperly", "thetelegramsiteisworkingproperly"));
//        add(new SiteInfo("[Social Media] Discord", "discord.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Social Media] Signal", "updates.signal.org/desktop/latest.yml", "signal"));
//        add(new SiteInfo("[Social Media] WhatsApp: Site", "www.whatsapp.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Social Media] WhatsApp: Web", "web.whatsapp.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Social Media] Facebook: Site", "www.facebook.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Social Media] Facebook: Messenger", "www.messenger.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Social Media] Instagram", "www.instagram.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Social Media] Threads", "www.threads.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Social Media] Reddit", "www.reddit.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Social Media] X", "x.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Social Media] Bluesky", "bsky.app/robots.txt", "Allow"));
//        add(new SiteInfo("[Social Media] VK", "vk.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Social Media] Bale: Site", "bale.ai/robots.txt", "Disallow"));
//        add(new SiteInfo("[Social Media] Bale: Web", "web.bale.ai/robots.txt", "Disallow"));
//        add(new SiteInfo("[Social Media] Bale: Web Beta", "beta.bale.ai/robots.txt", "Disallow"));
//        add(new SiteInfo("[Social Media] Bale: API", "assets.bale.ai/configs.json", "bale"));
//        add(new SiteInfo("[Social Media] Bale: Signaling Server 1", "meet-gwbm1.ble.ir/", "OK"));
//        add(new SiteInfo("[Social Media] Bale: Signaling Server 2", "meet-gwbm2.ble.ir/", "OK"));
//        add(new SiteInfo("[Social Media] Bale: Signaling Server 3", "meet-gwbm3.ble.ir/", "OK"));
//        add(new SiteInfo("[Social Media] Bale: Signaling Server 4", "meet-gwbm4.ble.ir/", "OK"));
//        add(new SiteInfo("[Social Media] Bale: Signaling Server 5", "meet-gwbm5.ble.ir/", "OK"));
//        add(new SiteInfo("[Social Media] Bale: Signaling Server 6", "meet-gwbm6.ble.ir/", "OK"));
//        add(new SiteInfo("[Social Media] Bale: TURN Server", "meet-turn.ble.ir", "OK"));
//        add(new SiteInfo("[Social Media] Bale: Meet", "meet.bale.ai/token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"));
//        add(new SiteInfo("[Music] Spotify: Site", "www.spotify.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Music] Spotify: Web", "open.spotify.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Music] Spotify: API", "api.spotify.com/v1/me", "No token provided"));
//        add(new SiteInfo("[Music] YouTube Music", "music.youtube.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Music] Anghami", "play.anghami.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Video] Aparat", "www.aparat.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Video] Netflix", "www.netflix.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Video] YouTube", "www.youtube.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Video] BiliBili: API", "api.bilibili.com/x/web-interface/nav", "false"));
//        add(new SiteInfo("[Video] BiliBili: Image", "archive.biliimg.com", "OK"));
//        add(new SiteInfo("[Video] BiliBili: bimp", "bimp.hdslb.com", "OK"));
//        add(new SiteInfo("[Video] BiliBili: i0", "i0.hdslb.com", "OK"));
//        add(new SiteInfo("[Video] BiliBili: i1", "i1.hdslb.com", "OK"));
//        add(new SiteInfo("[Video] BiliBili: i2", "i2.hdslb.com", "OK"));
//        add(new SiteInfo("[Video] BiliBili: s1", "s1.hdslb.com", "OK"));
//        add(new SiteInfo("[Video] BiliBili: s2", "s2.hdslb.com", "OK"));
//        add(new SiteInfo("[Video] BiliBili: s3", "s3.hdslb.com", "OK"));
//        add(new SiteInfo("[Gaming] Biligame: API", "api.biligame.com", "SUCCESS"));
//        add(new SiteInfo("[Gaming] Biligame: API 1", "line1-h5-pc-api.biligame.com", "SUCCESS"));
//        add(new SiteInfo("[Gaming] Biligame: API 3", "line3-h5-pc-api.biligame.com", "SUCCESS"));
//        add(new SiteInfo("[Gaming] Kick", "kick.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Gaming] Twitch", "www.twitch.tv/robots.txt", "Disallow"));
//        add(new SiteInfo("[Shopping] Amazon: Canada", "www.amazon.ca/robots.txt", "Disallow"));
//        add(new SiteInfo("[Shopping] Amazon: US", "www.amazon.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Shopping] Amazon: UK", "www.amazon.co.uk/robots.txt", "Disallow"));
//        add(new SiteInfo("[Shopping] OpenSooq: Türkiye", "tr.opensooq.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Shopping] OpenSooq: UAE", "ae.opensooq.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[AI] Claude", "claude.ai/robots.txt", "Disallow"));
//        add(new SiteInfo("[AI] Copilot", "copilot.microsoft.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[AI] Grok", "grok.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[AI] Gemini", "gemini.google.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[AI] ChatGPT", "chatgpt.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Cloud Storage] DropBox: Site", "www.dropbox.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Cloud Storage] DropBox: Contents", "www.dropbox.com/scl/fi/dovory2z1y9xnj6kxwyq7/status?rlkey=48kb8gpm3fjnx76oglv1bm3u0&st=hy5uz0th&dl=1", "200"));
//        add(new SiteInfo("[Cloud Storage] pCloud", "www.pcloud.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Cloud Storage] OneDrive", "onedrive.live.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Cloud Storage] Google Drive", "drive.google.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Test] Postman Echo", "postman-echo.com/status/200", "200"));
//        add(new SiteInfo("[Test] Firefox DetectPortal", "detectportal.firefox.com/success.txt", "success"));
//        add(new SiteInfo("[Test] Fast", "fast.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Test] Speedtest", "www.speedtest.net/robots.txt", "Disallow"));
//        add(new SiteInfo("[Adult] PornHub", "www.pornhub.com/passkey/preflight", "false"));
//        add(new SiteInfo("[Adult] Fuq", "www.fuq.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Adult] xHamster", "xhamster.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Adult] XNXX", "www.xnxx.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Adult] RedGifs", "www.redgifs.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Adult] Stripchat", "stripchat.com/robots.txt", "Disallow"));
//        add(new SiteInfo("[Other] JS-org", "pad.js.org/status", "200"));
//        add(new SiteInfo("[Other] jsDelivr", "cdn.jsdelivr.net/gh/xmha97/test@1.0.0/status", "200"));
//        add(new SiteInfo("[Other] Google Translate", "translate.google.com/?text=thegoogletranslatesiteisworkingproperly", "thegoogletranslatesiteisworkingproperly"));
//        add(new SiteInfo("[Other] Docker Hub", "hub.docker.com/search?q=thedockerhubsiteisworkingproperly", "thedockerhubsiteisworkingproperly"));
//        add(new SiteInfo("[Other] Archive", "archive.org/download/xmha97/status", "200"));
//        add(new SiteInfo("[Other] Pastebin", "pastebin.com/raw/ER5BRSx7", "200"));
//    }};

    private void testURL(Map<String, String> status, @NonNull TextView textView, @NonNull SiteInfo site, SiteInfo[] sites) {
        new Thread(() -> {
            var result = "Invalid result";
            try (final var inputStream = new URL("https:/" + site.url()).openStream(); final var reader = new BufferedReader(new InputStreamReader(inputStream))) {
                final var expected = site.status();
                final var responseBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
                final var response = responseBuilder.toString();
                final var cleanResponse = response.replaceAll("[\\n\\r\\t .]", "");
                final var cleanExpected = expected.replaceAll("[\\n\\r\\t .]", "");
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
                status.put(site.name(), finalResult);
                displayResult(status, textView, sites);
            });
        }).start();
    }

    private void displayResult(Map<String, String> status, @NonNull TextView textView, SiteInfo[] sites) {
        final var text = new SpannableStringBuilder();
        for (final var site : sites) {
            final var key = site.name();
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
        final var scrollViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        scrollViewParams.weight = 1f;
        scrollView.setLayoutParams(scrollViewParams);
        final var root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.addView(scrollView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            root.setFitsSystemWindows(true);
        }
        final var buttonLayoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        buttonLayoutParams.weight = 1f;
        final var buttonBar = new LinearLayout(this);
        for (final var entry : Data.entries.entrySet()) {
            final var testButton = new Button(this);
            testButton.setText(entry.getKey());
            testButton.setOnClickListener((v) -> testAll(textView, entry.getValue()));
            testButton.setLayoutParams(buttonLayoutParams);
            buttonBar.addView(testButton);
        }
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

    private void testAll(TextView textView, @NonNull SiteInfo[] sites) {
        final var status = new HashMap<String, String>();

        displayResult(status, textView, sites);

        for (final var site : sites) {
            testURL(status, textView, site, sites);
        }
    }
}
