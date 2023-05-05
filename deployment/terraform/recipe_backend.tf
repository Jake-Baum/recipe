locals {
  backend_container_port = 8080
}

resource "kubernetes_deployment" "recipe_backend" {
  metadata {
    name   = "recipe-backend"
    labels = {
      App = "RecipeBackend"
    }
  }

  spec {
    replicas = 1
    selector {
      match_labels = {
        App = "RecipeBackend"
      }
    }

    template {
      metadata {
        labels = {
          App = "RecipeBackend"
        }
      }
      spec {
        container {
          image = "europe-west2-docker.pkg.dev/recipy-382818/docker-repository/recipe-backend:${var.recipe_backend_image_tag}"
          name  = "recipe-backend"

          env {
            name  = "DB_USERNAME"
            value = var.database_username
          }
          env {
            name  = "DB_PASSWORD"
            value = var.database_password
          }
          env {
            name  = "DB_HOST"
            value = kubernetes_service.recipe_database.metadata[0].name
          }
          env {
            name  = "DB_PORT"
            value = local.database_container_port
          }

          port {
            container_port = local.backend_container_port
          }

          resources {
            limits = {
              cpu    = "0.5"
              memory = "512Mi"
            }

            requests = {
              cpu    = "100m"
              memory = "50Mi"
            }
          }
        }
      }
    }
  }
}

resource "kubernetes_service" "recipe-backend" {
  metadata {
    name = "recipe-backend-service"
  }

  spec {
    selector = {
      App = kubernetes_deployment.recipe_backend.spec.0.template.0.metadata[0].labels.App
    }

    port {
      port        = local.backend_container_port
      target_port = local.backend_container_port
    }

    type = "LoadBalancer"
  }
}