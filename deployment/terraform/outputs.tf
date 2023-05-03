output "region" {
  value = var.region
}

output "project_id" {
  value = var.project_id
}

output "kubernetes_cluster_name" {
  value = google_container_cluster.primary.name
}

output "kubernetes_cluster_host" {
  value = google_container_cluster.primary.endpoint
}

output "load_balancer_ip" {
  value = kubernetes_service.recipe-backend.status.0.load_balancer.0.ingress.0.ip
}