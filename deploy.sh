#!/bin/bash

set -e  # Exit immediately if a command exits with a non-zero status

helm upgrade --install common-config ./fitplan-helm/charts/common-config --wait
helm upgrade --install configserver ./fitplan-helm/charts/configserver --wait
helm upgrade --install mysql ./fitplan-helm/charts/mysql --wait
helm upgrade --install mongodb ./fitplan-helm/charts/mongodb --wait
helm upgrade --install keycloak-mysql ./fitplan-helm/charts/keycloak-mysql --wait
helm upgrade --install zookeeper ./fitplan-helm/charts/zookeeper --wait
helm upgrade --install broker ./fitplan-helm/charts/broker --wait
helm upgrade --install kafka-ui ./fitplan-helm/charts/kafka-ui --wait
helm upgrade --install schema-registry ./fitplan-helm/charts/schema-registry --wait
helm upgrade --install keycloak ./fitplan-helm/charts/keycloak --wait
helm upgrade --install prometheus ./fitplan-helm/charts/prometheus --wait
helm upgrade --install loki ./fitplan-helm/charts/loki --wait
helm upgrade --install tempo ./fitplan-helm/charts/tempo --wait
helm upgrade --install grafana ./fitplan-helm/charts/grafana --wait
helm upgrade --install exercise ./fitplan-helm/charts/exercise --wait
helm upgrade --install fitplan-ingress ./fitplan-helm/charts/fitplan-ingress --wait
helm upgrade --install fitplanfrontend ./fitplan-helm/charts/fitplanfrontend --wait
helm upgrade --install gateway ./fitplan-helm/charts/gateway --wait
helm upgrade --install notification ./fitplan-helm/charts/notification --wait
helm upgrade --install validation ./fitplan-helm/charts/validation --wait
helm upgrade --install workout ./fitplan-helm/charts/workout --wait

echo "Deployment completed successfully!"
