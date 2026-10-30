package app.tini.android;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Size;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.ServiceWorkerClient;
import android.webkit.ServiceWorkerController;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Tını — Android kabuğu.
 * Sayfa: GitHub Pages (her güncelleme kendiliğinden gelir); açılmazsa Cloudflare kopyası.
 * - Kalıcı tam ekran (Chrome uyarısı yok).
 * - Telefondaki müzikler MediaStore'dan okunur, dosya kopyalanmaz: https://appassets.androidplatform.net/media/<id>
 * - Arka planda ve ekran kapalıyken çalma: ön plan hizmeti + medya bildirimi (CalmaServisi).
 * - Kilit ekranı, bildirim, kulaklık ve araç (Bluetooth / direksiyon) düğmeleri → window.tiniKomut(...)
 */
public class MainActivity extends Activity {
    private static final String ADRES = "https://aaskinkk-cloud.github.io/musikisinaz/";
    private static final String YEDEK = "https://ahmet-uygulamalar.pages.dev/musikisinaz/";
    static final String MEDYA_HOST = "appassets.androidplatform.net";
    private static final int DOSYA_SEC = 41, IZIN = 42;
    private static final int ZEMIN = 0xFF0D1118;

    static WeakReference<MainActivity> ornek = new WeakReference<>(null);
    static volatile boolean caliyor = false;

    private boolean yedekte = false;
    private TiniWeb web;
    private FrameLayout kap;
    private ValueCallback<Uri[]> dosyaCb;
    private final Handler ana = new Handler(Looper.getMainLooper());
    private ContentObserver gozcu;
    private final Runnable degisti = () -> js("window.nativeBridgeEvent&&nativeBridgeEvent('changed')");

    /** Arka plana geçince sayfa "görünmez" sayılmasın: YouTube ve müzik ekran kapalıyken de sürer. */
    static class TiniWeb extends WebView {
        TiniWeb(Context c) { super(c); }
        @Override protected void onWindowVisibilityChanged(int v) {
            if (v != View.VISIBLE && caliyor) return;
            super.onWindowVisibilityChanged(v);
        }
        @Override protected void onVisibilityChanged(View d, int v) {
            if (v != View.VISIBLE && caliyor) return;
            super.onVisibilityChanged(d, v);
        }
    }

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        ornek = new WeakReference<>(this);
        Window w = getWindow();
        if (Build.VERSION.SDK_INT >= 28) {
            WindowManager.LayoutParams lp = w.getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            w.setAttributes(lp);
        }
        web = new TiniWeb(this);
        web.setBackgroundColor(ZEMIN);
        kap = new FrameLayout(this);
        kap.setBackgroundColor(ZEMIN);
        kap.addView(web, new FrameLayout.LayoutParams(-1, -1));
        setContentView(kap);
        if (Build.VERSION.SDK_INT >= 26) web.setRendererPriorityPolicy(WebView.RENDERER_PRIORITY_IMPORTANT, false);

        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setDatabaseEnabled(true);
        s.setAllowFileAccess(false);
        s.setMediaPlaybackRequiresUserGesture(false);
        s.setLoadWithOverviewMode(true);
        s.setUseWideViewPort(true);
        s.setTextZoom(100);
        s.setSupportMultipleWindows(true);
        s.setJavaScriptCanOpenWindowsAutomatically(true);
        s.setUserAgentString(s.getUserAgentString() + " TiniAndroid/1");
        web.addJavascriptInterface(new Kopru(), "Android");

