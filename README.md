# Tını

**Uygulamayı aç:** https://aaskinkk-cloud.github.io/musikisinaz/ · Yedek: https://ahmet-uygulamalar.pages.dev/musikisinaz/
**Android uygulaması (APK):** https://github.com/aaskinkk-cloud/musikisinaz/releases/download/android/tini.apk

## Android uygulaması (APK)

| | |
|---|---|
| **Simge ve ad** | Turuncu zemin, beyaz nota; ana ekranda **Tını Çalar** adıyla görünür (tarayıcıdan yüklenen koyu simgeli Tını'dan ayırt edilir) |
| **Kalıcı tam ekran** | Durum ve gezinme çubukları gizli; Chrome'un "Tam ekrandan çıkmak için…" uyarısı yok. Kaydırınca çubuklar geçici görünür |
| **Telefondaki müzikler** | İzin verince telefondaki bütün şarkılar kendiliğinden listelenir (dosya seçmeye, kopyalamaya gerek yok); yeni indirilen şarkı listeye kendiliğinden gelir. Zil/bildirim sesleri, 15 sn'den kısa kayıtlar, WhatsApp sesli mesajları ve arama kayıtları alınmaz |
| **Arka planda çalma** | Ana ekrana dönünce ya da ekran kapanınca müzik (YouTube dahil) sürer; geri tuşu uygulamayı kapatmaz, arkaya alır |
| **Bildirim ve kilit ekranı** | Önceki · Çal/Duraklat · Sonraki düğmeleri |
| **Araç / kulaklık** | Bluetooth ve direksiyon ileri-geri, çal/duraklat düğmeleri çalışır |
| **Bağlantılar** | YouTube, YouTube Music vb. bağlantılar telefondaki uygulamada açılır |
| **Güncelleme** | Uygulama sayfayı GitHub'dan açar: web sürümüne gelen her güncelleme APK'ya da kendiliğinden gelir; GitHub açılmazsa Cloudflare kopyası açılır |

**Kurulum:** APK bağlantısına dokun → indir → aç → "Bilinmeyen kaynaklardan yükleme" iznini ver → Yükle. İlk açılışta **Müzik ve ses** ile **Bildirim** iznini ver.

## 6.16 (31.10.2026) — Dans eden ekolayzır
- Klasik görünümde ekolayzır çubuğuna **DANS** düğmesi eklendi (varsayılan açık). Şarkı çalarken ekolayzır düğmeleri müziğe göre oynuyor.
- **Yalnız görüntü:** sese, ekolayzır ayarlarına ve hazır ayarlara dokunmuyor. Durunca ya da duraklatınca düğmeler gerçek ayarlarına dönüyor.
- Bir banda dokununca dans duruyor ve gerçek düğmeler çıkıyor; elini çektikten 2,5 sn sonra dans yeniden başlıyor.
- Yerel şarkılarda gerçek frekans verisi kullanılıyor (EQ açıkken). YouTube'da tarayıcı ses verisine erişim vermediği için ritim taklit ediliyor (~126 vuruş/dk).

## 6.15 (31.10.2026) — Son eklenenleri geri al
- YouTube listesi ⋯ menüsü → **Son eklenenleri geri al…**: son 14 günde eklenen şarkılar yeniden eskiye, eklenme saatiyle listelenir.
- Bir seferde topluca gelenler ("toplu (20)") hazır işaretli, tek tek eklediklerin işaretsiz gelir; kontrol edip **Seçilenleri sil**.
- Eklenme zamanı her kaydın kimliğinden okunur; eski kayıtlar için de çalışır.

## 6.14 (31.10.2026) — YouTube listesi sadeleşti
- **Arama artık yalnız şarkı (video) döndürür.** Listeye 20 şarkı dolması, arama sonuçlarında çalma listelerinin çıkıp dokununca tümünün eklenmesinden kaynaklanıyordu. Çalma listesi sonuçları yalnız aramada "liste" / "playlist" geçerse gelir.
- Üst çubuktaki yeni simge düğmeleri soluk ve çerçevesiz.
- YouTube listesindeki her şarkının önünde **✕**: ilk dokunuş "Sil?" der, 3 sn içinde ikinci dokunuş siler.

## 6.13 (31.10.2026) — Albümden seçerek ekle
- Arama sonucundaki şarkıya dokununca şarkı çalar ve hemen **albümün şarkı listesi** açılır: yalnız seçtiğin şarkı işaretli (♪), listende olanlar soluk. İstediklerini işaretle → **Seçilenleri ekle**; hiçbirini istemiyorsan **Yalnız bu şarkı**. Artık albümün tamamı kendiliğinden eklenmez.
- Eklenenler albüm adıyla ayrı liste olarak durur (albüm sırasıyla).

## 6.12 (31.10.2026) — Modern başlık simgeleri
- Tını yazısının yanındaki simgeler yenilendi: ince çizgi, yumuşak zeminli yuvarlak kutular. İnternette ara → küre+büyüteç (kitaplık aramasından ayrışır), araç → yeni önden araç simgesi, tema → açıkta ay / koyuda güneş, ＋ gölgeli.
- Yeni araç simgesi uygulamanın her yerinde (araç modu düğmeleri dahil) kullanılır.

## 6.11 (31.10.2026) — Albümü sor
- YouTube aramasından bir şarkıyı çaldığında ya da eklediğinde şarkı hemen çalar; Tını arka planda şarkının **albümünü** bulur (YouTube Music albüm listesi) ve sorar: **Albümün tamamını ekle (n yeni şarkı)** · **Yalnız bu şarkı**.
- "Tamamını ekle" → albüm, YouTube listende **ayrı liste** olarak durur ("Albüm: Ad — Sanatçı"), albüm sırasıyla; çalan şarkı kesilmez, zaten olan şarkılar tekrar eklenmez.
- Albüm bulunamazsa soru çıkmaz. Sonuçlar telefonda saklanır (aynı şarkı için tekrar aranmaz). "Bir daha sorma" ya da Ayarlar › YouTube ve Spotify › **Albümü sor** ile kapatılır.
- Albüm araması API anahtarının günlük hakkından yer (şarkı başına bir arama).

## 6.10 (31.10.2026) — Liste bul
- YouTube sayfasında arama kutusunun altında küçük **Liste bul: YouTube Music · YouTube** düğmeleri: dokununca uygulamanın kendisi (listelerin sayfası) açılır. Listeyi bul → Paylaş → Bağlantıyı kopyala → Tını'ya dön → 📋 (dönüşte 📋 vurgulanır ve ipucu çıkar).

## 6.9 (31.10.2026) — YouTube sayfası sade
- **Tek kutu:** "Ara ya da bağlantı yapıştır" — bağlantı yapıştırılırsa listeye ekler, yazı yazılırsa arar (ayrı bağlantı kutusu kalktı); yanında 📋 yapıştır ve 🔍.
- **Küçük düğmeler:** oynatıcı altında ⇄ (listeler arası karışık) · ⏮ · ▶ · ⏭ · ▶YouTube (YouTube uygulamasında arka planda, Premium). Büyük "Arka planda çal" düğmesi simgeye dönüştü.
- **Listem başlığı:** ⇄ · Seç · ⋯ (Hepsini sırayla çal · Seç · Tekrar eden videoları temizle · Tümünü çalma listesine ekle · Bir YouTube listesini kaldır).
- **Listeler arası karışık çalma:** ⇄ → hangi listeler karışsın seç (tek tek eklediklerin de ayrı bir seçenek) → hepsi tek karışık sırada çalar; seçim hatırlanır.
- Liste başlıklarındaki ▶ ⇄ 🗑 düğmeleri küçültüldü, ince çerçeve. "Bilmen gerekenler" katlanır bilgi oldu.
- Çalma mantığına dokunulmadı.

## 6.8 (31.10.2026) — APK'da YouTube hesabına giriş (Premium → reklamsız)
- Reklam, **API anahtarıyla ilgili değildir**; anahtar yalnız aramaya yarar ve hangi Google hesabının anahtarı olursa olsun aynı çalışır.
- Reklamsız çalma için oynatıcının **Premium hesaba girişli** olması gerekir. APK'da: Ayarlar › YouTube ve Spotify › **YouTube hesabı (reklamsız) › Giriş yap** → Google girişi uygulamanın içinde açılır → giriş bitince Tını'ya kendiliğinden dönülür. Bir kez yeterli; satırda "Giriş yapıldı ✓" görünür.
- APK: üçüncü taraf çerezlere izin (YouTube oynatıcısı hesabı tanısın), Google girişini engelleyen "wv" işareti tarayıcı kimliğinden çıkarıldı. **Yeni APK'yı kurmak gerekir.**

## 6.7 (31.10.2026) — Dokun çal, basılı tut menü
- Klasik görünümde şarkıya **dokun → çalar**; **basılı tut → kısa menü** (tek sütun, sağda): üstte ⤒ ▲ ▼ ⤓ (taşırken açık kalır) ve 6 seçenek — Sıradaki olarak çal · Çalma listesine ekle · Listeden çıkar · Pasife al · Çoklu seçim · Şarkıyı sil (iki dokunuş, dosya telefonda kalır).
- Çoklu seçimde İŞLEM aynı menüyü seçilenler için açar ("Çoklu seçim" yerine "Seçilenleri çal").

## 6.6 (31.10.2026) — Klasik görünümde şarkı menüsü
- **Şarkıya dokun → menü açılır** (Winamp tarzı, iki sütun): ⤒ ▲ ▼ ⤓ (taşırken menü açık kalır, satır vurgulu ilerler) · Çal · Sıradaki çal · Listeye ekle · Şarkı bilgisi · Listeden çıkar · Pasife al · Kitaplıktan sil (iki dokunuş) · Telefondan sil · Birden çok seç.
- Menü açıkken **aynı şarkıya yine dokun → çalar**. Menünün dışına dokun → kapanır.
- **Alt çubuk her zaman tek sıra:** + EKLE · ARA · TÜMÜ · SEÇ. ARA'da kutu aynı sıraya açılır (✕ kapatır). Seçimde: İŞLEM (n) · TÜMÜNÜ SEÇ · BİTTİ — İŞLEM aynı menüyü seçilenler için açar. Satıra basılı tutmak da seçimi başlatır.
- 6.5'teki ikinci düğme sırası kaldırıldı.

## APK düzeltmesi (30.10.2026) — arka planda 2–3 şarkıdan sonra durma
- Şarkı geçişindeki çok kısa duraklamada uygulama "çalma bitti" sayılıp Android'in arka plan korumasından çıkıyordu; birkaç dakika sonra telefon uygulamayı uyutunca sıradaki şarkıya geçilemiyordu. Artık geçişlerde koruma sürer; yalnız gerçekten 45 sn'den uzun duraklatılınca bırakılır (bildirim kalır, oradan yeniden çalınır).
- Sayfa arka planda "görünmez" sayılmaz (iki yoldan da korunur) → zamanlayıcılar kısılmaz.
- İlk açılışta bir kez **pil kısıtlaması yok** izni istenir (Samsung'un pil tasarrufu arka plandaki çalmayı durdurmasın).
- **Yeni APK'yı kurmak gerekir** (eskinin üzerine).

## 6.5 (30.10.2026) — Klasik görünümde liste düzenleme
- **Seçim modu:** çalma listesinde bir satıra **basılı tut** ya da alttaki **SEÇ**'e dokun → satırlara dokunarak seç (☑).
- **▲ / ▼** seçilenleri bir sıra yukarı/aşağı taşır (birden çok seçili satır birlikte taşınır).
- **ÇIKAR** listeden çıkarır (şarkı kitaplıkta kalır; çalan şarkı çıkarılırsa sıradakine geçer).
- **PASİF** pasife alır (Listeler › Pasif müzikler'den geri alınır).
- **SİL** kitaplıktan kaldırır — yanlışlıkla olmasın diye **iki kez** dokunulur; dosyalar telefonda kalır.
- **⋯** Tümünü seç · Seçimi temizle · En üste / En alta taşı · Seçilenleri çal · Sıradaki olarak çal · Çalma listesine ekle · Telefondan da sil (tek şarkı) · Listeyi karıştır (çalan başta) · Listeyi çalma listesi olarak kaydet · Listeyi temizle.
- **BİTTİ (n)** seçim modundan çıkar. YouTube listesinde ▲▼ ve ÇIKAR çalışır; aramadayken PASİF, SİL ve ⋯ (sıraya/listeye ekle).
- Kitaplık listesinde düzenleyince görünen sıra kuyruk olur; ▶ ile o sırayla çalar.

## 6.2 (30.10.2026) — Klasik görünüm tek ekran
- Klasik görünüm telefon ekranına tam sığar ve hiçbir yerde boşluk bırakmaz (üstte kamera şeridi yok, kenarlar ve alt sıfır; TINI yazısı solda, kamera deliğinin altında kalmaz — 6.4): sayfa yukarı-aşağı kaymaz, başlık en üstte, alt çubuk (+ Ekle · Ara · Tümü) ekranın içinde.
- Çalma listesi kendi içinde kayar; sağda Winamp tarzı **asansör** (sürükle ya da çubuğa dokun). Çalan şarkı listede görünür tutulur.
- Ekolayzır sürgüleri kısa ekranlarda biraz kısalır, listeye yer açılır; frekans yazıları (ÖN, 60 … 16K) her ekran boyunda görünür (6.3).

## 6.1 (30.10.2026) — Sanatçı fotoğrafları
- Kapağı olmayan şarkılarda harf yerine **sanatçının fotoğrafı** (Deezer, ücretsiz, anahtarsız). Bulunan fotoğraflar telefonda saklanır, bir daha indirilmez; internet yokken ya da bulunamazsa harf kalır. "Türkü", "Music", "Bilinmeyen" gibi genel etiketler aranmaz; ad tam eşleşmezse fotoğraf konmaz.

## 6.0 (28.10.2026) — Android uygulaması + klasik görünüm
- **Android uygulaması (APK)** — yukarıdaki tablo.
- **Klasik görünüm (Winamp tarzı):** Ayarlar › Görünüm ve sekmeler › Klasik görünüm › Aç. Yeşil süre ekranı (dokun: kalan/geçen), kayan şarkı adı, spektrum, kbps/kHz, ses ve denge, konum çubuğu, ⏮ ▶ ‖ ■ ⏭ ⏏, ışıklı Karışık/Tekrar; 10 bant ekolayzır (ön yükseltme, hazır ayarlar, eğri) ve çalma listesi (ara, + Ekle, Tümü). EQ/PL pencereleri gizler; ekranın tamamını kaplar. "Açılışta klasik görünüm" seçilebilir.
- Tarayıcı sekmesinde açılınca "Uygulama olarak yükle" düğmesi.
- Büyük yazı boyutunda yana kayma giderildi.

## 5.13 (23.10.2026) — Tam ekran düğmesi geri
- Kurulu uygulamada gizlenen tam ekran düğmesi yeniden görünür ve çalışır. Düğmeye basınca Chrome kendi "Tam ekrandan çıkmak için…" uyarısını bir kez gösterir (tarayıcı kuralı). Dokununca kendiliğinden tam ekran isteği gönderilmez.

## 5.12 (22.10.2026) — Kurulu uygulamada tam ekran düğmesi
- Ana ekrandan yüklenen uygulama baştan tam ekran açıldığı için tam ekran düğmesi gizlendi ve basılsa da tam ekran isteği gönderilmez. Önceden düğmeye basınca uyarı çıkıyor, sayfa yeniden diziliyor, yazılar kayboluyor / sayfa değişiyordu.

## 5.11 (22.10.2026) — Tam ekran uyarısı kesin çözüm
- Sayfanın kendiliğinden tam ekrana geçme istekleri (ilk dokunuşta, açılışta, dosya seçiciden dönünce) **tamamen kaldırıldı**; Chrome'un "Tam ekrandan çıkmak için…" uyarısı ekrana dokununca artık çıkmaz. Tam ekran isteği yalnız tam ekran düğmesine basınca gönderilir. Ana ekrandan yüklenen uygulama zaten tam ekran açılır.

## 5.10 (22.10.2026) — Açılışta tam ekran
- Ana ekrandan açılınca uygulama **doğrudan tam ekran** gelir (`manifest.webmanifest`: `"display": "fullscreen"`). Tarayıcı içinden tam ekran isteği gönderilmediği için Chrome'un "Tam ekrandan çıkmak için…" uyarısı da çıkmaz.
- Daha önce yüklenmiş simge eski ayarla açılabilir: simgeyi kaldırıp Chrome › ⋮ › **Uygulamayı yükle** ile yeniden yükle.

## 5.9 (22.10.2026) — Sessiz ekran
- **Tam ekran uyarısı:** Ana ekrandan açılan (kurulu, tam ekran) uygulamada içeriden tam ekran isteği gönderilmez → Chrome'un "Tam ekrandan çıkmak için yukarıdan sürükleyip…" uyarısı çıkmaz. Chrome sekmesinde tam ekrana her girişte bu uyarıyı tarayıcı kendisi gösterir; sayfa kapatamaz.
- **Kelimeye dokununca Google paneli:** Chrome'un "Dokunarak ara" paneli açılmasın diye sayfa (ve içindeki çerçeveler) dokunuşu kendisi karşılar. Uzun basıp seçme çalışır.
- Blok `/* SESSİZ EKRAN */` adıyla `index.html`'in başındadır; tüm uygulamalarda aynıdır.

Tını: telefondaki şarkılarını çalan ücretsiz müzik çalar. 10 bant ekolayzır, vokal azaltma (yalnız müzik), sessizlik atlama, uyku zamanlayıcısı, araç modu, çalma listeleri, YouTube ve Spotify listeleri.

**Uygulamayı aç:** https://aaskinkk-cloud.github.io/musikisinaz/

## Özellikler

| | |
|---|---|
| **Kitaplık** | Telefondaki şarkılar; şarkı, albüm, sanatçı ve liste görünümü. 6 dakikadan uzun kayıtlar **Albüm** olur: dokununca içindeki şarkılar açılır, sırayla ya da kendi içinde karışık çalar |
| **Ekolayzır** | 10 bant, ön yükseltme, bas güçlendirici, stereo denge |
| **Vokal** | Tümü · Yalnız müzik (vokali azalt/kapat) · Yalnız sözler |
| **Şarkı bölümleri** | Sözlü yerleri ya da müzik aralarını atlayarak çalma (bölümleri şarkının kendisinden bulur) |
| **Akıllı çalma** | Sessizlik atlama, kesit (başlangıç-bitiş), süre sınırı, uyku zamanlayıcısı |
| **Şarkıya özel hız** | Çalma ekranında 1× · 1,1 · 1,2 · 1,3 · 1,5 — o şarkı hep bu hızda çalar |
| **Müzikle video çek** | Müzik durmadan Tını içinden kamera ile video kaydı (ortam sesi ya da müzik temiz + sesin) |
| **Diğer şarkıları getir** | Çalarken ⋮ menüsünden: *Sanatçının diğer şarkıları* / *Albümün diğer şarkıları* (telefondakiler + YouTube listendekiler) |
| **Benzer adlı sanatçılar** | Neşet Ertaş · NEŞET ERTAŞ · Neset Ertas · "feat." ekli adlar tek sanatçıda toplanır |
| **Sıralama** | Kuyruk, çalma listesi ve YouTube listende ⋮⋮ tutamağını **0,3 sn basılı tut + sürükle**; hızlı kaydırma yanlışlıkla taşımaz |
| **Çalma ekranı** | Tek sayfa; kapağı sola/sağa kaydır → sonraki/önceki şarkı; kapağın sağına/soluna çift dokun → ±10 sn; üstte sıra ve sıradaki şarkı |
| **Şarkılar arası geçiş** | Biten şarkı kısılır, yenisi yavaşça açılır (3/6/10 sn) |
| **Kaldığım yer** | 10 dk+ kayıtlar kaldığı saniyeden başlar |
| **İstatistik** | Bugün, son 7 gün, en çok dinlenen şarkı ve sanatçılar |
| **Ücretsiz sürümü bul** | İnternette ara → İnternet Arşivi (yasal, ücretsiz) ve YouTube'da bulur; tek dokunuşla listeye ekler |
| **YouTube çalma ekranı** | Alt oynatıcı son çalan kaynağı (yerel / YouTube / Spotify) iki satırda gösterir; YouTube'da dokununca tam ekran: video, ilerleme, ±10 sn, önceki/sonraki, videoya özel hız, kesit, uyku, tekrar; bilgi kartını kaydırınca video değişir |
| **Basit mod** | Ayarlar › Görünüm › Basit mod: büyük yazı ve düğmeler; kuyruk, ekolayzır ve ayrıntılı ayarlar gizlenir (silinmez). Üstteki "Basit ✕" ile tek dokunuşla çıkılır |
| **Seçerek ekleme** | Birden çok dosya eklerken liste açılır, istediklerini işaretlersin; kitaplıktan kaldırdıkların işaretsiz gelir, "Yeniden tara" onları geri getirmez |
| **Telefonda YouTube Music'e geç** | YouTube'dan çalarken mini oynatıcıdaki kırmızı düğme ya da YouTube çalma ekranındaki *YouTube Music'te sürdür* düğmesi şarkıyı kaldığı yerden YouTube Music (ya da YouTube) uygulamasında açar, Tını durur. Telefonda ana ekrana dönünce otomatik geçişi tarayıcı engeller; bu yüzden tek dokunuş |
| **Bilgisayarda YouTube Music'e geç** | Bilgisayarda Tını küçültülünce ya da arkada kalınca YouTube'dan çalan şarkı kaldığı saniyeden YouTube Music (ya da YouTube) penceresinde sürer, Tını durur. Bir kez *açılır pencere izni* gerekir (Ayarlar › YouTube ve Spotify › İzin nasıl verilir?). Elle geçiş: şarkı menüsü › *YouTube Music'te sürdür* |
| **Ayarlar akordiyon** | Bölümler katlanır; açılışta yalnız ilk bölüm (Görünüm ve sekmeler) açık. Kaydırırken ya da parmak kayınca düğmeler tetiklenmez (yanlışlıkla dokunma koruması) |
| **Sekme gizleme** | Kuyruk ve Spotify sekmeleri varsayılan gizli; Ayarlar › Görünüm ve sekmeler'den açılır. Kuyruğa çalma ekranından her zaman ulaşılır |
| **Tek satır üst alan** | Kitaplıkta arama · Şarkı/Albüm/Sanatçı/Liste · sıralama menüsü tek satırda; 🔍 dokununca arama kutusu açılır (✕ kapatır). Sıralama, Yeniden tara ve Pasif müzikler sağdaki menüde; şarkı sayısı ve süre Tümünü çal kartında |
| **Ferah kitaplık** | Tümünü çal / Karıştır çubuğu ve her şarkı ince çerçeveli, zeminli ayrı kartta; çalan şarkı vurgulu |
| **Klasik görünüm (Winamp tarzı)** | Ayarlar › Görünüm ve sekmeler › Klasik görünüm. Çalar, 10 bant ekolayzır ve çalma listesi tek ekranda; aynı kitaplık ve kuyruk. × ya da geri tuşu modern görünüme döndürür |
| **Pasif müzikler** | Şarkının ⋮ menüsünden *Pasife al*: kitaplıkta görünmez, çalınmaz, kuyruktan çıkar. Albüm/sanatçı sayfasında tümü birden pasife alınabilir. *Listeler › Pasif müzikler*'de görünür; *Aktif yap* / *Tümünü aktif yap* ile geri gelir (favori, çalma sayısı korunur) |
| **Tekrar yükleme yok** | Dosya eklerken kitaplıkta (aktif) ya da Pasif listesinde olan dosyalar ve aynı seçimde iki kez gelenler atlanır |
| **YouTube albüm** | "albüm/album" içeren aramada yalnız uzun tek videolar (tam albüm) gelir; oynatma listesi aktarılırken hangi videoların alınacağını seçersin |
| **Araç modu** | Büyük düğmeler, kaydırarak şarkı değiştirme |
| **Sanatçıyla birlikte** | Sanatçı/albüm sayfasında ve aramada, YouTube listendeki aynı sanatçının şarkıları altta; hepsini birlikte çal |
| **Listeler arası karışık** | YouTube sayfasında ⇄: seçtiğin YouTube listelerinin bütün şarkıları tek karışık sırada çalar |
| **YouTube ve Spotify** | Resmi oynatıcılarla liste; bağlantı yapıştırarak ekleme; YouTube listeleri başlık altında toplanır: sırayla ya da karışık çal, tek dokunuşla kaldır |

## Kurulum

Chrome'da adresi aç → menü → **Ana ekrana ekle**. İnternetsiz de çalışır; yeni sürüm çıkınca ekranda "Yenile" görünür.

## Gizlilik

Dosyaların ve verilerin **yalnız cihazında** işlenir ve saklanır; hiçbir sunucuya gönderilmez.

## Dosyalar

| Dosya | Açıklama |
|---|---|
| `index.html` | Uygulamanın tamamı (tek dosya) |
| `sw.js` | Çevrimdışı önbellek ve sürüm güncelleme |
| `manifest.webmanifest`, `icon-*.png`, `icon.svg` | Ana ekran simgesi ve uygulama bilgileri |
| `index.cloudflare.html` | Cloudflare kopyası için kaynak (simge adları farklı) |
| `android/` | Android uygulaması (WebView kabuğu, çalma hizmeti, simgeler) |
| `.github/workflows/android.yml` | APK'yı derleyip *android* sürümüne yükler |

## Sürüm

**6.16** — Klasik görünümde DANS: EQ düğmeleri müziğe göre oynar (yalnız görüntü).
**6.15** — Son eklenenleri geri al (toplu gelenler işaretli).
**6.14** — Arama yalnız şarkı; soluk üst düğmeler; YouTube listesinde ✕ ile onaylı silme.
**6.13** — Albüm şarkıları seçilebilir liste olarak; yalnız seçilenler eklenir.  
6.12 — Modern başlık simgeleri.  
6.11 — YouTube aramasından eklenen şarkının albümünü bulup ayrı liste olarak ekleme sorusu.  
6.10 — Liste bul: YouTube Music / YouTube uygulamasını açan küçük düğmeler.  
6.9 — YouTube sayfası sade; listeler arası karışık çalma; tekrarları temizle.  
6.8 — APK'da YouTube hesabına giriş (Premium → reklamsız).  
6.7 — Dokun çal, basılı tut kısa menü.  
6.6 — Klasik görünümde şarkıya dokununca menü; alt çubuk tek sıra.  
6.5 — Klasik görünümde listeden çıkarma, taşıma, pasif, silme (seçim modu).  
6.4 — Klasik görünümde üst, yan ve alt boşluklar kaldırıldı.  
6.3 — Ekolayzır frekans yazıları kesilmez.  
6.2 — Klasik görünüm tek ekrana sığar, listede asansör.  
6.1 — Kapaksız şarkılarda sanatçı fotoğrafı.  
6.0 — Android uygulaması (APK), klasik (Winamp tarzı) görünüm.  
5.8 — Kitaplık üst alanı 5 satırdan 2 satıra indi (arama, sekmeler ve sıralama tek satır; sayı Tümünü çal kartında).  
5.7 — Çalma ekranındaki A-B düğmesi kaldırıldı (alt çubuktan taşıyordu).  
5.6 — Ayarlar akordiyon + dokunma koruması, Kuyruk/Spotify sekmesi gizleme (varsayılan gizli), kitaplıkta çerçeveli ferah şarkı kartları.  
5.5 — Telefonda tek dokunuşla YouTube Music uygulamasına geçiş (mini oynatıcı + YouTube çalma ekranı düğmesi).  
5.4 — Bilgisayarda küçültünce YouTube'dan çalan şarkı YouTube Music'te kaldığı yerden sürer (açılır pencere izni gerekir); elle geçiş menüsü.  
5.3 — Pasif müzikler listesi: pasife alınan şarkılar yerel listede görünmez, çalınmaz; yüklerken aktif ve pasif dosyalar yeniden yüklenmez.  
5.2 — `?cal=1` ile açılınca telefondaki müzikler kaldığı yerden çalar (Milli Emlak › Multimedya › Tını bu adresi açar).  
5.1 — YouTube aramasında yalnız yazdığın sözcüklerin hepsi geçen videolar gelir ("Mahmut Tuncer fazile" → yalnız Fazile); diğerleri "Diğer sonuçları da göster" ile.  
5.0 — Seçerek ekleme, kaldırılan şarkıların hatırlanması, YouTube albüm araması ve listeden seçerek aktarma, ekran kapalıyken ağır inceleme yapılmaz, çalma günlüğü kalıcı.  
4.9 — Ekran kapalıyken kesintisiz şarkı geçişi (bitişe 0,3 sn kala sıradakine geçer; oynatıcı durmaz, kilit ekranı kumandası kaybolmaz); arka planda ses motoru beklenmez.  
4.8 — Ekran kapalıyken şarkı geçişinde ses kısık/sessiz kalma düzeltildi (yumuşak başlatma ve geçiş arka planda atlanır), çalma günlüğüne ses düzeyi ve sıra eklendi.  
4.7 — Liste bitince durmaz: baştan sürer, karışıksa her turda yeniden karıştırılır (Ayarlar › Liste bitince: Devam et / Dur); ekran kapalıyken çalma engellenirse otomatik yeniden dener; takılma bekçisi.  
4.6 — Kesik kesik çalma düzeltmesi (sistem durdurunca zorla yeniden çalma kaldırıldı), kilit ekranı kumandası her çalışta yeniden kurulur (YouTube dahil), Ayarlar'da çalma günlüğü, A-B en az 3 sn.  
4.5 — Basit mod.  
4.4 — YouTube tam ekran çalma ekranı; alt oynatıcı her zaman 2 satır ve çalan kaynağı gösterir; küçük video kutusu küçüldü.  
4.3 — Çalma ekranı her telefonda tek sayfa: İçerik/Hız/Ses özet düğmeleri, dokununca ayarlar açılır; yer kalmazsa kapak küçülür.  
4.2 — Tek sayfa çalma ekranı, kaydırarak şarkı değiştirme, 6 dk+ kayıtta Sonraki listedeki şarkıya geçer ("tek şarkı" işareti), YouTube/Spotify sekmesinde kendi kumandası, ücretsiz sürüm bulma, A-B, geçiş, kaldığım yer, istatistik.  
4.1 — Çalarken sanatçının/albümün diğer şarkılarını getir, benzer adlı sanatçıları birleştirme, basılı tut-sürükle sıralama, küçültünce ilk seferde susma düzeltmesi.  
4.0 — Şarkıya özel hız, müzik çalarken video çekme, sanatçı sayfasında YouTube şarkıları ve birlikte çalma, uzun adlar alt satıra geçer.  
3.9 — Albüm (6 dk+) alt listesi ve kendi içinde karışık çalma, YouTube listeleri başlık altında, sözlü/müzik bölümü bulma yenilendi, bölüm bölüm Ayarlar, "Telefondan da sil" yol göstericisi.  
3.8 — YouTube listesinden gelen şarkıları toplu kaldırma (Listeyi kaldır düğmesi, şarkı menüsü ya da liste bağlantısı).  
3.7 — Arama motorları için sayfa bilgileri ve README.  
3.6 — Açılışta kaynak seçimi, sekmeler arası kaydırma, Spotify sekmesi.
