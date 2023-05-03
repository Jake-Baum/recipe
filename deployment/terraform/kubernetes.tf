data "google_client_config" "default" {}

data "google_container_cluster" "recipe_cluster" {
  name     = google_container_cluster.primary.name
  location = google_container_cluster.primary.location
}

provider "google" {
  project = var.project_id
  region  = var.region
}

provider "kubernetes" {
  host = google_container_cluster.primary.endpoint

  token                  = data.google_client_config.default.access_token
  cluster_ca_certificate = base64decode(data.google_container_cluster.recipe_cluster.master_auth[0].cluster_ca_certificate)
}

resource "kubernetes_deployment" "recipe-backend" {
  metadata {
    name   = "recipe-backend"
    labels = {
      App = "RecipeBackend"
    }
  }

  spec {
    replicas = 2
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
          image = "europe-west2-docker.pkg.dev/recipy-382818/docker-repository/recipe-backend@sha256:64ffaaaf9838ab5726a2e61090d46018b95f4df554ca42eee6b2c8a3c5965e26"
          name  = "recipe-backend"

          port {
            container_port = 8080
          }

          resources {
            limits = {
              cpu    = "0.5"
              memory = "512Mi"
            }

            requests = {
              cpu    = "250m"
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
    name = "recipe-backend"
  }

  spec {
    selector = {
      App = kubernetes_deployment.recipe-backend.spec.0.template.0.metadata[0].labels.App
    }

    port {
      port        = 8080
      target_port = 8080
    }

    type = "LoadBalancer"
  }
}