package app.tini.android;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadata;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;

/**
 * Çalma hizmeti: Tını arka planda / ekran kapalıyken çalarken uygulamanın kapanmamasını sağlar.
 * Medya bildirimi + kilit ekranı kumandası + kulaklık / Bluetooth / direksiyon düğmeleri (MediaSession).
 * Düğmeler sayfaya window.tiniKomut('play'|'pause'|'next'|'prev') olarak iletilir.
 */
public class CalmaServisi extends Service {
    private static final String KANAL = "calma";
    private static final int NO = 7;
    private static final String EYLEM = "app.tini.android.KOMUT";

    private static CalmaServisi canli;
    private static boolean sonCal = false;
    private static String sonBaslik = "", sonSanatci = "";

    private MediaSession oturum;
    private PowerManager.WakeLock kilit;
    private Bitmap simge;
    private boolean ilkBaslat = true;
    private boolean onPlanda = false;

    private static volatile boolean istenen = false;
    private static final android.os.Handler ana = new android.os.Handler(android.os.Looper.getMainLooper());
    /** Şarkı geçişinde sayfa çok kısa "durdu" der; bu süre içinde yeniden çalarsa hiçbir şey değişmez. */
    private static final Runnable duraklat = () -> { if (!CalmaServisi.istenen && CalmaServisi.canli != null) { CalmaServisi.sonCal = false; CalmaServisi.canli.uygula(); } };
    /** Gerçekten durdurulduysa: 45 sn sonra ön plan bırakılır (bildirim kalır, oradan yeniden çalınır). */
    private static final Runnable birak = () -> { if (!CalmaServisi.istenen && CalmaServisi.canli != null) CalmaServisi.canli.onPlandanCik(); };

    /** Sayfadan gelen çalma durumu. Hizmet yoksa ve çalıyorsa başlatılır. */
    static void guncelle(Context c, boolean cal, String baslik, String sanatci) {
        istenen = cal;
        if (baslik != null && !baslik.isEmpty()) sonBaslik = baslik;
        if (sanatci != null && !sanatci.isEmpty()) sonSanatci = sanatci;
        ana.removeCallbacks(duraklat); ana.removeCallbacks(birak);
        if (canli != null) {
            if (cal) { sonCal = true; canli.uygula(); }
            else { ana.postDelayed(duraklat, 2500); ana.postDelayed(birak, 45000); }
            return;
        }
        if (!cal) return;
        sonCal = true;
        try {
            Intent i = new Intent(c, CalmaServisi.class);
            if (Build.VERSION.SDK_INT >= 26) c.startForegroundService(i); else c.startService(i);
        } catch (Exception e) { }
    }

