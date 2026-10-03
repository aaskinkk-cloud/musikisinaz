/* Musikisinaz çevrimdışı önbelleği. Sürüm değişince eski önbellek silinir. */
const V='musikisinaz-2.2';
const SHELL=['./','index.html','manifest.webmanifest','icon.svg'];
self.addEventListener('install',e=>{e.waitUntil(caches.open(V).then(c=>c.addAll(SHELL)).then(()=>self.skipWaiting()))});
self.addEventListener('activate',e=>{e.waitUntil(caches.keys().then(ks=>Promise.all(ks.filter(k=>k!==V).map(k=>caches.delete(k)))).then(()=>self.clients.claim()))});
self.addEventListener('fetch',e=>{
  const r=e.request;if(r.method!=='GET')return;
  const u=new URL(r.url);
  if(/youtube\.com|ytimg\.com|googlevideo\.com/.test(u.hostname))return; /* YouTube her zaman ağdan */
  if(r.mode==='navigate'){
    /* Önce ağ (güncelleme gelsin), 3 sn'de yanıt yoksa önbellek: internetsiz açılış takılmaz */
    e.respondWith(Promise.race([
      fetch(r).then(res=>{const c=res.clone();caches.open(V).then(x=>x.put('index.html',c));return res}),
      new Promise((_,rej)=>setTimeout(rej,3000))
    ]).catch(()=>caches.match('index.html')));
    return;
  }
  /* Yazı tipleri, etiket kütüphanesi ve uygulama dosyaları: önbellekten, arkada yenile */
  e.respondWith(caches.match(r).then(hit=>{
    const net=fetch(r).then(res=>{if(res&&(res.ok||res.type==='opaque')){const c=res.clone();caches.open(V).then(x=>x.put(r,c))}return res}).catch(()=>hit);
    return hit||net;
  }));
});
