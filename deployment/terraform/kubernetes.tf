variable "recipe_backend_image_tag" {
  nullable = false
}

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