        web.setWebViewClient(new WebViewClient() {
            @Override public WebResourceResponse shouldInterceptRequest(WebView v, WebResourceRequest r) { return medya(r); }
            @Override public boolean shouldOverrideUrlLoading(WebView v, WebResourceRequest r) {
                if (!r.isForMainFrame()) return false;
                return disariAc(r.getUrl());
            }
            @Override public void onPageFinished(WebView v, String url) { v.evaluateJavascript(INDIR_JS, null); tamEkran(); }
            @Override public void onReceivedHttpError(WebView v, WebResourceRequest r, WebResourceResponse res) {
                if (r.isForMainFrame() && res != null && (res.getStatusCode() == 404 || res.getStatusCode() >= 500)) yedegeGec(v);
            }
            @Override public void onReceivedError(WebView v, WebResourceRequest r, android.webkit.WebResourceError e) {
                if (r.isForMainFrame()) yedegeGec(v);
            }
        });
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                ServiceWorkerController.getInstance().setServiceWorkerClient(new ServiceWorkerClient() {
                    @Override public WebResourceResponse shouldInterceptRequest(WebResourceRequest r) { return medya(r); }
                });
            } catch (Exception e) { }
        }
        web.setWebChromeClient(new WebChromeClient() {
            @Override public boolean onShowFileChooser(WebView v, ValueCallback<Uri[]> cb, FileChooserParams p) {
                if (dosyaCb != null) dosyaCb.onReceiveValue(null);
                dosyaCb = cb;
                Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, p.getMode() == FileChooserParams.MODE_OPEN_MULTIPLE);
                try { startActivityForResult(i, DOSYA_SEC); } catch (Exception e) { dosyaCb = null; return false; }
                return true;
            }
            /* target=_blank / window.open: YouTube, YouTube Music vb. telefondaki uygulamada açılır */
            @Override public boolean onCreateWindow(WebView v, boolean dialog, boolean user, android.os.Message m) {
                WebView gecici = new WebView(MainActivity.this);
                gecici.setWebViewClient(new WebViewClient() {
                    @Override public boolean shouldOverrideUrlLoading(WebView g, WebResourceRequest r) { disariAc(r.getUrl()); g.destroy(); return true; }
                });
                ((WebView.WebViewTransport) m.obj).setWebView(gecici);
                m.sendToTarget();
                return true;
            }
        });

        gozcu = new ContentObserver(ana) {
            @Override public void onChange(boolean self) { ana.removeCallbacks(degisti); ana.postDelayed(degisti, 2500); }
        };
        try { getContentResolver().registerContentObserver(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, true, gozcu); } catch (Exception e) { }

        if (b != null) web.restoreState(b); else web.loadUrl(ADRES);
        tamEkran();
        pilSor();
    }

    /** Samsung pil tasarrufu arka plandaki çalmayı durdurmasın: bir kez "Kısıtlama yok" izni istenir. */
    private void pilSor() {
        try {
            android.os.PowerManager pm = getSystemService(android.os.PowerManager.class);
            if (pm == null || pm.isIgnoringBatteryOptimizations(getPackageName())) return;
            android.content.SharedPreferences sp = getSharedPreferences("tini", MODE_PRIVATE);
            if (sp.getBoolean("pilSoruldu", false)) return;
            sp.edit().putBoolean("pilSoruldu", true).apply();
            ana.postDelayed(() -> {
                try {
                    Intent i = new Intent(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS, Uri.parse("package:" + getPackageName()));
                    startActivity(i);
                    Toast.makeText(this, "Arka planda kesintisiz çalma için \"İzin ver\" de", Toast.LENGTH_LONG).show();
                } catch (Exception e) { }
            }, 2500);
        } catch (Exception e) { }
    }

    /* ---------- Tam ekran ---------- */
    private void tamEkran() {
        final Window w = getWindow();
        final View d = w.getDecorView();
        w.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        d.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        if (Build.VERSION.SDK_INT >= 30) {
            Runnable r = () -> {
                WindowInsetsController c = d.getWindowInsetsController();
                if (c == null) c = w.getInsetsController();
                if (c != null) {
                    c.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
                    c.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
                }
            };
            r.run(); d.post(r); d.postDelayed(r, 300);
        }
    }

    @Override public void onWindowFocusChanged(boolean f) { super.onWindowFocusChanged(f); if (f) tamEkran(); }
    @Override protected void onResume() {
        super.onResume(); tamEkran();
        if (web != null) { web.onResume(); web.resumeTimers(); js("window.nativeBridgeEvent&&nativeBridgeEvent('resume')"); }
    }
    /* Bilerek web.onPause() çağrılmaz: müzik arka planda sürer. */
    @Override protected void onPause() { super.onPause(); }
    @Override protected void onSaveInstanceState(Bundle o) { super.onSaveInstanceState(o); if (web != null) web.saveState(o); }
    @Override protected void onDestroy() {
        try { getContentResolver().unregisterContentObserver(gozcu); } catch (Exception e) { }
        if (isFinishing()) { try { stopService(new Intent(this, CalmaServisi.class)); } catch (Exception e) { } }
        super.onDestroy();
    }

    /** Geri: önce sayfanın içindeki geri (açık pencere, klasik görünüm); en sonda uygulama kapanmaz, arkaya alınır → müzik sürer. */
    @Override public void onBackPressed() {
        if (web != null && web.canGoBack()) web.goBack(); else moveTaskToBack(true);
    }

    @Override protected void onActivityResult(int req, int res, Intent d) {
        if (req == DOSYA_SEC && dosyaCb != null) {
            Uri[] r = null;
            if (res == RESULT_OK && d != null) {
                if (d.getClipData() != null) {
                    int n = d.getClipData().getItemCount(); r = new Uri[n];
                    for (int i = 0; i < n; i++) r[i] = d.getClipData().getItemAt(i).getUri();
                } else if (d.getData() != null) r = new Uri[]{ d.getData() };
            }
            dosyaCb.onReceiveValue(r); dosyaCb = null;
            return;
        }
        super.onActivityResult(req, res, d);
    }

    @Override public void onRequestPermissionsResult(int req, String[] p, int[] g) {
        super.onRequestPermissionsResult(req, p, g);
        if (req == IZIN) js("window.nativeBridgeEvent&&nativeBridgeEvent('permission'," + izinVar() + ")");
    }

    /* ---------- Yardımcılar ---------- */
    void js(String kod) { ana.post(() -> { if (web != null) web.evaluateJavascript(kod, null); }); }

    /** Bildirim / kilit ekranı / direksiyon düğmesi → sayfa */
    static void komut(String k) {
        MainActivity a = ornek.get();
        if (a != null) a.js("window.tiniKomut&&tiniKomut('" + k + "')");
    }

    private boolean disariAc(Uri u) {
        if (u == null) return false;
        String sema = u.getScheme() == null ? "" : u.getScheme();
        String host = u.getHost() == null ? "" : u.getHost();
        if (sema.equals("intent")) {
            try { Intent i = Intent.parseUri(u.toString(), Intent.URI_INTENT_SCHEME); i.addCategory(Intent.CATEGORY_BROWSABLE); i.setComponent(null); startActivity(i); }
            catch (Exception e) {
                try { Intent i = Intent.parseUri(u.toString(), Intent.URI_INTENT_SCHEME); String y = i.getStringExtra("browser_fallback_url"); if (y != null) startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(y))); } catch (Exception x) { }
            }
            return true;
        }
        if ((sema.equals("http") || sema.equals("https")) && (host.endsWith("github.io") || host.endsWith("pages.dev"))) return false;
        try { startActivity(new Intent(Intent.ACTION_VIEW, u)); } catch (Exception e) { Toast.makeText(this, "Açılamadı: " + u, Toast.LENGTH_SHORT).show(); }
        return true;
    }

    private void yedegeGec(WebView v) {
        if (!yedekte) {
            yedekte = true;
            ana.post(() -> { Toast.makeText(this, "GitHub açılmadı — yedek adresten (Cloudflare) açılıyor", Toast.LENGTH_LONG).show(); v.loadUrl(YEDEK); });
        } else {
            ana.post(() -> v.loadDataWithBaseURL(null,
                "<html><body style='font:18px sans-serif;background:#0d1118;color:#e8ecf2;padding:40px 24px;text-align:center'>"
                + "<p>Tını şu an açılamıyor.<br>İnternet bağlantını kontrol et.</p>"
                + "<p><a href='" + ADRES + "' style='display:inline-block;margin-top:16px;padding:14px 22px;border-radius:12px;background:#e4502a;color:#fff;text-decoration:none'>Yeniden dene</a></p></body></html>",
                "text/html", "utf-8", null));
        }
    }

    private boolean izinVar() {
        String p = Build.VERSION.SDK_INT >= 33 ? Manifest.permission.READ_MEDIA_AUDIO : Manifest.permission.READ_EXTERNAL_STORAGE;
        return checkSelfPermission(p) == PackageManager.PERMISSION_GRANTED;
    }

    /* ---------- Telefondaki müzikler: liste, ses ve kapak ---------- */
    private String tara() {
        JSONArray a = new JSONArray();
        if (!izinVar()) return a.toString();
        ContentResolver cr = getContentResolver();
        String[] k = { MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.YEAR, MediaStore.Audio.Media.TRACK, MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE, MediaStore.Audio.Media.MIME_TYPE, MediaStore.Audio.Media.DATE_ADDED };
        /* zil, bildirim, alarm sesleri ve 15 sn'den kısa kayıtlar alınmaz; WhatsApp sesli mesajları ve arama kayıtları alınmaz */
        String sec = MediaStore.Audio.Media.DURATION + ">=15000 AND " + MediaStore.Audio.Media.IS_RINGTONE + "=0 AND "
                + MediaStore.Audio.Media.IS_NOTIFICATION + "=0 AND " + MediaStore.Audio.Media.IS_ALARM + "=0";
        if (Build.VERSION.SDK_INT >= 29) sec += " AND IFNULL(" + MediaStore.Audio.Media.RELATIVE_PATH + ",'') NOT LIKE '%WhatsApp%' AND IFNULL("
                + MediaStore.Audio.Media.RELATIVE_PATH + ",'') NOT LIKE '%Call%'";
        try (Cursor c = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, k, sec, null, MediaStore.Audio.Media.DATE_ADDED + " DESC")) {
            if (c == null) return a.toString();
            while (c.moveToNext()) {
                try {
                    JSONObject o = new JSONObject();
                    o.put("id", c.getLong(0));
                    o.put("name", c.getString(1));
                    o.put("title", c.getString(2));
                    o.put("artist", c.getString(3));
                    o.put("album", c.getString(4));
                    o.put("albumId", c.getLong(5));
                    o.put("year", c.getInt(6));
                    o.put("track", c.getInt(7));
                    o.put("duration", c.getLong(8) / 1000.0);
                    o.put("size", c.getLong(9));
                    o.put("mime", c.getString(10));
                    o.put("added", c.getLong(11) * 1000L);
                    a.put(o);
                } catch (Exception e) { }
            }
        } catch (Exception e) { }
        return a.toString();
    }

    private static Map<String, String> basliklar() {
        Map<String, String> h = new HashMap<>();
        h.put("Access-Control-Allow-Origin", "*");
        h.put("Access-Control-Allow-Headers", "*");
        h.put("Access-Control-Allow-Methods", "GET, HEAD, OPTIONS");
        h.put("Access-Control-Expose-Headers", "Content-Length, Content-Range, Accept-Ranges");
        h.put("Cache-Control", "no-store");
        return h;
    }

    private static WebResourceResponse yok() {
        WebResourceResponse r = new WebResourceResponse("text/plain", "utf-8", new ByteArrayInputStream(new byte[0]));
        r.setStatusCodeAndReasonPhrase(404, "Not Found"); r.setResponseHeaders(basliklar());
        return r;
    }

    WebResourceResponse medya(WebResourceRequest r) {
        Uri u = r.getUrl();
        if (u == null || !MEDYA_HOST.equals(u.getHost())) return null;
        if ("OPTIONS".equalsIgnoreCase(r.getMethod())) {
            WebResourceResponse o = new WebResourceResponse("text/plain", "utf-8", new ByteArrayInputStream(new byte[0]));
            o.setStatusCodeAndReasonPhrase(204, "No Content"); o.setResponseHeaders(basliklar());
            return o;
        }
        java.util.List<String> p = u.getPathSegments();
        if (p.size() < 2) return yok();
        long id;
        try { id = Long.parseLong(p.get(1)); } catch (Exception e) { return yok(); }
        if (p.get(0).equals("media")) return ses(id, r.getRequestHeaders());
        if (p.get(0).equals("art")) return kapak(id);
        return yok();
    }

    private WebResourceResponse ses(long id, Map<String, String> istek) {
        try {
            Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
            ContentResolver cr = getContentResolver();
            AssetFileDescriptor afd = cr.openAssetFileDescriptor(uri, "r");
            if (afd == null) return yok();
            long boy = afd.getLength();
            if (boy < 0) boy = afd.getParcelFileDescriptor().getStatSize();
            String tur = cr.getType(uri);
            if (tur == null) tur = "audio/mpeg";
            String aralik = null;
            if (istek != null) for (Map.Entry<String, String> e : istek.entrySet()) if (e.getKey().equalsIgnoreCase("Range")) aralik = e.getValue();
            long bas = 0, son = boy - 1;
            boolean kismi = false;
            if (aralik != null && aralik.startsWith("bytes=") && boy > 0) {
                String[] x = aralik.substring(6).split(",")[0].trim().split("-", -1);
                try {
                    if (x[0].isEmpty()) { long n = Long.parseLong(x[1]); bas = Math.max(0, boy - n); }
                    else { bas = Long.parseLong(x[0]); if (x.length > 1 && !x[1].isEmpty()) son = Math.min(boy - 1, Long.parseLong(x[1])); }
                    kismi = true;
                } catch (Exception e) { bas = 0; son = boy - 1; kismi = false; }
            }
            Map<String, String> h = basliklar();
            h.put("Accept-Ranges", "bytes");
            if (bas > son || bas >= boy) {
                afd.close();
                WebResourceResponse z = new WebResourceResponse(tur, null, new ByteArrayInputStream(new byte[0]));
                h.put("Content-Range", "bytes */" + boy);
                z.setStatusCodeAndReasonPhrase(416, "Range Not Satisfiable"); z.setResponseHeaders(h);
                return z;
            }
            InputStream in = afd.createInputStream();
            long atla = bas;
            while (atla > 0) { long s = in.skip(atla); if (s <= 0) break; atla -= s; }
            final long uzun = son - bas + 1;
            InputStream sinirli = new FilterInputStream(in) {
                long kalan = uzun;
                @Override public int read() throws IOException { if (kalan <= 0) return -1; int b = super.read(); if (b >= 0) kalan--; return b; }
                @Override public int read(byte[] b, int o, int n) throws IOException {
                    if (kalan <= 0) return -1; int r = super.read(b, o, (int) Math.min(n, kalan)); if (r > 0) kalan -= r; return r;
                }
                @Override public void close() throws IOException { super.close(); try { afd.close(); } catch (Exception e) { } }
            };
            h.put("Content-Length", String.valueOf(uzun));
            WebResourceResponse res = new WebResourceResponse(tur, null, sinirli);
            if (kismi) { h.put("Content-Range", "bytes " + bas + "-" + son + "/" + boy); res.setStatusCodeAndReasonPhrase(206, "Partial Content"); }
            else res.setStatusCodeAndReasonPhrase(200, "OK");
            res.setResponseHeaders(h);
            return res;
        } catch (Exception e) { return yok(); }
    }

    private WebResourceResponse kapak(long albumId) {
        try {
            byte[] veri = null;
            if (Build.VERSION.SDK_INT >= 29) {
                Bitmap bm = getContentResolver().loadThumbnail(ContentUris.withAppendedId(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, albumId), new Size(512, 512), null);
                ByteArrayOutputStream o = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 88, o);
                veri = o.toByteArray();
            } else {
                try (InputStream in = getContentResolver().openInputStream(ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId))) {
                    ByteArrayOutputStream o = new ByteArrayOutputStream(); byte[] t = new byte[16384]; int n;
                    while ((n = in.read(t)) > 0) o.write(t, 0, n);
                    veri = o.toByteArray();
                }
            }
            if (veri == null || veri.length == 0) return yok();
            Map<String, String> h = basliklar();
            h.put("Cache-Control", "max-age=86400");
            WebResourceResponse res = new WebResourceResponse("image/jpeg", null, new ByteArrayInputStream(veri));
            res.setStatusCodeAndReasonPhrase(200, "OK"); res.setResponseHeaders(h);
            return res;
        } catch (Exception e) { return yok(); }
    }

    /* ---------- Sayfa ile köprü: window.Android ---------- */
    class Kopru {
        @JavascriptInterface public String scan() { return tara(); }
        @JavascriptInterface public boolean hasPermission() { return izinVar(); }
        @JavascriptInterface public void requestPermission() {
            ana.post(() -> {
                java.util.ArrayList<String> l = new java.util.ArrayList<>();
                l.add(Build.VERSION.SDK_INT >= 33 ? Manifest.permission.READ_MEDIA_AUDIO : Manifest.permission.READ_EXTERNAL_STORAGE);
                if (Build.VERSION.SDK_INT >= 33) l.add(Manifest.permission.POST_NOTIFICATIONS);
                requestPermissions(l.toArray(new String[0]), IZIN);
            });
        }
        /** Çalma durumu: bildirim, kilit ekranı ve araç ekranı güncellenir; çalarken uygulama arka planda kapanmaz. */
        @JavascriptInterface public void setPlaying(boolean p, String baslik, String sanatci) {
            if (p) caliyor = true; /* false'a çalma hizmeti uzun duraklamada çevirir */
            ana.post(() -> CalmaServisi.guncelle(MainActivity.this, p, baslik, sanatci));
        }
        @JavascriptInterface public void kaydet(String ad, String tur, String b64) {
            try {
                byte[] veri = Base64.decode(b64, Base64.DEFAULT);
                if (ad == null || ad.trim().isEmpty()) ad = "tini-dosya";
                if (Build.VERSION.SDK_INT >= 29) {
                    ContentValues cv = new ContentValues();
                    cv.put(MediaStore.Downloads.DISPLAY_NAME, ad);
                    cv.put(MediaStore.Downloads.MIME_TYPE, (tur == null || tur.isEmpty()) ? "application/octet-stream" : tur);
                    Uri u = getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, cv);
                    try (OutputStream o = getContentResolver().openOutputStream(u)) { o.write(veri); }
                } else {
                    File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), ad);
                    try (FileOutputStream o = new FileOutputStream(f)) { o.write(veri); }
                }
                final String m = ad;
                ana.post(() -> Toast.makeText(MainActivity.this, "İndirilenler'e kaydedildi: " + m, Toast.LENGTH_LONG).show());
            } catch (Exception e) {
                ana.post(() -> Toast.makeText(MainActivity.this, "Kaydedilemedi: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        }
    }

    /** <a download href="blob:..."> tıklamalarını yakalayıp dosyayı Android.kaydet'e gönderir. */
    private static final String INDIR_JS =
        "(function(){if(window.__tiniIndir)return;window.__tiniIndir=1;var o=HTMLAnchorElement.prototype.click;" +
        "HTMLAnchorElement.prototype.click=function(){var h=this.href||'',ad=this.getAttribute('download');" +
        "if(ad!==null&&/^(blob:|data:)/.test(h)&&window.Android&&Android.kaydet){fetch(h).then(function(r){return r.blob()}).then(function(b){" +
        "var f=new FileReader();f.onload=function(){var s=String(f.result),i=s.indexOf(',');Android.kaydet(ad||'tini-dosya',b.type||'',s.slice(i+1))};f.readAsDataURL(b)});return}" +
        "return o.apply(this,arguments)}})();";
}
