# FitPlan - Tehnična dokumentacija

## Uvod
FitPlan je aplikacija za načrtovanje fitnes vadb in beleženje napredka. Namen dokumentacije je zagotoviti podroben pregled arhitekture, uporabljenih tehnologij in postopkov implementacije.


## Arhitektura
### Pregled
Aplikacija temelji na mikrostoritveni arhitekturi:
- **Frontend mikrostoritev**: Angular + TypeScript za uporabniški vmesnik.
- **Gateway storitev**: Preusmerja zahteve do ustreznih mikrostoritev.
- **Workout mikrostoritev**: Upravljanje vadbenih načrtov in komunikacija z drugimi storitvami.
- **Notification mikrostoritev**: Pošiljanje e-poštnih obvestil.
- **Validation mikrostoritev**: Preverjanje skladnosti vadbenih načrtov.
- **AI povratne informacije**: Komunikacija z modelom za nasvete o vadbi.


## Uporabljene tehnologije
- **Java & Spring Boot**: Implementacija zalednega sistema.
- **Angular**: Razvoj uporabniškega vmesnika.
- **MySQL in MongoDB**: Shranjevanje relacijskih in NoSQL podatkov.
- **Kubernetes (Kind)**: Upravljanje in orkestracija storitev.
- **Kafka**: Asinhrona komunikacija med mikrostoritvami.
- **Docker**: Kontejnerizacija aplikacije.
- **GitHub Actions**: CI/CD avtomatizacija.


## Funkcionalnosti
### Ključne funkcionalnosti
1. **Kreacija vaj:** Uporabniki lahko dodajo vaje z opisom in kategorijami.
2. **Dodajanje načrtov:** Povezovanje več vaj v vadbeni načrt.
3. **Obvestila:** Pošiljanje e-pošte o zaključkih vadb.
4. **AI povratne informacije:** Nasveti za izboljšanje vadbenih načrtov.


## Namestitev

### Klonirajte repozitorij projekta:

git clone https://github.com/FitPlanGiN/FitPlan
cd FitPlan/k8s/kind

### Ustvarjanje Kubernetes gruče

Ustvarite Kind Kubernetes gručo z uporabo konfiguracijske datoteke:
kind create cluster --name microservices --config kind-config.yaml

Nato izberite kontekst za delo z novo ustvarjeno gručo:
kubectl config use-context kind-microservices

### Uvedba manifestov na gručo

cd ../manifests
kubectl apply -f infrastructure
kubectl apply -f applications

Opomba: Če mikrostoritve ostanejo zamrznjene v initcontainer, jih ponovno zaženite:

kubectl delete deployment ime_poda
kubectl apply -f applications

### Dodajanje zapisa v hosts datoteko
Za pravilno delovanje aplikacije dodajte naslednji zapis v datoteko /etc/hosts (ali ustrezno datoteko za vaš OS):

127.0.0.1 keycloak.default.svc.cluster.local
ipconfig /flushdns

### Port forwarding (obvezen)

kubectl port-forward svc/fitplanfrontend 4200:80
kubectl port-forward svc/keycloak 8080:8080
kubectl port-forward svc/gateway 9000:9000

### Vpogled v metrike in beleženje dnevnikov

kubectl port-forward svc/grafana 3000:3000

### Izbris gruče

kubectl delete deployments --all
kind delete cluster
