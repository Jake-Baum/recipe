variable "database_username" {
  default = "admin"
}

variable "database_password" {
  nullable  = false
  sensitive = true
}

locals {
  database_container_port = 27017
}

resource "kubernetes_deployment" "recipe_database" {

  metadata {
    name   = "recipe-database"
    labels = {
      App = "RecipeDatabase"
    }
  }

  spec {
    replicas = 1
    selector {
      match_labels = {
        App = "RecipeDatabase"
      }
    }

    template {
      metadata {
        labels = {
          App = "RecipeDatabase"
        }
      }
      spec {
        container {
          image = "mongo:6.0.5"
          name  = "recipe-database"

          env {
            name  = "MONGO_INITDB_ROOT_USERNAME"
            value = var.database_username
          }
          env {
            name  = "MONGO_INITDB_ROOT_PASSWORD"
            value = var.database_password
          }
          env {
            name  = "MONGO_INITDB_DATABASE"
            value = "recipe"
          }

          volume_mount {
            mount_path = "/data/db"
            name       = "mongodb-persistent-storage"
          }

          port {
            container_port = local.database_container_port
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

        volume {
          name = "mongodb-persistent-storage"
          persistent_volume_claim {
            claim_name = kubernetes_persistent_volume_claim.database_pvc.metadata[0].name
          }
        }
      }
    }
  }
}

resource "kubernetes_service" "recipe_database" {
  metadata {
    name = "recipe-database-service"
  }

  spec {
    selector = {
      App = kubernetes_deployment.recipe_database.spec.0.template.0.metadata[0].labels.App
    }

    port {
      port        = local.database_container_port
      target_port = local.database_container_port
    }

    type = "ClusterIP"
  }
}

resource "kubernetes_persistent_volume_claim" "database_pvc" {

  metadata {
    name = "recipe-database-pvc"
  }

  spec {
    access_modes = ["ReadWriteOnce"]

    resources {
      requests = {
        storage : "500Mi"
      }
    }
  }

  wait_until_bound = false
}