stari workflow:

name: SpringBoot CI/CD Pipeline

on:
  push:
    branches: [ "main" ]
  workflow_dispatch:

jobs:
  build:

    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v4
    - name: Set up JDK 21
      uses: actions/setup-java@v4
      with:
        java-version: '21'
        distribution: 'temurin'
        cache: maven

    - name: Build and push images using spring-boot:build-image
      env:
        DOCKER_PASSWORD: ${{ secrets.DOCKER_PASSWORD }}
      run: |
        mvn clean spring-boot:build-image -DskipTests

    - name: Building and pushing fitplanfrontend
      run: |
        docker build --no-cache -t ${{ secrets.DOCKER_USERNAME }}/fitplanfrontend:latest .
        echo ${{ secrets.DOCKER_PASSWORD }} | docker login -u ${{ secrets.DOCKER_USERNAME }} --password-stdin
        docker push ${{ secrets.DOCKER_USERNAME }}/fitplanfrontend:latest
      working-directory: fitplanfrontend


    # Google Cloud login using Service Account Key
    - name: Google Cloud authentication
      uses: google-github-actions/auth@v2
      with:
        credentials_json: ${{ secrets.GCP_SERVICE_ACCOUNT_KEY }}

    # Get GKE credentials
    - name: Get GKE credentials
      uses: google-github-actions/get-gke-credentials@v2
      with:
        cluster_name: 'fitplan'
        location: 'europe-central2-a'

    # Set Kubernetes context
    - name: Set up kubectl
      run: |
        kubectl config use-context gke_${{ secrets.PROJECT_ID }}_europe-central2-a_fitplan

    # Deploy manifests to GKE
    #- name: Deploy manifests to GKE
    #  run: |
     #   kubectl apply -f ./k8s/manifests/infrastructure
      #  kubectl apply -f ./k8s/manifests/applications

    # Cleanup existing PVC and PV (if any)
    #- name: Cleanup MySQL PVC and PV
     # run: |
      #  kubectl delete pvc mysql-pvc --ignore-not-found
       # kubectl delete pv mysql-pv --ignore-not-found

    # Create a Persistent Disk for MySQL
    #- name: Create GCE Persistent Disk for MySQL
     # run: |
      #  gcloud compute disks create mysql-disk --size=10Gi --zone=europe-central2-a

    - name: Check if MySQL disk exists
      run: |
        if ! gcloud compute disks describe mysql-disk --zone=europe-central2-a > /dev/null 2>&1; then
          gcloud compute disks create mysql-disk --size=10Gi --zone=europe-central2-a
        else
          echo "MySQL disk already exists, skipping creation."
        fi

    #- name: Delete existing MySQL disk (if any)
      #run: |
      #  if gcloud compute disks describe mysql-disk --zone=europe-central2-a > /dev/null 2>&1; then
      #    gcloud compute disks delete mysql-disk --zone=europe-central2-a --quiet
      #  else
      #    echo "MySQL disk not found, skipping deletion."
      #  fi

    
    #- name: Create MySQL disk
    #  run: |
     #   gcloud compute disks create mysql-disk --size=10Gi --zone=europe-central2-a

    
    
    - name: Deploy MySQL to GKE
      run: |
        kubectl apply -f ./k8s/manifests/infrastructure/mysql.yaml
        kubectl wait --for=condition=ready pod --selector=app=mysql --namespace=default --timeout=300s

    - name: Deploy MongoDB to GKE
      run: |
        kubectl apply -f ./k8s/manifests/infrastructure/mongodb.yml
        kubectl wait --for=condition=ready pod --selector=app=mongodb --namespace=default --timeout=300s

    - name: Deploy Keycloak MySQL to GKE
      run: |
        kubectl apply -f ./k8s/manifests/infrastructure/keycloak-mysql.yml
        kubectl wait --for=condition=ready pod --selector=app=keycloak-mysql --namespace=default --timeout=300s

    - name: Deploy Zookeeper to GKE
      run: |
        kubectl apply -f ./k8s/manifests/infrastructure/zookeeper.yml
        kubectl wait --for=condition=ready pod --selector=app=zookeeper --namespace=default --timeout=300s

    - name: Deploy Kafka broker to GKE
      run: |
        kubectl apply -f ./k8s/manifests/infrastructure/kafka.yml
        kubectl wait --for=condition=ready pod --selector=app=broker --namespace=default --timeout=300s

    - name: Deploy Kafka UI to GKE
      run: |
        kubectl apply -f ./k8s/manifests/infrastructure/kafka-ui.yml
        kubectl wait --for=condition=ready pod --selector=app=kafka-ui --namespace=default --timeout=300s
    
    - name: Deploy Schema Registry to GKE
      run: |
        kubectl apply -f ./k8s/manifests/infrastructure/schema-registry.yml
        kubectl wait --for=condition=ready pod --selector=app=schema-registry --namespace=default --timeout=300s

    - name: Deploy Keycloak to GKE
      run: |
        kubectl apply -f ./k8s/manifests/infrastructure/keycloak.yml
        kubectl wait --for=condition=ready pod --selector=app=keycloak --namespace=default --timeout=300s

    - name: Deploy Prometheus to GKE
      run: |
        kubectl apply -f ./k8s/manifests/infrastructure/prometheus.yml
        kubectl wait --for=condition=ready pod --selector=app=prometheus --namespace=default --timeout=300s

    - name: Deploy Loki to GKE
      run: |
        kubectl apply -f ./k8s/manifests/infrastructure/loki.yml
        kubectl wait --for=condition=ready pod --selector=app=loki --namespace=default --timeout=300s

    
    - name: Deploy Tempo to GKE
      run: |
        kubectl apply -f ./k8s/manifests/infrastructure/tempo.yml
        kubectl wait --for=condition=ready pod --selector=app=tempo --namespace=default --timeout=300s

    - name: Deploy Grafana to GKE
      run: |
        kubectl apply -f ./k8s/manifests/infrastructure/grafana.yml
        kubectl wait --for=condition=ready pod --selector=app=grafana --namespace=default --timeout=300s


      
    # Deploy infrastructure manifests first
    #- name: Deploy infrastructure to GKE
     # run: |
      #  kubectl apply -f ./k8s/manifests/infrastructure
       # kubectl wait --for=condition=ready pod --all --namespace=default --timeout=1000s
    
    # Deploy config server (must be deployed first in applications)
    - name: Deploy Config Server to GKE
      run: |
        kubectl apply -f ./k8s/manifests/applications/configserver.yml
        kubectl wait --for=condition=ready pod --selector=app=configserver --namespace=default --timeout=600s
    
    # Deploy the rest of the applications
    - name: Deploy remaining applications to GKE
      run: |
        kubectl apply -f ./k8s/manifests/applications
