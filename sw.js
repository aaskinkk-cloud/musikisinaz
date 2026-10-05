/* musikisinaz çevrimdışı önbelleği. Yalnızca kendi önbelleğini siler (aynı adresteki diğer uygulamalara dokunmaz). */
const P='musikisinaz-',V=P+'3.4';
const SHELL=['./','index.html','manifest.webmanifest','icon.svg'];
self.addEventListener('install',e=>{e.waitUntil(caches.open(V).then(c=>c.addAll(SHELL)).then(()=>self.skipWaiting()))});
self.addEventListener('activate',e=>{e.waitUntil(caches.keys().then(ks=>Promise.all(ks.filter(k=>k.indexOf(P)===0&&k!==V).map(k=>caches.delete(k)))).then(()=>self.clients.claim()))});
self.addEventListener('fetch',e=>{
  const r=e.request;
  /* Paylaş → Musikisinaz: gelen şarkılar geçici kutuya konur, uygulama açılınca kitaplığa eklenir */
  if(r.method==='POST'&&new URL(r.url).searchParams.has('paylas')){
    e.respondWith((async()=>{try{const fd=await r.formData(),c=await caches.open('paylasim-musikisinaz');let i=0;
      for(const f of fd.getAll('audio')){if(!f||!f.name)continue;await c.put('/paylasim/'+Date.now()+'-'+(i++),new Response(f,{headers:{'content-type':f.type||'application/octet-stream','x-ad':encodeURIComponent(f.name),'x-tarih':String(f.lastModified||Date.now())}}))}}catch(x){}
      return Response.redirect('./?paylasildi=1',303)})());
    return;
  }
  if(r.method!=='GET')return;
  const u=new URL(r.url);
  if(/youtube\.com|ytimg\.com|googlevideo\.com|googleapis\.com/.test(u.hostname))return; /* her zaman ağdan */
  if(r.mode==='navigate'){
    /* Önce ağ (güncelleme gelsin); 3 sn'de yanıt yoksa önbellek: internetsiz açılış takılmaz. Geç gelen yanıt yine önbelleğe yazılır. */
    const net=fetch(r.url,{cache:'no-cache',credentials:'same-origin'}).then(res=>{if(res&&res.ok){const c=res.clone();caches.open(V).then(x=>x.put('index.html',c))}return res});
    e.respondWith(Promise.race([net,new Promise((_,rej)=>setTimeout(rej,3000))]).catch(()=>caches.match('index.html',{cacheName:V}).then(h=>h||net)));
    return;
  }
  e.respondWith(caches.match(r).then(hit=>{
    const net=fetch(r).then(res=>{if(res&&(res.ok||res.type==='opaque')){const c=res.clone();caches.open(V).then(x=>x.put(r,c))}return res}).catch(()=>hit);
    return hit||net;
  }));
});
