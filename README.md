# FitPlan - Tehnična dokumentacija

## Uvod
FitPlan je aplikacija za načrtovanje fitnes vadb in beleženje napredka. Namen dokumentacije je zagotoviti podroben pregled arhitekture, uporabljenih tehnologij in postopkov implementacije.


## Arhitektura
### Pregled
Aplikacija temelji na mikrostoritveni "cloud native" arhitekturi:
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


# Installation Guide

## Clone Repository
```bash
git clone https://github.com/FitPlanGiN/FitPlan
cd FitPlan/k8s/kind
```

## Creating Kubernetes Cluster
Create a Kind Kubernetes cluster using the configuration file:
```bash
kind create cluster --name microservices --config kind-config.yaml
```

Select the context for working with the newly created cluster:
```bash
kubectl config use-context kind-microservices
```

## Deploy Manifests to Cluster
```bash
cd ../manifests
kubectl apply -f infrastructure
kubectl apply -f applications
```

> **Note**: If microservices remain frozen in initcontainer, restart them:
```bash
kubectl delete deployment pod_name
kubectl apply -f applications
```

## Add Hosts File Entry
For proper application functionality, add the following entry to your `/etc/hosts` file (or equivalent for your OS):
```
127.0.0.1 keycloak.default.svc.cluster.local
```

Then flush DNS cache:
```bash
ipconfig /flushdns
```

## Port Forwarding (Required)
```bash
kubectl port-forward svc/fitplanfrontend 4200:80
kubectl port-forward svc/keycloak 8080:8080
kubectl port-forward svc/gateway 9000:9000
```

## Metrics and Logging
```bash
kubectl port-forward svc/grafana 3000:3000
```

## Delete Cluster
```bash
kubectl delete deployments --all
kind delete cluster
```
