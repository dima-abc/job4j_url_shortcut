echo "!!!!!!!!!!!delete spring-deployment!!!!!!!!!!"
kubectl delete -f spring-boot-deployment.yml
sleep 10
kubectl get pods
echo "!!!!!!!!!!!delete postgresdb-deployment!!!!!!!!!!"
kubectl delete -f postgresdb-deployment.yml
sleep 10
kubectl get pods
echo "!!!!!!!!!!!delete spring-deployment ConfigMap!!!!!!!!!!"
kubectl delete -f postgresdb-configmap.yml
sleep 3
echo "!!!!!!!!!!!delete spring-deployment Secret!!!!!!!!!!"
kubectl delete -f postgresdb-secret.yml