    @Override public void onCreate() {
        super.onCreate();
        canli = this;
        NotificationManager nm = getSystemService(NotificationManager.class);
        if (Build.VERSION.SDK_INT >= 26 && nm.getNotificationChannel(KANAL) == null) {
            NotificationChannel k = new NotificationChannel(KANAL, "Çalan şarkı", NotificationManager.IMPORTANCE_LOW);
            k.setShowBadge(false); k.setSound(null, null);
            nm.createNotificationChannel(k);
        }
        try { simge = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher); } catch (Exception e) { }
        oturum = new MediaSession(this, "Tını Çalar");
        oturum.setCallback(new MediaSession.Callback() {
            @Override public void onPlay() { MainActivity.komut("play"); }
            @Override public void onPause() { MainActivity.komut("pause"); }
            @Override public void onSkipToNext() { MainActivity.komut("next"); }
            @Override public void onSkipToPrevious() { MainActivity.komut("prev"); }
            @Override public void onStop() { MainActivity.komut("pause"); }
        });
        Intent ac = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        oturum.setSessionActivity(PendingIntent.getActivity(this, 0, ac, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT));
        oturum.setActive(true);
        PowerManager pm = getSystemService(PowerManager.class);
        kilit = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "tini:calma");
        kilit.setReferenceCounted(false);
    }

    @Override public int onStartCommand(Intent i, int f, int id) {
        if (i != null && EYLEM.equals(i.getAction())) {
            String k = i.getStringExtra("k");
            if (k != null) MainActivity.komut(k);
        }
        uygula();
        return START_NOT_STICKY;
    }

    private PendingIntent eylem(String k, int no) {
        Intent i = new Intent(this, CalmaServisi.class).setAction(EYLEM).putExtra("k", k);
        return PendingIntent.getService(this, no, i, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void uygula() {
        long eylemler = PlaybackState.ACTION_PLAY | PlaybackState.ACTION_PAUSE | PlaybackState.ACTION_PLAY_PAUSE
                | PlaybackState.ACTION_SKIP_TO_NEXT | PlaybackState.ACTION_SKIP_TO_PREVIOUS | PlaybackState.ACTION_STOP;
        oturum.setPlaybackState(new PlaybackState.Builder().setActions(eylemler)
                .setState(sonCal ? PlaybackState.STATE_PLAYING : PlaybackState.STATE_PAUSED, PlaybackState.PLAYBACK_POSITION_UNKNOWN, 1f).build());
        MediaMetadata.Builder m = new MediaMetadata.Builder()
                .putString(MediaMetadata.METADATA_KEY_TITLE, sonBaslik.isEmpty() ? "Tını" : sonBaslik)
                .putString(MediaMetadata.METADATA_KEY_ARTIST, sonSanatci);
        if (simge != null) m.putBitmap(MediaMetadata.METADATA_KEY_ART, simge);
        oturum.setMetadata(m.build());

        Notification.Builder b = Build.VERSION.SDK_INT >= 26 ? new Notification.Builder(this, KANAL) : new Notification.Builder(this);
        b.setSmallIcon(R.drawable.ic_nota)
         .setContentTitle(sonBaslik.isEmpty() ? "Tını" : sonBaslik)
         .setContentText(sonSanatci)
         .setLargeIcon(simge)
         .setOngoing(sonCal)
         .setShowWhen(false)
         .setVisibility(Notification.VISIBILITY_PUBLIC)
         .setContentIntent(oturum.getController().getSessionActivity())
         .setDeleteIntent(eylem("pause", 9))
         .addAction(new Notification.Action.Builder(android.graphics.drawable.Icon.createWithResource(this, R.drawable.ic_onceki), "Önceki", eylem("prev", 1)).build())
         .addAction(new Notification.Action.Builder(android.graphics.drawable.Icon.createWithResource(this, sonCal ? R.drawable.ic_duraklat : R.drawable.ic_oynat), sonCal ? "Duraklat" : "Çal", eylem(sonCal ? "pause" : "play", 2)).build())
         .addAction(new Notification.Action.Builder(android.graphics.drawable.Icon.createWithResource(this, R.drawable.ic_sonraki), "Sonraki", eylem("next", 3)).build())
         .setStyle(new Notification.MediaStyle().setMediaSession(oturum.getSessionToken()).setShowActionsInCompactView(0, 1, 2));
        Notification n = b.build();
        if (sonCal || ilkBaslat || onPlanda) {
            ilkBaslat = false; onPlanda = true;
            try {
                if (Build.VERSION.SDK_INT >= 29) startForeground(NO, n, ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK);
                else startForeground(NO, n);
            } catch (Exception e) { getSystemService(NotificationManager.class).notify(NO, n); }
        }
        if (onPlanda) { try { if (!kilit.isHeld()) kilit.acquire(6 * 60 * 60 * 1000L); } catch (Exception e) { } }
        /* Duraklatılınca hemen ön plandan çıkılmaz: hizmet ön planda kalır, yalnız bildirim "duraklatıldı" görünür */
        if (!sonCal) { try { getSystemService(NotificationManager.class).notify(NO, n); } catch (Exception e) { } }
    }

    /** Uzun süre duraklatıldı: ön plan ve uyanık kalma bırakılır, bildirim kalır. */
    void onPlandanCik() {
        onPlanda = false;
        try { if (kilit.isHeld()) kilit.release(); } catch (Exception e) { }
        MainActivity.caliyor = false;
        if (Build.VERSION.SDK_INT >= 24) stopForeground(Service.STOP_FOREGROUND_DETACH); else stopForeground(false);
        ilkBaslat = false;
        try { uygula(); } catch (Exception e) { }
    }

    @Override public void onTaskRemoved(Intent i) {
        /* Uygulama son uygulamalardan kaydırılıp kapatılırsa çalma durur, bildirim kalkar */
        stopForeground(true); stopSelf();
        super.onTaskRemoved(i);
    }

    @Override public void onDestroy() {
        canli = null;
        try { if (kilit != null && kilit.isHeld()) kilit.release(); } catch (Exception e) { }
        try { oturum.setActive(false); oturum.release(); } catch (Exception e) { }
        try { getSystemService(NotificationManager.class).cancel(NO); } catch (Exception e) { }
        super.onDestroy();
    }

    @Override public IBinder onBind(Intent i) { return null; }
}
