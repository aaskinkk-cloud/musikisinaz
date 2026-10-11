# Tını

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
| **A-B tekrar** | Bir bölümü döngüde çal (A-B düğmesi: başlangıç, bitiş, kapat) |
| **Şarkılar arası geçiş** | Biten şarkı kısılır, yenisi yavaşça açılır (3/6/10 sn) |
| **Kaldığım yer** | 10 dk+ kayıtlar kaldığı saniyeden başlar |
| **İstatistik** | Bugün, son 7 gün, en çok dinlenen şarkı ve sanatçılar |
| **Ücretsiz sürümü bul** | İnternette ara → İnternet Arşivi (yasal, ücretsiz) ve YouTube'da bulur; tek dokunuşla listeye ekler |
| **YouTube çalma ekranı** | Alt oynatıcı son çalan kaynağı (yerel / YouTube / Spotify) iki satırda gösterir; YouTube'da dokununca tam ekran: video, ilerleme, ±10 sn, önceki/sonraki, videoya özel hız, kesit, uyku, tekrar; bilgi kartını kaydırınca video değişir |
| **Basit mod** | Ayarlar › Görünüm › Basit mod: büyük yazı ve düğmeler; kuyruk, ekolayzır ve ayrıntılı ayarlar gizlenir (silinmez). Üstteki "Basit ✕" ile tek dokunuşla çıkılır |
| **Araç modu** | Büyük düğmeler, kaydırarak şarkı değiştirme |
| **Sanatçıyla birlikte** | Sanatçı/albüm sayfasında ve aramada, YouTube listendeki aynı sanatçının şarkıları altta; hepsini birlikte çal |
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
| `manifest.webmanifest`, simgeler | Ana ekran simgesi ve uygulama bilgileri |

## Sürüm

**4.6** — Kesik kesik çalma düzeltmesi (sistem durdurunca zorla yeniden çalma kaldırıldı), kilit ekranı kumandası her çalışta yeniden kurulur (YouTube dahil), Ayarlar'da çalma günlüğü, A-B en az 3 sn.  
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
